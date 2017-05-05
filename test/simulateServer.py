#!/usr/bin/env python
# -*- coding: utf-8 -*-


import re
import web
import json


server_port = 8520

client_port_1 = server_port + 2
client_port_2 = server_port + 3

class MyApplication (web.application):

  def run (self, port=8080, *middleware):
    func = self.wsgifunc (*middleware)
    return web.httpserver.runsimple (func, ('0.0.0.0', port))



class GetLoggedUsers ():


  def POST ():
    return 

  def GET (*args):
    print args
    return """
<userlist>
  <user id="1" login="test_user_1">
    <client type="heavy" ip="127.0.0.1" port="%d" />
  </user>
  <user id="2" login="test_user_2">
  </user>
  <user id="3" login="test_user_3">
    <client type="heavy" ip="127.0.0.1" port="%d" />
  </user>
</userlist> """ % (client_port_1, client_port_2)


if __name__ == "__main__":

  urls = ("/project/users/(.*)/(.*)", "GetLoggedUsers")
  
  application = MyApplication (urls, globals ())
  application.run (port=server_port)
