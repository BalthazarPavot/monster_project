
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Accès restreint</title>
      
<link type="text/css" rel="stylesheet" href="chatbox.css" />
    </head>
    <body>
        <p>Vous êtes connecté(e) avec l'adresse ${sessionScope.sessionUtilisateur.email}, vous avez bien accès à l'espace restreint.</p>
	      <p>mot de passe ${sessionScope.sessionUtilisateur.motDePasse}, vous avez bien accès à l'espace restreint.</p>
	
	<div id="wrapper">
    <div id="menu">
        <p class="welcome">Welcome, ${sessionScope.sessionUtilisateur.email} <b></b></p>
        <p class="logout"><a id="exit" href="/MonsterProject/deconnexion">Deconnexion</a></p>
        <div style="clear:both"></div>
    </div>
     
    <div id="chatbox"></div>
     
    <form name="message" method="post" action="accesRestreint.jsp">
        <input  type="text" id="usermsg" name="usermsg" value="<c:out value="${message.textmsg}"/>" size="63" />
      
        <input name="submitmsg" type="submit"  id="submitmsg" value="send" />
              
      
	     <p>DATE envoi ${sessionScope.sessionUtilisateur.email},</p>
	
    </form>
</div>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>
<script type="text/javascript">
// jQuery Document
$(document).ready(function(){
 
});
</script>   
    </body>
</html>
