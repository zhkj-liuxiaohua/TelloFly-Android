package com.lxh.tellofly;


import java.net.InetAddress;

import com.lxh.tellofly.net.Constant;
import com.lxh.tellofly.net.P2PLoader;
import com.lxh.tellofly.net.UDPGetter.UDPInfoListener;
import com.lxh.telloservice.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	P2PLoader telloController = null;
	TextView output = null;
	Handler mainHand = null;
	Runnable runTakeOff = null;
	Runnable runUp = null;
	Runnable runFlip = null;
	Runnable runRight = null;
	Runnable runLand = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		output = (TextView) findViewById(R.id.output);
		mainHand = new Handler();
		telloController = new P2PLoader(null);
		initCommands();
	}
		
	private void initCommands() {
		runTakeOff = new Runnable() {
			@Override
			public void run() {
				printTxt("起飞！");
				telloController.sendMsg("takeoff");
			}
		};
		runUp = new Runnable() {
			@Override
			public void run() {
				printTxt("向上移动50cm！");
				telloController.sendMsg("up 50");
			}
		};
		runFlip = new Runnable() {
			@Override
			public void run() {
				printTxt("翻转！");
				telloController.sendMsg("flip l");
			}
		};
		runRight = new Runnable() {
			@Override
			public void run() {
				printTxt("向右移动一米！");
				telloController.sendMsg("right 100");
			}
		};
		runLand = new Runnable() {
			@Override
			public void run() {
				printTxt("着陆！");
				telloController.sendMsg("land");
			}
		};
	}

	public void connectTello(View v) {
		printTxt("程序开始启动！");
		telloController.startListening();
		telloController.setUDPInfoListener(new UDPInfoListener() {
			public void onInfoReciver(InetAddress ip, String msg) {
				if (Constant.OK_MSG.equals(msg)) {
					printTxt("命令模式成功");
//					try {
//						Thread.sleep(7000);
//						nextStep();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
				}
				if (Constant.ERROR_MSG.equals(msg)) {
					printTxt("命令模式失败");
				} else {
					printTxt("IP=" + ip.toString() + " msg=" + msg);
				}
			}
		});
		telloController.sendMsg("command");
		waitToRun(new Runnable() {
			@Override
			public void run() {
				try {
					runTakeOff.run();
					Thread.sleep(7000);
					runUp.run();
					Thread.sleep(5000);
					for (int i = 0; i < 2; i++) {
						runRight.run();
						Thread.sleep(7000);
						runFlip.run();
						Thread.sleep(7000);
					}
					
					runLand.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 7000);
	}

	// 延时执行
	private void waitToRun(final Runnable r, final int ms) {
		Runnable subr = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(ms);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				r.run();
			}
		};
		new Thread(subr).start();
	}
	
	private void nextStep() {
		telloController.setUDPInfoListener(new UDPInfoListener() {
			public void onInfoReciver(InetAddress ip, String msg) {
				if (Constant.OK_MSG.equals(msg)) {
					printTxt("起飞模式成功");
					try {
						Thread.sleep(7000);
						endStep();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (Constant.ERROR_MSG.equals(msg)) {
					printTxt("起飞模式失败");
				} else {
					printTxt("IP=" + ip.toString() + " msg=" + msg);
				}
			}
		});
		telloController.sendMsg("takeoff");
	}

	// 设置输出信息
	private void printTxt(final String str) {
		mainHand.post(new Runnable() {
			
			@Override
			public void run() {
				output.setText(str);
			}
		});
	}
	
	private void endStep() {
		telloController.sendMsg("land");
		telloController.stopListening();
	}
	
	public void exitApp(View v) {
		if (telloController != null) {
			telloController.stopListening();
			telloController = null;
		}
		System.exit(0);
	}
}
