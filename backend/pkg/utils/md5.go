package utils

import (
	"crypto/md5"
	"demo/constant"
	"encoding/hex"
	"fmt"
)

type dict struct {
	StringCon stringCon
	Convert   convert
}

type stringCon struct{}
type convert struct{}

var Dict = new(dict)

func (s *stringCon) MD5(str string) string {
	return fmt.Sprintf("%x", md5.Sum([]byte(str)))
}

func EncodeMD5(value string) string {
	m := md5.New()
	m.Write([]byte(value))
	return hex.EncodeToString(m.Sum(nil))
}

func (s *convert) BoolIntToString(num int) string {
	if num == 1 {
		return "是"
	}
	if num == 0 {
		return "否"
	}
	return "error"
}

func (s *convert) BoolStringToInt(str string) int {
	if str == "是" {
		return 1
	}
	if str == "否" {
		return 0
	}
	return -1
}

func (s *convert) GenderIntToString(num int) string {
	if num == 1 {
		return "男"
	} else if num == 2 {
		return "女"
	} else {
		return "error"
	}
}

func (s *convert) GenderIntToStringFM(num int) string {
	if num == 1 {
		return "M"
	} else if num == 2 {
		return "F"
	} else {
		return "error"
	}
}

func (s *convert) GenderToInt(str string) int {
	if str == "男" {
		return 1
	}
	if str == "女" {
		return 2
	}
	return 0
}

func (s *convert) GenderIntToStringEn(num int) string {
	if num == 1 {
		return "■ Male  □ Female"
	}
	if num == 2 {
		return "□ Male  ■ Female"
	}
	return ""
}

func (s *convert) GenderIntToStringTra(num int) string {
	if num == 1 {
		return "■ 男  □ 女"
	}
	if num == 2 {
		return "□ 男  ■ 女"
	}
	return ""
}

func (s *convert) SubjectTypeToString(num string) string {
	if num == "7" {
		return "patient"
	} else if num == "8" {
		return "suspected victims"
	} else if num == "9" {
		return "phenotype normal"
	} else {
		return "error"
	}
}

func (s *convert) VerificationStatusIntToString(num int) string {
	if num == constant.VerificationStatusInt1 {
		return constant.VerificationStatus1
	} else if num == constant.VerificationStatusInt2 {
		return constant.VerificationStatus2
	} else if num == constant.VerificationStatusInt3 {
		return constant.VerificationStatus3
	} else {
		return "error"
	}
}

func (s *convert) BoReportTypePushToReportType(num int) string {
	if num == 1 {
		return "180"
	} else if num == 2 {
		return "400"
	} else if num == 3 {
		return "188"
	} else if num == 4 {
		return "187"
	} else if num == 5 {
		return "189"
	} else if num == 6 {
		return "190"
	} else {
		return "error"
	}
}

func (s *convert) BoReportTypeSplit(num int, resultType string) string {
	if num == 1 {
		return "正式报告-" + resultType
	} else if num == 2 {
		return "补充报告-" + resultType
	} else if num == 3 {
		return "SMN2报告"
	} else if num == 4 {
		return "核型报告"
	} else if num == 5 {
		return "地贫报告"
	} else if num == 6 {
		return "DMD失败报告"
	} else {
		return "error"
	}
}

func (s *convert) BoPushResultType(str string) string {
	if str == "-" || str == "携带者" {
		return ""
	} else {
		return str
	}
}
func (s *convert) JudgeHospitalCodeAndReportType(hospitalCode string) (string, int) {
	if hospitalCode != "" && hospitalCode != "0" {
		return hospitalCode, 2
	} else {
		return "0", 1
	}
}
