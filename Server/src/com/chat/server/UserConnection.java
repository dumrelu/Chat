package com.chat.server;

import com.chat.command.ConnectCommand;
import com.chat.command.ListCommand;
import com.chat.command.MessageCommand;
import com.chat.command.QuitCommand;
import com.chat.command.UpdateCommand;
import com.chat.packet.Packet;
import com.chat.packet.io.PacketReceiver;
import com.chat.packet.io.PacketReceiverSubscriber;
import com.chat.packet.io.PacketSender;
import com.chat.packet.io.PacketSenderSubscriber;
import com.chat.user.UserData;
import java.io.IOException;
import java.net.Socket;

/**
 *  Represents a user connected to the server. The user
 * is considered connected to the chat if a username is set.
 *
 * @author Relu
 */
public class UserConnection implements PacketReceiverSubscriber
{
    private PacketSender m_sender;
    private PacketReceiver m_receiver;
    private Socket m_socket;
    private UserData m_userData;

    public UserConnection(Socket socket) 
    {
        m_sender = new PacketSender(socket);
        m_receiver = new PacketReceiver(socket);
        m_socket = socket;
        m_userData = null;
        
        m_sender.start();
        m_receiver.start();
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
        
    }
    
    private void onConnectCommand(ConnectCommand connect)
    {
        
    }
    
    private void onListCommand(ListCommand list)
    {
        
    }
    
    private void onMessageCommand(MessageCommand message)
    {
        
    }
    
    private void onQuitCommand(QuitCommand quit)
    {
        
    }
    
    private void onUpdateCommand(UpdateCommand update)
    {
        
    }
}
