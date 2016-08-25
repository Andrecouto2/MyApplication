package com.esperienza.intranetmall.mobile.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.data.AppExceptionDatabase;

public class AppExceptionHandler implements UncaughtExceptionHandler {
	private UncaughtExceptionHandler defaultH;
	private Activity activity;
	
	public AppExceptionHandler(Activity activity){
		this.activity = activity;
		this.defaultH = Thread.getDefaultUncaughtExceptionHandler();
		
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		StringBuilder report = new StringBuilder();
		report.append("Erro: ").append(ex.getMessage()).append("\n");
		getInformationSystem(report);
		report.append("\n").append("\n");
		report.append("Stack:\n");
	    final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    ex.printStackTrace(printWriter);
	    report.append(result.toString());
	    printWriter.close();
	    report.append("\n");
	    AppExceptionDatabase.report(activity.getApplicationContext(), ex, report.toString());
	    Toast.makeText(activity.getBaseContext(), "Ocorreu um erro no aplicativo e ser� fechado.", Toast.LENGTH_LONG).show();
		//AppHelper.exitApplication(activity);
		activity.finish();

/*	    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
	    
	    dialog.setTitle("Problemas com o Aplicativo");
	    dialog.setMessage("Ocorreu um erro no aplicativo e ser� fechado. \n Por favor entre novamente no aplicativo!");
	    dialog.setPositiveButton("OK", new OnClickListener(){
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				AppHelper.exitApplication(activity);
				activity.finish();
				
			}
	    });
	    dialog.create().show();
*/	}
	private void getInformationSystem(StringBuilder message) {
		Context context = activity.getBaseContext();
		try {
			message.append("Usuario: ").append(AppHelper.getUsuario().getLogin()).append('\n');
			message.append("Codigo Usuario: ").append(AppHelper.getUsuario().getIduser()).append('\n');
		} catch (Exception e) {
			Log.e("CustomExceptionHandler", "Erro", e);
			message.append("Usuario: Sem informa��es do usuario.\n"+e.getMessage()).append(context.getPackageName());

		}
		message.append("Locale: ").append(Locale.getDefault()).append('\n');
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			message.append("Vers�o: ").append(pi.versionName).append('\n');
			message.append("Package: ").append(pi.packageName).append('\n');
		} catch (Exception e) {
			Log.e("CustomExceptionHandler", "Erro", e);
			message.append("Erro ao pegar as informa��es de vers�o ").append(context.getPackageName());
		}
		message.append("Model: ").append(android.os.Build.MODEL).append('\n');
		message.append("Android Version: ").append(android.os.Build.VERSION.RELEASE).append('\n');
		message.append("Board: ").append(android.os.Build.BOARD).append('\n');
		message.append("Brand: ").append(android.os.Build.BRAND).append('\n');
		message.append("Device: ").append(android.os.Build.DEVICE).append('\n');
		message.append("Host: ").append(android.os.Build.HOST).append('\n');
		message.append("ID: ").append(android.os.Build.ID).append('\n');
		message.append("Model: ").append(android.os.Build.MODEL).append('\n');
		message.append("Product: ").append(android.os.Build.PRODUCT).append('\n');
		message.append("Type: ").append(android.os.Build.TYPE).append('\n');
		
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		message.append("Total Internal memory: ").append(getTotalInternalMemorySize(stat)).append('\n');
		message.append("Available Internal memory: ").append(getAvailableInternalMemorySize(stat)).append('\n');
	}

	private long getAvailableInternalMemorySize(StatFs stat) {
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}
	private long getTotalInternalMemorySize(StatFs stat) {
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}	
}
