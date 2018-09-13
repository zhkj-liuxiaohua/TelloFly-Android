package com.lxh.tellofly;


import java.net.InetAddress;

import com.lxh.tellofly.net.Constant;
import com.lxh.tellofly.net.P2PLoader;
import com.lxh.tellofly.net.UDPGetter.UDPInfoListener;
import com.lxh.telloservice.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	P2PLoader telloController = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		telloController = new P2PLoader(null);
	}
		
	public void connectTello(View v) {
		
		telloController.startListening();
		telloController.setUDPInfoListener(new UDPInfoListener() {
			public void onInfoReciver(InetAddress ip, String msg) {
				if (Constant.OK_MSG.equals(msg)) {
					Toast.makeText(MainActivity.this, "命令模式成功", Toast.LENGTH_SHORT).show();
					try {
						Thread.sleep(7000);
						nextStep();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (Constant.ERROR_MSG.equals(msg)) {
					Toast.makeText(MainActivity.this, "命令模式失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
		telloController.sendMsg("command");
	}
	
	private void nextStep() {
		telloController.setUDPInfoListener(new UDPInfoListener() {
			public void onInfoReciver(InetAddress ip, String msg) {
				if (Constant.OK_MSG.equals(msg)) {
					Toast.makeText(MainActivity.this, "起飞模式成功", Toast.LENGTH_SHORT).show();
					try {
						Thread.sleep(7000);
						endStep();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (Constant.ERROR_MSG.equals(msg)) {
					Toast.makeText(MainActivity.this, "起飞模式失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
		telloController.sendMsg("takeoff");
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
