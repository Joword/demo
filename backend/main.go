package main

import (
	setting "demo/conf"
	logging "demo/log"
	"demo/pkg/utils"
	"demo/routers"
	"fmt"
	"log"
	"net/http"

	"github.com/gin-gonic/gin"
)

func init() {
	setting.Setup()
	logging.Setup()
	utils.Setup()
	utils.RedisSetup()
}

func main() {
	gin.SetMode(setting.ServerSetting.RunMode)

	routerInit := routers.InitRouter()
	readTimeout := setting.ServerSetting.ReadTimeout
	writeTimeout := setting.ServerSetting.WriteTimeout
	endPoint := fmt.Sprintf("%d", setting.ServerSetting.HttpPort)
	maxHeaderBytes := 1 << 20

	server := &http.Server{
		Addr:           endPoint,
		Handler:        routerInit,
		ReadTimeout:    readTimeout,
		WriteTimeout:   writeTimeout,
		MaxHeaderBytes: maxHeaderBytes,
	}
	log.Printf("[INFO] server start %s", endPoint)
	server.ListenAndServe()
}
