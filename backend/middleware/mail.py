"""
@author Joword 
@time: 2024/07/03 10:34:50
@version: 1.0.0
@description: send mail
"""
import aiosmtplib
from .task import AsyncTask

from config import settings
from email.mime.text import MIMEText
from email.message import EmailMessage
from ..models.types import EmailInputInfo
from email.mime.multipart import MIMEMultipart

class EmailCenter(object):
    def __init__(self, param: EmailInputInfo) -> None:
        self.info = param
        self.send = self.__send_mail()
        
    def _make_email_code_tpl(self) -> str:
        return "<span style=\"font-size:15px;\">Dear user:"+\
            "</span><br /><br/><span style=\"font-size: 15px;\">&nbsp;&nbsp;"+\
                "We received that you are using " + self.info.email.split("@")[0][0:3] + "***@" + self.info.email.split("@")[1] + \
                    " to register for AIGI.&nbsp;if not by yourself, please ignore this message.</span><br />"+\
                        "<span style=\"font-size: 15px;\">&nbsp;&nbsp;Your registered verification code is: <b style=\"font-size:20px;\">" + str(int(self.info.code)) + "</b>. </span><br /><br />"+\
                            "<span style=\"font-size:15px\">Thanks!&nbsp;</span><br /><span style=\"font-size:15px;\">AIGI work team</span>"
    
    def _make_aio_msg(self) -> EmailMessage:
        msg = MIMEMultipart()
        msg['From'] = settings.EMAIL_FROM
        msg['To'] = self.info.email
        msg['Subject'] = self.info.subject
        msg.attach(MIMEText(self._make_email_code_tpl(),"html","utf-8"))
        return msg
    
    def __send_mail(self) -> bool:
        try:
            client = aiosmtplib.SMTP(hostname=settings.EMAIL_SERVER,port=settings.EMAIL_PORT)
            AsyncTask(client.connect())
            AsyncTask(client.login(settings.EMAIL_USERNAME,settings.EMAIL_PASSWORD))
            AsyncTask(client.send_message(self._make_aio_msg()))
            AsyncTask(client.quit())
            #await client.connect()
            #await client.login(settings.EMAIL_USERNAME,settings.EMAIL_PASSWORD)
            #await client.send_message(self._make_aio_msg())
            #await client.quit()
            return True
        except:
            return False
            
        
