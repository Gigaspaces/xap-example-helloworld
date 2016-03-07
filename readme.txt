===============================
=== OpenSpaces HelloWorld !! Example ===
===============================

1. MOTIVATION

Learn how to create and run a Processing Unit - a scalable unit of deployment, inside your development environment.
Learn how to use the XAP basic API, by implementing a simple processor and feeder application.

2. COMPONENTS

There are three components in our scenario:

(1) Processor Processing Unit - Processes Message objects as they are written to the data grid (Space) It contains 3 components:
a Polling Container component that listens to new Message objects written to the Space, and a Processor Bean that is delegated the actual processing work by the Polling Container.

(2) Feeder - an application that feeds unprocessed Message objects to the Space, and after a certain period of time, counts and reads one of them at random.

(3) Message Object - a simple POJO with an id and info attributes.

3. WORKFLOW

(1) The helloFeeder application writes 1000 Message objects (POJOs) to the space and waits.

(2) Inside the Processing Unit, the Polling Container continuously removes unprocessed objects from the data grid (one at a time) and hands them to its Processor Bean for processing.

(3) After each Message object has been processed, the Polling Container writes it back to the Space. (Steps 2 & 3 repeat, until there are no more unprocessed Message objects left to process in the Space.)

(4) After waiting 100 milliseconds (to allow for all the objects to be processed), the feeder counts all the processed Message objects inside the Processor Processing Unit’s Space, and reads one of them at random.

3. BUILD AND DEPLOYMENT

The example uses maven as its build tool. It comes with a build script that runs maven automatically.
Running the build script with no parameters within
the current directory will list all the relevant tasks that can be run with this example.

Running 'build.(sh/bat) compile' will compile all the different modules. In case of the Processor
and Feeder modules, it will compile the classes directly into their respective PU structure.

Running 'build.(sh/bat) package' will finalize the processing unit structure of both the Processor
and the Feeder by copying the Common module jar file into the 'lib' directory within the
processing unit structure. In case of the processor module, it will copy the jar file to
'processor/target/org.openspaces.example.helloworld-processor/lib', and will make 'processor/target/org.openspaces.example.helloworld-processor' a ready
to use processing unit.

Running 'build.(sh/bat) intellij' will create run configuration for IntelliJ IDE, allowing you to run the
Processor and the Feeder using IntelliJ run (or debug) targets.

In order to deploy the hello world example onto the Service Grid, simply run gigaspaces agent (gs-agent.sh/bat) which will start
 a GSM and *two* GSCs. Next, 'build.(sh/bat) deploy-processor' will need to be executed. The task
will deploy the org.openspaces.example.helloworld-processor.jar into the running GSM.
Now, run the command 'build.(sh/bat) run-feeder' to start feeding unprocessed data into
the processing unit.

Run the GS-UI in order to see the PU instance deployed.



