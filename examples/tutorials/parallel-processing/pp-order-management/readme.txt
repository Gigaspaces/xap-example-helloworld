----------------------------------------------------------------------------------------------------------
 Purpose
---------

This example illustrates GigaSpaces simple order management system implementation using GigaSpace API. 
The order management system is compiled from a set of processing units that form the entire workflow:

* Client	- a processing unit that writes 'new' Orders into the space and takes 'processed' and 'rejected' orders 
from the space. 

* Validator	- a processing unit that takes 'new' orders from the space, validates them and writes them back as 
'approved' or 'rejected' orders. 

* Processor	- a processing unit that takes 'approved' orders from the space and writes back 'processed' Orders. 

* Enterprise Data Grid comprised of one space instance.

You may start multiple clients on the same machine or different machines. 

You may start multiple validators and processors on the same machine or different machines to improve 
the client overall orders processing throughput.


----------------------------------------------------------------------------------------------------------
 Tutorial Wiki Page
--------------------

This example is explained in detail in the following Wiki tutorial page:
http://www.gigaspaces.com/wiki/display/GS6/Parallel+Processing+Tutorial+-+Order+Management+%28OnePager%29


----------------------------------------------------------------------------------------------------------
 Structure
-----------

The example has four modules:

* Common Module -- includes domain model that is shared between the modules.

* Client Module --  a client processing unit that contains feeding and counting service beans.

* Validator Module -- a validator processing unit that contains an order validation service bean. 

* Processor Module -- a processor processing unit that contains an order processing service bean.

	+-------------+	 +--------------+  +--------------+
	| Client PU   |	 | Validator PU |  | Processor PU |
	| +---------+ |	 | +----------+ |  | +----------+ |
	| |Order    | |	 | |Order     | |  | |Order     | |
	| |Feeder   | |	 | |Validator | |  | |Processor | |
	| |Bean     | |	 | |Bean      | |  | |Bean      | |
	| +---------+ |	 | +----------+ |  | +----------+ |
	| |Order    | |	 +--------------+  +--------------+
	| |Counter  | |    
	| |Bean     | |
	| +---------+ |    
	+-------------+	


-------------------------------------------------------------------------------------------------------------
 Workflow
----------

When the Client processing unit starts, it connects to the Enterprise Data Grid space instance a.k.a the space,
its order feeder starts cycles of writing new order (OrderEvent) objects to the space.
The order object contain 4 attributes: a user name, an order ID, placing client ID, and status.

The space is now fed continuously by the client's feeder with new orders.

When the Validator processing unit starts, it connects to the space, its order validator bean takes 
new orders, sets their status to approved/rejected randomly {simulating a validation mechanism), and writes 
them back to the space.

When the processor processing unit starts, it connects to the space, takes the previously validated and approved 
orders from the space, processes them (by setting their status attribute to 'processed'), and writes them back
to the space.

During the entire process, the client processing unit is connected to the space, and takes all the 'rejected' and 
'processed' orders written to the space.

 
-----------------------------------------------------------------------------------------------------------------	    
 Compiling and preparing for deployment
----------------------------------------

Choose one of the 2 ways of compiling and preparing the 3 modules for deployment:

A. Using scripts:

1. Run <Example Folder>\bin\compile.bat/.sh                  - Compile the modules.
2. Run <Example Folder>\bin\copy_to_deploy_folder.bat/.sh    - Copy ready-to-deploy modules to the deploy folder.


B. Using Ant build tool and the provided standard build.xml file:

1. Running 'build' compiles all the different modules. In the case of the Client, Validator
and Processor modules, 'build' compiles the classes directly into their respective processing unit structure.
2. Running 'dist' finalizes the processing unit structure of all the processing
units by copying the Common Module JAR into the 'shared-lib' directory within the 
processing unit structure. For example in the case of the client module, 'dist' copies the JAR to
'client/pu/pp-oms-client/shared-lib', and makes 'client/pu/pp-oms-client' a ready-to-use processing unit.
3.Running 'copy-local-client', 'copy-local-validator' and 'copy-local-processor' copies the finalized
processing units to the GigaSpaces 'deploy' folder, ready to be deployed onto the Service Grid.


------------------------------------------------------------------------------------------------------------------
 Deployment
------------

To deploy the processing units onto the Service Grid:

>> Starting the Service Grid
1. Start a GSM (Grid Service Manager) by executing <gs home dir>\bin\gsm.bat (.sh)
2. Start a GSC (Grid Service Container) by executing <gs home dir>\bin\gsc.bat (.sh)
3. Start another 3 GSC by repeating step 2 three times (Total of 4 GSCs). 
>> Deploying the Enterprise Data Grid onto the Service Grid
4. Start the GigaSpaces Management Center by executing <gs home dir>\bin\gs-ui.bat (.sh)
5. In the GS-UI, click the 'Deployment, Details' tab.
6. From the 'Deployment' menu, select 'Deploy Application'.
7. Select 'Enterprise Data Grid' and click 'Next'.
8. In the 'Data Grid Name' field, type 'spacePP'.
9. In the 'Cluster schema' field, choose blank (None).
10. Click 'Deploy'.
>> Deploying the Client Processing Unit onto the Service Grid
11. From the 'Deployment' menu, select 'Deploy Application'.
12. Select 'Open Spaces - Processing Unit' and click 'Next'.
13. In the 'Processing Unit Name' field, type 'pp-oms-client'.
14. Click deploy to deploy the client processing unit. 
15. Wait until the Client Processing Unit finish loading.
>> Deploying the other modules (Validator, Processor) onto the Service Grid:
16. Repeat steps 11-15 to deploy the Validator Module. In step 13, type 'pp-oms-validator' as the deployment name.
17. Click deploy to deploy the Validator Module. 
18. Repeat steps 11-15 to deploy the Processor Module. In step 13, type 'pp-oms-processor' as the deployment name.
19. Click deploy to deploy the Processor Module. 



