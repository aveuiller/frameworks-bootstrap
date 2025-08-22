import asyncio
import subprocess
import sys
import signal
import os
from fastapi import FastAPI

app = FastAPI()

DEFAULT_CMD = ["python", "./subchild.py"]
TIMEOUT_S = 10

def sync_subp(cmd, timeout):
    try:
        p = subprocess.Popen(cmd)
        p.wait(timeout=timeout)
    except subprocess.TimeoutExpired:
        print("Timeout expired")
        print('Terminating the whole process group...', file=sys.stderr)
        # os.killpg(os.getpgid(p.pid), signal.SIGTERM)
        return False
    return True


async def run_subp(cmd, timeout):
    try:
        p = subprocess.Popen(cmd)
        p.wait(timeout=timeout)
    except subprocess.TimeoutExpired:
        print("Timeout expired")
        print('Terminating the whole process group...', file=sys.stderr)
        # os.killpg(os.getpgid(p.pid), signal.SIGTERM)
        return False
    return True


@app.get("/popen/{id}")
async def popen(id: int):
    print(f"Handling {id} - Direct Popen")
    res = sync_subp(DEFAULT_CMD, TIMEOUT_S)
    return {"id": id, "res": res, "type": "popen"}


@app.get("/asyncio/{id}")
async def asyncio_subprocess(id: int):
    print(f"Handling {id} - Async Subprocess")
    cmd = DEFAULT_CMD
    timeout_s = TIMEOUT_S
    res = True
    try:
        p = asyncio.subprocess.create_subprocess_exec(
                cmd[0], ' '.join(cmd[1:])
        )
        await asyncio.wait_for(p, timeout=timeout_s)
    except TimeoutError:
        print(f'Timeout for {cmd} ({timeout_s}s) expired', file=sys.stderr)
        print('Terminating the whole process group...', file=sys.stderr)
        # os.killpg(os.getpgid(p.pid), signal.SIGTERM)
        res = False
    return {"id": id, "res": res, "type": "asyncio-subprocess"}


@app.get("/thread/{id}")
async def asthread(id: int):
    print(f"Handling {id} - Synchronous Subprocess to Asyncio Thread")
    res = await asyncio.to_thread(sync_subp, DEFAULT_CMD, TIMEOUT_S)
    return {"id": id, "res": res, "type": "runsync"}


@app.get("/run/{id}")
async def read_root(id: int):
    print(f"Handling {id} - Async run subprocess")
    res = await run_subp(DEFAULT_CMD, TIMEOUT_S)
    return {"id": id, "res": res, "type": "runasync"}
