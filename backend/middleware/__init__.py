#!/usr/bin/python
# -*- coding: utf-8 -*-
"""
@author Joword 
@time: 2024/04/24 17:44:14
@version: 1.0.0
@description: middleware_init
"""
import re
import json
from config import settings
from ..commons.auth import Auth
from ..middleware.redis import RedisPool
from starlette.datastructures import MutableHeaders
from starlette.responses import Response, JSONResponse
from starlette.middleware.cors import CORSMiddleware
from fastapi import Request, FastAPI, HTTPException, status, Response
from typing import Callable, Awaitable, List
from starlette.middleware.base import BaseHTTPMiddleware, RequestResponseEndpoint

from ..commons.constant import ResultCode

class DispatchHttp(BaseHTTPMiddleware):
    def __create_receive(self, body: bytes):
        async def receive() -> dict:
            return {"type": "http.request", "body": body, "more_body": False}
        return receive

    def __override_reqeust(self, request: Request, body: bytes) -> Request:
        body_scope = {**request.scope}
        body_header = MutableHeaders(headers=dict(request.headers))
        body_header['content-length'] = str(len(body))
        body_header['content-type'] = "application/json"
        body_scope['headers'] = list(body_header.items())
        return Request(scope=body_scope, receive=self.__create_receive(body))
    
    async def dispatch(self, req: Request, call_next: Callable[[Request], Awaitable[Response]]) -> Response:
        variantId: str = r'/^(chr)?[\dXY]{1,2}-\d+-[ATCGN]+-[ATCG]+$/'
        white_list: List = settings.WHITE_LIST.split(",")
        logout_status, auto_manual, tables = "/user/logout", "/switch/variant", "/interpretation/clztable"
        if re.match(r"^/static/.*$", req.url.path):
            return await call_next(req)
        if req.url.path in white_list:
            return await call_next(req)
        redis_pool = RedisPool()
        auth_token: str = req.headers.get("Authorization")
        print(auth_token)
        if auth_token:
            try:
                man, token = auth_token.split(": ")
                if man.lower()!="bearer":
                    return JSONResponse(
                        status_code=status.HTTP_401_UNAUTHORIZED,
                        content="Invalid token person",
                        headers={"www-Authorization":"Bearer"},
                    )
                if req.url.path == logout_status:
                    return JSONResponse(
                        status_code=status.HTTP_200_OK,
                        content={"code":ResultCode.LOGOUT, "msg":"user logout"},
                        headers={"Authorization":"Bearer: None"})
                redis_status = await redis_pool.exists_code(token)
                if redis_status:
                    if req.url.path == auto_manual:
                        if redis_status:
                            return JSONResponse(
                                status_code=status.HTTP_200_OK,
                                content={"code":ResultCode.RESULT_SUCC,"msg":"Token is fine."},
                                headers={"Authorization":"Bearer: "+token}
                            )
                    if req.url.path == tables:
                        if req.method=="POST":
                            print(token)
                            req_params = await req.json()
                            auth_parser = Auth()
                            req_params['u'] = str(auth_parser.parse_jwt(token=token)['userId'])
                            req_body = self.__override_reqeust(request=req, body=json.dumps(req_params).encode("utf-8"))
                            return await call_next(req_body)
                    res = await call_next(req)
                    return res
                else:
                    return JSONResponse(
                        status_code=status.HTTP_203_NON_AUTHORITATIVE_INFORMATION,
                        content={"code":ResultCode.TOKEN_EXPIRED,"msg":"Token is expired."},
                        headers={"Authorization":"Bearer: None"}
                    )
            except(ValueError, HTTPException):
                return JSONResponse(
                    status_code=status.HTTP_401_UNAUTHORIZED,
                    content="Invalid token",
                    headers={"Authorization":"Bearer: None"},
                )
        else:
            if req.url.path == tables:
                if req.method=="POST":
                    body = await req.json()
                    body['u'] = "auto"
                    request_body = self.__override_reqeust(request=req, body=json.dumps(body).encode("utf-8"))
                    return await call_next(request_body)
            return JSONResponse(
                status_code=status.HTTP_401_UNAUTHORIZED,
                content="No auth",
                headers={"Authorization":"Bearer: None"},
            )

# 指定允许跨域请求的url
origins = [
    "*"
]


def middleware_init(app):
    app.add_middleware(
        CORSMiddleware,
        allow_origins=origins,
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )