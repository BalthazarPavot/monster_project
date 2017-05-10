#!/usr/bin/env python
# -*- coding: utf-8 -*-


import re
import web
import json
import time
import sys
import random


server_port = 8520


## 
# Requests dispatcher
## 

class MyApplication (web.application):

  def run (self, port=8080, *middleware):
    func = self.wsgifunc (*middleware)
    return web.httpserver.runsimple (func, ('0.0.0.0', port))


## 
# models
## 

class User ():

  XML_TEMPLATE = """<user%(attributes)s>
  %(client)s
</user>"""

  def __init__ (self, id=0, login="anonymous", password=None, email=None, client=None):
    self.id = id
    self.login = login
    self.password = password
    self.email = email
    self.client = client or Client ()
    self.connected = False

  def xml (self):
    client_xml = self.client.xml () if self.client else ""
    return User.XML_TEMPLATE % {
      "attributes": self.xml_attributes (),
      "client": client_xml
    }

  def xml_attributes (self):
    return ((" id=\"%(id)s\"" if self.id else "") + \
      " login=\"%(login)s\"" + \
      (" email=\"%(email)s\"" if self.email else "")) % self.__dict__


class Client ():

  XML_TEMPLATE = "<client%(attributes)s/>"
  HEAVY = "heavy"
  LIGHT = "light"

  def __init__ (self, type=LIGHT, ip=None, port=None):
    self.type = type
    self.ip = ip
    self.port = port
    self.last_update = int (time.time ())

  def is_heavy (self):
    return self.type.lower () == Client.HEAVY

  def xml (self):
    return Client.XML_TEMPLATE % {
      "attributes": self.xml_attributes (),
    }

  def xml_attributes (self):
    return (" type=\"%(type)s\"" + \
      (" ip=\"%(ip)s\"" if self.ip else "") + \
      (" port=\"%(port)s\"" if self.port else "")) % self.__dict__


class Document ():

  XML_TEMPLATE = """<document%(attributes)s>
  %(permission)s
  %(content)s
</document>"""

  def __init__ (self, id=0, owner_id=0, name=None, perm=None, content=None):
    self.id = id
    self.owner_id = owner_id
    self.name = name
    self.permission = perm or Permission ()
    self.content = content or Content ()

  def xml (self):
    permission_xml = self.permission.xml ()
    content_xml = self.content.xml ()
    return Document.XML_TEMPLATE % {
      "attributes": self.xml_attributes (),
      "permission": permission_xml,
      "content": content_xml,
    }

  def xml_attributes (self):
    return (
      (" id=\"%(id)s\"" if self.id else "") + \
      (" owner_id=\"%(owner_id)s\"" if self.owner_id else "") + \
      (" name=\"%(name)s\"" if self.name else "")) % self.__dict__


class Permission ():

  XML_TEMPLATE = "<permission%(attributes)s />"

  def __init__ (self, user_w=1, user_r=1, group_w=1, group_r=1, other_w=0, other_r=0):
    self.user_w = user_w
    self.user_r = user_r
    self.group_w = group_w
    self.group_r = group_r
    self.other_w = other_w
    self.other_r = other_r

  def xml (self):
    return Permission.XML_TEMPLATE % {
      "attributes": self.xml_attributes (),
    }

  def xml_attributes (self):
    return (
      " user_write=\"%(user_w)d\"" + \
      " user_read=\"%(user_r)d\"" + \
      " group_write=\"%(group_w)d\"" + \
      " group_read=\"%(group_r)d\"" + \
      " other_write=\"%(other_w)d\"" + \
      " other_read=\"%(other_r)d\"") % self.__dict__

class Content ():

  XML_TEMPLATE = "<content>%(text)s</content>"

  def __init__ (self, text=""):
    self.text = text

  def xml (self):
    return Content.XML_TEMPLATE % self.__dict__

## 
# Application context and database
## 

class Context ():

  def __init__ (self):
    self.users = [
      User("1", "user_1", password="12345", email="x@y.z",
        client=Client(type=Client.HEAVY, port=8522, ip="127.0.0.1")),
      User("2", "user_2", password="12345", email="x@y.z"), 
      User("3", "user_3", password="12345", email="x@y.z",
        client=Client(type=Client.HEAVY, port=8523, ip="127.0.0.1")),
    ]
    self.documents = {
      "doc_1": Document (content=Content ("This is the editable content of the doc no 1")), 
      "doc_2": Document (content=Content ("This is the editable content of the doc no 2")), 
      "doc_3": Document (content=Content ("This is the editable content of the doc no 3"))
    }

  def heavy_users (self):
    current_time = int (time.time ())
    return [
      user
      for user in self.users
        if user.client.is_heavy () and \
          user.connected and \
          current_time - user.client.last_update < 10
    ]

  def set_user_client (self, user_info):
    id = user_info.get ("id", None)
    if id:
      user = filter (lambda user:user.id == id, self.users)
      user = user and user[0] or None
      if user:
        user.client.ip = user_info.get ("ip", None)
        user.client.port = user_info.get ("port", None)
        user.client.type = user_info.get ("type", Client.LIGHT)
        user.client.last_update = int (time.time ())


## 
# Controllers (and views)
## 

class GetLoggedUsers ():

  def POST (self, *args):
    user_client = web.input ()
    context.set_user_client (user_client)
    users = self.get_connected_users ()
    xml_users = map (lambda user:user.xml(), users)
    xml_data = '\n'.join (["<userlist>"] + xml_users + ["</userlist>"])
    return xml_data

  def GET (self, *args):
    return
    #users = self.get_connected_users ()
    #xml_users = map (lambda user:user.xml(), users)
    #xml_data = '\n'.join (["<userlist>"] + xml_users + ["</userlist>"])
    #return xml_data

  def get_connected_users (self):
    return context.heavy_users ()


class CreateUser ():

  def POST (self, *args):
    register_form = web.input ()
    if register_form.has_key ("login") and register_form.has_key ("email") and \
        register_form.has_key ("password") and \
        register_form.has_key ("verif_password") and \
        register_form["password"] == register_form["verif_password"]:
      self.register (register_form)
      web.ctx.status = 201 #created
    web.ctx.status = 400 #bad request

  def GET (self, *args):
    return

  def register (self, form):
    context.users.append (User (**form))
    context.logged_users.append (context.users[-1])


class LoginUser ():

  def POST (self, *args):
    login_form = web.input ()
    if not (login_form.has_key ("login") and \
        login_form.has_key ("password")):
      web.ctx.status = 400 #bad request
    else:
      users = filter (lambda user:user.login == login_form["login"],
        context.users)
      for user in users: # there are only one user here, in the real app...
        if user.password == login_form["password"]:
          break
      else:
        web.ctx.status = 422 # Unprocessable Entity (bad password or login)
        return
      user.connected = True
      return user.xml ()

  def GET (self, *args):
    return

  def register (self, form):
    context.users.append (User (**form))
    context.logged_users.append (context.users[-1])

class GetDocument ():

  def POST (self, *args):
    return

  def GET (self, *args):
    form = web.input ()
    doc = context.documents.get (form.get ("project_name", None), None)
    return doc and doc.xml ()

  def register (self, form):
    context.users.append (User (**form))
    context.logged_users.append (context.users[-1])


if __name__ == "__main__":

  sys.stdout = sys.stderr

  urls = (
    "/document/users", "GetLoggedUsers",
    "/document/ask", "GetDocument",
    "/user/new", "CreateUser",
    "/user/login", "LoginUser",
    )

  context = Context ()
  application = MyApplication (urls, globals ())
  application.run (port=server_port)
