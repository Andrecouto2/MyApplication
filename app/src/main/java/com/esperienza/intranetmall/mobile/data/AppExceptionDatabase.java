package com.esperienza.intranetmall.mobile.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.esperienza.intranetmall.mobile.util.DateHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppExceptionDatabase extends SQLiteOpenHelper {
	
	public static class Dados{
		public Integer id;
		public Date data;
		public String tela;
		public String message;
		public String tracktrace;
		public Dados() {
			
		}
	}
	
	public AppExceptionDatabase(Context context) {
		super(context, "bug.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE TABLE ERROR_LOG(");
		sql.append("_id integer not null  primary key autoincrement,");
		sql.append("data text not null,");
		sql.append("tela varchar(100) not null,");
		sql.append("message text not null,");
		sql.append("tracktrace text not null)");
		db.execSQL(sql.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int newVersion, int OldVersion) {

	}

	public static void report(Context context, Throwable error, String message){
		AppExceptionDatabase h = new AppExceptionDatabase(context);
		SQLiteDatabase db = h.getWritableDatabase();
		ContentValues vl = new ContentValues();
		vl.put("data", DateHelper.toStringDateTimeSQLServer(new Date()));
		vl.put("tela", getNameActivity(error));
		vl.put("message", message);
		vl.put("tracktrace", error.getStackTrace().toString());
		db.insert("ERROR_LOG", "_id", vl);
		db.close();
	}
	private static String getNameActivity(Throwable e){
		for(StackTraceElement item:  e.getStackTrace()){
			if(item.getClassName().indexOf("br.com.vicsistems.webconsignado.") > -1){
				return item.getClassName();
			}
		}
		return "Sem Activity";
	}
	public static void cleanReport(Context context){
		AppExceptionDatabase h = new AppExceptionDatabase(context);
		SQLiteDatabase db = h.getWritableDatabase();
		db.delete("ERROR_LOG", null, null);
		db.close();
	}
	public static List<Dados> getListReport(Context context){
		List<Dados> lista = new ArrayList<Dados>();
		AppExceptionDatabase h = new AppExceptionDatabase(context);
		SQLiteDatabase db = h.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM ERROR_LOG", null);
		if(c.getCount() > 0){
			while (c.moveToNext()) {
				Dados d = new Dados();
				d.data = DateHelper.toDate(c.getString(c.getColumnIndex("data")));
				d.id = c.getInt(c.getColumnIndex("_id"));
				d.message = c.getString(c.getColumnIndex("message"));
				d.tela = c.getString(c.getColumnIndex("tela"));
				d.tracktrace = c.getString(c.getColumnIndex("tracktrace"));
				lista.add(d);
			}
		}
		c.close();
		db.close();

		return lista;
		
	}
}
