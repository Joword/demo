package user

import (
	"demo/constant"
	dbTools "demo/db"
	"demo/pkg/result"
	"demo/service/auth_service"
	"demo/types"
	"fmt"

	"github.com/gin-gonic/gin"
)

func addPermissionInfo(c *gin.Context) {
	// 接收参数
	payload := types.ReqAddPermissionInfo{}
	if err := c.Bind(&payload); err != nil {
		result.Failure(c, gin.H{"msg": constant.SystemError}, "fail")
		return
	}
	fmt.Printf("权限增加参数：%v\n", payload)
	if payload.PerName == "" {
		result.Failure(c, gin.H{"msg": "权限名称为空"}, "fail")
		return
	}
	if payload.PerSlug == "" {
		result.Failure(c, gin.H{"msg": "权限硬编码为空"}, "fail")
		return
	}
	if payload.PerHtmlName == "" {
		result.Failure(c, gin.H{"msg": "权限页面模块名为空"}, "fail")
		return
	}
	db := dbTools.Get()
	err := auth_service.AddPermission(db, payload.PerName, payload.PerSlug, payload.PerDescription, payload.PerHtmlName)
	defer dbTools.DBClose(db)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	result.Success(c, gin.H{"msg": "操作成功"}, "success")
	return
}
func queryPermissionList(c *gin.Context) {

	db := dbTools.Get()
	retList, err := auth_service.QueryPermissionList(db)
	defer dbTools.DBClose(db)
	if err != nil {
		result.Failure(c, gin.H{"msg": err.Error()}, "fail")
		return
	}
	result.Success(c, gin.H{"msg": "操作成功", "retList": retList}, "success")
}
