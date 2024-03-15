package permission_control

import "demo/models"

func GetPermissionIdListByRolePermission(rolePermissionList []*models.RolePermission) []int {
	var permissionIdList []int
	for _, row := range rolePermissionList {
		permissionIdList = append(permissionIdList, row.PermissionId)
	}
	return permissionIdList
}
