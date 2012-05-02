package org.openspaces.example.data.deploy;

import java.util.concurrent.TimeUnit;

import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.application.Application;

public class UndeployDataApplication {

	/**
	 * Un-deploys the data application example 
	 */
	public static void main(String[] args) {
		
		final Admin admin = createAdmin();
		
		final Application application = admin.getApplications().waitFor("data-app",1,TimeUnit.MINUTES);
		if (application == null) {
			throw new IllegalStateException("data-app is not deployed");
		}
		
		if (!application.undeployAndWait(1,TimeUnit.MINUTES)) {
			throw new IllegalStateException("data-app undeploy failed");
		}
	}

	private static Admin createAdmin() {
		final Admin admin = new AdminFactory().useDaemonThreads(true).create();
		return admin;
	}
	
	

}
