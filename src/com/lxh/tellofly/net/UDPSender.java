package com.lxh.tellofly.net;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramSocket;


/**************************************************
 * function: 消息发信�?
 * @author 刘小�?
 * @date 2013-12-16  
 *************************************************/
public class UDPSender
{
    private DatagramSocket server = null;
    private InetAddress address = null;
    private int port = Constant.SENDPORT;

    public UDPSender() {
        try
        {
            server = new DatagramSocket();
            address = InetAddress.getByName(Constant.GROUP_IP);   // dest IP
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * function:发�?�消�?
     * @author 刘小�?
     * @date 2013-12-16       
     * @param msg - 待发送的消息
     */    
    public void sendMsg(String msg)
    {
        try
        {
            byte [] msgdata = msg.getBytes();
            DatagramPacket p = new DatagramPacket(msgdata, msgdata.length, address, port);
            //System.out.println("send:" + msg);
            server.send(p);
            //server.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * function:发�?�消�?
     * @author 刘小�?
     * @date 2013-12-16
     * @param port - 端口�?
     * @param msg - 待发送的消息
     */    
    public void sendMsg(int port, String msg)
    {
        try
        {
            //server = new MulticastSocket();
            //address = InetAddress.getByName(Constant.GROUP_IP);   // dest IP
            byte [] msgdata = msg.getBytes();
            DatagramPacket p = new DatagramPacket(msgdata, msgdata.length, address, port);
            //System.out.println("send:" + msg);
            server.send(p);
            //server.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * function:�?毁变�?
     * @author 刘小�?
     * @date 2013-12-19        
     */
    public void destroy() {
        server.close();
        server = null;
    }
}
