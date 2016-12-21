package arabiannight.tistroy.com.listview.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	private ArrayList<Motion> mActionList = null;
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
        
        mActionList = new ArrayList<Motion>();
        
        // BaseAdapter 연결
//        mListView.setAdapter(new CustomBaseAdapter(this, mCareList));
        
        // ArrayAdapter 연결
        mCustomArrayAdaptor = new CustomArrayAdapter(this, R.layout.list_row, mActionList);
        mListView.setAdapter( mCustomArrayAdaptor );
        
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				
				mPosition = position;
				
				Motion motionObject = mActionList.get(position);
				
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

				Motion motion = new Motion(
						mCount,
						mCount + " 번째" + " ListView 입니다.", 
						sensor,
						motors,
						sound,
						led
						);
				
				mActionList.add( motion );
				mCount++;
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
					mActionList.remove(mCount);
					
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
				Motion tmp = mActionList.set(mPosition, motion);
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


	private ListView mListView = null;
    
    private void setLayout(){
    	mListView = (ListView) findViewById(R.id.lv_list);
    }
}


