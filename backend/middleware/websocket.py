"""
@author Joword 
@time: 2024/05/07 15:14:39
@version: 1.0.0
@description: websocket center
"""
import uuid
import time
from typing import Never
from starlette.websockets import WebSocket
from app.middleware.chat import ChatCenter

from app.models.chat.type import InputParamters
from app.commons.utils import SnowFlakeGenerator

class WebsocketCenter(object):
    
    def __init__(self, param: WebSocket) -> Never:
        self.link = param
        self.websocket_id = self.__get_websocket_id
    
    def __get_websocket_id() -> str:
        websocket_id = uuid.uuid1()
        epoch_start = int(time.mktime(time.strptime(time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()),'%Y-%m-%d %H:%M:%S'))) * 1000
        return str(SnowFlakeGenerator(epoch_start=epoch_start,worker_id=websocket_id).get_id()).encode("utf-8")
    
    async def chatting(self) -> Never:
        await self.link.accept()
        while True:
            receive_text = await self.link.receive_text()
            message = InputParamters()
            message.message = eval(receive_text)['content']['question']
            chat_msg = ChatCenter(version="4",msg=message)
            answer_msg = chat_msg.reply
            await self.link.send_text(f"{answer_msg}")
    
    async def fetch_json(self) -> Never:
        await self.link.accept()
        json_data = await self.link.receive_json()
        await self.link.send_json(f"===={json_data}====")
        
    async def fetch_bytes(self) -> Never:
        await self.link.accept()
        bytes_data = await self.link.receive_bytes()
        await self.link.receive_bytes(f"===={bytes_data}====")
    
    