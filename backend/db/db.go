package dbTools

import (
	"fmt"
	"os"
	"time"

	logging "github.com/sirupsen/logrus"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

var db *gorm.DB

func Get() *gorm.DB {
	if db != nil {
		return db
	}
	uri := fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?multiStatements=true&parseTime=true&loc=Local", os.Getenv("DB_USERNAME"), os.Getenv("DB_PASSWORD"), os.Getenv("DB_HOST"), os.Getenv("DB_PORT"), os.Getenv("DB_NAME"))

	db, err := gorm.Open(mysql.Open(uri), &gorm.Config{})
	if err != nil {
		logging.WithFields(logging.Fields{
			"error": err.Error(),
			"uri":   uri,
		}).Fatal("db.New func called")
	}

	if os.Getenv("APP_MODE") == "development" {
		return db.Debug()
	}
	pkDB, err := db.DB()
	pkDB.SetMaxIdleConns(50)
	pkDB.SetMaxOpenConns(50)
	pkDB.SetConnMaxLifetime(time.Minute)

	return db
}

func DBClose(db *gorm.DB) {
	if sqlDB, err := db.DB(); err != nil {
		logging.WithFields(logging.Fields{"error": err.Error()}).Error("DBClose")
	} else {
		if err = sqlDB.Close(); err != nil {
			logging.WithFields(logging.Fields{"error": err.Error()}).Error("DBClose")
		}
	}
	return
}
