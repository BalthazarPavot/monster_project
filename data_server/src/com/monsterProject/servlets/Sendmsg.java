package com.monsterProject.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.monsterProject.beans.Message;
import com.monsterProject.beans.Utilisateur;
import com.monsterProject.forms.ConnexionForm;
import com.monsterProject.forms.chatBoxForm;
import com.monsterProject.servlets.Connexion;


/**
 * Servlet implementation class Sendmsg
 */
public class Sendmsg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  public static final String VUE = "/restreint/accesRestreint.jsp";

    public Sendmsg() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Cast des objets request et response */
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		/* Préparation de l'objet formulaire */
        chatBoxForm form = new chatBoxForm();
        /* Traitement de la requête et récupération du bean en résultant */
       Message message = form.EnvoyerMessage( request );
       /* Récupération de la session depuis la requête */
       HttpSession session = req.getSession();
     // String att= (String) session.getAttribute("sessionUtilisateur");
      
       session.setAttribute( "usermsg",message );
     //  request.setAttribute("usermsg",message );
       
       this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
       
	}

}
