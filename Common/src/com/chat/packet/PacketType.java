package com.chat.packet;

/**
 *  Types of packets that are used to communicate between
 * server and client.
 * 
 * @author Relu
 */
public enum PacketType 
{
    COMMAND_CONNECT,    //Connect to server command.
    COMMAND_LIST,       //Get all the connected users.
    COMMAND_MSG,        //Send a private or broadcast message.
    COMMAND_UPDATE,     //Update the user data.
    COMMAND_QUIT,       //Disconnect from the server.
    
    MESSAGE_CONNECT,    //A user has connected to the server.
    MESSAGE_DISCONNECT, //A user has disconnected from the server.
    MESSAGE_UPDATE,     //A user changed its data.
    MESSAGE_CHAT,       //A user sent a chat message(private or broadcast).
    MESSAGE_ERROR,      //An error has occured when executing a command.
}
