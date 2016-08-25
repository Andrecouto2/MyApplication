package com.esperienza.intranetmall.mobile.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.esperienza.intranetmall.mobile.activity.LoginActivity;
import com.esperienza.intranetmall.mobile.gcm.Constants;
import com.esperienza.intranetmall.mobile.gcm.GCM;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by ThinkPad on 22/03/2016.
 */
public class RegistrationDeviceAsync extends AsyncTask<Void,Void,String> {

    public interface Action {
        void preExecute();
        void postExecute(String result);
    }
    private Context context;
    private Action action;
    private ProgressDialog dialog;

    public RegistrationDeviceAsync(Context context,Action action)
    {
        this.context=context;
        this.action=action;
        this.dialog = new ProgressDialog (context);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setTitle("Sistema");
        this.dialog.setMessage("Iniciando sistema...");
        this.dialog.setCancelable(true);
        this.dialog.show();
    }
    @Override
    protected String doInBackground(Void... params) {
        boolean ok = checkPlayServices();
        String retorno="0";
        if (ok) {
            // Já está registrado
            String regId = GCM.getRegistrationId(context);
            if (regId != null && !regId.equals("")) {

                retorno = regId;

            } else {
                retorno =GCM.register(context, Constants.PROJECT_NUMBER);
            }
        }
        return retorno;
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            /*if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
                //GooglePlayServicesUtil.getErrorDialog(resultCode, LoginActivity.class, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //Toast.makeText(LoginActivity.this,"Este aparelho não suporta o Google Play Services.",Toast.LENGTH_SHORT).show();
                //finish();
            }*/
            return false;
        }
        return true;
    }
    @Override
    protected void onPostExecute(String result) {

        //action.preExecute();
        super.onPostExecute(result);
        action.postExecute(result);
        this.dialog.dismiss();


    }
}
