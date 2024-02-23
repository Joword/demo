"""
@author Joword 
@time: 2024/01/31 11:41:24
@version:  1.0.0
@description: 初始化文件
"""
from fastapi import FastAPI

#from backend.middleware import middleware_init
from .routers import router_init
from backend.logs import log_init, sys_log
from backend.schemas import db_init
from backend.commons.utils import write_log
from .schemas import mongodb_init


def conf_init(app):
    from config import settings
    sys_log.info(msg=f'Start app with {settings.ENVIRONMENT} environment')
    if settings.ENVIRONMENT == 'production':
        app.docs_url = None
        app.redoc_url = None
        app.debug = False


async def start_event():
    await write_log(msg='AIGI start')


async def shutdown_event():
    await write_log(msg='AIGI close')


def create_app():
    app = FastAPI(title="AIGI",
                  description="智能基因组解读平台接口文档",
                  version="1.0.0",
                  on_startup=[start_event],
                  on_shutdown=[shutdown_event])

    # 初始化日志
    log_init()
    # 加载配置
    conf_init(app)
    # 初始化路由配置
    router_init(app)
    ## 初始化中间件
    #middleware_init(app)
    ## 建表
    db_init(app)
    # MongoDB初始化
    mongodb_init()

    return app
