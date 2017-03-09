package org.mgr.fdbk_v1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class FS extends Activity {

	TextView title;
	Intent i;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fs);
		title = (TextView)findViewById(R.id.title);
		String blueString = getResources().getString(R.string.event_title);
		title.setText(Html.fromHtml(blueString));
	}
	
	public void updCall(View v){
		i = new Intent(FS.this,UES.class);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		startActivity(i);
	}
	
	public void fdbkCall(View v){
		i = new Intent(FS.this,LS.class);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		startActivity(i);
	}
}
