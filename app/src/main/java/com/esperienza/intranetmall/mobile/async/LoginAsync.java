package com.esperienza.intranetmall.mobile.async;



import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.LoginActivity;
import com.esperienza.intranetmall.mobile.entidade.EntidadeLogin;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.webservices.ValidarUsuarioWs;


public class LoginAsync extends AsyncTask<String, String, EntidadeLogin> {
	public interface Action{
		void onPreExecute();
		//void getResult(Boolean result);
		void onPostExecute(EntidadeLogin result);
	}
	private Action action;

	private Context context;
	
	public LoginAsync(Context context,  Action action){
		this.context=context;
		this.action = action;


	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		action.onPreExecute();
		//this.dialog.setTitle("Login");
		//this.dialog.setMessage("Validando o login...");






		//WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);


        //this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		//this.dialog.setCancelable(true);


	}

	@Override
	protected EntidadeLogin doInBackground(String... dados) {

		Usuario u = null;
		EntidadeLogin el= null;
		if(AppHelper.isInternetOnline()){
			ValidarUsuarioWs ws = new ValidarUsuarioWs();
			ws.setUsuario(dados[0]);
			ws.setSenha(dados[1]);
			ws.setIdShopping(dados[2]);
			ws.setIdDevice(dados[3]);
			ws.setIpDevice(dados[4]);
			ws.setImei(dados[5]);
			ws.setAltitude(dados[6]);
			ws.setLongitude(dados[7]);
			ws.setLatitude(dados[8]);
			ws.setDescricao(dados[9]);
			ws.setGmt(dados[10]);
			ws.setDataDispositivo(dados[11]);
			ws.setVelocidade(dados[12]);
			ws.setPrecisao(dados[13]);

			el = ws.getResult();
			u=el.getUsuario();
			if(u != null&&u.getIduser()>0){
				AppHelper.setUsuario(u);
				AppHelper.setIdShop(Integer.valueOf(dados[2]));

			}
			
		}
		return el;
	}
	
	@Override
	protected void onPostExecute(EntidadeLogin result) {

		super.onPostExecute(result);
		action.onPostExecute(result);


	}

}
