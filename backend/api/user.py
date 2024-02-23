"""
@author Joword 
@time: 2024/01/31 00:54:24
@version: 1.0.0
@description: 用户api
"""
from fastapi import APIRouter

from backend.commons import Result
from backend.models.response import ResultBody
from backend.commons.constant import ResultCode
from backend.models.request.type import UserLoginInfo, UserRegisterInfo

from backend.services.user import handler_user_login, handler_user_register

userRouter = APIRouter()

@userRouter.post("/user/login", response_model=ResultBody, tags=['用户登录接口'])
def login(data: UserLoginInfo) -> ResultBody:
    if data.username==None:
        return Result.failure(ResultCode.USERNAME_NONEXIST,"用户名不能为空",None)
    elif data.password==None:
        return Result.failure(ResultCode.PASSWORD_NONEXIST,"用户密码不能为空",None)
    return Result.success(handler_user_login(data))

@userRouter.post("/user/register",tags=['用户注册接口'],response_model=ResultBody)
def register(data: UserRegisterInfo) -> ResultBody:
    if data.email==None:
        return Result.failure(ResultCode.EMAIL_NONEXIST,"邮箱不能为空",None)
    elif data.password==None:
        return Result.failure(ResultCode.PASSWORD_NONEXIST,"用户密码不能为空",None)
    elif data.email_code==None:
        return Result.failure(ResultCode.EMAIL_CODE_NONEXIST,"邮箱验证码不能为空",None)
    elif data.username==None:
        return Result.failure(ResultCode.USERNAME_NONEXIST,"用户名不能为空",None)
    if data.password==data.test_password:
        if data.email_code=="test123":
            return Result.success(handler_user_register(data=data))
        return Result.failure(ResultCode.EMAIL_CODE_ERROR,"邮箱验证码不正确",None)
    return Result.failure(ResultCode.PASSWORD_ERROR,"用户密码不正确",None)

    
