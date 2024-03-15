package api

import (
	"demo/constant"
	"demo/db/operate"
	"demo/pkg/app"
	"demo/pkg/utils"
	"demo/types"
	"net/http"

	"github.com/astaxie/beego/validation"
	"github.com/gin-gonic/gin"
)

func GetAuth(c *gin.Context) {
	appG := app.Gin{C: c}
	valid := validation.Validation{}

	username := c.PostForm("username")
	password := c.PostForm("password")

	a := types.Auth{Username: username, Password: password}
	ok, _ := valid.Valid(&a)

	if !ok {
		app.MarkErrors(valid.Errors)
		appG.Response(http.StatusBadRequest, constant.INVALID_PARAMS, nil)
		return
	}

	authService := types.Auth{Username: username, Password: password}
	isExist, err := operate.CheckAuth(authService.Username, authService.Password)
	if err != nil {
		appG.Response(http.StatusInternalServerError, constant.ERROR_AUTH_CHECK_TOKEN_FAIL, nil)
		return
	}

	if !isExist {
		appG.Response(http.StatusUnauthorized, constant.ERROR_AUTH, nil)
		return
	}

	token, err := utils.GenerateToken(username, password)
	if err != nil {
		appG.Response(http.StatusInternalServerError, constant.ERROR_AUTH_TOKEN, nil)
		return
	}

	appG.Response(http.StatusOK, constant.SUCCESS, map[string]string{
		"token": token,
	})
}
