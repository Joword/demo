package constant

const (
	TimeLayoutStr = "2006-01-02 15:04:05"
	TimeYYYYMMDD  = "2006-01-02"
	TimeYYYYMM    = "2006-01"
	TimeYYYY      = "2006"
	TimeMM        = "01"
)

const (
	SystemError    = "系统故障，请联系管理员"
	OperateSuccess = "操作成功"
)

const (
	UserEmailNotInput                = "请输入登录邮箱"
	UserPasswordNotInput             = "请输入登录密码"
	OldPasswordNotInput              = "请输入旧密码"
	NewPassword1NotInput             = "请输入新密码"
	NewPassword2NotInput             = "请确认新密码"
	NewPassword2NotEqualNewPassword2 = "新密码输入不一致"
	PasswordUpdateSuccess            = "密码修改成功"
)

const (
	DateAfterNowError = "查询日期必须在今天之前"
	SentToDateNotNull = "到样日期不能为空"
)

const (
	FileForm = "(.PNG|.png|.JPG|.jpg|.jpeg|.JPEG|.gif|.GIF|.bmp|.BMP|.tiff|.TIFF|.pdf|.xlsx|.xls|.doc|.docx)$"
)
