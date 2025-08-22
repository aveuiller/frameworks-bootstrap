import asyncio
import subprocess
import sys
import signal
import os
from fastapi import FastAPI

app = FastAPI()

DEFAULT_CMD = ["python", "./subchild.py"]


def sync_subp(cmd):
    try:
        p = subprocess.Popen(cmd)
        p.wait(timeout=10)
    except subprocess.TimeoutExpired:
        print("Timeout expired")
        print('Terminating the whole process group...', file=sys.stderr)
        # os.killpg(os.getpgid(p.pid), signal.SIGTERM)
        return False
    return True


async def run_subp(cmd):
    try:
        p = subprocess.Popen(cmd)
        p.wait(timeout=10)
    except subprocess.TimeoutExpired:
        print("Timeout expired")
        print('Terminating the whole process group...', file=sys.stderr)
        # os.killpg(os.getpgid(p.pid), signal.SIGTERM)
        return False
    return True

@app.get("/popen/{id}")
async def popen(id: int):
    print(f"Handling {id}")
    timeout_s = 10  # how many seconds to wait
    cmd = DEFAULT_CMD
    try:
        print("Hello")
        p = asyncio.subprocess.create_subprocess_exec(
                cmd[0], ' '.join(cmd[1:])
        )
        await asyncio.wait_for(p, timeout=timeout_s)
    except TimeoutError:
        print(f'Timeout for {cmd} ({timeout_s}s) expired', file=sys.stderr)
        print('Terminating the whole process group...', file=sys.stderr)
        os.killpg(os.getpgid(p.pid), signal.SIGTERM)
    return {"id": id}


@app.get("/thread/{id}")
async def asthread(id: int):
    res = await asyncio.to_thread(sync_subp, DEFAULT_CMD)
    return {"id": id, "res": res, "type": "runsync"}


@app.get("/run/{id}")
async def read_root(id: int):
    res = await run_subp(DEFAULT_CMD)
    return {"id": id, "res": res, "type": "runasync"}
