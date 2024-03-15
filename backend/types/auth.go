package types

import (
	"demo/models"
	"time"

	"github.com/dgrijalva/jwt-go"
)

type Auth struct {
	Username string `valid:"Required; MaxSize(50)"`
	Password string `valid:"Required; MaxSize(50)"`
}

type AuthId struct {
	ID       int    `gorm:"primary_key" json:"id"`
	Username string `json:"username"`
	Password string `json:"password"`
}

type ReqLogin struct {
	Email    string `form:"email"`
	Password string `form:"password"`
}

type User struct {
	models.BaseModel
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

type Claims struct {
	UserId int
	jwt.StandardClaims
}

type RetPerList struct {
	Number                int
	PermissionName        string
	PermissionDescription string
	Slug                  string //硬编码
	HtmlName              string //页面模块名
	Selected              bool
}

type RetUserRoleList struct {
	Number          int
	RoleName        string
	RoleSlug        string
	RoleDescription string
	Selected        bool
}

type RetUserRoleDetail struct {
	UserName          string
	UserEmail         string
	IsInterpretPerson string
	UserRole          []*RetUserRoleList
}
