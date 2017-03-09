package org.mgr.fdbk_v1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class UES extends Activity {

	WebView wv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ues);
		wv = (WebView)findViewById(R.id.webView);
		wv.getSettings().setUseWideViewPort(true);
	    wv.getSettings().setLoadWithOverviewMode(true);
	    wv.getSettings().setBuiltInZoomControls(true);
	    wv.getSettings().setDisplayZoomControls(false);
		if(isNetworkAvailable())
			wv.loadUrl("https://www.drmgrdu.ac.in/Univ_Event/Eve_pre_details/Eve_OA.htm");
		else{
			Toast.makeText(getApplicationContext(), "No Internet.", Toast.LENGTH_LONG).show();
			Intent i = new Intent(UES.this,FS.class);
			startActivity(i);
			finish();
		}
	}
	
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
}
