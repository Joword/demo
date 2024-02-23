FROM tiangolo/uvicorn-gunicorn-fastapi:python3.11

ARG VERSION=1.0.0

LABEL maintainer="Joword <mengjunhua@genomics.cn>"

# RUN apt-get update && apt-get install -y vim

EXPOSE 9670/tcp

WORKDIR /app

COPY . /app
COPY ./pip.conf /root/.pip/

# Install pip requirements
COPY requirements.txt .
RUN python -m pip install -r requirements.txt