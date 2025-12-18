import signal
from subprocess import Popen

class SigForwarder:
    def __init__(self, signals: list[int]) -> "SigForwarder":
        self.events = {}
        self.orig_handlers = {}

        for sig in signals:
            self.orig_handlers[sig] = signal.getsignal(sig)
            signal.signal(sig, self.trigger_events)

    def add_process(self, process: Popen) -> None:
        print(f"Process {process.pid} added to fowarder")
        self.events[process.pid] = process
        # TODO: add hook to cleanup on process termination?

    def remove_process(self, process: Popen) -> None:
        del self.events[process.pid]

    def trigger_events(self, signum, frame) -> None:
        for process in self.events.values():
            print(f"Forwarding {signum} towards pid {process.pid}")
            process.send_signal(signum)
        handler = self.orig_handlers.get(signum)
        if handler:
            handler(signum, frame)
        else:
            print(f"Signal number {signum} not handled")

