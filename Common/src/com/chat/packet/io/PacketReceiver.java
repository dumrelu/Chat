package com.chat.packet.io;

import com.chat.packet.Packet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Receives packets from a connection.
 * 
 * @author Relu
 */
public class PacketReceiver extends PacketIOThread
{
    private List<PacketReceiverSubscriber> m_subscribers;
    private Socket m_socket;
    
    public PacketReceiver(Socket socket)
    {
        m_subscribers = new ArrayList<>();
        m_socket = socket;
    }
    
    public synchronized void subscribe(PacketReceiverSubscriber subscriber)
    {
        m_subscribers.add(subscriber);
    }
    
    public synchronized boolean unsubscribe(PacketReceiverSubscriber subscriber)
    {
        return m_subscribers.remove(subscriber);
    }
    
    @Override
    public void run()
    {
        try {
            ObjectInputStream ois = new ObjectInputStream(m_socket.getInputStream());
            
            while(isRunning())
            {
                Packet packet = (Packet) ois.readObject();
                notifySubscribers(packet);
            }
            
        } catch (IOException | ClassNotFoundException ex) {
            
        }
    }
    
    private synchronized void notifySubscribers(Packet packet)
    {
        for(PacketReceiverSubscriber subscriber : m_subscribers)
        {
            subscriber.onPacketReceived(packet);
        }
    }
}
