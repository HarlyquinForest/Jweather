package com.Jweather;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Settings
{
    public static City city = new City();
    public static boolean Celcius;
    public static boolean ready = false ;
    public static boolean offline = false ;
    public static boolean refresh = false ;
    public static Hashtable< Integer , String > Cities  = new Hashtable<Integer, String>();
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
            //System.out.println("can't find server ");
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
                    //System.out.println("Server is reachable ");

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
        //System.out.println("Read Settings");
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
        String temp[] = new String[3];
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
                //System.out.println(temp[1]);
                Cities.put(Integer.parseInt(temp[0]), temp[1].replace("*", ""));
                City_List.add(temp[1].replace("*", ""));
                if (temp[2].contains("*"))
                {
                    System.out.println(temp[0]);
                    city.setId(Integer.parseInt(temp[0]));
                    city.setName(temp[1]);
                    city.setCountry(temp[2]);
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
        if(city.getId() != 0)
        {
            return true;
        }
        else
            return false ;
    }

    public static int getID()
    {
        return city.getId();
    }

    public static void save() throws Exception
    {
        File file = new File(Settings.CONFIG_PATH+"/config.conf");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

        String line ;
        String[] temp = new String[3];
        char unit = ' ';
        if(Settings.Celcius)
            unit='C';
        else
            unit='F';
        String Data = unit+"\n";
        randomAccessFile.seek(2);
        while((line = randomAccessFile.readLine()) != null )
        {

            temp = line.split(",");
            if(Integer.parseInt(temp[0]) == Settings.city.getId())
            {
                //System.out.println(line+"Will use as default");
                if(!temp[2].contains("*"))
                    Data+=line+"*\n";
                else
                    Data+=line+"\n";
            }
            else if(temp[2].contains("*"))
            {
                Data+=line.replace("*","")+"\n";
            }
            else
                Data+=line+"\n";

        }
        file.delete();
        //System.out.println(Data);
        //System.out.println(lineNumber);
        FileUtils.writeStringToFile(file , Data , "utf-8");

    }

}
