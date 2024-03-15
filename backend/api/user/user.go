package user

import (
	"demo/constant"
	dbTools "demo/db"
	"demo/pkg/result"
	"demo/service/auth_service"
	"demo/service/auth_service/permission"
	"demo/types"
	"encoding/base64"
	"fmt"

	"github.com/gin-gonic/gin"
	logging "github.com/sirupsen/logrus"
)

func login(c *gin.Context) {
	// 接收参数
	payload := types.ReqLogin{}
	if err := c.Bind(&payload); err != nil {
		logging.WithFields(logging.Fields{
			"error": err.Error(),
		}).Error("user.user#payload")
		result.Failure(c, gin.H{"msg": "查询参数错误"}, "fail")
		return
	}
	fmt.Printf("登录用户参数：%v\n", payload)

	if payload.Email == "" {
		result.Failure(c, gin.H{"msg": constant.UserEmailNotInput}, "fail")
		return
	}
	if payload.Password == "" {
		result.Failure(c, gin.H{"msg": constant.UserPasswordNotInput}, "fail")
		return
	}
	decoded, _ := base64.StdEncoding.DecodeString(payload.Password)
	password := string(decoded)

	db := dbTools.Get()
	user, err := auth_service.Login(db, payload.Email, password)
	if err != nil {
		defer dbTools.DBClose(db)
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}

	//发放token
	token, err := auth_service.GetToken(user)
	if err != nil {
		result.Failure(c, gin.H{"msg": "请联系管理员"}, "fail")
		logging.WithFields(logging.Fields{
			"msg": "token发送失败",
		}).Error("login")
		return
	}
	result.Success(c, gin.H{
		"msg":   "登录成功",
		"token": token,
		"email": payload.Email,
	}, "success")
	return
}

func updatePassword(c *gin.Context) {
	payload := types.ReqUserData{}
	if err := c.Bind(&payload); err != nil {
		logging.WithFields(logging.Fields{
			"error": err.Error(),
		}).Error("api.user.password.updatePassword#payload")
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	fmt.Printf("获取编辑保存数据：%v", payload)

	oldPassword, _ := base64.StdEncoding.DecodeString(payload.OldPassword)
	newPassword1, _ := base64.StdEncoding.DecodeString(payload.NewPassword1)
	newPassword2, _ := base64.StdEncoding.DecodeString(payload.NewPassword2)
	//oldPassword := string(oldPassword)
	fmt.Printf("oldPassword:%v	newPassword1:%v	newPassword2:%v", string(oldPassword), string(newPassword1), string(newPassword2))
	if payload.Email == "" {
		result.Failure(c, gin.H{"msg": constant.UserEmailNotInput}, "fail")
		return
	}
	if string(oldPassword) == "" {
		result.Failure(c, gin.H{"msg": constant.OldPasswordNotInput}, "fail")
		return
	}
	if string(newPassword1) == "" {
		result.Failure(c, gin.H{"msg": constant.NewPassword1NotInput}, "fail")
		return
	}
	if string(newPassword2) == "" {
		result.Failure(c, gin.H{"msg": constant.NewPassword2NotInput}, "fail")
		return
	}
	if string(newPassword1) != string(newPassword2) {
		result.Failure(c, gin.H{"msg": constant.NewPassword2NotEqualNewPassword2}, "fail")
		return
	}
	db := dbTools.Get()
	err := auth_service.UpdatePassword(db, payload.Email, string(oldPassword), string(newPassword1))
	if err != nil {
		defer dbTools.DBClose(db)
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	defer dbTools.DBClose(db)
	result.Success(c, gin.H{"msg": constant.PasswordUpdateSuccess}, "success")
	return
}

func addUser(c *gin.Context) {
	payload := types.ReqUserRole{}
	if err := c.Bind(&payload); err != nil {
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	fmt.Printf("获取用户新增信息:%v", payload)
	if payload.UserName == "" {
		result.Failure(c, gin.H{"msg": constant.UserNameIsNull}, "fail")
		return
	}
	if payload.UserEmail == "" {
		result.Failure(c, gin.H{"msg": constant.UserEmailIsNull}, "fail")
		return
	}
	if payload.UserPassword1 == "" {
		result.Failure(c, gin.H{"msg": constant.UserPasswordIsNull}, "fail")
		return
	}
	if payload.UserPassword1 != payload.UserPassword2 {
		result.Failure(c, gin.H{"msg": constant.UserPasswordNotEqual}, "fail")
		return
	}
	if len(payload.UserRoleSlug) > 1 {
		result.Failure(c, gin.H{"msg": constant.UserOnlyOneRole}, "fail")
		return
	}
	db := dbTools.Get()
	err := permission.AddUser(db, payload.UserName, payload.UserEmail, payload.UserPassword1, payload.UserRoleSlug)

	defer dbTools.DBClose(db)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	result.Success(c, gin.H{"msg": constant.OperateSuccess}, "success")
	return
}

func queryUserList(c *gin.Context) {
	db := dbTools.Get()
	userList, err := permission.QueryUserList(db)
	defer dbTools.DBClose(db)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
	}

	result.Success(c, gin.H{"msg": "操作成功", "userList": userList}, "success")
	return
}

func queryUserDetail(c *gin.Context) {
	payload := types.ReqUserEmail{}
	if err := c.Bind(&payload); err != nil {
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	fmt.Printf("获取用户更新信息:%v", payload)
	db := dbTools.Get()
	retUserRoleInfo, err := permission.QueryUserDetail(db, payload.UserEmail)
	defer dbTools.DBClose(db)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	result.Success(c, gin.H{"msg": constant.OperateSuccess, "retUserRoleInfo": retUserRoleInfo}, "success")
	return
}

func updateUser(c *gin.Context) {
	payload := types.ReqUserRole{}
	if err := c.Bind(&payload); err != nil {
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	fmt.Printf("获取用户更新信息:%v", payload)
	if payload.UserName == "" {
		result.Failure(c, gin.H{"msg": constant.UserNameIsNull}, "fail")
		return
	}
	if payload.UserEmail == "" {
		result.Failure(c, gin.H{"msg": constant.UserEmailIsNull}, "fail")
		return
	}
	if len(payload.UserRoleSlug) > 1 {
		result.Failure(c, gin.H{"msg": constant.UserOnlyOneRole}, "fail")
		return
	}

	db := dbTools.Get()
	err := permission.UpdateUser(db, payload.UserName, payload.UserEmail, payload.IsInterpretPerson, payload.UserRoleSlug)

	defer dbTools.DBClose(db)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	result.Success(c, gin.H{"msg": constant.OperateSuccess}, "success")
	return
}

func deletedUser(c *gin.Context) {
	payload := types.ReqDeletedUserInfo{}
	if err := c.Bind(&payload); err != nil {
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	fmt.Printf("获取删除邮箱信息:%v", payload)

	db := dbTools.Get()
	err := permission.DeletedUser(db, payload.DeletedUserEmailList)
	defer dbTools.DBClose(db)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	result.Success(c, gin.H{"msg": constant.OperateSuccess}, "success")
	return
}

func currentUserName(c *gin.Context) {
	userInfo, existUser := c.Get("user")
	if existUser == false {
		logging.WithFields(logging.Fields{
			"existUser": existUser,
		}).Error("pkg.permissionControl.currentUserName#userInfo&existUser")
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	result.Success(c, gin.H{"msg": constant.OperateSuccess, "currentUserName": userInfo.(*types.User).Username}, "success")
	return
}
