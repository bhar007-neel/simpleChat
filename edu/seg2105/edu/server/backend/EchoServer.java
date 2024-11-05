package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;

import edu.seg2105.client.common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
	
  
  /**
   * The default port to listen on.
   */
  	
  final public static int DEFAULT_PORT = 5555;
  ChatIF serverUI;
  
 
  
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port,ChatIF serverUI) 
  {
    super(port);
    this.serverUI= serverUI;
    
    try {
		listen();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client) {
	    String message = (String) msg;

	    try {
	        // Check if the message starts with #login
	        if (message.startsWith("#login")) {
	            String loginId = message.substring(6).trim(); 
	            
	          
	            if (client.getInfo("loginKey") == null) {
	                client.setInfo("loginKey", loginId);// If not logged in, store login ID
	                System.out.println( loginId + " has logged on");
	                System.out.println("Message received: " + message + " from client " + client);
	                
	                
	                client.sendToClient( loginId);
	            } else {
	                client.sendToClient("Error: You are already logged in. Sorry,Connection will be closed.");
	                client.close();
	                System.exit(1);
	            }
	        } else {
	            
	            String loginId = (String) client.getInfo("loginKey");

	            if (loginId == null) {
	                client.sendToClient("Error: Please login using #login <loginId>.");
	                client.close();
	                System.exit(1);
	            } else {
	                String pastUser = loginId + ": " + message;
	                sendToAllClients(pastUser);
	                System.out.println("sending message: " + pastUser);
	            }
	        }
	    } catch (IOException e) {
	        System.err.println("Error handling message from client: " + e.getMessage());
	    }
	}
  
  public void handleMessageFromServerUI(String message)
  {
    try
    {
      if(message.startsWith("#")){
    	  handleCommand(message);
    	  
      }
      else {
    	  sendToAllClients("SERVER MSG > "+message);
      }
       
    }
    catch(IOException e)
    {
      serverUI.display
        ("Could not send message to server.  Terminating client.");
      System.exit(1);;
    }
  }
  private void handleCommand(String command ) throws IOException {
	  if(command.equals("#quit")) {
		  serverUI.display("server shutting down .....");
		  System.exit(1);;
	  }
	  else if(command.equals("#stop")) {
		 if(isListening()) {
			 stopListening();
			 serverUI.display("Server stopped listing to clients");
		 }
		 else {
			 serverUI.display("Server is already stopped");
		 }
		 
	  }
	  else if(command.equals("#close")) {
		  if(isListening()) {
				 stopListening();
				 sendToAllClients("Server is closing connections");
			 }
		  close();
		  serverUI.display("Server Stopped Listening and all disconnected all the connections");
		 
		  }
	  
	  else if(command.startsWith("#setport")) {
		  if(!isListening()) {
				 String[] token= command.split(" ");
				 if (token.length> 1){
					 int port= Integer.parseInt(token[1]);
					 setPort(port);
				 }
				 else {
					 serverUI.display("error");
				 }
				 
			 }
			 else {
				 serverUI.display(" is connected at this moment, cannot set another one");
				 
			 }
	  }
	  else if(command.equals("#start")) {
		  if(!isListening()) {
			  listen();
		  }
		  else {
			  serverUI.display("Server is running");
		  }
		 
	  }
	
	  else if(command.equals("#getport")) {
		  serverUI.display("port is "+ getPort());
	  }
	  
		  
	  
	  else {
		  serverUI.display("Error does not about this command");
	  }
			 
	  
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
	  serverUI.display
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
	  serverUI.display
      ("Server has stopped listening for connections.");
  }
  
  @Override
  protected void clientConnected(ConnectionToClient client){
	  serverUI.display(" A Client is  connected to server");
  	
  	
  }

  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println(client.getInfo("loginKey") +" has disconnected");
  	
  }
  
  
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  
}
//End of EchoServer class
