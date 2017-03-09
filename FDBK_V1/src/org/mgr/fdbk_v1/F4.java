package org.mgr.fdbk_v1;

import java.util.HashMap;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;

public class F4 extends Activity {

	Bundle b;
	RatingBar r1,r2;
	TextView t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f4);
		b = getIntent().getExtras();
		r1 = (RatingBar)findViewById(R.id.ratingBar1);
		r2 = (RatingBar)findViewById(R.id.ratingBar2);
		t = (TextView)findViewById(R.id.textView1);
		t.setText(b.getString("event_name")+" FeedBack");
	}
	
	//To Check Internet Connection
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    if(!(activeNetworkInfo != null && activeNetworkInfo.isConnected())){
	    	//Log.e("Internet", "No Internet");
	    	return false;
	    }
	    return true;
	}
	
	public void done(View v){
		float rt1 = r1.getRating();
		float rt2 = r2.getRating();
		if(!isNetworkAvailable()){			
	    	Toast.makeText(getBaseContext(), "Please Connect to the Internet and then click Submit", Toast.LENGTH_LONG).show();
		}
		else{
			b.putFloat("speaker_rating", rt1);
			b.putFloat("overall_rating", rt2);
			HashMap<String,Object> data = new HashMap<String,Object>();
			for(String x : b.keySet()){
				data.put(x, b.get(x));
			}
			Gson gson = new Gson();
			RequestParams params = new RequestParams();
			AsyncHttpClient client = new AsyncHttpClient();
			String dt = gson.toJson(data);
			//Log.e("Sending Data", dt);
			params.add("feeddata", dt);
			client.post("http://www.scholarcouncil.com/www/mysqlsqlitesync/storeFeedback.php", params, new AsyncHttpResponseHandler(){

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					//Log.e("Insert Failed", arg3+"");
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					//Log.e("Inserted Successfully", "Superb");
					Toast.makeText(getApplicationContext(), "Thanks for your Feedback", Toast.LENGTH_LONG).show();
				}				
			});
			feedbackDone(getApplicationContext());
		}				
	}
	
	public void feedbackDone(Context c){
		Intent intent = new Intent(c, LS.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}
