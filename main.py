import uvicorn
from backend import create_app

app = create_app()


if __name__ == "__main__":
    uvicorn.run(app="main:app", 
                host="0.0.0.0", 
                port=9670,
                reload=True)
