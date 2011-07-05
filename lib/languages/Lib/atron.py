def new(module):
    return Module(module)

class Module:
    def __init__(self, m):
        self.module = m
    def disconnectAll(self):
        self.module.disconnectAll()
