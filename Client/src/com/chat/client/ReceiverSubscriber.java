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
import java.util.Date;

/**
 *
 * @author Relu
 */
public class ReceiverSubscriber implements PacketReceiverSubscriber
{
    private GUI m_gui;
    private String m_username;

    public ReceiverSubscriber(GUI gui) 
    {
        m_gui = gui;
    }
    
    private String composeMessage(String from, String to, String message, Date date)
    {
        String result = "";
        
        result += from.equals(m_username) ? "me" : from;
        if(to != null)
        {
            result += " to ";
            result += to.equals(m_username) ? "me" : to;
        }
        result += "[" + date.toString() + "]: ";
        result += message;
        
        return result;
    }
    
    @Override
    public void onPacketReceived(Packet packet) 
    {
        switch(packet.getType())
        {
            case MESSAGE_CONNECT:
                UserData connectedUser = ((ConnectMessage) packet).getUserData();
                if(m_username == null)
                {
                    m_username = connectedUser.getUsername();
                    m_gui.addMessage("You connected to the server with the name " + m_username, GUI.INFO);
                }
                else if(m_username.equals(connectedUser.getUsername()))
                {
                    m_username = connectedUser.getUsername();
                    m_gui.addMessage("You changed your name to " + m_username, GUI.INFO);
                }
                else
                {
                    m_gui.addUser(connectedUser);
                    m_gui.addMessage(connectedUser.getUsername() + " connected.", GUI.INFO);
                }
                
                break;
            case MESSAGE_DISCONNECT:
                String disconnectedUsername = ((DisconnectMessage) packet).getUsername();
                m_gui.removeUser(disconnectedUsername);
                m_gui.addMessage(disconnectedUsername + " disconnected.", GUI.INFO);
                break;
            case MESSAGE_UPDATE:
                UpdateMessage um = (UpdateMessage) packet;
                if(m_username.equals(um.getOldUsername()))
                {
                    m_username = um.getUserData().getUsername();
                }
                else
                {
                    m_gui.removeUser(um.getOldUsername());
                    m_gui.addUser(um.getUserData());
                }
                m_gui.addMessage(um.getOldUsername() + " changed to " + um.getUserData().getUsername(), GUI.INFO);
                break;
            case MESSAGE_LIST:
                ListMessage lm = (ListMessage) packet;
                
                m_gui.addMessage("Connected users: ", GUI.INFO);
                for(UserData user : lm.getUsers())
                {
                    if(!m_username.equals(user.getUsername()))
                        m_gui.addUser(user);
                    m_gui.addMessage("\t->" + user.getUsername(), GUI.INFO);
                }
                
                break;
            case MESSAGE_CHAT:
                ChatMessage cm = (ChatMessage) packet;
                String message = composeMessage(cm.getSource(), cm.getDestination(), cm.getMessage(), cm.getDate());
                m_gui.addMessage(message, cm.isPrivate() ? GUI.PRIVATE : GUI.BCAST);
                break;
            case MESSAGE_ERROR:
                ErrorMessage em = (ErrorMessage) packet;
                m_gui.addMessage(em.getErrorMessage(), GUI.ERROR);
                break;
        }
    }
    
}
