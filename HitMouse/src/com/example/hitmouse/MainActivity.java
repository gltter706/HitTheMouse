package com.example.hitmouse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.publicData.PublicData;

public class MainActivity extends Activity {
	
	BackHoleThread backHoleThread = null;
	MakeMouseThread makeMouseThread = null;
	static BackHoleHandler backHoleHandler = null;
	static MakeMouseHandler makeMouseHandler = null;
	static ImageButton mouseButtons[] = null;
	MouseButtonClickListener mouseButtonClickListener = null;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		makeMouseThread.start();
	}

	public void init(){
		makeMouseThread = new MakeMouseThread();
		backHoleHandler = new BackHoleHandler(getMainLooper());
		makeMouseHandler = new MakeMouseHandler(getMainLooper());
		mouseButtons = new ImageButton[9];
		mouseButtons[0] = (ImageButton) findViewById(R.id.image1);
		mouseButtons[1] = (ImageButton) findViewById(R.id.image2);
		mouseButtons[2] = (ImageButton) findViewById(R.id.image3);
		mouseButtons[3] = (ImageButton) findViewById(R.id.image4);
		mouseButtons[4] = (ImageButton) findViewById(R.id.image5);
		mouseButtons[5] = (ImageButton) findViewById(R.id.image6);
		mouseButtons[6] = (ImageButton) findViewById(R.id.image7);
		mouseButtons[7] = (ImageButton) findViewById(R.id.image8);
		mouseButtons[8] = (ImageButton) findViewById(R.id.image9);
		mouseButtonClickListener = new MouseButtonClickListener();
		for(int i = 0 ; i < PublicData.status.length ; i++){
			PublicData.status[i] = 0;
			mouseButtons[i].setOnClickListener(mouseButtonClickListener);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	
	public class MouseButtonClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Message back_msg = backHoleHandler.obtainMessage();
			switch(v.getId()){
			case R.id.image1:
				if(1 == PublicData.status[0]){
				mouseButtons[0].setImageResource(R.drawable.dizzy);
				back_msg.arg1 = 0;
				back_msg.arg2 = 0;
				}
				break;
			case R.id.image2:
				if(1 == PublicData.status[1]){
					mouseButtons[1].setImageResource(R.drawable.dizzy);
					back_msg.arg1 = 1;
					back_msg.arg2 = 0;
					}
				break;
			case R.id.image3:
				if(1 == PublicData.status[2]){
					mouseButtons[2].setImageResource(R.drawable.dizzy);
					back_msg.arg1 = 2;
					back_msg.arg2 = 0;
					}
				break;
			case R.id.image4:
				if(1 == PublicData.status[3]){
					mouseButtons[3].setImageResource(R.drawable.dizzy);
					back_msg.arg1 = 3;
					back_msg.arg2 = 0;
					}
				break;
			case R.id.image5:
				if(1 == PublicData.status[4]){
					mouseButtons[4].setImageResource(R.drawable.dizzy);
					back_msg.arg1 = 4;
					back_msg.arg2 = 0;
					}
				break;
			case R.id.image6:
				if(1 == PublicData.status[5]){
					mouseButtons[5].setImageResource(R.drawable.dizzy);
					back_msg.arg1 = 5;
					back_msg.arg2 = 0;
					}
				break;
			case R.id.image7:
				if(1 == PublicData.status[6]){
					mouseButtons[6].setImageResource(R.drawable.dizzy);
					back_msg.arg1 = 6;
					back_msg.arg2 = 0;
					}
				break;
			case R.id.image8:
				if(1 == PublicData.status[7]){
					mouseButtons[7].setImageResource(R.drawable.dizzy);
					back_msg.arg1 = 7;
					back_msg.arg2 = 0;
					}
				break;
			case R.id.image9:
				if(1 == PublicData.status[8]){
					mouseButtons[8].setImageResource(R.drawable.dizzy);
					back_msg.arg1 = 8;
					back_msg.arg2 = 0;
					}
				break;
			}
			backHoleHandler.sendMessageDelayed(back_msg, PublicData.dizzyTime);
		}
		
	}
	
	
	public class BackHoleHandler extends Handler{
		public BackHoleHandler(Looper looper){
			super(looper);
		}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int index = msg.arg1;
			int is_hitted = msg.arg2;
			//第一次处理消息
			if(0 == msg.what){
				backHoleThread = new BackHoleThread(index, is_hitted);
				backHoleThread.start();
				System.out.println("backHoleThread-->start");
			}
			mouseButtons[index].setImageResource(R.drawable.ready);
			PublicData.status[index] = 0;
		} 
	}
	
	
	public class MakeMouseHandler extends Handler{
		
		public MakeMouseHandler(Looper looper){
			super(looper);
		}
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Message back_msg = backHoleHandler.obtainMessage();
			back_msg.arg1 = msg.arg1;
			back_msg.arg2 = 0;
			int index = msg.arg1;
			if(0 == PublicData.status[index]){
				System.out.println("in-->MakeMouseHandler");
				mouseButtons[index].setImageResource(R.drawable.comeout);
				System.out.println("index-->"+index);
				PublicData.status[index] = 1;
				backHoleHandler.sendMessageDelayed(back_msg, PublicData.stayTime);
			}
		}
	}
	
	
}
