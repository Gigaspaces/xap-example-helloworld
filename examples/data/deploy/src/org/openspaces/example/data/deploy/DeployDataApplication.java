package org.openspaces.example.data.deploy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.application.Application;
import org.openspaces.admin.application.ApplicationDeployment;
import org.openspaces.admin.gsm.GridServiceManager;
import org.openspaces.admin.pu.ProcessingUnit;


public class DeployDataApplication {

	/**
	 * Deploys the data application example 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println("Looking for Manager...");
		final GridServiceManager gsm = waitForGridServiceManager();
		File applicationFolder = getApplicationFolder(args);
		
		System.out.println("Deploying " + applicationFolder);
		ApplicationDeployment deployment = new ApplicationDeployment(applicationFolder);
		Application dataApp = gsm.deploy(deployment);
		for (ProcessingUnit pu : dataApp.getProcessingUnits()) {
			pu.waitFor(pu.getTotalNumberOfInstances());
		}
	}

	private static GridServiceManager waitForGridServiceManager() {
		
		final Admin admin = createAdmin();
		final GridServiceManager gsm = admin.getGridServiceManagers().waitForAtLeastOne(1,TimeUnit.MINUTES);
		if (gsm == null) {
			throw new IllegalStateException("Failed to discover GSM");
		}
		return gsm;
	}

	private static Admin createAdmin() {
		final Admin admin = new AdminFactory().useDaemonThreads(true).create();
		return admin;
	}

	private static File getApplicationFolder(String[] args)
			throws FileNotFoundException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Usage: java DeployDataApplication.class [data-application-folder]");
		}
		final File appFolder = new File(args[0]);
		if (!appFolder.exists()) {
			throw new FileNotFoundException("File Not Found: " + appFolder.getAbsolutePath());
		}
		return appFolder;
	}

}
