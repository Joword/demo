"""
@author Joword 
@time: 2024/01/31 11:12:14
@version: 1.0.0
@description: 
"""
from typing import Dict
from ..schemas.mongo import collections
from ..commons.utils import is_email, check_password

from ..models.types import UserInfo
from ..models.request.type import UserLoginInfo, UserRegisterInfo

def handler_user_login(data: UserLoginInfo) -> str:
    check_username: bool = is_email(arg=data)
    user_info_all = UserInfo()
    user_info_all = collections['user_info'].find_one({"email":data.username}) if check_username else collections['user_info'].find_one({"username":data.username})
    is_login: bool = check_password(data.password, user_info_all['password'])
    return "success" if is_login else "failure"

def handler_user_register(data: UserRegisterInfo) -> str:

    return "success"