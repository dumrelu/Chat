package com.chat.server;

import com.chat.command.ConnectCommand;
import com.chat.command.ListCommand;
import com.chat.command.MessageCommand;
import com.chat.command.QuitCommand;
import com.chat.command.UpdateCommand;
import com.chat.message.ChatMessage;
import com.chat.message.ConnectMessage;
import com.chat.message.ErrorMessage;
import com.chat.message.ListMessage;
import com.chat.packet.Packet;
import com.chat.packet.io.PacketReceiver;
import com.chat.packet.io.PacketReceiverSubscriber;
import com.chat.packet.io.PacketSender;
import com.chat.packet.io.PacketSenderSubscriber;
import com.chat.user.UserData;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Represents a user connected to the server. The user
 * is considered connected to the chat if a username is set.
 *
 * @author Relu
 */
public class UserConnection implements PacketReceiverSubscriber
{
    private UserConnectionTable m_table;
    private PacketSender m_sender;
    private PacketReceiver m_receiver;
    private Socket m_socket;
    private UserData m_userData;

    public UserConnection(UserConnectionTable table, Socket socket) 
    {
        m_table = table;
        m_sender = new PacketSender(socket);
        m_receiver = new PacketReceiver(socket);
        m_socket = socket;
        m_userData = null;
        
        m_sender.start();
        m_receiver.start();
        m_receiver.subscribe(this);
    }
    
    public PacketSender getPacketSender()
    {
        return m_sender;
    }
    
    public PacketReceiver getPacketReceiver()
    {
        return m_receiver;
    }
    
    public Socket getSocket()
    {
        return m_socket;
    }
    
    public UserData getUserData()
    {
        return m_userData;
    }
    
    public void setUserData(UserData userData)
    {
        m_userData = userData;
    }
    
    public void close() throws IOException
    {
        m_sender.stop();
        m_receiver.stop();
        m_socket.close();
    }

    @Override
    public void onPacketReceived(Packet packet) 
    {
        switch(packet.getType())
        {
            case COMMAND_CONNECT:
                onConnectCommand((ConnectCommand) packet);
                break;
            case COMMAND_LIST:
                onListCommand((ListCommand) packet);
                break;
            case COMMAND_MSG:
                onMessageCommand((MessageCommand) packet);
                break;
            case COMMAND_UPDATE:
                onUpdateCommand((UpdateCommand) packet);
                break;
            case COMMAND_QUIT:
            {
                try {
                    onQuitCommand((QuitCommand) packet);
                } catch (IOException ex) {
                    Logger.getLogger(UserConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
        }
    }
    
    private void onConnectCommand(ConnectCommand connect)
    {
        if(!m_table.addUser(connect.getUserData(), this))
        {
            m_sender.send(new ErrorMessage("Username already taken."));
        }
        else
        {
            m_table.broadcast(new ConnectMessage(connect.getUserData()));
        }
    }
    
    private void onListCommand(ListCommand list)
    {
        if(m_userData == null)
        {
            m_sender.send(new ErrorMessage("You need to pick a username first"));
            return;
        }
        
        List<UserData> users = new ArrayList<>();
        for(UserConnection user : m_table.getUsers())
            users.add(user.getUserData());
        
        m_sender.send(new ListMessage(users));
    }
    
    private void onMessageCommand(MessageCommand message)
    {
        if(m_userData == null)
        {
            m_sender.send(new ErrorMessage("You need to pick a username first"));
            return;
        }
        
        ChatMessage chatMessage = new ChatMessage(m_userData.getUsername(), message.getMessage(), message.getDate(), message.isBroadcast());
        String destination = message.getDestination();
        
        if(destination != null)
        {
            m_table.broadcast(chatMessage);
        }
        else
        {
            if(!m_table.send(destination, chatMessage))
            {
                m_sender.send(new ErrorMessage("Username " + destination + " is invalid."));
            }
        }
    }
    
    private void onQuitCommand(QuitCommand quit) throws IOException
    {
        m_table.removeUser(m_userData);
        close();
    }
    
    private void onUpdateCommand(UpdateCommand update)
    {
        if(m_userData == null)
        {
            m_sender.send(new ErrorMessage("You need to pick a username first"));
            return;
        }
        
        if(!m_table.update(m_userData, update.getUserData(), this))
        {
            m_sender.send(new ErrorMessage("Username already taken"));
        }
    }
}
