package com.lxh.tellofly.net;

/**************************************************
 * function: 常量�?
 * @author 刘小�?  
 * @date 2013-11-25  
 *************************************************/
public class Constant
{
    /** RSS网址 */
    public static final String API_URL = "http://rss.sina.com.cn/roll/sports/hot_roll.xml";
    
    /** �?-目标IP */
    public static final String SRC_DST_IPS [] = {  "192.168.0.45","192.168.0.45",};
    /** 工作组IP */
    public static final String GROUP_IP = "192.168.10.1";
    public static final int SENDPORT = 8889;
    public static final int GETPORT = 8890;
    public static final String OK_MSG = "ok";
    public static final String ERROR_MSG = "error";
    
    public static final int ALIVEGETPORT = 6061;
    
    public static final int MIN_PORT = 5050;
    public static final String NAME = "Name=";
    public static final int NAMELEN = NAME.length();
    public static final String NAMEREQUEST = "LCALL";
    public static final String LOGOUT = "OUTLINE";
}
