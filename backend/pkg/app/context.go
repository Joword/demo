package app

import (
	"demo/constant"
	logging "demo/log"
	types "demo/types"
	"net/http"

	"github.com/astaxie/beego/validation"
	"github.com/gin-gonic/gin"
)

type Gin struct {
	C *gin.Context
}

func (g *Gin) Response(statusCode int, errCode int, data interface{}) {
	g.C.JSON(statusCode, types.ResponseType{
		Code: errCode,
		Msg:  constant.GetMsg(errCode),
		Data: data,
	})
}

func MarkErrors(errors []*validation.Error) {
	for _, err := range errors {
		logging.Info(err.Key, err.Message)
	}
}

func BindValid(ctx *gin.Context, form interface{}) (int, int) {
	err := ctx.Bind(form)
	if err != nil {
		return http.StatusBadRequest, constant.INVALID_PARAMS
	}
	valid := validation.Validation{}
	check, err := valid.Valid(form)
	if err != nil {
		return http.StatusInternalServerError, constant.ERROR
	}
	if !check {
		MarkErrors(valid.Errors)
		return http.StatusBadRequest, constant.INVALID_PARAMS
	}
	return http.StatusOK, constant.SUCCESS
}
