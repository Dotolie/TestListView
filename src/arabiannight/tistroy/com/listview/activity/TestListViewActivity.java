package arabiannight.tistroy.com.listview.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import arabiannight.tistroy.com.listview.R;
import arabiannight.tistroy.com.listview.adapter.CustomArrayAdapter;
import arabiannight.tistroy.com.listview.data.Led;
import arabiannight.tistroy.com.listview.data.Motion;
import arabiannight.tistroy.com.listview.data.Motor;
import arabiannight.tistroy.com.listview.data.Sensor;
import arabiannight.tistroy.com.listview.data.Sound;

public class TestListViewActivity extends Activity {
	private final String TAG = "ActionTester";
	
	private static final int RequestCode2Config = 1;
	private CustomArrayAdapter mCustomArrayAdaptor = null;
	private ArrayList<Motion> mMotionList = null;
	private Button mBtnAdd = null;
	private Button mBtnDeletes = null;
	private Button mBtnEdit = null;
	private TextView mTvActionItems = null;
	
	private int mCount = 0;
	private int mPosition = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setLayout();
        setViews();

        
        mMotionList = xmlParser();
        
//      BaseAdapter 연결
//      mListView.setAdapter(new CustomBaseAdapter(this, mCareList));
        
        // ArrayAdapter 연결
        mCustomArrayAdaptor = new CustomArrayAdapter(this, R.layout.list_row, mMotionList);
        mListView.setAdapter( mCustomArrayAdaptor );
        
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				
				mPosition = position;
				
				Motion motionObject = mMotionList.get(position);
				
				Intent intent = new Intent(getApplicationContext(), ConfigActivity.class);
				intent.putExtra("MotionObject", motionObject);
				
				startActivityForResult(intent, RequestCode2Config);
				
				Toast.makeText(
						getApplicationContext(), 
						"ITEM CLICK = " + position,
						Toast.LENGTH_SHORT
						).show();
			}
		});
    }
    
    private void setViews() {
    	mBtnAdd = (Button)findViewById(R.id.btn_add);
    	mBtnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Sensor sensor = new Sensor(3,10);
				Motor[] motors = { 
						new Motor(1,0,9,100,50,5),
						new Motor(2,0,9,100,50,5),
						new Motor(3,0,9,100,50,5),
						new Motor(4,0,9,100,50,5)
						};
				Sound sound = new Sound(3, 100);
				Led led = new Led(5, 20);

				mCount = mMotionList.size();
				Motion motion = new Motion(
						mCount,
						mCount + " 번째" + " ListView 입니다.", 
						sensor,
						motors,
						sound,
						led
						);
				
				mMotionList.add( motion );
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mTvActionItems.setText(""+mCount);	
					}
				});
				
				mCustomArrayAdaptor.notifyDataSetChanged();
			}

		});
    	
    	
    	mBtnDeletes = (Button)findViewById(R.id.btn_deletes);
    	mBtnDeletes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if( mCount>0) {
					mCount--;
					mMotionList.remove(mCount);
					
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							mTvActionItems.setText(""+mCount);
						}
					});
					
					mCustomArrayAdaptor.notifyDataSetChanged();
				}
			}
		});
    	
    	
    	mBtnEdit = (Button)findViewById(R.id.btn_edit);
    	
    	mTvActionItems = (TextView)findViewById(R.id.tv_actionItems);
    }
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch( requestCode ) {
		case RequestCode2Config:
			if( resultCode == RESULT_OK) {
				Motion motion = (Motion)data.getSerializableExtra("MotionObject");
				Motion tmp = mMotionList.set(mPosition, motion);
				Log.d(TAG, "received intent OK");
			}
			else {
				Log.d(TAG,  "received intent CANCEL");
			}
			break;
		default:
			break;
		}
	}

    
    private ArrayList<Motion> xmlParser() {
    	ArrayList<Motion> arrayList = new ArrayList<Motion>();
    	
    	try {
    		String dirPath = getConfigPath();

        	InputStream is = new FileInputStream(dirPath + "motions.xml");
    		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
    		XmlPullParser parser = factory.newPullParser();
    		parser.setInput(new InputStreamReader(is, "UTF-8"));
    		int eventType = parser.getEventType();
    		   
    		int nMotors = 0;
    		boolean bOnSound = false;
    		boolean bOnSensor = false;
    		boolean bOnMotor = false;
    		boolean bOnLed = false;
    		
    		Motion motion = null;
    		Sensor sensor = null;
    		Motor[] motors = null;
    		Motor motor = null;
    		Sound sound = null;
    		Led led = null;
    		
    		while(eventType != XmlPullParser.END_DOCUMENT ) {
    			switch( eventType ) {
    			case XmlPullParser.START_TAG:
    				String startTag = parser.getName();
    				
    				//--------------------------------------------------------
    				if( startTag.equals("motion")) {
    					motion = new Motion();
    					String no = parser.getAttributeValue(0);
    					motion.no = Integer.parseInt( no );
    				}
    				if( startTag.equals("name")) {
    					motion.title = parser.nextText();
    				}
    				//--------------------------------------------------------
    				if( startTag.equals("sensor")) {
    					sensor = new Sensor();
    					bOnSensor = true;
    				}
    				if( bOnSensor && startTag.equals("type")) {
   						sensor.type = Byte.parseByte( parser.nextText() );    						
    				}
    				//--------------------------------------------------------
    				if( startTag.equals("motor")) {
    					if( nMotors == 0) {
    						motors = new Motor[4];
    					}
    					motor = new Motor();
    					bOnMotor = true;
    				}
    				if( bOnMotor && startTag.equals("init")) {
    					motor.angle_init = Byte.parseByte( parser.nextText());
    				}
    				if( bOnMotor && startTag.equals("start")) {
    					motor.angle_start = Byte.parseByte( parser.nextText());
    				}
    				if( bOnMotor && startTag.equals("stop")) {
    					motor.angle_stop = Byte.parseByte( parser.nextText());
    				}
    				if( bOnMotor && startTag.equals("delay")) {
    					motor.delay_time = Byte.parseByte( parser.nextText());
    				}
    				if( bOnMotor && startTag.equals("hold")) {
    					motor.hold_time = Byte.parseByte( parser.nextText());
    				}    				
    				if( bOnMotor && startTag.equals("repeat")) {
    					motor.repeat_count = Byte.parseByte( parser.nextText());
    				}    				
    				//--------------------------------------------------------
    				if( startTag.equals("sound")) {
    					sound = new Sound();
    					bOnSound = true;
    				}
    				if( bOnSound && startTag.equals("index")) {
    					sound.index = Byte.parseByte( parser.nextText());
    				}
    				if( bOnSound && startTag.equals("delay")) {
    					sound.delay_time = Byte.parseByte( parser.nextText());
    				}
    				//--------------------------------------------------------
    				if( startTag.equals("led")) {
    					led = new Led();
    					bOnLed = true;
    				}
    				if( bOnLed && startTag.equals("blink")) {
    					led.blink = Byte.parseByte( parser.nextText());
    				}
    				if( bOnLed && startTag.equals("delay")) {
    					led.delay_time = Byte.parseByte( parser.nextText());
    				}
    				break;
    				
    			case XmlPullParser.END_TAG:
    				String endTag = parser.getName();
    				
    				//--------------------------------------------------------
    				if( endTag.equals("motion")) {
    					motion.Motors = motors;
    					arrayList.add(motion);
    					nMotors = 0;
    				}
    				//--------------------------------------------------------
    				if( endTag.equals("sensor")) {
    					motion.Sensor = sensor;
    					bOnSensor = false;
    				}    				
    				//--------------------------------------------------------
    				if( endTag.equals("motor")) {
    					motors[nMotors++] = motor;
    					bOnMotor = false;
    				}    				
    				//--------------------------------------------------------
    				if( endTag.equals("sound")) {
    					motion.Sound = sound;
    					bOnSound = false;
    				}    				
    				//--------------------------------------------------------
    				if( endTag.equals("led")) {
    					motion.Led = led;
    					bOnLed = false;
    				}    				
    				//--------------------------------------------------------
    				break;
    			}
    			eventType = parser.next();    			
    		}
    	}
    	catch( XmlPullParserException e ) {
    		e.printStackTrace();
    	}
    	catch( UnsupportedEncodingException e) {
    		e.printStackTrace();
    	}
    	catch( IOException e) {
    		e.printStackTrace();
    	}
    	
    	return arrayList;
    }

    private String getConfigPath() {
    	AssetManager assetManager = getAssets();
    	InputStream in = null;
    	OutputStream out = null;
    	
		String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XML/";
		File file = new File(dirPath);
		
		if(!file.exists())
			file.mkdirs();
		
		File xmlFile = new File(dirPath + "motions.xml");
		if(!xmlFile.exists()) {
			try {
				in = assetManager.open("motions_default.xml");
				out = new FileOutputStream(dirPath + "motions.xml");
				byte[] buffer = new byte[1024];
				int read;
				while( (read = in.read(buffer)) != -1 ) {
					out.write(buffer,0,read);
				}
				in.close();
				out.flush();
				out.close();
				in=null;
				out= null;
			}
			catch( Exception e) {
				e.printStackTrace();
			}
		}

		return dirPath;
    }
	private ListView mListView = null;
    
    private void setLayout(){
    	mListView = (ListView) findViewById(R.id.lv_list);
    }
}


