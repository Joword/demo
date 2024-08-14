"""
@author Joword 
@time: 2024/07/08 17:47:43
@version: 1.0.0
@description: 验证码生成
"""
import random, string
from PIL import Image
from io import BytesIO
from base64 import b64encode
from captcha.image import ImageCaptcha

class Captcha:
    def __init__(self) -> None:
        self.__path = "./app/files/img/{0}.jpg"
        self.code = "".join(random.sample(string.ascii_letters + string.digits, 4))
        self.generating = self.__generate_code()
    
    def __generate_code(self):
        image = ImageCaptcha().generate_image(str(self.code))
        image.save(self.__path.format(str(self.code)))
    
    def byte_stream(self) -> bytes:
        image = Image.open(self.__path.format(self.code))
        return b64encode(image.tobytes())
    