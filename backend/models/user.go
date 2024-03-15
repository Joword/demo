package models

import (
	"demo/pkg/utils"
	"time"
)

type User struct {
	BaseModel
	Username          string
	Email             string
	Password          string
	Salt              string
	RoleId            int
	Status            int
	LoginTimes        int
	LoginTime         time.Time
	IsInterpretPerson int
	ResetPassword     int
}

func (User) TableName() string {
	return "user"
}

func (u User) PasswordEqual(pwd string) bool {
	return utils.Dict.StringCon.MD5(pwd+u.Salt) == u.Password
}
