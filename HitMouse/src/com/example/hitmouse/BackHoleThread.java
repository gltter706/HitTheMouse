package com.example.hitmouse;

import android.os.Message;

import com.example.publicData.PublicData;

public class BackHoleThread extends Thread{
	private int index,is_hitted;
	private boolean runOrStop = true;
	
	public BackHoleThread(int index, int is_hitted){
		this.index = index;
		this.is_hitted = is_hitted;
	}
	
	public void setRunOrStop(boolean runOrStop){
		this.runOrStop = runOrStop;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		if(runOrStop){
		if(1 == is_hitted){
			try {
				Thread.sleep(PublicData.dizzyTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
				try {
					Thread.sleep(PublicData.stayTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		Message back_msg = MainActivity.backHoleHandler.obtainMessage();
		this.index = back_msg.arg1;
		this.is_hitted = back_msg.arg2;
		back_msg.what = 1;
		MainActivity.backHoleHandler.sendMessage(back_msg);
		}
	}
}
