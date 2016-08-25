package com.esperienza.intranetmall.mobile.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.esperienza.intranetmall.mobile.data.AppDataBaseHelper;

public abstract class DAO {
	private SQLiteDatabase db;
	public DAO() {
       try
       {
		db = AppDataBaseHelper.factory();
       }
       catch(Exception e)
       {
    	   Log.d("Teste",e.getMessage());
       }
	}
	@Override
	protected void finalize() throws Throwable {
		db.close();
		super.finalize();
	}
	public SQLiteDatabase getDb() {
		if(!db.isOpen()){
			db = AppDataBaseHelper.factory();
		}
		return db;
	}
}
