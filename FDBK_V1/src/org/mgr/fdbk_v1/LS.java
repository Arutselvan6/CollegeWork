package org.mgr.fdbk_v1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;

@SuppressLint("SimpleDateFormat")
public class LS extends Activity {

	TextView t;
	EditText code,email;
	Bundle bdl;
	String cd,ename,uid;
	Calendar edate,ct,cdate;
	RelativeLayout r1,r2;
	Intent i;
	static int flag=0;
	
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
		setContentView(R.layout.activity_ls);
		NoteDialogFragment alert = new NoteDialogFragment();
	    alert.show(getFragmentManager(),"");
	    code = (EditText)findViewById(R.id.lcode);
	    email = (EditText)findViewById(R.id.lemail);
	    r1 = (RelativeLayout)findViewById(R.id.rl11);
	    r2 = (RelativeLayout)findViewById(R.id.rl12);
	    t = (TextView)findViewById(R.id.textView1);
	    t.setText("Welcome to Feedback App");
	    bdl = new Bundle();	    
	    if(!isNetworkAvailable()){			
	    	Toast.makeText(getBaseContext(), "Please Connect to the Internet and then Start this Application", Toast.LENGTH_LONG).show();		
	    	new CountDownTimer(500,500) {
				
				@Override
				public void onTick(long millisUntilFinished) {
				}
				
				@Override
				public void onFinish() {
					finish();
				}
			}.start();	    	
		}
		else{
			r1.setVisibility(View.GONE);
			r2.setVisibility(View.VISIBLE);
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
	        client.post("http://www.scholarcouncil.com/www/mysqlsqlitesync/getformdata.php",params,new JsonHttpResponseHandler(){
	        	@Override
	        	public void onSuccess(int status, Header[] h, JSONObject obj){
	        		try {
	        			Log.e("Data Recvd1", obj+"");
	        			bdl.putString("eventname",obj.getString("EventName"));
	        			bdl.putString("eventdate",obj.getString("EventDate"));
	        			bdl.putString("venue",obj.getString("Venue"));
	        			bdl.putString("dpt",obj.getString("EventDept"));
	        			cd = obj.getString("Code");
	        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        			edate = Calendar.getInstance();
	        			edate.setTime(sdf.parse(obj.getString("EventDate")));	        			
	        			ename = obj.getString("EventName");
	        			r1.setVisibility(View.VISIBLE);
	        			r2.setVisibility(View.GONE);
	        		} catch (JSONException e) {
	        			bdl.putString("eventname","");
	        			bdl.putString("eventdate","");
	        			bdl.putString("venue","");
	        			bdl.putString("dpt","");
	        			cd="NULL";
	        			e.printStackTrace();
	        		}
	        		catch(ParseException pe){
	        			Log.e("Date Problem", "Unable to convert Date");
	        		}
	        	}
	        		
	        	@Override
	        	public void onFailure(int status, Header[] h, String res, Throwable t){
	        		t.printStackTrace();
	        	}
	        });
	        
		}
		
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void login(View v) {
		ct = Calendar.getInstance();
		if(code.getText().toString().equals("")||email.getText().toString().equals("")){
	    	Toast.makeText(getApplicationContext(), "Please fill both data and proceed..", Toast.LENGTH_LONG).show();
	    }
		else if((ct.get(Calendar.DAY_OF_MONTH)==edate.get(Calendar.DAY_OF_MONTH)||ct.get(Calendar.DAY_OF_MONTH)==(edate.get(Calendar.DAY_OF_MONTH)+1))&&(ct.get(Calendar.MONTH)==edate.get(Calendar.MONTH)&&(ct.get(Calendar.YEAR)==edate.get(Calendar.YEAR)))){
			TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
			uid = tm.getDeviceId();
			Log.e("UID", uid);			
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			HashMap<String, String> map = new HashMap<String,String>();
			map.put("uid", uid);
			Gson g = new Gson();
			String a = g.toJson(map);
			params.add("udata", a);
			r2.setVisibility(View.VISIBLE);
			r1.setVisibility(View.GONE);			
	        client.post("http://www.scholarcouncil.com/www/mysqlsqlitesync/check_user.php",params,new JsonHttpResponseHandler(){
	        	@Override
	        	public void onSuccess(int status, Header[] h, JSONObject obj){
	        		try {        			
	        			Log.e("Data Recvd2", obj+"");
	        			if(obj!=null){
	        				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        				cdate = Calendar.getInstance();
	        				cdate.setTime(sdf.parse(obj.getString("event_date")));
        					if(ename.equals(obj.getString("event_name"))&&compDates(edate,cdate)){        						        						
        						Log.e("login", "repeating");
        						callPrev();
	        				}
        					else{
        						callNext();
        						Log.e("login",ename );
        					}
	        			}
	        			else{
	        				callNext();
	        				Toast.makeText(getApplicationContext(), "Good to Go..", Toast.LENGTH_LONG).show();
	        				Log.e("login", "first time");
	        			}        			
	        		} catch (Exception e) {
	        			Log.e("Error in checking user",e+"");
	        		}
	        	}
	        		
	        	@Override
	        	public void onFailure(int status, Header[] h, String res, Throwable t){
	        		t.printStackTrace();
	        		Log.e("Error in Internet Connection_Check_user",res);
	        	}
	        });
			/*if(flag==0&&cd.equals(code.getText().toString())){
				bdl.putString("email", email.getText().toString());
				//i = new Intent(LS.this,F1.class);
				i.putExtras(bdl);
				startActivity(i);
			}
			else if(cd.equals(code.getText().toString())){
				Toast.makeText(getApplicationContext(), "You have already submitted the Feeback.", Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), "Closing Application", Toast.LENGTH_LONG).show();
				//i = new Intent(LS.this,FS.class);
				startActivity(i);
			}
			else{
				Toast.makeText(getApplicationContext(), "Incorrect Code..", Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), "Please Try Again.", Toast.LENGTH_LONG).show();
			}*/
		}
	}
	
	public void callPrev(){
		if(cd.equals(code.getText().toString())){
			Toast.makeText(getApplicationContext(), "You have already submitted the Feedback for this Event.", Toast.LENGTH_LONG).show();
		}
		else{
			Toast.makeText(getApplicationContext(), "Incorrect Code..", Toast.LENGTH_LONG).show();
			Toast.makeText(getApplicationContext(), "Please Try Again.", Toast.LENGTH_LONG).show();
		}
		i = new Intent(LS.this,FS.class);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
	}
	
	public void callNext(){
		bdl.putString("email", email.getText().toString());
		i = new Intent(LS.this,F1.class);
		i.putExtras(bdl);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	public boolean compDates(Calendar c1,Calendar c2){
		return((c1.get(Calendar.DAY_OF_MONTH)==c2.get(Calendar.DAY_OF_MONTH))&&(c1.get(Calendar.MONTH)==c2.get(Calendar.MONTH))&&(c1.get(Calendar.YEAR)==c2.get(Calendar.YEAR)));		
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
		else{
			r1.setVisibility(View.VISIBLE);
			r2.setVisibility(View.GONE);
		}
	}
}
class NoteDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do not press \"Back Button\" after Login")
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dismiss();
                   }
               });
        return builder.create();
    }
}