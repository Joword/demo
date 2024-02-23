"""
@author Joword 
@time: 2024/01/30 19:05:45
@version: 1.0.0
@description: 使用协程, 获取mongodb所有文档, 若抛异常则分为连接异常与文档异常, 文档异常请检查collection是否存在
"""
import asyncio
from typing import Optional, Dict
from pymongo import MongoClient
from config import settings
from ..exceptions.exception import MongoException

class MongoCollections(object):
    def __init__(self) -> None:
        self.collections = asyncio.run(self._get_collection())

    async def _get_collection(self) -> Dict:
        if settings.MONGODB_COLLECTIONS:
            return {i.replace(".","_"):await self.__connections(i) for i in settings.MONGODB_COLLECTIONS.split(",")}
        raise MongoException("Mongodb文档异常")

    async def __connections(self, db: str) -> Optional[Dict]:
        decoding_uri: str = settings.MONGO_FORMAT_STR.format(settings.MONGO_DECODING_USER,settings.MONGO_DECODING_PASSWORD,settings.MONGO_URL,str(settings.MONGO_PORT),db)
        chat_uri: str = settings.MONGO_FORMAT_STR.format(settings.MONGO_CHAT_USER,settings.MONGO_CHAT_PASSWORD,settings.MONGO_URL,str(settings.MONGO_PORT),db)
        user_uri: str = settings.MONGO_FORMAT_STR.format(settings.MONGO_USERINFO,settings.MONGO_USERINFO_PASSWORD,settings.MONGO_URL,str(settings.MONGO_PORT),db)
        if len(db.split("."))>0:
            connection: MongoClient
            collection_name, collections = db.split(".")[0], db.split(".")[1]
            if collection_name=='aigidb':
                connection = MongoClient(decoding_uri)
            elif collection_name=='paper':
                connection = MongoClient(chat_uri)
            elif collection_name=='user':
                connection = MongoClient(user_uri)
            return connection[collection_name][collections]
        raise MongoException

collections = MongoCollections().collections