package edu.seg2105.client.ui;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.Scanner;


import edu.seg2105.client.common.*;
import edu.seg2105.edu.server.backend.EchoServer;

public class ServerConsole implements ChatIF {
	final public static int DEFAULT_PORT = 5555;
	EchoServer Server;
	Scanner Console;
	
	
	public ServerConsole(int port){
	
		try {
			Server = new EchoServer(port,this);
		}
		catch(Exception Exception)
		{
			System.out.println("\"Error: Can't setup connection!\"\r\n"
					+ "                + \" Terminating server.");
			System.exit(1);
		}
		Console = new Scanner(System.in);
		
		
	}
	public void setServer(EchoServer Server) {
		this.Server=Server;
	}
	

	 public void accept(){
	    try
	    {

	      String message;
	      

	      while (true) 
	      {
	        message = Console.nextLine();
	        Server.handleMessageFromServerUI(message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	  }

	  /**
	   * This method overrides the method in the ChatIF interface.  It
	   * displays a message onto the screen.
	   *
	   * @param message The string to be displayed.
	   */
	 @Override
	  public void display(String message) 
	  {
	    System.out.println( message);
	  }
	public static void main(String[] args) 
	  {
	    int port = 0 ; //Port to listen on
	    

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
	    ServerConsole console= new ServerConsole(port);
	    console.accept();
		    
	 }   
	  
	  }

