package com.chat.server;

import com.chat.constants.Constants;
import com.chat.packet.Packet;
import com.chat.packet.io.PacketSender;
import com.chat.packet.io.PacketSenderSubscriber;
import com.chat.test.TestPacket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class TestSubscriber implements PacketSenderSubscriber
{
    private PacketSender m_sender;

    public TestSubscriber(PacketSender m_sender) 
    {
        this.m_sender = m_sender;
    }
    

    @Override
    public void onPacketSent(Packet packet) 
    {
        System.out.println("Message sent: " + ((TestPacket) packet).getTestMessage());
        m_sender.stop();
    }
    
}

public class Main 
{

    public static void main(String[] args) throws IOException 
    {
        ServerSocket serverSocket = new ServerSocket(Constants.PORT);
        UserConnectionTable table = new UserConnectionTable();
        
        System.out.println("Server started!");
        while(true)
        {
            Socket socket = serverSocket.accept();
            UserConnection user = new UserConnection(table, socket);
            System.out.println("User connected!");
        }
    }
    
}
