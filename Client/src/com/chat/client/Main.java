package com.chat.client;

import com.chat.constants.Constants;
import com.chat.packet.Packet;
import com.chat.packet.io.PacketReceiver;
import com.chat.packet.io.PacketReceiverSubscriber;
import com.chat.test.TestPacket;
import java.io.IOException;
import java.net.Socket;

class TestSubscriber implements PacketReceiverSubscriber
{
    private PacketReceiver m_receiver;

    public TestSubscriber(PacketReceiver m_receiver) 
    {
        this.m_receiver = m_receiver;
    }
    
    @Override
    public void onPacketReceived(Packet packet) 
    {
        System.out.println("Message received: " + ((TestPacket) packet).getTestMessage());
        m_receiver.stop();
    }
    
}

public class Main 
{

    public static void main(String[] args) throws IOException 
    {
        Socket socket = new Socket("localhost", Constants.PORT);
        
        PacketReceiver receiver = new PacketReceiver(socket);
        receiver.start();
        receiver.subscribe(new TestSubscriber(receiver));
    }
    
}
