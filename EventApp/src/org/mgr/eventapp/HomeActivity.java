package org.mgr.eventapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends Activity {

	Intent i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}
	
	public void viewData(View v){
		i = new Intent(HomeActivity.this,DataViewActivity.class);
		startActivity(i);
	}
	
	public void viewResult(View v){
		i = new Intent(HomeActivity.this,ResultViewActivity.class);
		startActivity(i);
	}
}
