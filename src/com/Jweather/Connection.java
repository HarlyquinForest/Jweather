package com.Jweather;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connection extends Thread
{
    boolean ok = false;
    @Override
    public void run() {
        ok = checkConnection();
    }

    public  boolean checkConnection()
    {
        String IP ;
        try(final DatagramSocket socket = new DatagramSocket())
        {
            socket.connect(InetAddress.getByName("www.openweathermap.org"),10002 );
            socket.setSoTimeout(300);
            IP = socket.getInetAddress().getHostAddress();
        }catch (Exception e)
        {
            e.printStackTrace();
            return false ;
        }
        int success = 0 ;
        try
        {
            InetAddress Ping  = InetAddress.getByName(IP);
            for(int i = 0 ; i < 4 ; i++)
            {
                if(Ping.isReachable(2000))
                    success++;
            }
        }catch (Exception e )
        {
            e.printStackTrace();
            return false;
        }
        ok = success > 0 ;
        return ok;

    }
}
