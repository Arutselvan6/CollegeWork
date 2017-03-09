package org.mgr.fdbk_v1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class F2 extends Activity {

	Bundle b;
	Spinner s;
	RadioGroup rg1,rg2;
	RadioButton r;
	TextView t;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f2);
		b = getIntent().getExtras();
		s = (Spinner)findViewById(R.id.spinner1);
		rg1 = (RadioGroup)findViewById(R.id.radioGroup2);
		rg2 = (RadioGroup)findViewById(R.id.radioGroup3);
		t = (TextView)findViewById(R.id.textView1);
		t.setText(b.getString("event_name")+" FeedBack");
		List<String>categories1 = new ArrayList<String>();
		categories1.add("Circular");
		categories1.add("Website");
		categories1.add("Social Media");
		categories1.add("Brochure");
		categories1.add("Through Collegue");
		categories1.add("Through friend");
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categories1);
		dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(dataAdapter1);
	}
	
	public void moveNext(View v){
		b.putString("how_know_event", (String)s.getSelectedItem());
		r = (RadioButton)findViewById(rg1.getCheckedRadioButtonId());
		b.putString("event_started_ontime", r.getText().toString());
		r = (RadioButton)findViewById(rg2.getCheckedRadioButtonId());
		b.putString("eventvenue_hospitality", r.getText().toString());
		Intent i = new Intent(F2.this,F3.class);
		i.putExtras(b);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}
