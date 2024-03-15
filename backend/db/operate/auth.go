package operate

import (
	"demo/types"

	"gorm.io/gorm"
)

var db *gorm.DB

// CheckAuth checks if authentication information exists
func CheckAuth(username, password string) (bool, error) {
	var auth types.AuthId
	err := db.Select("id").Where(types.AuthId{Username: username, Password: password}).First(&auth).Error
	if err != nil && err != gorm.ErrRecordNotFound {
		return false, err
	}
	if auth.ID > 0 {
		return true, nil
	}
	return false, nil
}
