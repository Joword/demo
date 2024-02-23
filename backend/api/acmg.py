"""
@author Joword 
@time: 2024/02/01 15:11:41
@version: 1.0.0
@description: ACMG 路由层
"""
from fastapi import APIRouter
from ..commons import Result
from ..models.response import ResultBody
from ..models.request.type import VariantArgs, CriteriaArgs, ChatpaperShowing
from ..commons.constant import ResultCode

from ..services.acmg import handler_acmg_info, handler_acmg_criteria, handler_chat_info

acmgRouter = APIRouter()

@acmgRouter.post("/interpretation/variant", tags=["ACMG突变位点数据"], response_model=ResultBody)
def get_acmg_info(req_data: VariantArgs) -> dict:
    if req_data.variant_id==None:
        return Result.failure(ResultCode.VARIANTID_NONEXIST, "variant_id can not be None.", None)
    elif req_data.user_id==None:
        return Result.failure(ResultCode.USERNAME_NONEXIST, "user_id can not be None.", None)
    elif req_data.genome_version==None:
        return Result.failure(ResultCode.GENOMEVERSION_ISEXIST, "genome_version can not be None.", None)
    elif req_data.version==None:
        return Result.failure(ResultCode.ACMGVERSION_ISEXIST, "version can not be None.", None)
    return Result.success(handler_acmg_info(req_data))

@acmgRouter.post("/interpretation/clztable",tags=['ACMG证据项位点数据'], response_model=ResultBody)
def get_acmg_criteria(data: CriteriaArgs) -> dict:
    if data.vd==None:
        return Result.failure(ResultCode.VARIANTID_NONEXIST,"The variant_id can not be None.",None)
    elif data.u==None:
        return Result.failure(ResultCode.USERNAME_NONEXIST,"The user_id can not be None.",None)
    return Result.success(handler_acmg_criteria(data))

@acmgRouter.post("/paper/showing",tags=['chatpaper展示接口'],response_model=ResultBody)
def get_amg_chat_info(data: ChatpaperShowing) -> dict:
    if data.variantId==None:
        return Result.failure(ResultCode.VARIANTID_NONEXIST,"The variant_id can not be None.",None)
    elif data.userId==None:
        return Result.failure(ResultCode.USERNAME_NONEXIST,"The user_id can not be None.",None)
    elif data.pmid==None:
        return Result.failure(ResultCode.PMID_NONEXIST,"The pmid can not be None.",None)
    return Result.success(handler_chat_info(data))
    
