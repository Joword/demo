package models

type Role struct {
	BaseModel
	Name        string
	Slug        string
	Description string
}

func (r *Role) TableName() string {
	return "role"
}

type Permission struct {
	BaseModel
	Name        string
	Slug        string
	Description string
	HtmlName    string //页面模块名
}

func (p *Permission) TableName() string {
	return "permission"
}

type PermissionAjax struct {
	BaseModel
	SystemIdentify string
	AjaxName       string
	Remark         string
}

func (p *PermissionAjax) TableName() string {
	return "permission_ajax"
}

type RolePermission struct {
	BaseModel
	RoleId       int
	PermissionId int
}

func (r *RolePermission) TableName() string {
	return "role_permission"
}
