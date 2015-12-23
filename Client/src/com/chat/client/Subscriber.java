package com.chat.client;

import com.chat.message.ChatMessage;
import com.chat.message.ConnectMessage;
import com.chat.message.DisconnectMessage;
import com.chat.message.ErrorMessage;
import com.chat.message.ListMessage;
import com.chat.message.UpdateMessage;
import com.chat.packet.Packet;
import com.chat.packet.io.PacketReceiverSubscriber;
import com.chat.user.UserData;

/**
 *
 * @author Relu
 */
public class Subscriber implements PacketReceiverSubscriber
{

    @Override
    public void onPacketReceived(Packet packet) 
    {
        switch (packet.getType())
        {
            case MESSAGE_CONNECT:
                onConnectMessage((ConnectMessage) packet);
                break;
            case MESSAGE_DISCONNECT:
                onDisconnectMessage((DisconnectMessage) packet);
                break;
            case MESSAGE_UPDATE:
                onUpdateMessage((UpdateMessage) packet);
                break;
            case MESSAGE_LIST:
                onListMessage((ListMessage) packet);
                break;
            case MESSAGE_CHAT:
                onChatMessage((ChatMessage) packet);
                break;
            case MESSAGE_ERROR:
                onErrorMessage((ErrorMessage) packet);
                break;
            
        }
    }
    
    private void onConnectMessage(ConnectMessage connect)
    {
        System.out.println(connect.getUserData().getUsername() + " connected!");
    }
    
    public void onDisconnectMessage(DisconnectMessage disconnect)
    {
        System.out.println(disconnect.getUsername() + " disconnected!");
    }
    
    public void onUpdateMessage(UpdateMessage update)
    {
        System.out.println(update.getOldUsername() + " -> " + update.getUserData().getUsername());
    }
    
    public void onListMessage(ListMessage list)
    {
        System.out.println("Connected users: ");
        for(UserData user : list.getUsers())
        {
            System.out.println("\t" + user.getUsername());
        }
    }
    
    public void onChatMessage(ChatMessage chat)
    {
        if(chat.isPrivate())
        {
            System.out.println("Message from " + chat.getSource() + ": " + chat.getMessage());
        }
        else
        {
            System.out.println("Broadcast from " + chat.getSource() + ": "+ chat.getMessage());
        }
    }
    
    public void onErrorMessage(ErrorMessage error)
    {
        System.err.println(error.getErrorMessage());
    }
}
