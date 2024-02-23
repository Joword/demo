"""
@author Joword 
@time: 2024/01/31 10:48:54
@version: 1.0.0
@description: 初始化mysql/mongodb/redis等数据库连接
"""
import redis
from config import settings
from .mongo import collections
from tortoise.contrib.fastapi import register_tortoise


# mysql数据库url
SQLALCHEMY_DATABASE_URL = "mysql://{}:{}@{}:{}/{}?charset=utf8".format(
    settings.MYSQL_USER,
    settings.MYSQL_PASSWORD,
    settings.MYSQL_SERVER,
    settings.MYSQL_PORT,
    settings.MYSQL_DB_NAME
)

# 数据库迁移配置
TORTOISE_ORM = {
    "connections": {"default": SQLALCHEMY_DATABASE_URL},
    "apps": {
        "models": {
            "models": ["aerich.models", "backend.models.model"],
            # 须添加“aerich.models” 后者“models”是上述models.py文件的路径
            "default_connection": "default",
        },
    },
}


# db = databases.Database(SQLALCHEMY_DATABASE_URL)
# metadata = sqlalchemy.MetaData()
# engine = sqlalchemy.create_engine(SQLALCHEMY_DATABASE_URL, pool_size=20, max_overflow=0, echo=True)
# metadata.create_all(engine)


def db_init(app):
    register_tortoise(
        app,
        db_url=SQLALCHEMY_DATABASE_URL,
        modules={"models": ["backend.schemas.model"]},
        generate_schemas=True,
        add_exception_handlers=True,
    )

def mongodb_init():
    return collections

#pool = redis.ConnectionPool(
#    host=settings.REDIS_HOST,
#    port=settings.REDIS_PORT,
#    # password=settings.REDIS_PASSWORD,
#    db=settings.REDIS_DB,
#)
#redis_session = redis.Redis(connection_pool=pool)


#if __name__ == "__main__":
#    token = {
#        "expiredTime": "1506433269",
#        "expiration": "2024-01-23T00:40:09Z",
#        "credentials": {
#            "sessionToken": "sdadffwe2323er4323423",
#            "tmpSecretId": "VpxrX0IMC pHXWL0Wr3KQNCqJix1uhMqD",
#            "tmpSecretKey": "VpxrX0IMC pHXWL0Wr3KQNCqJix1uhMqD"
#        }
#    }

#    import pickle
#    redis_session.setex("user", 100, pickle.dumps(token))
#    print(pickle.loads(redis_session.get("user")))

