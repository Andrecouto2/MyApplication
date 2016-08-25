package com.esperienza.intranetmall.mobile.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.esperienza.intranetmall.mobile.R;
//import br.com.vicsistems.webconsignado.activity.LoginActivity;
import com.esperienza.intranetmall.mobile.dao.UsuarioDAO;
import com.esperienza.intranetmall.mobile.data.AppDataBaseHelper;
import com.esperienza.intranetmall.mobile.entidade.Destaque;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.services.AppSyncService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;

//import com.vicsistems.webconsignado.db.DAO.UsuarioDAO;
//import com.vicsistems.webconsignado.db.entidade.Usuario;
//import com.vicsistems.webconsignado.services.AppSyncService;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.guava.primitives.Bytes;

public class AppHelper {
	private static Context context = null;
	private static Usuario usuario = null;
	private static Usuario superUsuario = null;
	private static int idShop;
	private static List<Destaque> destaque;

	//private static Produto produtoSelecionado = null;
	@SuppressWarnings("unused")
	private static AppSyncService service;


	//TODO Criar ou pegar o identificador do DEVICE NUMBER ou ANDROID_ID

	public static Context getApplicationContext() {
		return context;
	}

	public static void setApplicationContext(Context context) {
		AppHelper.context = context;
	}
	public static void onInitApplication(Context context) {
		AppHelper.context = context;
		//AppHelper.setProdutoSelecionado(null);
		try {
			AppDataBaseHelper.factory(); // Iniciando o banco de dados
		} catch (Exception e) {
			Log.d("Teste", e.getMessage());
		}
	}
	public static boolean isInternetOnline() {
	    ConnectivityManager cm = (ConnectivityManager) AppHelper.context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return (netInfo != null && netInfo.isConnectedOrConnecting());
	}

	public static Usuario getUsuario() {
        if (usuario == null) {
            setUsuario(null);
        }
        return usuario;
    }
	public static int getIdShop(){
		return idShop;
	}
	public static void setIdShop(int id){
		idShop=id;
	}
	public static void setUsuario(Usuario u) {
	Log.d("Teste","Entrou... "+u);
		UsuarioDAO dao = new UsuarioDAO();
		if(u == null){
 			Log.d("Teste","Passou 1... "+u);

 			Log.d("Teste","Passou 2... "+u);
			//u = dao.getUsuario();
  			Log.d("Teste", "Passou 3... " + u);
		}else{
			dao.save(u);
		}
		usuario = u;
	}
	public static String getIMEI(Context context){

		try
		{
			TelephonyManager  tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			String imei= tm.getDeviceId();
			return imei;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "0";
		}


	}
   /*
	public static void closeApp(Activity a){
		usuario = null;
		System.runFinalizersOnExit(true);
		System.exit(0);
		a.moveTaskToBack(true);
		a.finish();
		
	}
	public static Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public static void setProdutoSelecionado(Produto produtoSelecionado) {
		AppHelper.produtoSelecionado = produtoSelecionado;
	}

	public static Usuario getSuperUsuario() {
		return superUsuario;
	}

	public static void setSuperUsuario(Usuario superUsuario) {
		AppHelper.superUsuario = superUsuario;
	} */
	public static String getAppName(){
		return "IntraMall";
		//return Resources.getSystem().getString(R.string.app_name);
	}
	public static void esconderTeclado(View v){
		InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow( v.getWindowToken(), 0);
	}
	@SuppressLint("SdCardPath")
	public static String getPathData(){
		String p = "/data/data/com.esperienza.intranetmall.mobile";
		try {
			p = AppHelper.getApplicationContext().getPackageManager().getPackageInfo( AppHelper.getApplicationContext().getPackageName(), 0).applicationInfo.dataDir;
		} catch (NameNotFoundException e) {
			Log.e("PathData", "Erro ao pegar o caminho da para de Dados do aplicativo.", e);
		}
		
		return p;
	}
	public static String getPathAppDataSD(){
		File extStore = Environment.getExternalStorageDirectory();
		String p = extStore.getAbsolutePath() + "/webConsignado";
		return p;
	}

	public static void startService(Activity activity){
		service = new AppSyncService();
		Intent iservice = new Intent(activity.getBaseContext(), AppSyncService.class);
		activity.startService(iservice);
		
	}
	public static Boolean isTablet(){
		int size  = (AppHelper.context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);
		Boolean isTablet =  size > Configuration.SCREENLAYOUT_SIZE_NORMAL;
		return isTablet;
	}
	public static void setWindowFullScreen(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	public static void setWindowTabletScreen(Activity activity){
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	public static void setWindowSmallScreen(Activity activity){
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		
	}
	public static void alertMsg(Context context, String titulo, String msg){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(titulo).setMessage(msg).setCancelable(false);
		dialog.setPositiveButton(R.string.lbl_btn_ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		dialog.create().show();

	}
	public static String getLocalIpAddress(){
          try{
              for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                  NetworkInterface intf = en.nextElement();
                  for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                      InetAddress inetAddress = enumIpAddr.nextElement();
                      if (!inetAddress.isLoopbackAddress()) {
                          return inetAddress.getHostAddress().toString();
                      }
                  }
              }
          }catch (Exception ex) {
              Log.e("IP Address", ex.toString());
          }
          return null;
	}

	public static String encodeTobase64(Bitmap image)
	{
		Bitmap immagex=image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		Log.e("LOOK", imageEncoded);
		return imageEncoded;
	}
	public static Bitmap decodeBase64(String input)
	{
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
	public static byte[] readFully(InputStream stream) throws IOException
	{
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int bytesRead;
		while ((bytesRead = stream.read(buffer)) != -1)
		{
			baos.write(buffer, 0, bytesRead);
		}
		return baos.toByteArray();
	}
	public static byte[] loadFile(String sourcePath) throws IOException
	{
		InputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(sourcePath);
			return readFully(inputStream);
		}
		finally
		{
			if (inputStream != null)
			{
				inputStream.close();
			}
		}
	}
	public static String getIpAddress() {
		try {
			for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
						String ipAddress=inetAddress.getHostAddress().toString();
						Log.e("IP address",""+ipAddress);
						return ipAddress;
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("Socket GetIP ", ex.toString());
		}
		return null;
	}
	public static byte[] readFile(File file) throws IOException {
		// Open file
		RandomAccessFile f = new RandomAccessFile(file, "r");
		try {
			// Get and check length
			long longlength = f.length();
			int length = (int) longlength;
			if (length != longlength)
				throw new IOException("File size >= 2 GB");
			// Read file and return data
			byte[] data = new byte[length];
			f.readFully(data);
			return data;
		} finally {
			f.close();
		}
	}
	public static int getVersionCode(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException ex) {}
		return 0;
	}
	public static int getVersionName(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			return Integer.parseInt(pi.versionName);
		} catch (NameNotFoundException ex) {}
		return 0;
	}
	public static String getVersionName(Activity activity) {
		PackageManager pm = activity.getPackageManager();
		String packageName = activity.getPackageName();
		String versionName;
		try {
			PackageInfo info = pm.getPackageInfo(packageName, 0);
			versionName = info.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			versionName = "N/A";
		}
		return versionName;
	}
	public static boolean isAvailable(Context ctx, Intent intent) {
		final PackageManager mgr = ctx.getPackageManager();
		List<ResolveInfo> list =
				mgr.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	public static List<Destaque> getDestaque() {
		return destaque;
	}

	public static void setDestaque(List<Destaque> destaque) {
		AppHelper.destaque = new ArrayList<>();
		AppHelper.destaque = destaque;
	}

	public static void generateQRCode_general(String data, ImageView img)throws WriterException {
		com.google.zxing. MultiFormatWriter writer =new  MultiFormatWriter();


		String finaldata = Uri.encode(data, "utf-8");

		BitMatrix bm = writer.encode(finaldata, BarcodeFormat.QR_CODE,150, 150);
		int width   = bm.getWidth ();
		int height  = bm.getHeight ();
		Bitmap ImageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		for (int i = 0; i < width; i++) {//width
			for (int j = 0; j < height; j++) {//height
				ImageBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK: Color.WHITE);
			}
		}

		if (ImageBitmap != null) {
			img.setImageBitmap(ImageBitmap);
		} else {
			Toast.makeText(getApplicationContext(), "Error code.",
					Toast.LENGTH_SHORT).show();
		}
	}
	public static String timeZone()
	{
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
		String   timeZone = new SimpleDateFormat("Z").format(calendar.getTime());
		return timeZone.substring(0, 3) + ":"+ timeZone.substring(3, 5);
	}
	public static String getDetalhesDispositivo(Activity activity)
	{
		int measuredWidth = 0;
		int measuredHeight = 0;
		WindowManager w = activity.getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point size = new Point();
			w.getDefaultDisplay().getSize(size);
			measuredWidth = size.x;
			measuredHeight = size.y;
		} else {
			Display d = w.getDefaultDisplay();
			measuredWidth = d.getWidth();
			measuredHeight = d.getHeight();
		}
		String details =  "VERSION.RELEASE : "+ Build.VERSION.RELEASE
				+" VERSION.INCREMENTAL : "+Build.VERSION.INCREMENTAL
				+" VERSION.SDK.NUMBER : "+Build.VERSION.SDK_INT
				+" BOARD : "+Build.BOARD
				+" BOOTLOADER : "+Build.BOOTLOADER
				+" BRAND : "+Build.BRAND
				+" DISPLAY : "+Build.DISPLAY
				+" FINGERPRINT : "+Build.FINGERPRINT
				+" HARDWARE : "+Build.HARDWARE
				+" HOST : "+Build.HOST
				+" ID : "+Build.ID
				+" MANUFACTURER : "+Build.MANUFACTURER
				+" MODEL : "+Build.MODEL
				+" PRODUCT : "+Build.PRODUCT
				+" SERIAL : "+Build.SERIAL
				+" TAGS : "+Build.TAGS
				+" TIME : "+Build.TIME
				+" TYPE : "+Build.TYPE
				+" UNKNOWN : "+Build.UNKNOWN
				+" USER : "+Build.USER
		        +" WIDTHSCREEN : "+measuredWidth
		        +" HEIGHTSCREEN : "+measuredHeight;



		return details;
	}

	public static final Drawable getDrawable(Activity activity, int id)
	{

		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
		{

			return activity.getResources().getDrawable(id,activity.getApplicationContext().getTheme());

		}
		else
		{
			return activity.getResources().getDrawable(id);

		}
	}
	public static void scaleViewAndChildren(View root, float scale)
	{
		// Retrieve the view's layout information
		ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
		// Scale the view itself
		if (layoutParams.width != ViewGroup.LayoutParams.FILL_PARENT &&
				layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT)
		{
			layoutParams.width *= scale;
		}
		if (layoutParams.height != ViewGroup.LayoutParams.FILL_PARENT &&
				layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT)
		{
			layoutParams.height *= scale;
		}

		// If this view has margins, scale those too
		if (layoutParams instanceof ViewGroup.MarginLayoutParams)
		{
			ViewGroup.MarginLayoutParams marginParams =
					(ViewGroup.MarginLayoutParams)layoutParams;
			marginParams.leftMargin *= scale;
			marginParams.rightMargin *= scale;
			marginParams.topMargin *= scale;
			marginParams.bottomMargin *= scale;
		}

		// Set the layout information back into the view
		root.setLayoutParams(layoutParams);

		root.setPadding(
				(int)(root.getPaddingLeft() * scale),
				(int)(root.getPaddingTop() * scale),
				(int)(root.getPaddingRight() * scale),
				(int)(root.getPaddingBottom() * scale));

		// If the root view is a TextView, scale the size of its text
		if (root instanceof TextView)
		{
			TextView textView = (TextView)root;
			textView.setTextSize(textView.getTextSize() * scale);
		}

		// If the root view is a ViewGroup, scale all of its children recursively
		if (root instanceof ViewGroup)
		{
			ViewGroup groupView = (ViewGroup)root;
			for (int cnt = 0; cnt < groupView.getChildCount(); ++cnt)
				scaleViewAndChildren(groupView.getChildAt(cnt), scale);
		}
	}
	public static String getSizeScreen()
	{
		int screenSize = context.getResources().getConfiguration().screenLayout &Configuration.SCREENLAYOUT_SIZE_MASK;
		String result="";
		switch(screenSize) {
			case Configuration.SCREENLAYOUT_SIZE_XLARGE:
				result="xlarge";
				break;
			case Configuration.SCREENLAYOUT_SIZE_LARGE:
				result="large";
				break;
			case Configuration.SCREENLAYOUT_SIZE_NORMAL:
				result="normal";
				break;
			case Configuration.SCREENLAYOUT_SIZE_SMALL:
				result="small";
				break;
		}
		return result;
	}




}
