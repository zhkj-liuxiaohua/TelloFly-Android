package com.lxh.tellofly.net;

import com.lxh.tellofly.net.UDPGetter.UDPInfoListener;

/**************************************************
 * function: P2P聊天使用者
 * @author 刘小华
 * @date 2013-12-16  
 *************************************************/
public class P2PLoader
{
    UDPGetter getter = null;
    UDPSender sender = null;
    UDPInfoListener il = null;

    public P2PLoader(UDPInfoListener il) {
        this.il = il;
    }
    
    /**
     * function:开启消息监听
     * @author 刘小华
     * @date 2013-12-16        
     */    
    public void startListening() {
        if (null != getter)
            return;
        getter = new UDPGetter(il);
        new Thread(getter).start();
    }
    
    /**
     * function:停止消息监听
     * @author 刘小华
     * @date 2013-12-16        
     */    
    public void stopListening()
    {
        if (null != getter)
        {
            getter.stop();
            getter = null;
        }
        if (null != sender)
        {
            sender.destroy();
            sender = null;
        }
    }

    /**
     * 换接口
     * @param il
     */
    public void setUDPInfoListener(UDPInfoListener il) {
    	this.il = il;
    	getter.setUDPInfoListener(this.il);
    }
    
    /**
     * function:发送消息
     * @author 刘小华
     * @date 2013-12-16       
     * @param msg - 待发送消息
     */    
    public void sendMsg(final String msg) {
        if (null == sender)
        {
            try {
                sender = new UDPSender();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        if (null != sender) {
            new Thread(new Runnable()
            {
                public void run()
                {
                    sender.sendMsg(msg);
                }
            }).start();
        }
    }
}
