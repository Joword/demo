package permission

import (
	"demo/constant"
	"demo/db/operate"
	"demo/models"
	"demo/pkg/utils"
	"demo/service/auth_service/permission_control"
	"demo/types"
	"errors"

	"github.com/gin-gonic/gin"
	logging "github.com/sirupsen/logrus"
	"gorm.io/gorm"
)

func FindUserOperateInHtml(userPermissionSlug, permissionSlug string) bool {
	if userPermissionSlug == permissionSlug {
		return true
	}
	return false
}

//FindUserPermissionInHtml 查询用户在具体页面的权限标识
func FindUserPermissionInHtml(db *gorm.DB, c *gin.Context, htmlName string) (*models.Permission, string, error) {
	userInfo, existUser := c.Get("user")
	if existUser == false {
		logging.WithFields(logging.Fields{
			"existUser": existUser,
		}).Error("pkg.permissionControl.FindUserPermissionHtmlSlug#userInfo&existUser")
		return nil, "", errors.New(constant.UserInfoGetFail)
	}

	permissionList, err := QueryUserRolePermission(db, userInfo.(*models.User).RoleId)
	if err != nil {
		logging.WithFields(logging.Fields{
			"err": err.Error(),
		}).Error("pkg.permissionControl.FindUserPermissionHtmlSlug#permissionList&err")
		return nil, "", errors.New(constant.UserPermissionGetFail)
	}
	permissionInfo := FindUserHtmlPermission(permissionList, htmlName)
	if permissionInfo == nil {
		return nil, "", errors.New("constant.UserNotHtmlPermission")
	}
	return permissionInfo, userInfo.(*models.User).Username, nil
}

//QueryUserRolePermission 获取当前用户角色具有那些权限
func QueryUserRolePermission(db *gorm.DB, roleId int) ([]*models.Permission, error) {
	roleInfo, err := operate.ModelsDB.SysRole.QueryById(db, roleId)
	if err != nil {
		return nil, err
	}
	if roleInfo == nil {
		return nil, nil
	}

	rolePermissionList, err := operate.ModelsDB.SysRolePermission.QueryByRoleId(db, roleId)
	if err != nil {
		return nil, err
	}
	permissionIdList := permission_control.GetPermissionIdListByRolePermission(rolePermissionList)

	permissionList, err := operate.ModelsDB.SysPermission.QueryByIdList(db, permissionIdList)
	if err != nil {
		return nil, err
	}

	return permissionList, nil
}

//FindUserHtmlPermission 查找用户页面权限
func FindUserHtmlPermission(permissionList []*models.Permission, htmlName string) *models.Permission {
	for _, permissionInfo := range permissionList {
		if permissionInfo.HtmlName == htmlName {
			return permissionInfo
		}
	}
	return nil
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

func FindRoleInfoByRoleId(roleList []*models.Role, roleId int) *models.Role {
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

func findPermissionInfo(permissionList []*models.Permission, permissionId int) *models.Permission {
	for _, row := range permissionList {
		if row.ID == permissionId {
			return row
		}
	}
	return nil
}

func FindRolePermissionInfo(rolePermissionList []*models.RolePermission, permissionList []*models.Permission, roleId int) []string {
	var permissionName []string
	for _, row := range rolePermissionList {
		if row.RoleId == roleId {
			permissionInfo := findPermissionInfo(permissionList, row.PermissionId)
			if permissionInfo != nil {
				permissionName = append(permissionName, permissionInfo.Name)
			}
		}
	}
	return permissionName
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

		permissionName := FindRolePermissionInfo(rolePermissionList, permissionList, row.ID)
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

func findRolePermissionByPermissionId(rolePermissionList []*models.RolePermission, permissionId int) *models.RolePermission {
	for _, row := range rolePermissionList {
		if row.PermissionId == permissionId {
			return row
		}
	}
	return nil
}

func QueryRoleDetail(db *gorm.DB, roleSlug string) (*types.RetRoleDetailInfo, error) {
	roleInfo, err := operate.ModelsDB.SysRole.QueryBySlug(db, roleSlug)
	if err != nil {
		return nil, err
	}
	if roleInfo == nil {
		return nil, errors.New("角色信息不存在")
	}
	retRoleDetailInfo := types.RetRoleDetailInfo{
		RoleName:        roleInfo.Name,
		RoleSlug:        roleInfo.Slug,
		RoleDescription: roleInfo.Description,
	}
	rolePermissionList, err := operate.ModelsDB.SysRolePermission.QueryByRoleId(db, roleInfo.ID)
	if err != nil {
		return nil, err
	}
	permissionList, err := operate.ModelsDB.SysPermission.Query(db)
	if err != nil {
		return nil, err
	}
	num := 0
	for _, row := range permissionList {
		num = num + 1
		retPerInfo := types.RetPerList{
			Number:                num,
			PermissionName:        row.Name,
			PermissionDescription: row.Description,
			Slug:                  row.Slug,
			HtmlName:              row.HtmlName,
		}

		permissionInfo1 := findRolePermissionByPermissionId(rolePermissionList, row.ID)
		if permissionInfo1 != nil {
			retPerInfo.Selected = true
		}

		retRoleDetailInfo.RolePermission = append(retRoleDetailInfo.RolePermission, &retPerInfo)
	}
	return &retRoleDetailInfo, nil
}

func updateRolePermission(db *gorm.DB, rolePermissionList []*models.RolePermission, permissionList []*models.Permission, roleId int) error {
	for _, rowRolePer := range rolePermissionList {
		perInfo := findPermissionInfo(permissionList, rowRolePer.PermissionId)
		if perInfo == nil {
			rowRolePer.IsDeleted = true
			err := operate.ModelsDB.SysRolePermission.Update(db, rowRolePer)
			if err != nil {
				return err
			}
		}
	}
	for _, rowPer := range permissionList {
		rolePerInfo := findRolePermissionByPermissionId(rolePermissionList, rowPer.ID)
		if rolePerInfo == nil {
			newRolePerInfo := models.RolePermission{
				PermissionId: rowPer.ID,
				RoleId:       roleId,
			}
			err := operate.ModelsDB.SysRolePermission.Add(db, &newRolePerInfo)
			if err != nil {
				return err
			}
		}
	}
	return nil
}

func UpdateRoleInfo(db *gorm.DB, roleName, roleSlug, roleDescription string, permissionSlugList []string) error {
	//处理角色信息更新
	roleInfo, err := operate.ModelsDB.SysRole.QueryBySlug(db, roleSlug)
	if err != nil {
		return err
	}
	if roleInfo == nil {
		return errors.New("角色信息不存在")
	}
	roleInfo.Name = roleName
	roleInfo.Description = roleDescription
	err = operate.ModelsDB.SysRole.Update(db, roleInfo)
	if err != nil {
		return err
	}
	//查询更新的权限信息
	permissionList, err := operate.ModelsDB.SysPermission.QueryBySlugList(db, permissionSlugList)
	if err != nil {
		return err
	}
	//查询角色权限信息
	rolePermissionList, err := operate.ModelsDB.SysRolePermission.QueryByRoleId(db, roleInfo.ID)
	if err != nil {
		return err
	}
	err = updateRolePermission(db, rolePermissionList, permissionList, roleInfo.ID)
	if err != nil {
		return err
	}
	return nil
}

func DeletedRole(db *gorm.DB, roleSlugList []string) error {
	for _, roleSlug := range roleSlugList {
		roleInfo, err := operate.ModelsDB.SysRole.QueryBySlug(db, roleSlug)
		if err != nil {
			return err
		}
		if roleInfo == nil {
			return errors.New("角色信息不存在")
		}

		userList, err := operate.ModelsDB.SysUser.QueryByRoleId(db, roleInfo.ID)
		if err != nil {
			return err
		}
		if len(userList) > 0 {
			return errors.New("角色信息有被用户使用，无法删除")
		}
		roleInfo.IsDeleted = true
		err = operate.ModelsDB.SysRole.Update(db, roleInfo)
		if err != nil {
			return err
		}

		rolePermissionList, err := operate.ModelsDB.SysRolePermission.QueryByRoleId(db, roleInfo.ID)
		if err != nil {
			return err
		}
		for _, rowRole := range rolePermissionList {
			rowRole.IsDeleted = true
			err = operate.ModelsDB.SysRolePermission.Update(db, rowRole)
			if err != nil {
				return err
			}
		}
	}
	return nil
}

func AddRolePermission(db *gorm.DB, roleSlug string, permissionSlugList []string) error {
	roleInfo, err := operate.ModelsDB.SysRole.QueryBySlug(db, roleSlug)
	if err != nil {
		return err
	}
	if roleInfo == nil {
		return errors.New("角色信息不存在")
	}
	for _, permissionSlug := range permissionSlugList {
		permissionInfo1, err := operate.ModelsDB.SysPermission.QueryBySlug(db, permissionSlug)
		if err != nil {
			return err
		}
		if permissionInfo1 == nil {
			return errors.New("权限信息不存在")
		}
		rolePermission := models.RolePermission{
			RoleId:       roleInfo.ID,
			PermissionId: permissionInfo1.ID,
		}
		err = operate.ModelsDB.SysRolePermission.Add(db, &rolePermission)
		if err != nil {
			return err
		}
	}
	return nil
}

func AddUser(db *gorm.DB, userName, userEmail, password string, roleSlugList []string) error {
	userInfo, err := operate.ModelsDB.SysUser.QueryByEmail(db, userEmail)
	if err != nil {
		return err
	}
	if userInfo != nil {
		return errors.New(constant.UserEmailIsExist)
	}

	salt := "wowah"
	newUserInfo := models.User{
		Username: userName,
		Email:    userEmail,
		Password: utils.Dict.StringCon.MD5(password + salt),
		Salt:     salt,
	}
	for _, roleSlug := range roleSlugList {
		roleInfo, err := operate.ModelsDB.SysRole.QueryBySlug(db, roleSlug)
		if err != nil {
			return err
		}
		if roleInfo != nil {
			newUserInfo.RoleId = roleInfo.ID
		}
	}
	err = operate.ModelsDB.SysUser.Add(db, &newUserInfo)
	if err != nil {
		return err
	}
	return nil
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
		roleInfo1 := FindRoleInfoByRoleId(roleList, row.RoleId)
		if roleInfo1 != nil {
			retInfo.RoleName = roleInfo1.Name
			retInfo.RoleSlug = roleInfo1.Slug
			retInfo.RoleDescription = roleInfo1.Description
		}
		retList = append(retList, &retInfo)
	}
	return retList, nil
}

func QueryUserDetail(db *gorm.DB, userEmail string) (*types.RetUserRoleDetail, error) {
	//查询用户信息
	userInfo, err := operate.ModelsDB.SysUser.QueryByEmail(db, userEmail)
	if err != nil {
		return nil, err
	}
	if userInfo == nil {
		return nil, errors.New("用户信息不存在")
	}
	retUserRoleInfo := types.RetUserRoleDetail{
		UserName:          userInfo.Username,
		UserEmail:         userInfo.Email,
		IsInterpretPerson: utils.Dict.Convert.BoolIntToString(userInfo.IsInterpretPerson),
	}

	//查询角色信息
	roleList, err := operate.ModelsDB.SysRole.Query(db)
	if err != nil {
		return nil, err
	}

	num := 0
	for _, rowRole := range roleList {
		num = num + 1
		retRoleInfo := types.RetUserRoleList{
			Number:          num,
			RoleName:        rowRole.Name,
			RoleSlug:        rowRole.Slug,
			RoleDescription: rowRole.Description,
		}
		if userInfo.RoleId == rowRole.ID {
			retRoleInfo.Selected = true
		}

		retUserRoleInfo.UserRole = append(retUserRoleInfo.UserRole, &retRoleInfo)
	}
	return &retUserRoleInfo, nil
}

func UpdateUser(db *gorm.DB, userName, userEmail, isInterpretPerson string, roleSlug []string) error {
	//更新用户数据
	userInfo, err := operate.ModelsDB.SysUser.QueryByEmail(db, userEmail)
	if err != nil {
		return err
	}
	if userInfo == nil {
		return errors.New("用户信息不存在")
	}
	userInfo.Username = userName
	userInfo.IsInterpretPerson = utils.Dict.Convert.BoolStringToInt(isInterpretPerson)

	//更新角色信息
	if len(roleSlug) == 0 {
		userInfo.RoleId = 0
	}
	for _, row := range roleSlug {
		roleInfo, err := operate.ModelsDB.SysRole.QueryBySlug(db, row)
		if err != nil {
			return err
		}
		if roleInfo == nil {
			return errors.New("角色信息不存在")
		}
		userInfo.RoleId = roleInfo.ID
	}

	err = operate.ModelsDB.SysUser.Update(db, userInfo)
	if err != nil {
		return err
	}
	return nil
}

func DeletedUser(db *gorm.DB, userEmailList []string) error {
	for _, email := range userEmailList {
		//删除用户信息
		userInfo, err := operate.ModelsDB.SysUser.QueryByEmail(db, email)
		if err != nil {
			return err
		}
		if userInfo == nil {
			return errors.New("用户信息不存在")
		}
		userInfo.IsDeleted = true

		err = operate.ModelsDB.SysUser.Update(db, userInfo)
		if err != nil {
			return err
		}
	}
	return nil
}

func QueryInterpretPerson(db *gorm.DB) ([]string, error) {
	userList, err := operate.ModelsDB.SysUser.QueryInterpretUser(db)
	if err != nil {
		return nil, err
	}
	var nameList []string
	for _, row := range userList {
		nameList = append(nameList, row.Username)
	}
	return nameList, nil
}
