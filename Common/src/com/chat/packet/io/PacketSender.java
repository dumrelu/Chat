package com.chat.packet.io;

import com.chat.packet.Packet;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Used to send packets to a given connection.
 * 
 * @author Relu
 */
public class PacketSender extends PacketIOThread
{
    private final List<PacketSenderSubscriber> m_subscribers;
    private final Queue<Packet> m_packets;    //Packets to send.
    private final Socket m_socket;
    
    public PacketSender(Socket socket)
    {
        m_subscribers = new ArrayList<>();
        m_packets = new LinkedList<>();
        m_socket = socket;
    }
    
    public synchronized void subscribe(PacketSenderSubscriber subscriber)
    {
        m_subscribers.add(subscriber);
    }
    
    public synchronized boolean unsubscribe(PacketSenderSubscriber subscriber)
    {
        return m_subscribers.remove(subscriber);
    }
    
    public synchronized void send(Packet packet)
    {
        m_packets.add(packet);
        notify();
    }
    
    @Override
    public void run()
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(m_socket.getOutputStream());
            
            while(isRunning())
            {
                Packet packet = getNextPacket();
                oos.writeObject(packet);
                notifySubscribers(packet);
            }
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PacketSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized Packet getNextPacket() throws InterruptedException
    {
        while(m_packets.isEmpty())
            wait();
        
        return m_packets.poll();
    }
    
    private synchronized void notifySubscribers(Packet packet)
    {
        for(PacketSenderSubscriber subscriber : m_subscribers)
        {
            subscriber.onPacketSent(packet);
        }
    }
}
