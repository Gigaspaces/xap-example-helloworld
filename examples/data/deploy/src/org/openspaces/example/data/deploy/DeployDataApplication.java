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
import org.openspaces.admin.pu.ProcessingUnitDeployment;
import org.openspaces.admin.pu.dependency.ProcessingUnitDeploymentDependenciesConfigurer;
import org.openspaces.admin.space.SpaceDeployment;


public class DeployDataApplication {

	/**
	 * Deploys the data application example 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		
		final File feederArchive = getFeederArchiveFile(args);
		
		final GridServiceManager gsm = getGridServiceManager();
		
		Application dataApp = gsm.deploy(
		  
				new ApplicationDeployment("data-app")

			  .addProcessingUnitDeployment(
			    new ProcessingUnitDeployment(feederArchive)
			    .name("feeder")

//			    .addDependency("space"))
			    
			    .addDependencies(
			    	new ProcessingUnitDeploymentDependenciesConfigurer()
                	.dependsOnMinimumNumberOfDeployedInstancesPerPartition("space",1)
                	.create()))
	
			  .addProcessingUnitDeployment(
			    new SpaceDeployment("space").partitioned(1, 1).maxInstancesPerVM(1))
		);

		ProcessingUnit feeder = dataApp.getProcessingUnits().getProcessingUnit("feeder");
		feeder.waitFor(1);
	}

	private static GridServiceManager getGridServiceManager() {
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

	private static File getFeederArchiveFile(String[] args)
			throws FileNotFoundException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Usage: java DeployDataApplication.class feeder.jar");
		}
		final File feederArchive = new File(args[0]);
		if (!feederArchive.exists()) {
			throw new FileNotFoundException("File Not Found: " + feederArchive.getAbsolutePath());
		}
		return feederArchive;
	}

}
