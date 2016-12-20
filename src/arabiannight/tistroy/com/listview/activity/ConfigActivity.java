package arabiannight.tistroy.com.listview.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import arabiannight.tistroy.com.listview.R;

public class ConfigActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.config);

        // 이전 액티비티로부터 넘어온 데이터를 꺼낸다.
        String title = getIntent().getStringExtra("title");
        String no = getIntent().getStringExtra("no");
       
         
        // 백그라운드 레이아웃(액티비티의 루트 레이아웃)을 레퍼런스 한다.
//        LinearLayout llBackground = (LinearLayout)findViewById(R.id.main_back);
         
        // 이전 액티비티로부터 받아온 색상을 배경색으로 세팅한다.
//        llBackground.setBackgroundColor(color);
         
        // 다이얼로그를 생성해, 색상 스트링과 설명을 출력한다.
//        alert(title, no);

	}
    /**
     * 자체적으로 만든 알럿다이얼로그 생성 메소드
     * 
     * @param title 타이틀
     * @param message 메시지
     */
    private void alert(String title, String message)
    {
        // 체인형으로 메소드를 사용한다.
        new AlertDialog.Builder(this)
            // 색상을 타이틀에 세팅한다.
            .setTitle(title)
            // 설명을 메시지 부분에 세팅한다.
            .setMessage(message)
            // 취소를 못하도록 막는다.
            .setCancelable(false)
            // 확인 버튼을 만든다.
            .setPositiveButton("확인", new DialogInterface.OnClickListener()
            {
                /* (non-Javadoc)
                 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
                 */
                public void onClick(DialogInterface dialog, int which)
                {
                    // 확인버튼이 클릭되면 다이얼로그를 종료한다.
                    dialog.dismiss();
 
                    // 액티비티를 종료한다.
                    finish();
                }
            })
            .show();
    }

}
