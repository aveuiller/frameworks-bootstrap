# FastAPI & asyncio

## Run Server

Dev server directly with FastAPI
```bash
$ fastapi dev server.py
```

Uvicorn with configurations
```bash
# Run simple server
$ uvicorn server:app

# Customize worker processes and concurrency per worker
$ uvicorn --workers 2 --limit-concurrency 2 server:app
```

## Query Server 

```bash
# Send a single request
$ curl -i localhost:8000/run/1

# Send n requests to the server
$ END=8; for i in {1..$END}; do curl  -i localhost:8000/run/$i&; done;
 ```
