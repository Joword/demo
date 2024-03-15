package middleware

import (
	"bytes"
	logging "demo/log"

	"io/ioutil"
	"time"

	"github.com/gin-gonic/gin"
)

func LoggerToFile() gin.HandlerFunc {

	return func(ctx *gin.Context) {
		// 开始时间
		start := time.Now()

		// 请求报文
		var requestBody []byte
		if ctx.Request.Body != nil {
			var err error
			requestBody, err = ctx.GetRawData()
			if err != nil {
				logging.Warn(map[string]interface{}{"err": err.Error()}, "get http request body error")
			}
			ctx.Request.Body = ioutil.NopCloser(bytes.NewBuffer(requestBody))
		}

		// 处理请求
		ctx.Next()

		// 结束时间
		end := time.Now()

		logging.Info(map[string]interface{}{
			"statusCode": ctx.Writer.Status(),
			"cost":       float64(end.Sub(start).Nanoseconds()/1e4) / 100.0,
			"clientIp":   ctx.ClientIP(),
			"method":     ctx.Request.Method,
			"uri":        ctx.Request.RequestURI,
		})
	}
}
