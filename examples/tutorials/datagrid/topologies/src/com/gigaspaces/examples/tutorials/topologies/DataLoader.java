/*
 * Copyright 2007 GigaSpaces Technologies Ltd. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED INCLUDING BUT NOT LIMITED TO WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT. GIGASPACES WILL NOT 
 * BE LIABLE FOR ANY DAMAGE OR LOSS IN CONNECTION WITH THE SOFTWARE.
 */
/**
 * Title:        DataLoader.
 * Description:  Writes POJO account objects to the space.
 *
 **/

package com.gigaspaces.examples.tutorials.topologies;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import com.j_spaces.core.IJSpace;

public class DataLoader {
	
	private Integer maxAccounts;
	private Integer sampleSize; 
	GigaSpace gigaSpace;
		
	public void setMaxAccounts(Integer maxAccounts){
		this.maxAccounts = maxAccounts;
	}
	public Integer getMaxAccounts(){
		return maxAccounts;
	}
	public void setSampleSize(Integer sampleSize){
		this.sampleSize = sampleSize; 
	}
	public Integer getSampleSize(){
		return sampleSize;
	}
	
	/**
	 * Configure writer and connect to a space
	 * @param url
	 * @throws Exception
	 */
	public DataLoader(String url) throws Exception {
		
        this.maxAccounts = 100; // Number of accounts to write to the space
        
        this.sampleSize = 20; // During write, pause after each <sampleSize> number of accounts 
        
        IJSpace space = new UrlSpaceConfigurer(url).space(); // Connect to the space using the URL
        
        gigaSpace = new GigaSpaceConfigurer(space).gigaSpace(); // Use gigaSpace simplified API
	}
	
	/**
	 * Clears space of account POJOs
	 */ 
	public void clearSpaceOfAccountPOJOs() throws Throwable{
		
		Account account = new Account(); // Create a template for a general account object
		
		int count = gigaSpace.count(account); // Count account objects in the space
		
		if (count != 0){
			System.out.println("Clearing space of account objects...");
			
			gigaSpace.clear(account); // Clear space of accounts
		}
	}
	
	/**
	 * Writes accounts to the space
	 * @throws Throwable
	 */
	public void write() throws Throwable {
		
		long waitDuration = 3000; // Time to wait between write operations
		
		for (int i = 1; i < getMaxAccounts() +1; i++) {
			
			Account account = new Account(i,i*10); // Create an account to write to the space
			
			gigaSpace.write(account); // Write account to space.
			
			// Output a message and wait 3 seconds after every <sampleSize> write operations. 
			if (i % getSampleSize() == 0 && i != 0){ 
				System.out.println("Writing "+sampleSize+" account objects; total of "+i+" objects; ");
				if (i != getMaxAccounts()) sleep(waitDuration);
			}
		}
	}
	
	/**
	 * Sleep <timeToSleepInMillis> milliseconds
	 * @param timeToSleepInMillis
	 * @throws Throwable
	 */
	public void sleep(long timeToSleepInMillis) throws Throwable{
		System.out.print("Sleeping");
		Thread.sleep(timeToSleepInMillis/4);
		System.out.print(".");
		Thread.sleep(timeToSleepInMillis/4);
		System.out.print(".");
		Thread.sleep(timeToSleepInMillis/4);
		System.out.println(".");
		Thread.sleep(timeToSleepInMillis/4);
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.println("Usage: <URL>");
			System.out.println("<protocol>://host:port/containername/spacename");
			System.exit(1);
		}
		try {
			
			System.out.println("\nWelcome to GigaSpaces Data Grid Topologies Example");        
			System.out.println("This example writes to various caching topologies...");              
	        
			DataLoader dataLoader = new DataLoader(args[0]); // Create a dataLoader connected to the space
			
	        dataLoader.clearSpaceOfAccountPOJOs(); // Clear the space from account objects to ensure unique IDs
	        
	        System.out.println("\nWriting " + dataLoader.getMaxAccounts() + " account objects to space:");
			
	        dataLoader.write(); // Write accounts to the space
			
			System.exit(0);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
