"""
@author Joword 
@time: 2024/01/31 13:39:43
@version: 1.0.0
@description: 工具类
"""
import re

from datetime import datetime
from collections import namedtuple
from typing import Dict, List
from ..models.request.type import UserLoginInfo

class CommonQueryParams(object):
    """
    定义一个公共的查询参数类，用于fastapi的依赖
    可以以调用commons: CommonQueryParams = Depends()

    example：

        from fastapi import Depends, FastAPI
        app = FastAPI()
        fake_items_db = [{"item_name": "Foo"}, {"item_name": "Bar"}, {"item_name": "Baz"}]
        class CommonQueryParams:
            def __init__(self, q: str = None, skip: int = 0, limit: int = 100):
                self.q = q
                self.skip = skip
                self.limit = limit
        @app.get("/items/")
        async def read_items(commons: CommonQueryParams = Depends()):
            response = {}
            if commons.q:
                response.update({"q": commons.q})
            items = fake_items_db[commons.skip : commons.skip + commons.limit]
            response.update({"items": items})
            return response
    """

    def __init__(self, q: str = None, skip: int = 0, limit: int = 10):
        self.q = q
        self.skip = skip
        self.limit = limit

async def write_log(api=None, msg=None, user='root'):
    with open("log.log", mode="a", encoding='utf-8') as log:
        now = datetime.now()
        log.write(f"时间：{now}    API调用事件：{api}    用户：{user}    消息：{msg}\n")

def convert_camel(arg:str) -> str:
    return re.sub(r'(_[a-z])', lambda x: x.group(1)[1].upper(), arg)

def custom_class(arg: Dict) -> tuple:
    return namedtuple('X', arg.keys())(*arg.values())

class Criterias(object):
    @staticmethod
    def auto() -> List:
        return ['bs2','bs1','bs4','bp3','bp4','bp7','ps1','pvs1','ba1','pp3','pm1','pm5','pm2','pm4']
    @staticmethod
    def semiauto() -> List:
        return ['pm3','bp2','ps2','pm6','pp1','bs4','pp4','bp5','ps4','ps3','bs3']
    @staticmethod
    def population() -> List:
        return ['bs1','bs2','ba1','pm2']
    @staticmethod
    def computation() -> List:
        return ['pvs1','pm1','pp3','bp4','pm4','bp3','bp7','ps1','pm5']
    @staticmethod
    def case() -> List:
        return ['pm3','bp2','ps2','pm6','pp1','bs4','pp4','bp5','ps4','ps3','bs3']
    
def is_email(arg: UserLoginInfo) -> bool:
    if len(arg.username.split("@genomics.cn"))>1 or len(arg.username.split("@bgi.com"))>1:
        return True
    return False

def check_password(elder: str, fresh: str) -> bool:
    if elder==fresh:
        return True
    return False

