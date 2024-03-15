package constant

import (
	"os"
	"path/filepath"
)

const (
	ExportExcelPath = "files/public/xlsx/"
)

const (
	CxTplFilePath          = "files/public/fragile/tpl/"
	CxDataFilePath         = "files/public/fragile/dataFile/"
	CxDataPictureComPath   = "files/public/fragile/picture/compressFile/"
	CxDataPictureDeComPath = "files/public/fragile/picture/decompressFile/"
)

const (
	BoSxDataUploadPath      = "files/born/sxData/upload/"
	BoSxDataDeComPath       = "files/born/sxData/decompress/"
	BoFileGeneDiseaseImport = "files/born/geneDisease/upload/"
	BoFileTplUpload         = "files/born/tpl/upload/"
	BoFileCnvConfigUpload   = "files/born/cnvConfig/upload/"
	BoFileCnvConfigExport   = "files/born/cnvConfig/export/"
	BoFileDiseaseUpload     = "files/born/disease/upload/"
	BoReferencesFilePath    = "files/born/interpret/references/"
	BoReportExportPath      = "files/born/report/exportList/xlsx/"
	BoReviewExportPath      = "files/born/report/review/xlsx/"
	BoReviewExportComPath   = "files/born/report/review/"
)

const (
	XdzSXDataDownLoad   = "files/public/sxData/download/"
	XdzSXDataDecompress = "files/public/sxData/decompress/"
	XdzSampleAnnex      = "files/public/annex/"
)

const (
	FileTplUpload               = "files/public/tpl/"
	FileTplUploadCompress       = "files/public/tpl/compress/"
	FileTplUploadDecompress     = "files/public/tpl/"
	FilePrimerDesignImport      = "files/public/primerDesign/"
	FileResidualRiskImport      = "files/public/residualRisk/"
	FileDiseaseBackgroundImport = "files/public/diseaseBackground/"
	FileCnvConfigImport         = "files/public/cnvConfig/"
	FileDiseaseImport           = "files/public/disease/"
	FileGeneImport              = "files/public/gene/"
)

func GetXDZReportFilePath(filename string) string {
	configPath := filepath.Join("../../files/public/reportFilePDF", filename)
	return configPath
}

func GetFXReportFilePath(filename string) string {
	configPath := filepath.Join("../../files/public/fragile/reportFilePDF", filename)
	return configPath
}

func GetXDZPushReportFilePath(filename string) string {
	workPath, _ := os.Getwd()
	return filepath.Join(workPath, "/files/public/reportFilePDF", filename)
}

func GetFXPushReportFilePath(filename string) string {
	workPath, _ := os.Getwd()
	return filepath.Join(workPath, "/files/public/fragile/reportFilePDF", filename)
}
