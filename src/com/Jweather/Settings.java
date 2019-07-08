package com.Jweather;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Settings
{
    public static int City = 0;
    public static boolean Celcius;
    public static boolean ready = false ;
    public static boolean offline = false ;
    private Hashtable< Integer , String> Cities  = new Hashtable<Integer, String>();
    public static ArrayList<String> City_List = new ArrayList<String>();
    protected static String HOME_PATH = System.getProperty("user.home");
    public static String CONFIG_PATH = HOME_PATH+"/.Jweather";
    public static  boolean checkConnection()
    {
        String IP ;
        try(final DatagramSocket socket = new DatagramSocket())
        {
            socket.connect(InetAddress.getByName("www.openweathermap.org"),10002);
            IP = socket.getInetAddress().getHostAddress();

        }catch (Exception e)
        {
            System.out.println("can't find server ");
            offline = true ;
            return false ;


        }

        boolean reachable =  false ;
        try
        {
            InetAddress Ping  = InetAddress.getByName(IP);

            for(int i = 0 ; i < 4 ; i++)
            {
                if(Ping.isReachable(5000))
                {
                    reachable = true;
                    offline = false ;
                    offline = false ;
                    System.out.println("Server is reachable ");

                }
                else
                {
                    reachable = false ;
                    offline = true ;
                }

            }

        }catch (Exception e )
        {
            offline = true ;
            return false;

        }
        finally
        {
            return reachable ;
        }

    }


    public Settings()
    {
        CreateDir();
        read_Settings();

    }
    private void CreateDir()
    {
        File local = new File(CONFIG_PATH);

        if(!local.exists())
        {
            local.mkdir();
            System.out.println("Created");
        }

    }
    private void read_Settings()
    {
        File Settings = new File(CONFIG_PATH+"/config.conf");
        List<String> reader = new ArrayList<String>();

        try
        {
            reader = FileUtils.readLines(Settings,"utf-8");

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        switch (reader.get(0))
        {
            case "C":
                Celcius = true;
                break;
            case "F":
                Celcius = false ;
                break;
            default:
                Celcius = true;
                break;
        }
        String temp[] = new String[2];
        int i = 0 ;
        for (String line:reader)
        {
            if(i == 0 )
            {
                i++;
                continue;
            }
            try
            {

                temp = line.split(",");
                System.out.println(temp[1]);
                Cities.put(Integer.parseInt(temp[0]), temp[1].replace("*", ""));
                City_List.add(temp[1].replace("*", ""));
                if (temp[1].contains("*")) {
                    City = Integer.parseInt(temp[0]);
                }
            }
            catch (Exception e)
            {
                e.getCause();
            }

        }




    }

    public boolean state()
    {
        if(City != 0)
        {
            return true;
        }
        else
            return false ;
    }


}
