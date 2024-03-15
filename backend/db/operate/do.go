package operate

type model struct {
	SysPermission     sysPermission
	SysRole           sysRole
	SysRolePermission sysRolePermission
	SysUser           sysUser
}

var ModelsDB = new(model)
