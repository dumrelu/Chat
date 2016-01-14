package com.chat.client;

import com.chat.command.ConnectCommand;
import com.chat.command.ListCommand;
import com.chat.command.MessageCommand;
import com.chat.command.QuitCommand;
import com.chat.command.UpdateCommand;
import com.chat.constants.Constants;
import com.chat.packet.io.PacketReceiver;
import com.chat.packet.io.PacketSender;
import com.chat.user.UserData;
import java.net.Socket;

/**
 *
 * @author Relu
 */
public class CommandParser 
{
    private final GUI m_gui;
    private String m_lastCommand;
    
    public CommandParser(GUI gui)
    {
        m_gui = gui;
    }
    
    public void process(String line)
    {
        if(!line.startsWith("/"))
        {
            if(m_gui.getSender() != null)
                m_gui.getSender().send(new MessageCommand(line));
            else 
                m_gui.addMessage("You need to connect first!", GUI.ERROR);
            m_lastCommand = "bcast";
            return;
        }
        
        try
        {
            parse(line);
        }
        catch(Exception e)
        {
            System.err.println("Invalid command");
        }
    }
    
    private void parse(String line) throws Exception
    {
        int spaceIdx = line.indexOf(" ");
        if(spaceIdx < 0)
            spaceIdx = line.length();
        String command = line.substring(1, spaceIdx);
        
        if(m_gui.getSender() == null && !command.equals("quit"))
        {
            if(!command.equals("connect"))
            {
                m_gui.addMessage("You need to connect first using the /connect command!", GUI.ERROR);
                return;
            }
            
            String username, host;
            int port = 0;
            
            String[] args = line.split(" ");
            username = args[1];
            if(args.length != 4)
            {
                host = "localhost";
                port = Constants.PORT;
            }
            else
            {
                host = args[2];
                try
                {
                    port = Integer.parseInt(args[3]);
                } catch(NumberFormatException e)
                {
                    m_gui.addMessage("Invalid port.", GUI.ERROR);
                }
            }
            
            Socket socket;
            try
            {
                m_gui.addMessage("Connecting to " + host + " on port " + port, GUI.INFO);
                socket = new Socket(host, port);
            }
            catch(Exception e)
            {
                m_gui.addMessage("Connection failed!", GUI.ERROR);
                return;
            }
            
            PacketSender sender = new PacketSender(socket);
            PacketReceiver receiver = new PacketReceiver(socket);

            sender.start();
            receiver.start();

            SenderSubscriber ss = new SenderSubscriber(m_gui);
            sender.subscribe(ss);

            ReceiverSubscriber rs = new ReceiverSubscriber(m_gui);
            receiver.subscribe(rs);
            
            m_gui.setSender(sender);
            m_gui.setReceiver(receiver);
            
            line = args[0] + " " + args[1];
        }
        
        switch(command)
        {
            case "connect":
                m_gui.getSender().send(new ConnectCommand(new UserData(line.substring(spaceIdx+1))));
                break;
            case "list":
                m_gui.getSender().send(new ListCommand());
                break;
            case "msg":
                int nextSpaceIdx = line.indexOf(" ", spaceIdx+1);
                String destination = line.substring(spaceIdx+1, nextSpaceIdx);
                String message = line.substring(nextSpaceIdx+1);
                if(!message.isEmpty())
                    m_gui.getSender().send(new MessageCommand(destination, message));
                break;
            case "bcast":
                String bcast = line.substring(spaceIdx+1);
                if(!bcast.isEmpty())
                    m_gui.getSender().send(new MessageCommand(bcast));
                break;
            case "nick":
                m_gui.getSender().send(new UpdateCommand(new UserData(line.substring(spaceIdx+1))));
                break;
            case "quit":
                if(m_gui.getSender() != null)
                    m_gui.getSender().send(new QuitCommand());
                else
                    m_gui.close();
                break;
            default:
                m_gui.addMessage("Invalid command: \"" + line + "\"", GUI.ERROR);
        }
        
        m_lastCommand = command;
    }
    
    public String getLastCommand()
    {
        return m_lastCommand;
    }
}
