
"""
@author Joword 
@time: 2024/08/06 17:40:39
@version: 1.0.0
@description: Redis center
"""
import json
import hashlib
import aioredis
import asyncio
import nest_asyncio

from gmssl import sm3
from config import settings
from asyncio.runners import Runner
from aioredis.commands import Redis
from typing import Never, Any, Dict, Optional



class RedisPool:
    def __init__(self) -> Never:
        nest_asyncio.apply()
        self.url = settings.REDIS_PREFIX+settings.REDIS_HOST+":"+str(settings.REDIS_PORT)
    
    @staticmethod
    def __running(event: Any) -> Runner:
        return asyncio.run(event)
    
    async def __get_pool(self) -> Redis:
        return await aioredis.create_redis_pool(self.url, password=settings.REDIS_PASSWORD, minsize=5, maxsize=10,encoding='utf-8')
    
    @staticmethod
    def __get_md5(code: str) -> Any:
        email_code_md5 = hashlib.md5()
        email_code_md5.update(str(code).encode("utf-8"))
        return email_code_md5
    
    @staticmethod
    def __get_encrypt(val: Any):
        return sm3.sm3_hash([i for i in bytes(json.dumps(str(val.hexdigest()+settings.SALT)).encode("utf-8"))])
    
    async def exists_code(self, code: str) -> bool:
        pool = await self.__get_pool()
        try:
            flag:int = await pool.exists(code)
            return True if flag==1 else False
        except:
            raise Exception("该key不存在Redis中")
        finally:
            pool.close()
            await pool.wait_closed()
    
    async def set_email_code(self,code:str,email:str) -> bool:
        email_code_md5 = self.__get_md5(code)
        encrypt_code = self.__get_encrypt(email_code_md5)
        pool = await self.__get_pool()
        try:
            email_code_dict = {"email":email, "expired_time":"300","code":str(int(code))}
            await pool.set(encrypt_code,str(email_code_dict),expire=300)
            pool.close()
            await pool.wait_closed()
            return True
        except:
            return False
    
    async def get_code(self,code:str) -> Dict:
        code_md5 = self.__get_md5(code=code)
        encrypt_code = self.__get_encrypt(code_md5)
        pool = await self.__get_pool()
        try:
            value = await pool.get(encrypt_code,encoding='utf-8')
            return eval(value)
        except:
            return {"msg":"Redis不存在该验证码"}
        finally:
            pool.close()
            await pool.wait_closed()
        
    
    async def delete_code(self,code:str) -> bool:
        md5_value = self.__get_md5(code=code)
        encrypt_code = self.__get_encrypt(md5_value)
        try:
            pool = await self.__get_pool()
            await pool.delete(encrypt_code)
            pool.close()
            await pool.wait_closed()
            return True
        except:
            return False
        finally:
            pool.close()
            await pool.wait_closed()

    async def set_code(self, code:str, date: Optional[str]="60") -> bool:
        code_md5 = self.__get_md5(code)
        encrypt_code = self.__get_encrypt(code_md5)
        pool = await self.__get_pool()
        try:
            code_dict = {"expired_time":date,"code":code}
            await pool.set(encrypt_code,str(code_dict),expire=int(date))
            return True
        except:
            return False
        finally:
            pool.close()
            await pool.wait_closed()

    async def set_expired_code(self, token:str, date: Optional[str|int]="60") -> bool:
        pool = await self.__get_pool()
        try:
            code_dict = {"expired_time":date,"token":str(token)}
            await pool.set(token,str(code_dict),expire=int(date))
            return True
        except:
            return False
        finally:
            pool.close()
            await pool.wait_closed()