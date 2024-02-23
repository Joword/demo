
from fastapi import APIRouter
from backend.api.acmg import acmgRouter
from backend.api.user import userRouter

router = APIRouter()

router.include_router(router=acmgRouter, tags=['variant messages'])
router.include_router(router=userRouter, tags=['user messages'])
