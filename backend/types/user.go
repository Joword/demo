package types

type ReqUserData struct {
	Email        string `form:"email"`
	OldPassword  string `form:"oldPassword"`
	NewPassword1 string `form:"newPassword1"`
	NewPassword2 string `form:"newPassword2"`
}

type ReqAddPermissionInfo struct {
	PerName        string `form:"permissionName"`
	PerSlug        string `form:"slug"`
	PerDescription string `form:"permissionDescription"`
	PerHtmlName    string `form:"htmlName"`
}

type ReqUserRole struct {
	UserName          string   `form:"userName"`
	UserEmail         string   `form:"userEmail"`
	UserPassword1     string   `form:"userPassword1"`
	UserPassword2     string   `form:"userPassword2"`
	IsInterpretPerson string   `form:"isInterpretPerson"`
	UserRoleSlug      []string `form:"userRoleSlug"`
}

type ReqUserEmail struct {
	UserEmail string `form:"userEmail"`
}

type ReqDeletedUserInfo struct {
	DeletedUserEmailList []string `form:"deletedUserEmailList"`
}

type RetUserInfo struct {
	Number            int
	Name              string //用户姓名
	Email             string //邮箱账号
	RoleName          string //角色名称
	RoleSlug          string //角色标识
	RoleDescription   string //角色描述
	IsInterpretPerson string //是否是解读人员
}

type RetRoleList struct {
	Number             int
	RoleName           string
	RoleSlug           string
	RoleDescription    string
	RolePermissionName string
}

type RetRoleDetailInfo struct {
	RoleName        string
	RoleSlug        string
	RoleDescription string
	RolePermission  []*RetPerList
}
