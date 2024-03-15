package result

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func Response(c *gin.Context, httpStatus, code int, data gin.H, msg string) {
	c.JSON(httpStatus, gin.H{"code": code, "data": data, "msg": msg})
}

func Success(c *gin.Context, data gin.H, msg string) {
	Response(c, http.StatusOK, 200, data, msg)
}

func Failure(c *gin.Context, data gin.H, msg string) {
	Response(c, http.StatusOK, 400, data, msg)
}
