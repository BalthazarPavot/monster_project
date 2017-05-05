#!/usr/bin/env python
# -*- coding: utf-8 -*-


import re
import web
import json

import random


server_port = 8520

client_port_1 = server_port + 2
client_port_2 = server_port + 3

class MyApplication (web.application):

  def run (self, port=8080, *middleware):
    func = self.wsgifunc (*middleware)
    return web.httpserver.runsimple (func, ('0.0.0.0', port))



class GetLoggedUsers ():
  
  USER1 = """
  <user id="1" login="test_user_1">
    <client type="heavy" ip="127.0.0.1" port="%d" />
  </user>
  """ % client_port_1
  USER2 = """
  <user id="2" login="test_user_2">
  </user>
  """
  USER3 = """
  <user id="3" login="test_user_3">
    <client type="heavy" ip="127.0.0.1" port="%d" />
  </user>
  """ % client_port_2
  XML = "<userlist>" + USER1 + USER2 + USER3 + "</userlist>"

  def POST ():
    return 

  def GET (*args):
    if random.randrange (5) == 0:
      GetLoggedUsers.XML = "<userlist>" + GetLoggedUsers.USER2 + GetLoggedUsers.USER3 + "</userlist>"
    else:
      GetLoggedUsers.XML = "<userlist>" + GetLoggedUsers.USER1 + GetLoggedUsers.USER2 + GetLoggedUsers.USER3 + "</userlist>"
    return GetLoggedUsers.XML


if __name__ == "__main__":

  urls = ("/project/users/(.*)/(.*)", "GetLoggedUsers")
  
  application = MyApplication (urls, globals ())
  application.run (port=server_port)
