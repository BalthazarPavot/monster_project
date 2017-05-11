package com.monsterProject.forms;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.monsterProject.beans.Utilisateur;
import com.monsterProject.beans.Message;


public final class chatBoxForm {
	 private static final String CHAMP_USERMSG  = "usermsg";
	// private static final String CHAMP_NOMCHATTER   = "nomChatter";

	 public Message EnvoyerMessage( HttpServletRequest request ) {
	        /* Récupération des champs du formulaire */
		  String usermsg = getValeurChamp( request, CHAMP_USERMSG);
		  
		 // String nomChatter = getValeurChamp( request, CHAMP_NOMCHATTER);
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		  Date date = new Date();
		  System.out.println(dateFormat.format(date)); 

		   Message message = new Message();
		   message.setTextmsg(usermsg);
		   message.setTime(dateFormat.format(date));
		  // message.setChatterName(nomChatter);
		//  sessionScope.sessionUtilisateur.email= usermsg;
		  // Utilisateur utilisateur = new Utilisateur();
		  // utilisateur.setMsg(message);
	        return message;
	    }
	 
	private  static String getValeurChamp(HttpServletRequest request, String nomChamp) {
	    String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
	}
	

}
