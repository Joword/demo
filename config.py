"""
@author Joword 
@time: 2024/02/01 17:07:58
@version: 1.0.0
@description: config
"""
import os
import io
import sys
import logging
from contextlib import contextmanager
from functools import lru_cache
from io import StringIO
from typing import Optional

from dotenv.main import load_dotenv, dotenv_values, find_dotenv
from pydantic import BaseSettings, Field

logger = logging.getLogger(__name__)

class Settings(BaseSettings):
    """System configurations."""
    # 系统环境
    ENVIRONMENT: Optional[str] = Field(None, env="ENVIRONMENT")
    # 系统安全秘钥
    SECRET_KEY = ''
    # API版本号
    API_V1_STR = "/api/v1"
    # token过期时间
    ACCESS_TOKEN_EXPIRE_MINUTES = 60 * 24 * 8
    # 算法
    ALGORITHM = "HS256"
    # 产品名称
    PRODUCTION_NAME = {
        "aigi": "AIGI"
    }
    # 加载.env文件的配置
    class Config:
        env_file = ".env"
        case_sensitive = True

class DevConfig(Settings):
    """Development configurations."""
    ## redis
    #REDIS_HOST: Optional[str] = Field(None, env="DEV_REDIS_HOST")
    #REDIS_PORT: Optional[int] = Field(None, env="DEV_REDIS_PORT")
    #REDIS_USERNAME: Optional[str] = Field(None, env="DEV_REDIS_USERNAME")
    #REDIS_PASSWORD: Optional[str] = Field(None, env="DEV_REDIS_PASSWORD")
    #REDIS_DB: Optional[int] = Field(None, env="DEV_REDIS_DB")

    # Mysql
    MYSQL_SERVER: str = Field(None, env="DEV_MYSQL_SERVER")
    MYSQL_USER: Optional[str] = Field(None, env="DEV_MYSQL_USER")
    MYSQL_PASSWORD: Optional[str] = Field(None, env="DEV_MYSQL_PASSWORD")
    MYSQL_DB_NAME: Optional[str] = Field(None, env="DEV_MYSQL_DB_NAME")
    MYSQL_PORT: Optional[int] = Field(None, env="DEV_MYSQL_PORT")

    #Mongodb
    MONGO_FORMAT_STR: Optional[str] = Field(None, env="MONGO_FORMAT_STR")
    MONGO_URL: Optional[str] = Field(None, env="MONGO_URL")
    MONGO_PORT: Optional[int] = Field(None, env="MONGO_PORT")
    MONGO_DECODING_USER: Optional[str] = Field(None, env="MONGO_DECODING_USER")
    MONGO_CHAT_USER: Optional[str] = Field(None, env="MONGO_CHAT_USER")
    MONGO_USERINFO: Optional[str] = Field(None, env="MONGO_USERINFO")
    MONGO_DECODING_PASSWORD: Optional[str] = Field(None, env="MONGO_DECODING_PASSWORD")
    MONGO_CHAT_PASSWORD: Optional[str] = Field(None, env="MONGO_CHAT_PASSWORD")
    MONGO_USERINFO_PASSWORD: Optional[str] = Field(None, env="MONGO_USERINFO_PASSWORD")
    MONGODB_COLLECTIONS: Optional[str] = Field(None, env="MONGODB_COLLECTIONS")

class TestConfig(Settings):
    """Production configurations."""

    REDIS_HOST: Optional[str] = Field(None, env="TEST_REDIS_HOST")
    REDIS_PORT: Optional[int] = Field(None, env="TEST_REDIS_PORT")
    REDIS_USERNAME: Optional[str] = Field(None, env="TEST_REDIS_USERNAME")
    REDIS_PASSWORD: Optional[str] = Field(None, env="TEST_REDIS_PASSWORD")
    REDIS_DB: Optional[int] = Field(None, env="TEST_REDIS_DB")

    QINIU_ACCESS_KEY: Optional[str] = Field(None, env="TEST_QINIU_ACCESS_KEY")
    QINIU_SECRET_KEY: Optional[str] = Field(None, env="TEST_QINIU_SECRET_KEY")
    QINIU_BUCKET_NAME: Optional[str] = Field(
        None, env="TEST_QINIU_BUCKET_NAME")
    QINIU_DOMAIN_NAME: Optional[str] = Field(
        None, env="TEST_QINIU_DOMAIN_NAME")

    TENCENT_SECRET_KEY: Optional[str] = Field(
        None, env="TEST_TENCENT_SECRET_KEY")
    TENCENT_ACCESS_ID: Optional[str] = Field(
        None, env="TEST_TENCENT_ACCESS_ID")

    MYSQL_SERVER: Optional[str] = Field(None, env="TEST_MYSQL_SERVER")
    MYSQL_USER: Optional[str] = Field(None, env="TEST_MYSQL_USER")
    MYSQL_PASSWORD: Optional[str] = Field(None, env="TEST_MYSQL_PASSWORD")
    MYSQL_DB_NAME: Optional[str] = Field(None, env="TEST_MYSQL_DB_NAME")
    MYSQL_PORT: Optional[int] = Field(None, env="TEST_MYSQL_PORT")

class ProdConfig(Settings):
    """Production configurations."""

    REDIS_HOST: Optional[str] = Field(None, env="PROD_REDIS_HOST")
    REDIS_PORT: Optional[int] = Field(None, env="PROD_REDIS_PORT")
    REDIS_USERNAME: Optional[str] = Field(None, env="PROD_REDIS_USERNAME")
    REDIS_PASSWORD: Optional[str] = Field(None, env="PROD_REDIS_PASSWORD")
    REDIS_DB: Optional[int] = Field(None, env="PROD_REDIS_DB")

    QINIU_ACCESS_KEY: Optional[str] = Field(None, env="PROD_QINIU_ACCESS_KEY")
    QINIU_SECRET_KEY: Optional[str] = Field(None, env="PROD_QINIU_SECRET_KEY")
    QINIU_BUCKET_NAME: Optional[str] = Field(
        None, env="PROD_QINIU_BUCKET_NAME")
    QINIU_DOMAIN_NAME: Optional[str] = Field(
        None, env="PROD_QINIU_DOMAIN_NAME")

    TENCENT_SECRET_KEY: Optional[str] = Field(
        None, env="PROD_TENCENT_SECRET_KEY")
    TENCENT_ACCESS_ID: Optional[str] = Field(
        None, env="PROD_TENCENT_ACCESS_ID")

    MYSQL_SERVER: Optional[str] = Field(None, env="PROD_MYSQL_SERVER")
    MYSQL_USER: Optional[str] = Field(None, env="PROD_MYSQL_USER")
    MYSQL_PASSWORD: Optional[str] = Field(None, env="PROD_MYSQL_PASSWORD")
    MYSQL_DB_NAME: Optional[str] = Field(None, env="PROD_MYSQL_DB_NAME")
    MYSQL_PORT: Optional[int] = Field(None, env="PROD_MYSQL_PORT")

class FactoryConfig:
    """Returns a config instance dependending on the ENV_STATE variable."""
    def __init__(self, env_state: Optional[str]):
        self.env_state = env_state
    def __call__(self):
        if self.env_state == "development":
            return DevConfig()
        elif self.env_state == "production":
            return ProdConfig()
        elif self.env_state == "testing":
            return TestConfig()

@lru_cache
def get_configs():
    """加载一下环境文件, 但存在缓存"""
    load_dotenv(find_dotenv(), override=True)
    return FactoryConfig(Settings().ENVIRONMENT)()

settings = get_configs()

print(settings)
