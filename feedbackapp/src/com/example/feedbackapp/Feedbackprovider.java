
package com.example.feedbackapp;

import java.util.HashMap;

//import java.sql.SQLException;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class Feedbackprovider extends ContentProvider {

	static final String PROVIDER_NAME = "com.example.feedbackapp.provider.College";
	static final String URL = "content://" + PROVIDER_NAME + "/students";
	static final Uri CONTENT_URI = Uri.parse(URL);
	static final int STUDENTS = 1;
	static final int STUDENT_ID = 2;
	static final String _ID = "_id";
	static final String PERSON_ID = "person_identity";
	static final String HOW_KNOW_EVENT = "how_know_event";
	static final String EVENT_STARTED_ONTIME = "event_started_ontime";
	static final String EVENTVENUE_HOSPITALITY = "eventvenue_hospitality";
	static final String OVERALL_EVEORG_SAT = "overall_eventorganization_satisfied";
	static final String CNT_USE = "content_usefulness";
	static final String SPKR_SKILL = "speaker_pptskill";
	static final String DEMO_EFF = "demo_effectiveness";
	static final String OVR_PPT_RAT ="overall_ppt_rating";
	static final String EVNT_STRNTH = "event_strength";
	static final String KEY_MSGS = "key_messages";
	static final String OTHR_INFO ="other_info";
	static final String INTRSTD_EVES = "interested_events";
	static final String OVERALL_RAT = "overall_performance";
	
	
	private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

	static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	    uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
	    uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENT_ID);
	}
	/**
	 * Database specific constant declarations
	**/
	private SQLiteDatabase db;
	static final String DATABASE_NAME = "College";
	static final String FEEDBACK_TABLE_NAME = "feedback";
	static final int DATABASE_VERSION = 1;
	static final String CREATE_DB_TABLE = 
	" CREATE TABLE " + FEEDBACK_TABLE_NAME +
	" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
	"person_identity TEXT,"+
	"how_know_event TEXT,"+
	"event_started_ontime TEXT,"+
	"eventvenue_hospitality TEXT,"+
	"overall_eventorganization_satisfied TEXT,"+
	"content_usefulness TEXT,"+
	"speaker_pptskill TEXT,"+
	"demo_effectiveness TEXT,"+
	"overall_ppt_rating TEXT,"+
	"event_strength TEXT,"+
	"key_messages TEXT,"+
	"other_info TEXT,"+
	"interested_events TEXT,"+
	"overall_performance TEXT);";

	/**
	* Helper class that actually creates and manages 
	* the provider's underlying data repository.
	**/
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
	      
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(CREATE_DB_TABLE);
		}
	      
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " +  FEEDBACK_TABLE_NAME);
			onCreate(db);
		}
	
	}
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
	      
	      switch (uriMatcher.match(uri)){
	         case STUDENTS:
	         count = db.delete(FEEDBACK_TABLE_NAME, selection, selectionArgs);
	         break;
	         
	         case STUDENT_ID:
	         String id = uri.getPathSegments().get(1);
	         count = db.delete( FEEDBACK_TABLE_NAME, _ID +  " = " + id + 
	         (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
	         break;
	         
	         default: 
	         throw new IllegalArgumentException("Unknown URI " + uri);
	      }
	      
	      getContext().getContentResolver().notifyChange(uri, null);
	      return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)){
        /**
        * Get all student records 
        */
        case STUDENTS:
        return "vnd.android.cursor.dir/vnd.example.students";
        
        /** 
        * Get a particular student
        */
        case STUDENT_ID:
        return "vnd.android.cursor.item/vnd.example.students";
        
        default:
        throw new IllegalArgumentException("Unsupported URI: " + uri);
    }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.i("Inside Insert", "Trying to Insert");
		if(db==null)
			return null;
		/**
	    * Add a new student record
	    **/
		long rowID = 0;
		if(values.size()!=0)
			rowID = db.insert(	FEEDBACK_TABLE_NAME, "", values);
		else
			Log.e("Insert Error", "Could not able to Insert");
	    /** 
	    * If record is added successfully
	    **/
	      
	    if (rowID > 0)
	    {
	    	Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
	        getContext().getContentResolver().notifyChange(_uri, null);
			Log.i("Inserting", "Done");	     
	        return _uri;
	    }
	    //throw new SQLException("Failed to add a record into " + uri);
	    return null;
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
	    DatabaseHelper dbHelper = new DatabaseHelper(context);
	   
	    /**
	    * Create a write able database which will trigger its 
	    * creation if it doesn't already exist.
	    **/
	    db = dbHelper.getWritableDatabase();
	    return (db == null);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(FEEDBACK_TABLE_NAME);
	      
	      switch (uriMatcher.match(uri)) {
	         case STUDENTS:
	         qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
	         break;
	         
	         case STUDENT_ID:
	         qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
	         break;
	         
	         default:
	         throw new IllegalArgumentException("Unknown URI " + uri);
	      }
	      
	      Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);
	      
	      /**
	      * register to watch a content URI for changes
	      */
	      c.setNotificationUri(getContext().getContentResolver(), uri);
	      return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;
	      
	      switch (uriMatcher.match(uri)){
	         case STUDENTS:
	         count = db.update(FEEDBACK_TABLE_NAME, values, selection, selectionArgs);
	         break;
	         
	         case STUDENT_ID:
	         count = db.update(FEEDBACK_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + 
	         (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
	         break;
	         
	         default: 
	         throw new IllegalArgumentException("Unknown URI " + uri );
	      }
	      getContext().getContentResolver().notifyChange(uri, null);
	      return count;
	}

}
