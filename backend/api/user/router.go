package user

import (
	"demo/middleware"

	"github.com/gin-gonic/gin"
)

func Router(router *gin.RouterGroup) {
	router.POST("/login", login)
	router.POST("/updatePassword", updatePassword)
	router.GET("/currentUserName", middleware.Auth(middleware.AuthAPI), currentUserName)

	router.GET("queryPermissionList", middleware.Auth(middleware.AuthAPI), queryPermissionList)
	router.POST("addPermissionInfo", middleware.Auth(middleware.AuthAPI), addPermissionInfo)

	router.POST("addRole", middleware.Auth(middleware.AuthAPI), addRole)
	router.GET("queryRoleList", middleware.Auth(middleware.AuthAPI), queryRoleList)
	router.GET("queryRoleDetail", middleware.Auth(middleware.AuthAPI), queryRoleDetail)
	router.POST("updateRole", middleware.Auth(middleware.AuthAPI), updateRole)
	router.POST("deletedRole", middleware.Auth(middleware.AuthAPI), deletedRole)

	router.GET("queryUserRoleList", middleware.Auth(middleware.AuthAPI), queryUserRoleList)

	router.POST("addUser", middleware.Auth(middleware.AuthAPI), addUser)
	router.GET("queryUserList", middleware.Auth(middleware.AuthAPI), queryUserList)
	router.GET("queryUserDetail", middleware.Auth(middleware.AuthAPI), queryUserDetail)
	router.POST("updateUser", middleware.Auth(middleware.AuthAPI), updateUser)
	router.POST("deletedUser", middleware.Auth(middleware.AuthAPI), deletedUser)
}
