package com.chat.client;

import com.chat.command.ConnectCommand;
import com.chat.command.QuitCommand;
import com.chat.constants.Constants;
import com.chat.packet.Packet;
import com.chat.packet.io.PacketReceiver;
import com.chat.packet.io.PacketReceiverSubscriber;
import com.chat.packet.io.PacketSender;
import com.chat.test.TestPacket;
import com.chat.user.UserData;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main 
{

    public static void main(String[] args) throws IOException 
    {
        Socket socket = new Socket("localhost", Constants.PORT);
        
        PacketReceiver receiver = new PacketReceiver(socket);
        receiver.start();
        receiver.subscribe(new Subscriber());
        
        PacketSender sender = new PacketSender(socket);
        sender.start();
        
        /*CommandParser parser = new CommandParser(sender, receiver);
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine())
        {
            parser.process(sc.nextLine());
            
            if(parser.getLastCommand().equals("quit"))
                break;
        }
                */
    }
    
}
