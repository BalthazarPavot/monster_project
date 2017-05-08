<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <link type="text/css" rel="stylesheet" href="///style.css" />
    </head>
    <body>
   <div class="form">
      
      <ul class="tab-group">
        <li class="tab active"><a href="inscription">Sign Up</a></li>
        <li class="tab"><a href="connexion">Log In</a></li>
      </ul>
      
      <div class="tab-content">
        <div id="login">   
          <h1>Welcome Back!</h1>
          
          <form  action="connexion" method="post">
          
            <div class="field-wrap">
            <label for="nom">
              Email Address<span class="req">*</span>
            </label>
            <input type="email"required autocomplete="off"  value="<c:out value="${utilisateur.email}"/>"  />
              
                <span class="erreur">${form.erreurs['email']}</span>
                
            
          </div>
          
          <div class="field-wrap">
            <label for="motdepasse">
              Password<span class="req">*</span>
            </label>
            <input type="password" id="motdepasse" name="motdepasse" value="" required autocomplete="off"/>
            <span class="erreur">${form.erreurs['motdepasse']}</span>

          </div>
          
          <p class="forgot"><a href="#">Forgot Password?</a></p>
          
         <input type="submit" value="Connexion" class="sansLabel"  />
                
             <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                
                <%-- Vérification de la présence d'un objet utilisateur en session --%>
                <c:if test="${!empty sessionScope.sessionUtilisateur}">
                    <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
                    <p class="succes">Vous êtes connecté(e) avec l'adresse : ${sessionScope.sessionUtilisateur.email}</p>
                </c:if>
      
          </form>

        </div>
        
      </div><!-- tab-content -->
      
</div> <!-- /form -->
 
    <script type="text/javascript" src="index.js"></script>

</body>
 