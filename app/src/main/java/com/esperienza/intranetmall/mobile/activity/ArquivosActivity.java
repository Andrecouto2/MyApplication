package com.esperienza.intranetmall.mobile.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.adapter.ListaArquivoAdapter;
import com.esperienza.intranetmall.mobile.adapter.ListaOrdemServicoAdapter;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.PermissionUtils;
import com.esperienza.intranetmall.mobile.util.Util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by ThinkPad on 23/02/2016.
 */
public class ArquivosActivity extends AppCompatActivity {

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    protected  RecyclerView.LayoutManager mLayoutManager;
    public ArquivosActivity(){}
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    protected LayoutManagerType mCurrentLayoutManagerType;
    private RecyclerView recyclerviewarquivo;
    private ListaArquivoAdapter mAdapter;
    public static List<ArquivoOS> mDataSet=new ArrayList<>();
    private ArquivoOS itemArquivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arquivos);


        recyclerviewarquivo = (RecyclerView) findViewById(R.id.recyclerViewArquivos);
        Util.setInsets(this,recyclerviewarquivo);

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaros);



        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            //getSupportActionBar().setTitle("O.S: " + os.getId_os());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

        mLayoutManager = new LinearLayoutManager(this);

        mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        if(mDataSet!=null)
            resetAdapter(mDataSet);

        events();
    }
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerviewarquivo.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerviewarquivo.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recyclerviewarquivo.setLayoutManager(mLayoutManager);
        recyclerviewarquivo.scrollToPosition(scrollPosition);
        registerForContextMenu(recyclerviewarquivo);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_arquivo, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu_contexto_item_arquivo, menu);

    }
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_remover:
                if(itemArquivo!=null)
                mDataSet.remove(itemArquivo);
                resetAdapter(mDataSet);
                break;

        }
        return super.onContextItemSelected(item);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_openfile:
                String[] PERMISSIONS_STORAGE = {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                PermissionUtils.validate(this,1,PERMISSIONS_STORAGE);
                Intent mRequestFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mRequestFileIntent.setType("*/*");
                mRequestFileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(mRequestFileIntent, 0);
                break;
        }
        return true;
    }

    public void events()
    {


    }
    private ListaArquivoAdapter.ArquivoOnClickListener onClickCarro() {
        return new ListaArquivoAdapter.ArquivoOnClickListener() {
            @Override
            public void onLongClickArquivo(View view, int idx) {
                itemArquivo = mDataSet.get(idx);

            }

        };
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent returnIntent) {
        // If the selection didn't work
        if (resultCode != RESULT_OK)
        {
            // Exit without doing anything else
            return;
        }
        else
        {

            Uri data = returnIntent.getData();


            String FilePath = returnIntent.getData().getPath();
            String FileName = returnIntent.getData().getLastPathSegment();
            int lastPos = FilePath.length() - FileName.length();
            //String Folder = FilePath.substring(0, lastPos);
            //String mimeType = getContentResolver().getType(data);
            Cursor returnCursor =
                    getContentResolver().query(data, null, null, null, null);

            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            //int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            String name=returnCursor.getString(nameIndex);
            if(!name.endsWith("jpeg")&&!name.endsWith("jpg")&&!name.endsWith("pdf")&&!name.endsWith("zip"))
            {
                Toast.makeText(this,"Não permitido está extensão do arquivo.",Toast.LENGTH_LONG).show();
                return;
            }

            byte[] pack = null;
            if(name.endsWith("zip"))
            {
                ZipInputStream zis = null;
                try {
                    zis = new ZipInputStream(new BufferedInputStream(getContentResolver().openInputStream(data)));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {

                    ZipEntry ze;
                    while ((ze = zis.getNextEntry()) != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int count;
                        while ((count = zis.read(buffer)) != -1) {
                            baos.write(buffer, 0, count);
                        }
                        name = ze.getName();
                        String[] div= name.split("\\/");
                        String[] nome = div[1].split("\\.");
                        ArquivoOS a = new ArquivoOS();
                        pack = baos.toByteArray();
                        if(name.endsWith("jpeg")||name.endsWith("jpg")||name.endsWith("pdf"))
                        {
                            a.setExtensao(nome[1].toString());
                            a.setCodgerador(nome[0].toString());
                            a.setDatablob(pack);
                            String encodedImage = Base64.encodeToString(pack, Base64.DEFAULT);
                            a.setUrlarquivo(encodedImage);

                            mDataSet.add(a);
                        }


                        // do something with 'filename' and 'bytes'...

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        resetAdapter(mDataSet);
                        zis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                File file = new File(getPath(this, data));

                try {
                    pack = AppHelper.readFile(file);
                    int tam  = pack.length/1024;
                    if(tam>4100)
                    {
                        Toast.makeText(this,"Tamanho do arquivo superior 4MB.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.d("Path???", Environment.getExternalStorageDirectory() + getPath(this, data));
                    ArquivoOS a = new ArquivoOS();

                    a.setDatablob(pack);
                    String[] nome = name.split("\\.");
                    a.setExtensao(nome[1].toString());
                    a.setCodgerador(nome[0].toString());
                    String encodedImage = Base64.encodeToString(pack, Base64.DEFAULT);
                    a.setUrlarquivo(encodedImage);
                    mDataSet.add(a);
                    resetAdapter(mDataSet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }





        }
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
    public  void resetAdapter(List<ArquivoOS> data) {
        mAdapter = new ListaArquivoAdapter(this, data,onClickCarro());
        // Set CustomAdapter as the adapter for RecyclerView.
        if(data!=null&&mAdapter!=null)
        {
            recyclerviewarquivo.setAdapter(mAdapter);
        }

    }

}
