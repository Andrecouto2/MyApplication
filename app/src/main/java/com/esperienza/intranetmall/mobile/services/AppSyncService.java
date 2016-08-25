package com.esperienza.intranetmall.mobile.services;

import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AppSyncService extends Service implements Runnable {
	
	public static final String FILTER_PROCESS_PROPOSTA = "br.com.vicsistems.webconsignado.sync.proposta";
	public static final String FILTER_PROCESS_ALERTA = "br.com.vicsistems.webconsignado.sync.alerta";
	public static final String FILTER_PROCESS_UPDATE = "br.com.vicsistems.webconsignado.sync.update";
	
	private Boolean isActive = true;
	private Thread thread;
	private Boolean syncPropostasExec = false;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return Service.START_NOT_STICKY;
	}
	@Override
	public void onCreate() {
		thread = new Thread(this);
		thread.start();
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		intent.getStringExtra("");
		return null;
	}

	@Override
	public void run() {
		while(isActive){
			// TODO: montar o servi�o de sync atualizar as inform��es no relat�rio
			//executeSyncProposta();
			//executeSyncErrors();
			try{
				Thread.sleep(DateHelper.getminutos(1));
			}catch(Exception e){

			}
		}
		
	}
	/*public void executeSyncProposta(){
		if(AppHelper.isInternetOnline() && !SyncPropostasThread.isExecute){
			new SyncPropostasThread(getBaseContext()).start();
		}
	}*/
	/*public void executeSyncErrors(){
		if(AppHelper.isInternetOnline() && !SyncErrorsThread.isExecute){
			new SyncErrorsThread(getBaseContext()).start();
		}
	}*/
}
