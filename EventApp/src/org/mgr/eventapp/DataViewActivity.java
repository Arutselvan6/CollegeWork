package org.mgr.eventapp;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;

public class DataViewActivity extends Activity {

	EditText name,date,dept,venue,code;
	Switch swt;
	LinearLayout pb;
	RelativeLayout rv;
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    if(!(activeNetworkInfo != null && activeNetworkInfo.isConnected())){
	    	Log.e("Internet", "No Internet");
	    	return false;
	    }
	    return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_view);
		name = (EditText)findViewById(R.id.ename);
		date = (EditText)findViewById(R.id.edate);
		dept = (EditText)findViewById(R.id.edept);
		venue= (EditText)findViewById(R.id.evenue);
		code = (EditText)findViewById(R.id.ecode);
		swt = (Switch)findViewById(R.id.switch1);
		pb = (LinearLayout)findViewById(R.id.pb_view);
		rv = (RelativeLayout)findViewById(R.id.nm_view);
		if(!isNetworkAvailable()){			
			pb.setVisibility(View.GONE);
			rv.setVisibility(View.VISIBLE);
	    	Toast.makeText(getBaseContext(), "Please Connect to the Internet and then Proceed", Toast.LENGTH_LONG).show();		
	    	new CountDownTimer(500,500) {
				
				@Override
				public void onTick(long millisUntilFinished) {}
				
				@Override
				public void onFinish() {
					finish();
				}
			}.start();	    	
		}
		else{
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			pb.setVisibility(View.VISIBLE);
			rv.setVisibility(View.GONE);
			// Make Http call to getusers.php
	        client.post("http://www.scholarcouncil.com/www/mysqlsqlitesync/getformData.php",params,new JsonHttpResponseHandler(){
	        	@Override
	        	public void onSuccess(int status, Header[] h, JSONObject obj){
	        		try {
	        			Log.e("Data Recvd1", obj+"");
	        			name.setText(obj.getString("EventName"));
	        			date.setText(obj.getString("EventDate"));
	        			dept.setText(obj.getString("EventDept"));
	        			venue.setText(obj.getString("Venue"));
	        			code.setText(obj.getString("Code"));
	        			pb.setVisibility(View.GONE);
	        			rv.setVisibility(View.VISIBLE);
	        		} catch (JSONException e) {
	        			name.setText(" ");
	        			date.setText(" ");
	        			dept.setText(" ");
	        			venue.setText(" ");
	        			code.setText(" ");
	        			Log.e("JSON Error", e.getLocalizedMessage());
	        		}
	        	}
	        		
	        	@Override
	        	public void onFailure(int status, Header[] h, String res, Throwable t){
	        		Log.e("Fetch Error", t.getLocalizedMessage());
	        		t.printStackTrace();
	        	}
	        });
	        swt.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	 
	        	   @Override
	        	   public void onCheckedChanged(CompoundButton buttonView,
	        	     boolean isChecked) {
	        	 
	        	    if(isChecked){
	        	    	name.setEnabled(true);
	        	    	date.setEnabled(true);
	        	    	dept.setEnabled(true);
	        	    	venue.setEnabled(true);
	        	    	code.setEnabled(true);
	        	    }else{
	        	    	name.setEnabled(false);
	        	    	date.setEnabled(false);
	        	    	dept.setEnabled(false);
	        	    	venue.setEnabled(false);
	        	    	code.setEnabled(false);
	        	    }
	        	 
	        	   }
	        	  });
		}
	}
	
	public void gosync(View v){
		if(isNetworkAvailable()){
			HashMap<String,Object> data = new HashMap<String, Object>();
			data.put("EventName", name.getText().toString());
			data.put("EventDate", date.getText().toString());
			data.put("EventDept", dept.getText().toString());
			data.put("Venue", venue.getText().toString());
			data.put("Code", code.getText().toString());
			new FBDataUpdater().updateMysQL(data);
			Toast.makeText(getApplicationContext(), "Form Data Updated", Toast.LENGTH_LONG).show();
			Intent i = new Intent(DataViewActivity.this,HomeActivity.class);
			startActivity(i);
		}
		else{
			Toast.makeText(getApplicationContext(), "Internet is Required to Proceed", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.data_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
