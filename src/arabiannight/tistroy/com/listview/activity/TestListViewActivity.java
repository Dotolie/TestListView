package arabiannight.tistroy.com.listview.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import arabiannight.tistroy.com.listview.R;
import arabiannight.tistroy.com.listview.adapter.CustomArrayAdapter;
import arabiannight.tistroy.com.listview.data.InfoClass;

public class TestListViewActivity extends Activity {
	private final String TAG = "ActionTester";
	
	private CustomArrayAdapter mCustomArrayAdaptor = null;
	private ArrayList<InfoClass> mActionList = null;
	private Button mBtnAdd = null;
	private Button mBtnDeletes = null;
	private Button mBtnEdit = null;
	private TextView mTvActionItems = null;
	
	private int mCount = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setLayout();
        setViews();
        
        mActionList = new ArrayList<InfoClass>();
        
        // BaseAdapter 연결
//        mListView.setAdapter(new CustomBaseAdapter(this, mCareList));
        
        // ArrayAdapter 연결
        mCustomArrayAdaptor = new CustomArrayAdapter(this, R.layout.list_row, mActionList);
        mListView.setAdapter( mCustomArrayAdaptor );
        
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				
				Bundle extras = new Bundle();
				extras.putString("title", mActionList.get(position).title);
				extras.putString("no" , mActionList.get(position).no );
				
				Intent intent = new Intent(getApplicationContext(), ConfigActivity.class);
				intent.putExtras(extras);
				
				startActivity(intent);
				
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
				mActionList.add(new InfoClass (
						mCount + " 번째" + " ListView 입니다.", 
						getResources().getDrawable(R.drawable.action), 
						""+ mCount
						));	
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
    
    private ListView mListView = null;
    
    private void setLayout(){
    	mListView = (ListView) findViewById(R.id.lv_list);
    }
}


