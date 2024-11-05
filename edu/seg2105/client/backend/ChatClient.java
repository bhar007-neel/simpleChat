// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import ocsf.client.*;


import java.io.*;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  String loginId;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String loginId,String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    if(loginId== null || loginId.isEmpty()) {
    	System.out.println("Error!! LoginId is mendatory, please mention!!");
    	System.exit(1);
    }
    this.loginId=loginId;
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
    
    
  }

 

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      if(message.startsWith("#")){
    	  handleCommand(message);
    	  
      }
      else {
    	  sendToServer(message);
      }
       
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  private void handleCommand(String command ) throws IOException {
	  if(command.equals("#quit")) {
		  quit();
	  }
	  else if(command.equals("#logoff")) {
		 if(isConnected()) {
			 closeConnection();
		 }
		 else {
			 clientUI.display("Client is already logged off");
		 }
		 
	  }
	  else if(command.startsWith("#sethost")) {
			 if(!isConnected()) {
				 String[] token= command.split(" ");
				 if (token.length> 1){
					 setHost(token[1]);
				 }
				 else {
					 clientUI.display("error");
				 }
				 
			 }
			 else {
				 clientUI.display("host is connected at this moment, cannot set another one");
				 
			 }
	  }
	  else if(command.startsWith("#setport")) {
		  if(!isConnected()) {
				 String[] token= command.split(" ");
				 if (token.length> 1){
					 int port= Integer.parseInt(token[1]);
					 setPort(port);
				 }
				 else {
					 clientUI.display("error");
				 }
				 
			 }
			 else {
				 clientUI.display(" is connected at this moment, cannot set another one");
				 
			 }
	  }
	  else if(command.equals("#login")) {
		  if(!isConnected()) {
			  openConnection();
		  }
		  else {
			  clientUI.display("Error!!! check if there is a connection already established");
		  }
	  }
	  else if(command.equals("#gethost")) {
		  clientUI.display("host is "+getHost());
		  
		  
	  }
	  else if(command.equals("#getport")) {
		  clientUI.display("port is "+ getPort());
	  }
	  else {
		  clientUI.display("ClientUI dispay in chatclient does not work for this");
	  }
			 
	  
  }
  
  
  
  @Override
  protected void connectionEstablished() {
	  try {
		  sendToServer("#login "+ loginId );
		  
		  
	  }
	  catch(IOException e) {
		  clientUI.display("Failed to assign Login Id");
		  quit();
	  }
  }
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
 @Override
 protected void connectionException(Exception exception) {
	clientUI.display("The server is down");
	  System.exit(0);
  }
 @Override
 protected void connectionClosed() {
	  clientUI.display("Connection closed");
	  
 }
}
//End of ChatClient class
