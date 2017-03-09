package org.mgr.fdbk_v1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class F3 extends Activity {

	Bundle b;
	RadioGroup rg;
	RadioButton r;
	EditText t1,t2;
	TextView t;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f3);
		b = getIntent().getExtras();
		rg = (RadioGroup)findViewById(R.id.radioGroup4);
		t1 = (EditText)findViewById(R.id.editText1);
		t2 = (EditText)findViewById(R.id.editText2);
		t = (TextView)findViewById(R.id.textView1);
		t.setText(b.getString("event_name")+" FeedBack");
	}
	
	public void moveNext(View v){
		r = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
		b.putString("overall_eventorganization_satisfied", r.getText().toString());
		b.putString("interested_events", t1.getText().toString());
		b.putString("other_info", t2.getText().toString());
		Intent i = new Intent(F3.this,F4.class);
		i.putExtras(b);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}
