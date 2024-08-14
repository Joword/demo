"""
@author Joword 
@time: 2024/07/17 18:32:31
@version: 1.0.0
@description: jwt auth
"""
import jwt
import datetime

from config import settings
from typing import Never, Dict, Any

class Auth(object):
    
    def __init__(self) -> Never:
        self.key = settings.JWT_SALT
    
    @staticmethod
    def generate_jwt(payload: Dict[str, Any], key: str=settings.JWT_SALT, alg='HS256') -> str:
        return jwt.encode(payload, key, algorithm=alg)
    
    @staticmethod
    def parse_jwt(token: str, key: str=settings.JWT_SALT, alg = ['HS256']) -> Dict[str, Any]:
        try:
            return jwt.decode(token, key, algorithms=alg)
        except jwt.ExpiredSignatureError:
            raise jwt.ExpiredSignatureError
        except jwt.InvalidTokenError:
            raise jwt.InvalidTokenError