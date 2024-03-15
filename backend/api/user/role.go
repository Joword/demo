package user

import (
	"demo/constant"
	dbTools "demo/db"
	"demo/pkg/result"
	"demo/service/auth_service/permission"
	"fmt"

	"github.com/gin-gonic/gin"
)

func queryUserRoleList(c *gin.Context) {
	db := dbTools.Get()
	retRoleList, err := permission.QueryUserRole(db)
	defer dbTools.DBClose(db)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	result.Success(c, gin.H{"msg": constant.OperateSuccess, "retRoleList": retRoleList}, "success")
	return
}

type reqDeletedRoleInfo struct {
	DeletedRoleSlugList []string `form:"deletedRoleSlugList"`
}

func deletedRole(c *gin.Context) {
	payload := reqDeletedRoleInfo{}
	if err := c.Bind(&payload); err != nil {
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	fmt.Printf("获取角色删除编码信息:%v", payload)
	db := dbTools.Get()
	err := permission.DeletedRole(db, payload.DeletedRoleSlugList)
	if err != nil {
		defer dbTools.DBClose(db)
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	defer dbTools.DBClose(db)
	result.Success(c, gin.H{"msg": constant.OperateSuccess}, "success")
	return
}

type reqRolePermission struct {
	RoleName           string   `form:"roleName"`
	RoleDescription    string   `form:"roleDescription"`
	RoleSlug           string   `form:"roleSlug"`
	PermissionSlugList []string `form:"permissionSlugList"`
}

func updateRole(c *gin.Context) {
	payload := reqRolePermission{}
	if err := c.Bind(&payload); err != nil {
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	fmt.Printf("获取角色更新信息:%v", payload)
	db := dbTools.Get()
	err := permission.UpdateRoleInfo(db, payload.RoleName, payload.RoleSlug, payload.RoleDescription, payload.PermissionSlugList)
	if err != nil {
		defer dbTools.DBClose(db)
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	defer dbTools.DBClose(db)
	result.Success(c, gin.H{"msg": constant.OperateSuccess}, "success")
	return
}

func addRole(c *gin.Context) {
	payload := reqRolePermission{}
	if err := c.Bind(&payload); err != nil {
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	fmt.Printf("获取角色新增信息:%v", payload)

	db := dbTools.Get()
	err := permission.AddRoleInfo(db, payload.RoleName, payload.RoleSlug, payload.RoleDescription)
	if err != nil {
		defer dbTools.DBClose(db)
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}

	err = permission.AddRolePermission(db, payload.RoleSlug, payload.PermissionSlugList)
	defer dbTools.DBClose(db)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}

	result.Success(c, gin.H{"msg": "操作成功"}, "success")
	return
}

func queryRoleList(c *gin.Context) {

	db := dbTools.Get()
	retRoleList, err := permission.QueryRoleList(db)
	defer dbTools.DBClose(db)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	result.Success(c, gin.H{"msg": constant.OperateSuccess, "retRoleList": retRoleList}, "success")
	return
}

type reqRoleSlug struct {
	RoleSlug string `form:"roleSlug"`
}

func queryRoleDetail(c *gin.Context) {
	payload := reqRoleSlug{}
	if err := c.Bind(&payload); err != nil {
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	fmt.Printf("获取角色修改查询硬编码信息:%v", payload)
	db := dbTools.Get()
	retRoleDetailInfo, err := permission.QueryRoleDetail(db, payload.RoleSlug)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	result.Success(c, gin.H{"msg": constant.OperateSuccess, "retRoleDetailInfo": retRoleDetailInfo}, "success")
	return
}
