package middleware

import (
	dbTools "demo/db"
	"demo/db/operate"
	"demo/pkg/utils"
	"demo/service/auth_service"
	"demo/service/auth_service/permission"
	"net/http"

	"github.com/gin-gonic/gin"
	logging "github.com/sirupsen/logrus"
)

const (
	AuthPage = "page"
	AuthAPI  = "api"
)

func Auth(authType string) func(*gin.Context) {
	if authType == AuthPage {
		return authPage
	}

	if authType == AuthAPI {
		return authAPI
	}

	return authPage
}

// authPage 验证用户
func authPage(c *gin.Context) {
	// Coding
	tokenString := c.GetHeader("Cookie")

	if tokenString == "" {
		c.Redirect(http.StatusTemporaryRedirect, "/login")
		logging.WithFields(logging.Fields{
			"err": "没有token",
		}).Error("services.middleware.auth.authPage#tokenString")
		c.Abort()
		return
	}
	_, tokenString = utils.Dict.StringCon.StringsIndex(tokenString, "token=", 6)

	token, claims, err := auth_service.ParseToken(tokenString)
	if err != nil {
		c.Redirect(http.StatusTemporaryRedirect, "/login")
		logging.WithFields(logging.Fields{
			"err": err.Error(),
		}).Error("services.middleware.auth.authPage#err")
		c.Abort()
		return
	}
	if !token.Valid {
		c.Redirect(http.StatusTemporaryRedirect, "/login")
		logging.WithFields(logging.Fields{
			"err": "token验证失败",
		}).Error("services.middleware.auth.authPage#token.Valid")
		c.Abort()
		return
	}
	db := dbTools.Get()
	//验证通过后获取claim中的userId
	user, err := operate.ModelsDB.SysUser.QueryById(db, claims.UserId)
	defer dbTools.DBClose(db)
	//用户不存在
	if err != nil {
		c.Redirect(http.StatusTemporaryRedirect, "/login")
		logging.WithFields(logging.Fields{
			"err": "用户不存在",
		}).Error("services.middleware.auth.authPage#user")
		c.Abort()
		return
	}

	//用户存在，将user的信息写入上下文
	c.Set("user", user)

	permissionList, _ := permission.QueryUserRolePermission(db, user.RoleId)
	c.Set("permissionList", permissionList)

	c.Next()
}

// authAPI 验证用户
func authAPI(c *gin.Context) {
	// Coding
	tokenString := c.GetHeader("Cookie")

	if tokenString == "" {
		c.JSON(http.StatusUnauthorized, gin.H{"code": 401, "msg": "未通过验证，请重新登录"})
		logging.WithFields(logging.Fields{
			"err": "没有token",
		}).Error("services.middleware.auth.authAPI#tokenString")
		c.Abort()
		return
	}

	_, tokenString = utils.Dict.StringCon.StringsIndex(tokenString, "token=", 6)

	token, claims, err := auth_service.ParseToken(tokenString)

	if err != nil || !token.Valid {
		c.JSON(http.StatusUnauthorized, gin.H{"code": 401, "msg": "未通过验证，请重新登录"})
		logging.WithFields(logging.Fields{
			"err": "token认证失败",
		}).Error("services.middleware.auth.authAPI#token&claims&err")
		c.Abort()
		return
	}
	db := dbTools.Get()
	//验证通过后获取claim中的userId
	user, err := operate.ModelsDB.SysUser.QueryById(db, claims.UserId)
	dbTools.DBClose(db)
	//用户不存在
	if err != nil {
		c.JSON(http.StatusUnauthorized, gin.H{"code": 401, "msg": "未通过验证，请重新登录"})
		logging.WithFields(logging.Fields{
			"err": "用户不存在",
		}).Error("services.middleware.auth.authAPI#user")
		c.Abort()
		return
	}

	//用户存在，将user的信息写入上下文
	c.Set("user", user)

	permissionList, _ := permission.QueryUserRolePermission(db, user.RoleId)
	c.Set("permissionList", permissionList)

	c.Next()
}
