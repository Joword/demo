"""
@author Joword 
@time: 2024/02/01 15:28:31
@version: 1.0.0
@description: 请求参数model
"""
from pydantic import BaseModel

class VariantArgs(BaseModel):
    variant_id: str
    user_id: str
    genome_version: str
    version: str

class CriteriaArgs(BaseModel):
    vd: str
    u: str

class UserLoginInfo(BaseModel):
    username: str
    password: str

class ChatpaperShowing(BaseModel):
    pmid: str
    variantId: str
    userId:str

class UserRegisterInfo(BaseModel):
    username: str
    password: str
    test_password: str
    organization: str
    email: str
    email_code: str


