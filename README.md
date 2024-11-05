SimpleChatClient


SimpleChatClient is a Java-based client application that allows real-time messaging with a chat server. This application serves as Phase 2 of the SimpleChat project, expanding on the basic client-server functionality provided in Phase 1. This project is implemented using the Object Client-Server Framework (OCSF), enabling the client and server to communicate seamlessly. This application was created as part of an individual school assignment to deepen understanding of networking, socket programming, and OCSF framework utilization.

Assignment Overview
Phase 1 Repository: https://github.com/husseinalosman/simpleChat
The objective of this assignment is to extend Phase 1 of the SimpleChat application by adding new client and server functionalities. The assignment involves modifying the SimpleChat application to handle server shutdowns, implement commands for client and server functionality, and establish unique client login IDs.

Features
Real-time messaging between clients connected to a server.
Enhanced error handling when server shutdown occurs.
Custom client and server commands for managing the connection and server behavior.
Login IDs to identify users in the chat.
Built on OCSF to handle client-server communication and messaging.
Requirements
Java (Version 8 or higher)
A compatible chat server to connect to
OCSF (Object Client-Server Framework)
Setup and Installation
Clone the Phase 1 Repository:

bash
Copy code
git clone https://github.com/husseinalosman/simpleChat
cd simpleChat
Compilation: Follow the instructions in the included “Instructions for Installing the SimpleChat Application” document to compile and set up the application.

Application Handles Server Shutdown and Port Configuration
Client Side:

Modify the client to detect server shutdown and quit with a message when it happens.
Allow client to accept a custom port from the command line, defaulting if omitted.
Server Side:
Modify the server to print a message whenever a client connects or disconnects.

Implement Commands 
Client Commands:

Add commands beginning with # for special functions:
#quit to quit the client.
#logoff to disconnect but keep the client open.
#sethost <host> to set host (only if logged off).
#setport <port> to set port (only if logged off).
#login to reconnect to the server.
#gethost and #getport to display current host and port.

Server Commands:

Create a ServerConsole class implementing ChatIF to enable server commands:
#quit to gracefully stop the server.
#stop to stop accepting new connections.
#close to stop listening and disconnect all clients.
#setport <port> to set server port when closed.
#start to resume listening for new clients.
#getport to display the current server port.

Implements Login IDs 
Client Side:

Add a mandatory login ID argument to identify each client. If missing, the client quits.
On connection, automatically send #login <loginid> to the server.

Server Side:

Recognize the #login command, validate it as the first command, and associate each client with their login ID.
Prefix all messages with the client’s login ID, and close the connection if #login is received at any other time.

Testing
Run Test Cases:
“Test Cases” document to verify that all implemented features work correctly.

