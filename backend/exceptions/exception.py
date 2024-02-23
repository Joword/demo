
class AuthenticationError(Exception):
    """
    未认证
    """
    def __init__(self, msg: str = "Unauthorized"):
        self.msg = msg


class AuthorizationError(Exception):
    """
    未授权
    """
    def __init__(self, msg: str = "Forbidden"):
        self.msg = msg

class MongoException(Exception):
    '''
    mongo connection exception
    '''
    def __init__(self, msg: str = "Connection fail"):
        self.msg = msg