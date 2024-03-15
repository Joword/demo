package constant

const (
	ReportFormal                   = "正式报告"
	ReportSupple                   = "补充报告"
	ReportSuppleH                  = "补充报告-亚效等位基因"
	ReportSuppleJA                 = "补充报告-吉安民生"
	ReportSupplyMotherCarrier      = "补充报告-母体携带"
	FReport                        = "阳性报告"
	FSReport                       = "阳性报告+补充报告"
	NReport                        = "阴性报告"
	NSReport                       = "阴性报告+补充报告"
	ResultNegative                 = "阴性"
	ResultNotRange                 = "检测范围外"
	ReportNot                      = "不报"
	ReportTypeUnknown              = "未知"
	ReportFormalRemindVerification = "正式报告_提醒验证"
	OtherTypes                     = "其它型别"
)

//cnv
const (
	DMDName = "进行性假肥大性肌营养不良"
	DMDGene = "DMD"
	F9Gene  = "F9"
	F9Name  = "乙型血友病"
)

//补充实验
const (
	SupplyDiseaseNameBeta  = "β地贫"
	SupplyDiseaseNameAlpha = "α地贫"
	SupplyDiseaseNameSmn   = "SMN1 EX7 del"
	SupplyDiseaseNameF81   = "F8int1h-1.5k&2k"
	SupplyDiseaseNameF822  = "F8int22h-10.8k&12k"
)
const (
	F8Name             = "甲型血友病"
	F8DiseaseGeneName  = "F8"
	Smn                = "脊髓性肌肉萎缩症"
	SmnDiseaseGeneName = "SMN1"
	HeteroPositive     = "杂合阳性"
	HomoPositive       = "纯合阳性"
	HemiPositive       = "半合阳性"
)
const (
	BetaChinese         = "Chinese"
	BetaSEAHPFH         = "SEA-HPFH"
	BetaTaiwanese       = "Taiwanese"
	Beta                = "β-地中海贫血"
	BetaDiseaseGeneName = "HBB"
)
const (
	Alpha37               = "3.7"
	Alpha42               = "4.2"
	AlphaSEA              = "SEA"
	AlphaTHAI             = "THAI"
	AlphaFIL              = "FIL"
	Alpha                 = "α-地中海贫血"
	AlphaDiseaseGeneName1 = "HBA1"
	AlphaDiseaseGeneName2 = "HBA2"
)

const (
	LikelyPathogenic                   = "Likely pathogenic"
	LikelyPathogenicName               = "疑似致病变异"
	Pathogenic                         = "Pathogenic"
	PathogenicName                     = "致病变异"
	Vus                                = "VUS"
	VusName                            = "意义未明变异"
	MutationTypeNotVus                 = "不报-VUS"
	MutationTypeNotVerifyIsFalse       = "不报-验证为假"
	MutationTypeNotCombinedUnSpiritual = "不报-合并后非烈性"
	MutationTypeNotCommonFalsePoint    = "不报-常见假点"
	MutationTypeNotDowngradeToVus      = "不报-库内位点降为VUS"
	MutationTypeNotMergeSite           = "不报-合并位点"
	MutationTypeNotOther               = "不报-其他"
	MutationTypeSupplyReport           = "补充报告报出"
	PUnVerification                    = "P-待核查"
	LPUnVerification                   = "LP-待核查"
	VUSUnVerification                  = "VUS-待核查"
)

const (
	Chr  = "Chr"
	ChrX = "ChrX"
	Xl   = "XL"
	Ar   = "AR"
	Xld  = "XLD"
	Xlr  = "XLR"
	Ad   = "AD"
	Mi   = "Mi"
)
const (
	Het                = "Het"
	HetName            = "杂合"
	Hom                = "Hom"
	HomName            = "纯合"
	Hemi               = "Hemi"
	HemiName           = "半合"
	Homogeneity        = "同质性"
	Heterogeneity      = "异质性"
	HeterogeneityLower = "异质性（低比例）"
)

const (
	ExperimentTypeVariants   = "variants实验"
	ExperimentTypeCnv        = "cnv实验"
	ExperimentTypeSupply     = "补充实验"
	ExperimentTypeFusionGene = "地贫融合基因"
	ExperimentDataNotFind    = "实验数据未找到"
)
