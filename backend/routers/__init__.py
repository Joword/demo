
from config import settings
from backend.routers import api


def router_init(app):
    app.include_router(
        api.router,
        prefix=settings.API_V1_STR,
        tags=["aigi router"],
        responses={404: {"description": "Not found"}},
    )