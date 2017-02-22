package org.mgr.eventapp;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends Activity {

	EditText ucode;
	String code;
	
	//To Check Internet Connection
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
		setContentView(R.layout.activity_login);
		ucode = (EditText)findViewById(R.id.codeText);
		if(!isNetworkAvailable()){			
	    	Toast.makeText(getBaseContext(), "Please Connect to the Internet and then Start this Application", Toast.LENGTH_LONG).show();		
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
			
			// Make Http call to getusers.php
	        client.post("http://www.scholarcouncil.com/www/mysqlsqlitesync/gmd.php",params,new JsonHttpResponseHandler(){
	        	@Override
	        	public void onSuccess(int status, Header[] h, JSONObject obj){
	        		try {
	        			Log.e("Data Recvd1", obj+"");
	        			code = obj.getString("mcode");
	        		} catch (JSONException e) {
	        			code="NULL";
	        			e.printStackTrace();
	        		}
	        	}
	        		
	        	@Override
	        	public void onFailure(int status, Header[] h, String res, Throwable t){
	        		t.printStackTrace();
	        	}
	        });
		}
	}	
	
	public void checkLogin(View v){
		Intent i;
		if(code.equals(ucode.getText().toString())){
			i = new Intent(LoginActivity.this,HomeActivity.class);
			startActivity(i);
		}
		else{
			Log.e("Incorrect Code", ucode.getText().toString());
			Toast.makeText(getApplicationContext(), "Incorrect Code", Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(), "Please check the Code and Try Again!", Toast.LENGTH_SHORT).show();
		}
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onResume(){
		super.onResume();
		Intent i = getIntent();
		if(i.getBooleanExtra("EXIT", false)){
			finishAndRemoveTask();
			finish();
		}
	}
}
