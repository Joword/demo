package middleware

import (
	"demo/constant"
	"demo/pkg/utils"
	"net/http"

	"github.com/dgrijalva/jwt-go"
	"github.com/gin-gonic/gin"
)

func JWT() gin.HandlerFunc {
	return func(ctx *gin.Context) {
		var code int
		var data interface{}
		token := ctx.Query("token")

		if token == "" {
			code = constant.INVALID_PARAMS
		} else {
			_, err := utils.ParseToken(token)
			if err != nil {
				switch err.(*jwt.ValidationError).Errors {
				case jwt.ValidationErrorExpired:
					code = constant.ERROR_AUTH_CHECK_TOKEN_TIMEOUT
				default:
					code = constant.ERROR_AUTH_CHECK_TOKEN_FAIL
				}
			}
		}
		if code != constant.SUCCESS {
			ctx.JSON(http.StatusUnauthorized, gin.H{
				"code": code,
				"msg":  constant.GetMsg(code),
				"data": data,
			})
			ctx.Abort()
			return
		}
		ctx.Next()
	}
}
