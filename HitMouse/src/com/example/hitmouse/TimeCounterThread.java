package com.example.hitmouse;

import com.example.publicData.PublicData;

import android.os.Message;


public class TimeCounterThread extends Thread{
		private int originalTime;
		private boolean runOrStop = true;
		
		public TimeCounterThread(int originalTime){
			this.originalTime = originalTime;
		}
		
		public void setRunOrStop(boolean runOrStop){
			this.runOrStop = runOrStop;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				while(runOrStop){
				Thread.sleep(1000);
				originalTime-- ;
				PublicData.makeMouseTime = PublicData.makeMouseTime-14;
				PublicData.stayTime = PublicData.stayTime-10;
				Message timeMsg = MainActivity.timeCounterHandler.obtainMessage();
				timeMsg.what = 0x111;
				timeMsg.arg1 = originalTime;
				MainActivity.timeCounterHandler.sendMessage(timeMsg);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
