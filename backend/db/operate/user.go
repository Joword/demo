package operate

import (
	logging "demo/log"
	"demo/models"
	"errors"

	"time"

	"gorm.io/gorm"
)

type sysUser struct{}

func (s *sysUser) Query(db *gorm.DB) ([]*models.User, error) {
	var userList []*models.User
	err := db.Where("is_deleted = 0").Order("email").Find(&userList).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system user query error": err.Error()})
		return nil, errors.New("用户信息查询错误")
	}
	return userList, nil
}

func (s *sysUser) QueryById(db *gorm.DB, userId int) (*models.User, error) {
	var user models.User
	if err := db.Where("is_deleted = 0").First(&user, userId).Error; err != nil {
		return nil, err
	}
	return &user, nil
}

func (s *sysUser) QueryByRoleId(db *gorm.DB, roleId int) ([]*models.User, error) {
	var userList []*models.User
	err := db.Where("role_id=? and is_deleted = 0", roleId).Find(&userList).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system user query error by roleId": err.Error()})
		return nil, errors.New("用户信息查询错误")
	}
	return userList, nil
}

func (s *sysUser) QueryByEmail(db *gorm.DB, email string) (*models.User, error) {
	var userInfo models.User
	err := db.Where("email=? and is_deleted = 0", email).First(&userInfo).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, nil
	} else if err != nil {
		logging.Error(map[string]interface{}{"system user query error by email": err.Error()})
		return nil, errors.New("用户信息查询错误")
	}
	return &userInfo, nil
}

//QueryInterpretUser 查找解读人员用户
func (s *sysUser) QueryInterpretUser(db *gorm.DB) ([]*models.User, error) {
	var userList []*models.User
	err := db.Where("is_interpret_person = 1 and is_deleted = 0").Find(&userList).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system user query interpret user error": err.Error()})
		return nil, errors.New("用户信息查询错误")
	}
	return userList, nil
}

func (s *sysUser) Add(db *gorm.DB, userInfo *models.User) error {
	if err := db.Create(&userInfo).Error; err != nil {
		logging.Error(map[string]interface{}{"system user add error": err.Error()})
		return errors.New("用户信息新增错误")
	}
	return nil
}

func (s *sysUser) Update(db *gorm.DB, userInfo *models.User) error {
	userInfo.UpdatedAt = time.Now()
	err := db.Save(userInfo).Error
	if err != nil {
		logging.Error(map[string]interface{}{"system user update error": err.Error()})
		return errors.New("用户信息更新错误")
	}
	return nil
}
