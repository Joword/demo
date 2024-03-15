package status

import "demo/constant"

const (
	SiteStatus1     = "新增"
	SiteStatus2     = "待确认"
	SiteStatus3     = "已确认"
	SiteStatusOther = "原始"
)

func SiteStatusIntToString(num int) string {
	if num == 1 {
		return SiteStatus1
	} else if num == 2 {
		return SiteStatus2
	} else if num == 3 {
		return SiteStatus3
	} else {
		return SiteStatusOther
	}
}

func GetSiteStatusList() []string {
	return []string{
		SiteStatusOther,
		SiteStatus1,
		SiteStatus2,
		SiteStatus3,
	}
}

func GetSiteStatusConfirmList() []string {
	return []string{
		SiteStatus2,
		SiteStatus3,
	}
}

func GetVerificationStatusList() []string {
	return []string{
		constant.VerificationStatusOther,
		constant.VerificationStatus1,
		constant.VerificationStatus2,
		constant.VerificationStatus3,
	}
}

func GetVerificationStatusConfirmList() []string {
	return []string{
		constant.VerificationStatus2,
		constant.VerificationStatus3,
	}
}

func VerificationStatusIntToString(num int) string {
	if num == 1 {
		return constant.VerificationStatus1
	} else if num == 2 {
		return constant.VerificationStatus2
	} else if num == 3 {
		return constant.VerificationStatus3
	} else {
		return constant.VerificationStatusOther
	}
}

const (
	SampleStatus1  = "待分配"
	SampleStatus2  = "待解读"
	SampleStatus3  = "待回传结果"
	SampleStatus4  = "待审核"
	SampleStatus5  = "待推送"
	SampleStatus11 = "标准品" //（不分配即不解读）
	SampleStatus12 = "已推送"
)

func SampleStatusIntToString(num int) string {
	if num == 1 {
		return SampleStatus1
	} else if num == 2 {
		return SampleStatus2
	} else if num == 3 {
		return SampleStatus3
	} else if num == 4 {
		return SampleStatus4
	} else if num == 5 {
		return SampleStatus5
	} else if num == 11 {
		return SampleStatus11
	} else if num == 12 {
		return SampleStatus12
	} else {
		return "error"
	}
}

const (
	SampleStatusUnAssign     = 1  //待分配
	SampleStatusUnInterpret  = 2  //待解读
	SampleStatusUnReturnInfo = 3  //待回传信息
	SampleStatusUnReview     = 4  //待审核
	SampleStatusUnPush       = 5  //待推送
	SampleStatusStandard     = 11 //标准品
	SampleStatusPush         = 12 //已推送
)

func HandleAuditStatus(str string) string {
	if str == "X" {
		return "已审核"
	} else if str == "W" {
		return "未审核"
	} else if str == "E" {
		return "存疑"
	} else if str == "B" {
		return "补录"
	} else if str == "O" {
		return "作废"
	} else if str == "D" {
		return "待作废"
	} else if str == "L" {
		return "锁定"
	} else {
		return "error"
	}
}
