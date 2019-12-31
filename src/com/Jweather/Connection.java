package com.Jweather;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connection
{
    public static  boolean checkConnection()
    {
        String IP ;
        try(final DatagramSocket socket = new DatagramSocket())
        {
            socket.connect(InetAddress.getByName("www.openweathermap.org"),10002);
            IP = socket.getInetAddress().getHostAddress();
        }catch (Exception e)
        {
            return false ;
        }
        int success = 0 ;
        try
        {
            InetAddress Ping  = InetAddress.getByName(IP);
            for(int i = 0 ; i < 4 ; i++)
            {
                if(Ping.isReachable(5000))
                    success++;
            }
        }catch (Exception e )
        {
            return false;
        }
        return(success == 4 || success == 3);

    }
}
