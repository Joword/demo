package operate

import (
	logging "demo/log"
	"demo/models"
	"errors"
	"time"

	"gorm.io/gorm"
)

type sysPermission struct{}

type sysRole struct{}

type sysRolePermission struct{}

func (s *sysRole) Query(db *gorm.DB) ([]*models.Role, error) {
	var roleList []*models.Role
	err := db.Where("is_deleted = 0").Order("slug").Find(&roleList).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system role query error": err.Error()})
		return nil, errors.New("角色信息查询错误")
	}
	return roleList, nil
}

func (s *sysRole) QueryById(db *gorm.DB, id int) (*models.Role, error) {
	var roleInfo *models.Role
	err := db.Where("id = ? and is_deleted = 0", id).First(&roleInfo).Error
	if errors.Is(gorm.ErrRecordNotFound, err) {
		return nil, nil
	} else if err != nil {
		logging.Error(map[string]interface{}{"system role query error by id": err.Error()})
		return nil, errors.New("角色信息查询错误")
	}
	return roleInfo, nil
}

func (s *sysRole) QueryBySlug(db *gorm.DB, slug string) (*models.Role, error) {
	var roleInfo models.Role
	err := db.Where("slug = ? and is_deleted = 0", slug).First(&roleInfo).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, nil
	} else if err != nil {
		logging.Error(map[string]interface{}{"system role query error by slug": err.Error()})
		return nil, errors.New("角色信息查询错误")
	}
	return &roleInfo, nil
}

func (s *sysRole) Add(db *gorm.DB, roleInfo *models.Role) error {
	err := db.Create(&roleInfo).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system role add error": err.Error()})
		return errors.New("角色信息新增错误")
	}
	return nil
}

func (s *sysRole) Update(db *gorm.DB, roleInfo *models.Role) error {
	if err := db.Save(&roleInfo).Error; err != nil {
		logging.Error(map[string]interface{}{"system role update error": err.Error()})
		return errors.New("角色信息更新错误")
	}
	return nil
}

func (s *sysPermission) Query(db *gorm.DB) ([]*models.Permission, error) {
	var permissionList []*models.Permission
	err := db.Where("is_deleted = 0").Order("html_name,slug asc").Find(&permissionList).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system permission query error": err.Error()})
		return nil, errors.New("权限信息查询错误")
	}
	return permissionList, nil
}

func (s *sysPermission) QueryBySlug(db *gorm.DB, perSlug string) (*models.Permission, error) {
	var permissionInfo *models.Permission
	err := db.Where("slug = ? and is_deleted = 0", perSlug).First(&permissionInfo).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, nil
	} else if err != nil {
		logging.Error(map[string]interface{}{"system permission query error by slug": err.Error()})
		return nil, errors.New("权限信息查询错误")
	}
	return permissionInfo, nil
}

func (s *sysPermission) QueryBySlugList(db *gorm.DB, perSlugList []string) ([]*models.Permission, error) {
	var permissionList []*models.Permission
	err := db.Where("slug in ? and is_deleted = 0", perSlugList).Find(&permissionList).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system permission query error by slugList": err.Error()})
		return nil, errors.New("权限信息查询错误")
	}
	return permissionList, nil
}

func (s *sysPermission) QueryByIdList(db *gorm.DB, idList []int) ([]*models.Permission, error) {
	var permissionList []*models.Permission
	err := db.Where("id in ? and is_deleted = 0", idList).Find(&permissionList).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system permission query error by idList": err.Error()})
		return nil, errors.New("权限信息查询错误")
	}
	return permissionList, err
}

func (s *sysPermission) Add(db *gorm.DB, permissionInfo *models.Permission) error {
	err := db.Create(&permissionInfo).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system permission add error": err.Error()})
		return errors.New("权限信息新增错误")
	}
	return nil
}

func (s *sysRolePermission) Query(db *gorm.DB) ([]*models.RolePermission, error) {
	var rolePermissionList []*models.RolePermission
	err := db.Where("is_deleted = 0").Find(&rolePermissionList).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system role permission query error": err.Error()})
		return nil, errors.New("角色权限信息查询错误")
	}
	return rolePermissionList, nil
}

func (s *sysRolePermission) QueryByRoleId(db *gorm.DB, roleId int) ([]*models.RolePermission, error) {
	var rolePermissionList []*models.RolePermission
	err := db.Where("role_id = ? and is_deleted = 0", roleId).Find(&rolePermissionList).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system role permission query error by roleId": err.Error()})
		return nil, errors.New("角色权限信息查询错误")
	}
	return rolePermissionList, nil
}

func (s *sysRolePermission) Add(db *gorm.DB, rolePermissionInfo *models.RolePermission) error {
	err := db.Create(&rolePermissionInfo).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system role permission add error": err.Error()})
		return errors.New("角色权限信息新增错误")
	}
	return nil
}

func (s *sysRolePermission) Update(db *gorm.DB, rolePermissionInfo *models.RolePermission) error {
	rolePermissionInfo.UpdatedAt = time.Now()
	if err := db.Save(&rolePermissionInfo).Error; err != nil {
		logging.Error(map[string]interface{}{"system role permission update error": err.Error()})
		return errors.New("角色权限信息更新错误")
	}
	return nil
}
