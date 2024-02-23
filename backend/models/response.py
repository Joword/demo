from pydantic import BaseModel
from typing import Optional, TypeVar, Any, Dict

T = TypeVar('T')

class ResultBody(BaseModel):
    code: int
    msg: str
    data: Optional[T]