=====================================================================
eXtreme Application Platform - Open Spaces Order Management Tutorial
=====================================================================


PURPOSE
--------

The purpose of this tutorial is to introduce the full concept of SBA (Space Based Architecture), 
and how to easily write stateful applications, deploy them and then scale them out linearly. 
In GigaSpaces, this is done using Open Spaces, a framework that greatly simplifies development, 
deployment and scaling by using Spring capabilities and configuration.

 Tutorial
------------
This example is described in details in the following wiki tutorial page:
http://www.gigaspaces.com/wiki/display/XAP96/XAP+Order+Management+Tutorial

STRUCTURE
----------

The example has four modules:

* Common Module -- includes domain model and a DAO interface that are shared between the modules.

* Runtime Module -- a processing unit that validates new order event objects 
and processes approved order event objects. Data objects are validated and processed using event containers.

* Feeder/Loader Module -- a processing unit that loads account data objects, and then feeds new order event objects. The order event objects are validated by the Runtime Module's validator bean, and if approved, processed by the Runtime Module's processor bean. 

* Stats Module -- a processing unit that listens for, writes, and changes the order event and account data objects, and outputs these events.

	+-------------+		+--------------+	+------------------+
	| Stats PU 	  |		| Runtime PU   |    | Feeder/Loader PU |
	| +---------+ |		| +----------+ |	| +--------------+ |
	| |Order 	| |		| |Embedded	 | |	| |Order		 | |
	| |Counter	| |		| |Space	 | |	| |Feeder		 | |
	| +---------+ |		| +----------+ |	| +--------------+ |
	| |Account	| |		| |Order	 | |	| |Account		 | |
	| |Counter  | |		| |Validator | |	| |Loader		 | |
	| +---------+ |     | +----------+ |	| +--------------+ |
	+-------------+		| |Order	 | |	+------------------+
						| |Processor | |
						| +----------+ |
						+--------------+

Note: The Feeder/Loader Module and Stats Module do not necessarily have to be processing units.
						
WORKFLOW
--------

When the Feeder/Loader PU starts, its order feeder bean preloads 100 
Account objects, with unique usernames and a fixed balance, to the runtime processing unit.
The goal of preloading is to populate the in-memory data grid (the runtime processing unit's embedded space) with the data objects.

After preloading is finished, the Feeder/Loader Processing Unit's order feeder starts cycles of writing order objects to the
runtime processing unit. The order objects contain a username, buy or sell operation flag, status and price attributes.

The runtime processing unit is now populated with accounts as data objects, and is fed continuously by the feeder with new orders.
Inside the processing unit, the order validator bean takes the new orders, and checks them by looking for the 
specific username in the preloaded account. If it doesn't find the username, the order is rejected; otherwise the order is approved.
The processor bean takes the approved orders, and updates the user account with the new balance (unless it is a buy order and there 
are insufficient funds for that account; in this case the order is rejected).

During the entire process, the stats processing unit is connected to the runtime processing unit, and receives notifications
when an account balance is written or updated; when a new order is written to the runtime processing unit; or when order status 
is changed (approved or rejected due to missing account or insufficient funds).
 
	    
COMPILING AND PREPARING FOR DEPLOYMENT
--------------------------------------

Choose one of the 2 ways of compiling and preparing the 3 modules for deployment:

A. Using scripts:

1. Run <Example Folder>\bin\compile.bat/.sh                  - Compile the modules.
2. Run <Example Folder>\bin\copy_to_deploy_folder.bat/.sh    - Copy ready to deploy modules to the deploy folder.

B. Using Ant as its build tool and provides a standard build.xml file:

1. Running 'build' compiles all the different modules. In the case of the Runtime, Feeder
and Stats modules, 'build' compiles the classes directly into their respective processing unit structure.
2. Running 'dist' finalizes the processing unit structure of all the processing
units by copying the Common Module JAR into the 'lib' directory within the 
processing unit structure. In the case of the runtime module, 'dist' copies the JAR to
'runtime/pu/oms-runtime/lib', and makes 'runtime/pu/oms-runtime' a ready-to-use processing unit.
3.Running 'copy-local-runtime', 'copy-local-feeder' and 'copy-local-stats' copies the finalized
processing units to the GigaSpaces 'deploy' folder, ready to be deployed onto the Service Grid.

DEPLOYMENT
----------

To deploy the processing units onto the Service Grid:
1. Start a GSM and *two* GSCs.
>> Deploying the Runtime Processing Unit
2. Start the GigaSpaces Management Center by executing <gs home dir>\gs-ui.bat (.sh)
3. From the 'Launch' menu, select 'SBA Application - Processing Unit...'.
4. In the 'Processing Unit' drop-down list select 'oms-runtime'.
5. In the 'Cluster schema' drop-down list select 'partitioned'.
7. In the 'Number of Instances' field, type '2' to create two processing units.
8. In the 'Backups' field, type '1' for one backup per instance.
10. Click deploy to deploy the runtime processing units. 
11. Wait until the Runtime Processing Units finish loading.
>> Deploying the other modules:
12. Repeat steps 3,4,7 to deploy the Feeder Module. In step 6 select 'oms-feeder' as the deployment name.
13. Click deploy to deploy the Feeder Module. 
14. Repeat steps 3,4,7 to deploy the Stats Module. In step 6 select 'oms-stats' as the deployment name.
15. Click deploy to deploy the Stats Module. 
>> Optional: viewing deployment
12. In the GS-UI, click the 'Deployed Processing Units' tab right click on 'oms-stats [1]' tree node
 and select "Show details of oms-stats [1]..." popup menu item, in opened dialog you will see graph with three monitors
counting the order events written, processed, and rejected due to missing account. 


