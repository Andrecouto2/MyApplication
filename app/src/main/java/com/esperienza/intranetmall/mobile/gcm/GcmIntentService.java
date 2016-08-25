package com.esperienza.intranetmall.mobile.gcm;

/**
 * Created by ThinkPad on 23/12/2015.
 */
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.LoginActivity;
import com.esperienza.intranetmall.mobile.activity.MenuActivity;
import com.esperienza.intranetmall.mobile.activity.SplashScreen;
import com.esperienza.intranetmall.mobile.dao.UsuarioDAO;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.NotificationUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.Console;


public class GcmIntentService  extends IntentService {

    private static final String TAG = "gcm";

    public GcmIntentService() {
        super(Constants.PROJECT_NUMBER);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        Log.i(TAG, "GcmIntentService.onHandleIntent: " + extras);
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        if (!extras.isEmpty()) {
            // Verifica o tipo da mensagem
            String messageType = gcm.getMessageType(intent);
            // O extras.isEmpty() precisa ser chamado para ler o bundle
            // Verifica o tipo da mensagem, no futuro podemos ter mais tipos
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                // Erro
                onError(extras);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // Mensagem do tipo normal. Faz a leitura do parâmetro "msg"
                // enviado pelo servidor
                onMessage(extras);
            }
        }
        // Libera o wake lock, que foi bloqueado pela classe
        // "GcmBroadcastReceiver".
        GcmBroadcastReceiver.completeWakefulIntent(intent);
        sendBroadcast(intent);
    }

    private void onError(Bundle extras) {
        Log.d(TAG, "Erro: " + extras.toString());
    }

    private void onMessage(Bundle extras) {
        // Lê a mensagem e mostra uma notificação
        String msg = extras.getString("message");
        String titulo = extras.getString("title");
        String iduser =  extras.getString("user");
        String idshop = extras.getString("shop");
        String login = extras.getString("login");
        String senha = extras.getString("senha");

        try
        {
                Usuario u=UsuarioDAO.getNewInstance().getUsuario(Integer.parseInt(iduser),AppHelper.getIdShop());
                //AppHelper.setUsuario(u);
                //AppHelper.setIdShop(Integer.parseInt(idshop));
                Intent intent =new Intent(this, LoginActivity.class);
                intent.putExtra("login",login);
                intent.putExtra("senha",senha);
                intent.putExtra("idshop",idshop);
                if(titulo.equals("Circular"))
                {
                    intent.putExtra("tipo","circular");
                }
                else
                {
                    intent.putExtra("tipo","ordem");
                }


            if (!SplashScreen.isinFG && extras.getString("message").length() != 0) {
                

                NotificationUtil.create(this, intent, titulo, msg, R.mipmap.ic_launcherx);

            }



        }
        catch (Exception e)
        {
          e.printStackTrace();
        }

        //Log.d(TAG, msg);





    }
}
