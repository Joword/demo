package utils

import (
	setting "demo/conf"
	"strings"
)

func Setup() {
	jwtSecret = []byte(setting.AppSetting.JwtSecret)
}

// StringsIndex 字符串截取
func (s *stringCon) StringsIndex(str string, indexStr string, n int) (string, string) {
	comma := strings.Index(str, indexStr)
	str1 := str[:comma]
	str2 := str[comma+n:]
	return str1, str2
}
