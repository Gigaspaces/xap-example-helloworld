/*
 * Copyright 2007 GigaSpaces Technologies LTD. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */
package com.gigaspaces.examples.tutorials.topologies;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.events.SpaceDataEventListener;
import org.openspaces.events.notify.SimpleNotifyContainerConfigurer;
import org.openspaces.events.notify.SimpleNotifyEventListenerContainer;
import org.springframework.transaction.TransactionStatus;

import com.j_spaces.core.IJSpace;


public class NotifiedReader {
	
	private static SimpleNotifyEventListenerContainer notifyContainer; 
		

	/**
	 * Connect to space and register for notifications
	 * @param args
	 */
	public static void listen(String url){
		

		//	Connect to space
		//	----------------
		IJSpace space = new UrlSpaceConfigurer(url).space(); // Connect to the space using the URL
		GigaSpace gigaSpace = new GigaSpaceConfigurer(space).gigaSpace(); // Use gigaSpace simplified API

		//	Register the listener for notification on every account write to the space
		//	-------------------------------------------------------------------------


		notifyContainer = new SimpleNotifyContainerConfigurer(gigaSpace)
		.template(new Account()).fifo(true).notifyWrite(true)
		.eventListener(new SpaceDataEventListener<Account>() {
			public void onEvent(Account account, GigaSpace gigaSpace,
					TransactionStatus txStatus, Object source) {
				 
				System.out.println("Read account info ["+account.toString()+"]");

			}
		}).notifyContainer();
		System.out.println("Listening...");	
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("Usage: <URL>");
			System.out.println("<protocol>://host:port/containername/spacename");
			System.exit(1);
		}
		try {
			System.out.println("\nWelcome to GigaSpaces Data_Grid Topologies Example");        
			System.out.println("Registering for account write notifications");       
	        
			NotifiedReader.listen(args[0]); //	Start listener and connect it to the space

			Thread.sleep(2000000); // Wait for notifications
			notifyContainer.destroy();
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}