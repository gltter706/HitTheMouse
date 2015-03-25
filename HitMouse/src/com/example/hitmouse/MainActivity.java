package com.example.hitmouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.publicData.PublicData;

public class MainActivity extends Activity {
	
	BackHoleThread backHoleThread = null;
	MakeMouseThread makeMouseThread = null;
	TimeCounterThread timeCounterThread = null;
	MouseCounterHandler mouseCounterHandler = null;
	static BackHoleHandler backHoleHandler = null;
	static MakeMouseHandler makeMouseHandler = null;
	static TimeCounterHandler timeCounterHandler= null;
	static ImageView mouseButtons[] = null;
	MouseButtonClickListener mouseButtonClickListener = null;
	
	TextView tv_time = null;
	TextView tv_goal = null;
	TextView tv_hitNumber = null;
	
	int hitNumber = 0;
	int goal = 50;
	int widthPixels,heightPixels = 0;
	int ImageViewHeight,ImageViewWidth = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		makeMouseThread.start();
		timeCounterThread.start();
	}

	public void init(){
		hitNumber = 0;
		PublicData.stayTime = 900;
		PublicData.makeMouseTime = 700;
		tv_time = (TextView)findViewById(R.id.tv_time);
		tv_time.setText("时间" + "0s");
		tv_goal = (TextView)findViewById(R.id.tv_goal);
		tv_goal.setText("目标:" + goal + "只");
		tv_hitNumber = (TextView)findViewById(R.id.tv_hitNumber);
		tv_hitNumber.setText("你打中了" + hitNumber + "只地屎!");
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		widthPixels = dm.widthPixels;
		heightPixels = dm.heightPixels;
		ImageViewHeight = (int) (heightPixels/4);
		ImageViewWidth = (int) (widthPixels/4);
		makeMouseThread = new MakeMouseThread();
		timeCounterThread = new TimeCounterThread(30);
		backHoleHandler = new BackHoleHandler(getMainLooper());
		makeMouseHandler = new MakeMouseHandler(getMainLooper());
		timeCounterHandler = new TimeCounterHandler(getMainLooper());
		mouseCounterHandler = new MouseCounterHandler(getMainLooper());
		mouseButtons = new ImageView[9];
		mouseButtons[0] = (ImageView) findViewById(R.id.image1);
		mouseButtons[1] = (ImageView) findViewById(R.id.image2);
		mouseButtons[2] = (ImageView) findViewById(R.id.image3);
		mouseButtons[3] = (ImageView) findViewById(R.id.image4);
		mouseButtons[4] = (ImageView) findViewById(R.id.image5);
		mouseButtons[5] = (ImageView) findViewById(R.id.image6);
		mouseButtons[6] = (ImageView) findViewById(R.id.image7);
		mouseButtons[7] = (ImageView) findViewById(R.id.image8);
		mouseButtons[8] = (ImageView) findViewById(R.id.image9);
		mouseButtonClickListener = new MouseButtonClickListener();
		
		LayoutParams lp = mouseButtons[0].getLayoutParams();
		lp.height = ImageViewHeight;
		lp.width = ImageViewWidth;
		for(int i = 0 ; i < 9 ; i++){
			PublicData.status[i] = 0;
			mouseButtons[i].setOnClickListener(mouseButtonClickListener);
			mouseButtons[i].setLayoutParams(lp);
		}
	}
	
	public void setDizzyAndCountNumber(int index, Message back_msg){
		if(1 == PublicData.status[index]){
			mouseButtons[index].setImageResource(R.drawable.dizzy);
			back_msg.arg1 = index;
			back_msg.arg2 = 0;
			hitNumber++;
			tv_hitNumber.setText("你打中了" + hitNumber + "只地屎!");
			PublicData.status[index] = 0;
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
				setDizzyAndCountNumber(0, back_msg);
				break;
			case R.id.image2:
				setDizzyAndCountNumber(1, back_msg);
				break;
			case R.id.image3:
				setDizzyAndCountNumber(2, back_msg);
				break;
			case R.id.image4:
				setDizzyAndCountNumber(3, back_msg);
				break;
			case R.id.image5:
				setDizzyAndCountNumber(4, back_msg);
				break;
			case R.id.image6:
				setDizzyAndCountNumber(5, back_msg);
				break;
			case R.id.image7:
				setDizzyAndCountNumber(6, back_msg);
				break;
			case R.id.image8:
				setDizzyAndCountNumber(7, back_msg);
				break;
			case R.id.image9:
				setDizzyAndCountNumber(8, back_msg);
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
			}
			mouseButtons[index].setImageResource(R.drawable.thehole);
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
				mouseButtons[index].setImageResource(R.drawable.comeout);
				PublicData.status[index] = 1;
				backHoleHandler.sendMessageDelayed(back_msg, PublicData.stayTime);
			}
		}
	}
	public class TimeCounterHandler extends Handler{
		
		public TimeCounterHandler(Looper looper){
			super(looper);
		}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(0x111 == msg.what){
				int currentTime = msg.arg1;
				tv_time.setText("时间:" + currentTime + "s");
				if(0 == currentTime){
					if(hitNumber < 80){
						makeMouseThread.setRunOrStop(false);
						timeCounterThread.setRunOrStop(false);
						backHoleThread.setRunOrStop(false);
						makeMouseThread.interrupt();
						timeCounterThread.interrupt();
						backHoleThread.interrupt();
						AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
						.setMessage("你输了!T—T")
						.setPositiveButton("怪我咯,不玩咯",new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								MainActivity.this.finish();
							}
							
						})
						.setNegativeButton("尼玛再来一局!", new DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								init();
								makeMouseThread.start();
								timeCounterThread.start();
							}
							
						}).create();
						
						dialog.setCanceledOnTouchOutside(false);
						dialog.show();

					}
				}
			}
		}
	}
	
	public class MouseCounterHandler extends Handler{
		public MouseCounterHandler(Looper looper){
			super(looper);
		}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
	}
	
}
