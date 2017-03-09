package org.mgr.fdbk_v1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class F1 extends Activity {

	TextView t1,t2,t3,t4;
	RadioGroup rg;
	RadioButton r;
	Bundle b,b1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f1);
		b = getIntent().getExtras();
		t1 = (TextView)findViewById(R.id.textView3);
		t2 = (TextView)findViewById(R.id.textView5);
		t3 = (TextView)findViewById(R.id.textView7);
		t4 = (TextView)findViewById(R.id.textView9);
		rg = (RadioGroup)findViewById(R.id.radioGroup1);
		t1.setText(b.getString("eventname"));
		t2.setText(b.getString("eventdate"));
		t3.setText(b.getString("dpt"));
		t4.setText(b.getString("venue"));		
	}
	
	public void moveNext(View v){
		TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		String uid = tm.getDeviceId();
		String pno = tm.getLine1Number();
		r = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
		b1 = new Bundle();
		b1.putString("uid", uid);
		b1.putString("PHNO", pno);
		b1.putString("email", b.getString("email"));
		b1.putString("event_date", b.getString("eventdate"));
		b1.putString("event_name", b.getString("eventname"));
		b1.putString("person_identity", r.getText().toString());
		Intent i = new Intent(F1.this,F2.class);
		i.putExtras(b1);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}