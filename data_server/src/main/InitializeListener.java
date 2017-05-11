package main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import database.DatabaseServer;

@WebListener
public class InitializeListener implements ServletContextListener {

	public static DatabaseServer dbs;

	@SuppressWarnings("static-access")
	public InitializeListener() {
		this.dbs = new DatabaseServer();

	}

	@SuppressWarnings("static-access")
	public final void contextInitialized(final ServletContextEvent sce) {
		this.dbs = new DatabaseServer();
	}

	public final void contextDestroyed(final ServletContextEvent sce) {

	}
}