package com.chat.packet.io;

/**
 *  Thread management for the sender and receiver.
 * 
 * @author Relu
 */
public abstract class PacketIOThread implements Runnable
{
    private Thread m_thread;
    private boolean m_running;

    public PacketIOThread() 
    {
        m_running = false;
    }
    
    public synchronized void start()
    {
        if(m_running)
            return;
        
        m_running = true;
        m_thread = new Thread(this);
        m_thread.start();
    }
    
    public synchronized void stop()
    {
        m_running = false;
    }
    
    public synchronized boolean isRunning()
    {
        return m_running;
    }
}
