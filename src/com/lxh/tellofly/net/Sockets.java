package com.lxh.tellofly.net;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class Sockets {

	private static HashMap<Integer, DatagramSocket> sockets = null;

	/**
	 * 获取指定端口监听连接
	 * @param port
	 * @return
	 * @throws SocketException
	 */
	public static synchronized DatagramSocket getSocket(int port) throws SocketException {
		if (null == sockets) {
			sockets = new HashMap<Integer, DatagramSocket>();
		}
		DatagramSocket socket = sockets.get(port);
		if (null == socket) {
			socket = new DatagramSocket(port);
			sockets.put(port, socket);
		}
		return socket;
	}

	/**
	 * 关闭指定端口连接
	 * @param port
	 */
	public static synchronized void close(int port) {
		if (null == sockets) {
			sockets = new HashMap<Integer, DatagramSocket>();
		}
		DatagramSocket socket = sockets.get(port);
		if (null != socket) {
			socket.close();
			sockets.remove(port);
		}
	}

	/**
	 * 清除所有连接
	 */
	public static synchronized void closeAll() {
		if (null != sockets) {
			for (DatagramSocket s : sockets.values()) {
				s.close();
			}
			sockets.clear();
			sockets = null;
		}
	}
}
