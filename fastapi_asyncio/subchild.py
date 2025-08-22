# import subprocess

# for i in range(100):
#    subprocess.run(["/usr/sbin/sleep", "60"])
import time

a = 1
time.sleep(5)
# for _ in range(1_000_000_000_000):
for _ in range(1_000):
    a = a * 170

print("DONE WAITING")
