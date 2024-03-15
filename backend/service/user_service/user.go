package user_service

import (
	"demo/constant"
	"demo/db/operate"
	"demo/models"
	"demo/pkg/utils"
	"demo/service/auth_service/permission"
	"demo/types"
	"errors"

	"gorm.io/gorm"
)

func AddUser(db *gorm.DB, userName, userEmail, password string, roleSlugList []string) error {
	userInfo, err := operate.ModelsDB.SysUser.QueryByEmail(db, userEmail)
	if err != nil {
		return err
	}
	if userInfo != nil {
		return errors.New(constant.UserEmailIsExist)
	}

	salt := "1234"
	newUserInfo := models.User{
		Username: userName,
		Email:    userEmail,
		Password: utils.Dict.StringCon.MD5(password + salt),
		Salt:     salt,
	}
	for _, roleSlug := range roleSlugList {
		roleInfo, err := operate.ModelsDB.SysRole.QueryBySlug(db, roleSlug)
		if err != nil {
			return err
		}
		if roleInfo != nil {
			newUserInfo.RoleId = roleInfo.ID
		}
	}
	err = operate.ModelsDB.SysUser.Add(db, &newUserInfo)
	if err != nil {
		return err
	}
	return nil
}

func QueryUserList(db *gorm.DB) ([]*types.RetUserInfo, error) {
	//查询用户信息
	userList, err := operate.ModelsDB.SysUser.Query(db)
	if err != nil {
		return nil, err
	}

	//查询角色信息
	roleList, err := operate.ModelsDB.SysRole.Query(db)
	if err != nil {
		return nil, err
	}

	var retList []*types.RetUserInfo
	count := 0
	for _, row := range userList {
		count = count + 1
		retInfo := types.RetUserInfo{
			Name:              row.Username,
			Email:             row.Email,
			IsInterpretPerson: utils.Dict.Convert.BoolIntToString(row.IsInterpretPerson),
			Number:            count,
		}
		roleInfo1 := permission.FindRoleInfoByRoleId(roleList, row.RoleId)
		if roleInfo1 != nil {
			retInfo.RoleName = roleInfo1.Name
			retInfo.RoleSlug = roleInfo1.Slug
			retInfo.RoleDescription = roleInfo1.Description
		}
		retList = append(retList, &retInfo)
	}
	return retList, nil
}
