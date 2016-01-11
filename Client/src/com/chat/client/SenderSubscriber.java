package com.chat.client;

import com.chat.command.ConnectCommand;
import com.chat.command.ListCommand;
import com.chat.command.MessageCommand;
import com.chat.command.UpdateCommand;
import com.chat.packet.Packet;
import com.chat.packet.io.PacketSenderSubscriber;
import java.util.Date;

/**
 *
 * @author Relu
 */
public class SenderSubscriber implements PacketSenderSubscriber
{
    private final GUI m_gui;
    private String m_username;

    public SenderSubscriber(GUI gui) 
    {
        m_gui = gui;
    }
    
    @Override
    public void onPacketSent(Packet packet) 
    {
        switch(packet.getType())
        {
            case COMMAND_CONNECT:
                m_username = ((ConnectCommand) packet).getUserData().getUsername();
                m_gui.addMessage("Connecting as " + m_username + "...", GUI.INFO);
                m_gui.getSender().send(new ListCommand());
                break;
            case COMMAND_UPDATE:
                String oldUsername = m_username;
                m_username = ((UpdateCommand) packet).getUserData().getUsername();
                m_gui.addMessage("Changin username from " + oldUsername + " to " + m_username, GUI.INFO);
                break;
            case COMMAND_QUIT:
                m_gui.addMessage("Quitting...", GUI.INFO);
                m_gui.close();
                break;
            
        }
    }
    
}
