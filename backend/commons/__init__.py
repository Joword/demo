from backend.models.response import T
from backend.commons.constant import ResultCode

class Result:
    @staticmethod
    def success(data:T) -> dict:
        return {"code":ResultCode.RESULT_SUCC,"msg":'success',"data":data}
    @staticmethod
    def failure(code:int, msg:str, data:T) -> dict:
        return {"code":code,"msg":msg,"data":data}