package auth_service

import (
	"demo/db/operate"
	"demo/models"
	"demo/pkg/utils"
	"demo/service/auth_service/permission"
	"demo/types"
	"errors"
	"os"
	"strconv"
	"time"
	"unicode"

	"github.com/dgrijalva/jwt-go"
	"gorm.io/gorm"
)

var jwtKey = []byte("interpretationsystem")

func Login(db *gorm.DB, email, pwd string) (*models.User, error) {
	var userInfo models.User
	err := db.Where("email=? and is_deleted = 0", email).First(&userInfo).Error
	if err != nil {
		return nil, nil
	}
	return &userInfo, nil
}

func GetToken(user *models.User) (string, error) {
	sessionTime, err := strconv.Atoi(os.Getenv("SESSION_TIME"))
	if err != nil {
		return "", nil
	}
	expiredTime := time.Now().Add(time.Duration(sessionTime) * time.Hour)
	Claims := &types.Claims{
		UserId: user.ID,
		StandardClaims: jwt.StandardClaims{
			ExpiresAt: expiredTime.Unix(),
			IssuedAt:  time.Now().Unix(),
			Issuer:    "localhost",
			Subject:   "userToken",
		},
	}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, Claims)
	tokenString, err := token.SignedString(jwtKey)
	if err != nil {
		return "", err
	}
	return tokenString, nil
}

func ParseToken(tokenString string) (*jwt.Token, *types.Claims, error) {
	Claims := &types.Claims{}
	token, err := jwt.ParseWithClaims(tokenString, Claims, func(token *jwt.Token) (i interface{}, err error) {
		return jwtKey, nil
	})
	return token, Claims, err
}

func UpdatePassword(db *gorm.DB, email, oldPassword, newPassword string) error {
	userInfo, err := operate.ModelsDB.SysUser.QueryByEmail(db, email)
	if err != nil {
		return err
	}
	if userInfo.PasswordEqual(oldPassword) == false {
		return errors.New("用户密码输入错误")
	}
	err = VerifyPasswordRule(newPassword)
	if err != nil {
		return err
	}
	userInfo.Password = utils.Dict.StringCon.MD5(newPassword + userInfo.Salt)
	userInfo.ResetPassword = 0
	err = operate.ModelsDB.SysUser.Update(db, userInfo)
	if err != nil {
		return err
	}
	return nil
}

func VerifyPasswordRule(str string) error {
	var (
		isUpper   = false
		isLower   = false
		isNumber  = false
		isSpecial = false
	)

	if len(str) < 8 || len(str) > 20 {
		return errors.New("密码长度应为8到20位")
	}

	for _, s := range str {
		switch {
		case unicode.IsUpper(s):
			isUpper = true
		case unicode.IsLower(s):
			isLower = true
		case unicode.IsNumber(s):
			isNumber = true
		case unicode.IsPunct(s) || unicode.IsSymbol(s):
			isSpecial = true
		default:
		}
	}

	if (isUpper && isLower) && (isNumber || isSpecial) {
		return nil
	}
	return errors.New("密码必须包含数字，大小写字母，特殊字符")
}

func AddPermission(db *gorm.DB, perName, slug, description, htmlName string) error {
	permissionInfo, err := operate.ModelsDB.SysPermission.QueryBySlug(db, slug)
	if err != nil {
		return err
	}
	if permissionInfo != nil {
		return errors.New("权限硬编码已存在")
	}
	newPermissionInfo := models.Permission{
		Name:        perName,
		Slug:        slug,
		Description: description,
		HtmlName:    htmlName,
	}
	err = operate.ModelsDB.SysPermission.Add(db, &newPermissionInfo)
	if err != nil {
		return err
	}
	return nil
}

func QueryPermissionList(db *gorm.DB) ([]*types.RetPerList, error) {
	permissionList, err := operate.ModelsDB.SysPermission.Query(db)
	if err != nil {
		return nil, err
	}
	var retList []*types.RetPerList
	num := 0
	for _, row := range permissionList {
		num = num + 1
		ret := types.RetPerList{
			Number:                num,
			PermissionName:        row.Name,
			PermissionDescription: row.Description,
			Slug:                  row.Slug,
			HtmlName:              row.HtmlName,
		}
		retList = append(retList, &ret)
	}
	return retList, nil
}

func QueryUserList(db *gorm.DB) ([]*types.RetUserInfo, error) {
	//查询用户信息
	userList, err := operate.ModelsDB.SysUser.Query(db)
	if err != nil {
		return nil, err
	}

	//查询角色信息
	roleList, err := operate.ModelsDB.SysRole.Query(db)
	if err != nil {
		return nil, err
	}

	var retList []*types.RetUserInfo
	count := 0
	for _, row := range userList {
		count = count + 1
		retInfo := types.RetUserInfo{
			Name:              row.Username,
			Email:             row.Email,
			IsInterpretPerson: utils.Dict.Convert.BoolIntToString(row.IsInterpretPerson),
			Number:            count,
		}
		roleInfo1 := findRoleInfoByRoleId(roleList, row.RoleId)
		if roleInfo1 != nil {
			retInfo.RoleName = roleInfo1.Name
			retInfo.RoleSlug = roleInfo1.Slug
			retInfo.RoleDescription = roleInfo1.Description
		}
		retList = append(retList, &retInfo)
	}
	return retList, nil
}

//QueryUserRole 查询角色信息，用于用户新增角色时使用
func QueryUserRole(db *gorm.DB) ([]*types.RetUserRoleList, error) {
	roleList, err := operate.ModelsDB.SysRole.Query(db)
	if err != nil {
		return nil, err
	}
	var retRoleList []*types.RetUserRoleList
	num := 0
	for _, role := range roleList {
		num = num + 1
		ret := types.RetUserRoleList{
			Number:          num,
			RoleName:        role.Name,
			RoleSlug:        role.Slug,
			RoleDescription: role.Description,
			Selected:        false,
		}
		retRoleList = append(retRoleList, &ret)
	}
	return retRoleList, nil
}

func findRoleInfoByRoleId(roleList []*models.Role, roleId int) *models.Role {
	for _, rowRole := range roleList {
		if roleId == rowRole.ID {
			return rowRole
		}
	}
	return nil
}

func AddRoleInfo(db *gorm.DB, roleName, roleSlug, roleDescription string) error {
	roleInfo, err := operate.ModelsDB.SysRole.QueryBySlug(db, roleSlug)
	if err != nil {
		return err
	}
	if roleInfo != nil {
		return errors.New("角色标识已存在")
	}
	newRoleInfo := models.Role{
		Name:        roleName,
		Slug:        roleSlug,
		Description: roleDescription,
	}
	return operate.ModelsDB.SysRole.Add(db, &newRoleInfo)
}

func QueryRoleList(db *gorm.DB) ([]*types.RetRoleList, error) {
	//查询角色信息
	roleList, err := operate.ModelsDB.SysRole.Query(db)
	if err != nil {
		return nil, err
	}

	//查询权限信息
	permissionList, err := operate.ModelsDB.SysPermission.Query(db)
	if err != nil {
		return nil, err
	}

	//查询角色权限关联信息
	rolePermissionList, err := operate.ModelsDB.SysRolePermission.Query(db)
	if err != nil {
		return nil, err
	}

	var retRoleList []*types.RetRoleList
	num := 0
	for _, row := range roleList {
		num = num + 1
		retInfo := types.RetRoleList{
			Number:          num,
			RoleName:        row.Name,
			RoleSlug:        row.Slug,
			RoleDescription: row.Description,
		}

		permissionName := permission.FindRolePermissionInfo(rolePermissionList, permissionList, row.ID)
		for _, permissionInfo1 := range permissionName {
			if retInfo.RolePermissionName == "" {
				retInfo.RolePermissionName = permissionInfo1
			} else {
				retInfo.RolePermissionName = retInfo.RolePermissionName + "，" + permissionInfo1
			}
		}

		retRoleList = append(retRoleList, &retInfo)
	}
	return retRoleList, nil
}
