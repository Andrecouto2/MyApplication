package com.esperienza.intranetmall.mobile.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.dao.FuncionarioDAO;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.BitmapUtil;
import com.esperienza.intranetmall.mobile.util.PermissionUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by ThinkPad on 07/12/2015.
 */
public class FragmentImagemFuncionario extends Fragment {


    public static ImageView imageViewFnc;
    private static final int FILE_SELECT_CODE = 1;
    private static final int GALLERY_KITKAT_INTENT_CALLED = 2;
    private static final int  GALLERY_INTENT_CALLED =3;
    private static final int TAKEPHOTO=5678;
    private static final int RECORTAIMG=8080;
    private static final int CAMERAPERMISSION=0;
    private String filePath;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_fotofuncionario, container, false);
        FragmentDadosFuncionario.viewfoto=rootview;

        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }

        imageViewFnc = (ImageView) rootview.findViewById(R.id.img_fnc);
        imageViewFnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewFnc.performLongClick();
            }
        });
        registerForContextMenu(imageViewFnc);
        //ScrollView scrollView = (ScrollView) rootview.findViewById(R.id.scrollViewImagemFnc);
        //scrollView.setClipToPadding(false);
        //setInsets(getActivity(), scrollView);




            PreencheTela();

        return rootview;
    }
    public void PreencheTela()
    {
        String img="iVBORw0KGgoAAAANSUhEUgAAAFMAAABvCAYAAACQCvzWAAAABGdBTUEAALGPC/xhBQAAEcdJREFUeF7tnFeoJcUTxo1rjiCKYkYQFDEL4oMJFB8EswuGNWLOAdEHxZwQYQ2oiFnQh8WAOeeI+mT2xZxzDq2/5n6Hmjo1c2bmzCz7v/8z8NHd1dXd1d9UxzP3zvffk7rE/PPPH8r/TxAKW6GMSC+fxoSHwt4gIqcpoaGwM0xjL4wQCjuF98ZpTHAobIUmZE1TQkPh2Cgjaxp7JQiFrbDoooum9ddfP22++eZps802y9hiiy3SBhtskGbMmJEWWGCBsNw0QihsDDzulFNOST/99FPi+eeffzJ4/vjjj3TggQeG5cZF5OleVmc0SKeObgVCYSPIgJdeeimTFz133XVXQXdMowsYVWfTttCvKlORPyQoha0gquy4445LF1xwQbrkkksyLr300hxedtllad99960yohZUNqpnnHqFDuoIhQVUNaI8q+P1xzWyqu5I1qS9cW1zCIVDqNOo1Snr4FJLLVWQ1wFlfd1Kl8nboKpszbpD4RCWW265tMMOO6Q5c+akhx56KN1///0ZxO+777708MMPpyeffDI9/vjj6YknniiE4NFHH01PPfVUeuSRRwZlH3zwwZGg/gceeCDME9CxafS9zAMdgO2k77nnntwH5vZtt90293nhhRfOaPCCQuEQLrroovTOO++kv/76K/3666/p559/ziv3L7/8ktNaxUc90iekjrbw5Wk/yitrBzn47bff0g8//JB+/PHHXMeff/6Zvvzyy7zFs/3v1DOfffbZAWlsdTCI+Lfffpvx+++/Z7JfffXV9MYbbxTw2muvpffffz+Xx1gML+tkFUQA+OKLL7JNeOAVV1yR7r333pz+6KOPsq5/YbYssPls4bAfuwhJH3TQQbnfDffHoXAIDFmRAImCDMWInXbaKTe+zDLLFIBs5syZ6e+//8661BPhu+++G3QQIKMNwu+//z5PF+xl11133dBGYbXVVktHH310uvvuu7PXAUsodaod6lUb8lBG36xZs3JdNT1SCIVDYL6TYRighiFAhnDaicqC3XffPRv59ddf56FFB1Sf7Qh5qhePJ7z++uvT2muv3bRjGSuuuGK6/PLLcz20TTvYr7gHbTPy9t9//7C+EQiFA8h4FheM8GC4aZhj9OGHH573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";
        String img2="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";

        if(FragmentDadosFuncionario.funcionario.getImagem()!=null&&!FragmentDadosFuncionario.funcionario.getImagem().toString().equals(img)&&!FragmentDadosFuncionario.funcionario.getImagem().toString().equals(img2))
        {
            try{
                /*byte[] decodedByte = Base64.decode(FragmentDadosFuncionario.funcionario.getImagem(), 0);
                Bitmap bitmap= BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);*/
                imageViewFnc.setImageBitmap(getCroppedBitmap(decodeBase64(FragmentDadosFuncionario.funcionario.getImagem())));
            }catch (Exception e)
            {
                Log.d("Erro conversão imgfnc", e.toString());
            }

        }

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.img_fnc) {
            getActivity().getMenuInflater().inflate(R.menu.menu_imagem_fnc, menu);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_img_fnc_buscarfoto:
                try
                {
                    showFileChooser();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Recurso indisponível.",Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getActivity(),"Em breve!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_img_fnc_tirarfoto:
                // Solicita as permissões

                String[] permissoes = new String[]{
                        Manifest.permission.CAMERA
                };
                // Se não possui permissão
                if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.CAMERA)) {
                        // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                        // Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                        Toast.makeText(this.getActivity(),"É importante aceitar o uso de câmera para capturar imagens.",Toast.LENGTH_LONG).show();
                    } else {
                        // Solicita a permissão
                        PermissionUtils.validate(this.getActivity(), CAMERAPERMISSION, permissoes);
                    }
                } else {
                    // Tudo OK, podemos prosseguir.
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getActivity().startActivityForResult(takePictureIntent, TAKEPHOTO);
                }

                return true;
            case R.id.menu_img_fnc_crop:
                try {
                    Intent takePictureIntent = new Intent("com.android.camera.action.CROP");
                    takePictureIntent.setType("image/*");
                    takePictureIntent.putExtra("crop", "true");
                    takePictureIntent.putExtra("aspectX", 2);
                    takePictureIntent.putExtra("aspectY", 3);
                    takePictureIntent.putExtra("outputX", 100);
                    takePictureIntent.putExtra("outputY", 100);
                    takePictureIntent.putExtra("return-data", true);
                    getActivity().startActivityForResult(takePictureIntent, RECORTAIMG);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Recurso indisponível.",Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }
    private void showFileChooser() {

        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setType("*/*");      //all files
        //intent.setType("image/*");   //XML file only

        try{
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), FILE_SELECT_CODE);


        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         switch(requestCode) {
            case FILE_SELECT_CODE:
                if(resultCode == Activity.RESULT_OK){
                    Uri datauri = data.getData();


                    String FilePath = data.getData().getPath();
                    String FileName = data.getData().getLastPathSegment();
                    /*int lastPos = FilePath.length() - FileName.length();
                    //String Folder = FilePath.substring(0, lastPos);
                    //String mimeType = getContentResolver().getType(data);
                    Cursor returnCursor =
                            getActivity().getContentResolver().query(datauri, null, null, null, null);

                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    //int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                    returnCursor.moveToFirst();
                    String name=returnCursor.getString(nameIndex);
                    if(!name.endsWith("jpeg")&&!name.endsWith("jpg"))
                    {
                        Toast.makeText(getActivity(),"Não permitido está extensão do arquivo.",Toast.LENGTH_LONG).show();
                        return;
                    }*/
                    File file = new File(getPath(AppHelper.getApplicationContext(), datauri));

                    try {
                        byte[] pack = AppHelper.readFile(file);
                        int tam  = pack.length/1024;
                        if(tam>4100)
                        {
                            Toast.makeText(AppHelper.getApplicationContext(),"Tamanho do arquivo superior 4MB.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //Log.d("Path???", Environment.getExternalStorageDirectory() + getPath(getActivity(), datauri));

                        //String[] nome = name.split("\\.");

                        String encodedImage = Base64.encodeToString(pack, Base64.DEFAULT);

                        Bitmap bitmap = AppHelper.decodeBase64(encodedImage);
                        Bitmap bitmapr =  BitmapUtil.getResizedBitmap(bitmap,bitmap.getHeight(),bitmap.getWidth()+30);
                        if(bitmap!=null)
                        {
                            imageViewFnc.setImageBitmap(getCroppedBitmap(bitmap));
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Erro na seleção da imagem.",Toast.LENGTH_SHORT).show();
                        }
                        FragmentDadosFuncionario.funcionario.setImagem(encodedImage);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    // At the end remember to close the cursor or you will end with the RuntimeException!



                }
                break;
             case TAKEPHOTO:
                 try
                 {
                     Bundle extras = data.getExtras();
                     Bitmap imagem = (Bitmap) extras.get("data");
                     Bitmap bMapScaled = Bitmap.createScaledBitmap(imagem, 249, 332, true);
                     //Bitmap bitmap = BitmapUtil.getResizedBitmap(imagem,imagem.getHeight(),imagem.getWidth());
                     imageViewFnc.setImageBitmap(getCroppedBitmap(bMapScaled));
                     FragmentDadosFuncionario.funcionario.setImagem(encodeTobase64(bMapScaled));

                 }catch (Exception e)
                 {
                     Log.d("error caputura img fnc.",""+e.getMessage());
                 }

                 break;
             case RECORTAIMG:
                 try {
                     Bundle extras = data.getExtras();
                     Bitmap imagem = (Bitmap) extras.get("data");
                     //Bitmap bMapScaled = Bitmap.createScaledBitmap(imagem, 249, 332, true);
                     //Bitmap bitmap = BitmapUtil.getResizedBitmap(imagem,imagem.getHeight(),imagem.getWidth());
                     imageViewFnc.setImageBitmap(getCroppedBitmap(imagem));
                     FragmentDadosFuncionario.funcionario.setImagem(encodeTobase64(imagem));
                 }
                 catch (Exception e)
                 {
                     e.printStackTrace();
                 }
                 break;
                }



    }



    public static String encodeTobase64(String Path)
    {
        Bitmap immagex=Bitmap.createScaledBitmap(BitmapFactory.decodeFile(Path), 140, 120, false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case CAMERAPERMISSION:
                // Check Permissions Granted or not
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //new ContactSyncTask().execute();
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getActivity().startActivityForResult(takePictureIntent, TAKEPHOTO);
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "Necessário permissão de câmera para capturar imagem.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public static void setInsets(Activity context, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        view.setPadding(0, config.getPixelInsetTop(true) * 2, config.getPixelInsetRight(), config.getPixelInsetBottom());
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
