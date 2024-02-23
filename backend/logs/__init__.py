#!/usr/bin/python
# -*- coding: utf-8 -*-

import logging
from logging import handlers


sys_log = logging.getLogger('Artificial Intellegent Genome Interpretation')
sys_log.setLevel(level=logging.DEBUG)


def log_init():
    sys_log.setLevel(level=logging.DEBUG)
    formatter = logging.Formatter(
        '进程ID:%(process)d - '
        '线程ID:%(thread)d- '
        '日志时间:%(asctime)s - '
        '代码路径:%(pathname)s:%(lineno)d - '
        '日志等级:%(levelname)s - '
        '日志信息:%(message)s'
    )
    sys_log.handlers.clear()
    file_handler = handlers.TimedRotatingFileHandler('./logs/aigi_logs.log', encoding='utf-8', when='W6')
    file_handler.setLevel(level=logging.INFO)
    file_handler.setFormatter(formatter)
    sys_log.addHandler(file_handler)
