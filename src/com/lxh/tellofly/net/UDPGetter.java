package com.lxh.tellofly.net;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



/**************************************************
 * UDPGetter<br>
 * function: 消息监听�?
 * @author 刘小华
 * @date 2013-12-16  
 *************************************************/
public class UDPGetter implements Runnable
{
    private DatagramSocket getter = null;
    private boolean stopped = false;
    private UDPInfoListener il = null;
    private int port = Constant.SENDPORT;

    public UDPGetter(UDPInfoListener il) {
        stopped = false;
        this.il = il;
    }

    public UDPGetter(int port, UDPInfoListener il) {
        stopped = false;
        this.il = il;
        this.port = port;
    }

    public void run()
    {
        try
        {
            getter = Sockets.getSocket(port);
        } catch (Exception e)
        {
            e.printStackTrace();    // 无效网络
            if (null != getter) {
            	Sockets.close(port);
                getter = null;
            }
        }
        byte[] b = new byte[1024];
        DatagramPacket p = new DatagramPacket(b, b.length);
        while (!stopped)
        {
            // 单次任务
            try
            {
                if (null == p)
                    p = new DatagramPacket(b, b.length);
                if (null != getter)
                {
                    getter.receive(p);
                    String msg = new String(p.getData(), p.getOffset(),
                            p.getLength());
                    // System.out.println(msg + ":" + p.getAddress());
                    if (null != il)
                    {
                        il.onInfoReciver(p.getAddress(), msg);
                        p = null;
                    }
                } else
                {
                    for (int i = 0; i < 6 && !stopped; i++)
                        Thread.sleep(500);
                    if (!stopped)
                    {
                        try
                        {
                            //InetAddress groupIP = InetAddress.getByName(Constant.GROUP_IP);
                            //getter = new DatagramSocket(port);
                            //getter.joinGroup(groupIP);
                            //getter.setSoTimeout(3000); // 3s监听�?�?
                        	getter = Sockets.getSocket(port);
                        } catch (Exception e)
                        {
                            //e.printStackTrace();    // 无效网络
                            if (null != getter) {
                                //getter.close();
                            	Sockets.close(port);
                                getter = null;
                            }
                        }
                    }
                }
            } catch (Exception e)
            {
                // do nothing
            }
        }
        if (null != getter)
        {
            //getter.close();
        	Sockets.close(port);
        	getter = null;
        }
    }
    
    /**
     * function:终止监听
     * @author 刘小华
     * @date 2013-12-16
     */    
    public void stop() {
        stopped = true;
        if (null != getter)
        {
            //getter.close();
        	Sockets.close(port);
            getter = null;
        }
    }

    /**
     * 设置新的监听接口
     * @param il
     */
    public void setUDPInfoListener(UDPInfoListener il) {
    	this.il = il;
    }

    /**************************************************
     * function: 消息接收监听接口
     * @author 刘小华
     * @date 2013-12-16  
     *************************************************/
    public interface UDPInfoListener
    {
        /**
         * function:消息接收监听触发
         * @author 刘小华
         * @date 2013-12-16       
         * @param msg - 传入的消息
         */
        public void onInfoReciver(InetAddress ip, String msg);
    }
}
