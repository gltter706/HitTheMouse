package com.example.hitmouse;

import java.util.Random;

import com.example.publicData.PublicData;

import android.os.Message;

public class MakeMouseThread extends Thread{
	
	private boolean runOrStop = true;
	
	public void setRunOrStop(boolean runOrStop){
		this.runOrStop = runOrStop;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while(runOrStop){
		int random = new Random().nextInt(9);
		Message createMsg = MainActivity.makeMouseHandler.obtainMessage();
		createMsg.arg1 = random;
		createMsg.arg2 = 0;
		MainActivity.makeMouseHandler.sendMessage(createMsg);
		try {
			Thread.sleep(PublicData.makeMouseTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}
}
