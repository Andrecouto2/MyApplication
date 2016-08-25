package com.esperienza.intranetmall.mobile.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by ThinkPad on 26/02/2016.
 */
public class ListaArquivoAdapter extends RecyclerView.Adapter<ListaArquivoAdapter.ViewHolder> {

    public  List<ArquivoOS> mDataSet;
    public int position;
    public Context context;
    //public ViewHolder v;
    private ArquivoOnClickListener arquivoOnClickListener;

    public ListaArquivoAdapter(Context context, List<ArquivoOS> dataSet,ArquivoOnClickListener arquivoOnClickListener)
    {
        this.mDataSet=dataSet;
        this.context=context;
        this.arquivoOnClickListener=arquivoOnClickListener;
    }
    public ListaArquivoAdapter(){}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_arquivo, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //v=holder;
        if(mDataSet!=null&&mDataSet.get(position)!=null) {
            if(mDataSet.get(position).getCodgerador().length()<=14)
            {
                holder.txtviewnomearq.setText(mDataSet.get(position).getCodgerador() );
            }
            else
            {
                String primeiro = mDataSet.get(position).getCodgerador().substring(0,5)+"..";
                String segundo = mDataSet.get(position).getCodgerador().substring(mDataSet.get(position).getCodgerador().length()-6,mDataSet.get(position).getCodgerador().length());
                holder.txtviewnomearq.setText(primeiro+segundo);
            }
            switch (mDataSet.get(position).getExtensao().toLowerCase().toString())
            {
                case "jpg":
                    Bitmap bitmap = BitmapFactory.decodeByteArray(mDataSet.get(position).getDatablob(), 0, mDataSet.get(position).getDatablob().length);
                    if(bitmap!=null)
                        holder.imgviewarq.setImageBitmap(bitmap);
                    break;
                case "jpeg":
                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(mDataSet.get(position).getDatablob(), 0, mDataSet.get(position).getDatablob().length);
                    if(bitmap2!=null)
                        holder.imgviewarq.setImageBitmap(bitmap2);
                    break;
                case "pdf":
                        holder.imgviewarq.setImageResource(R.drawable.icone_pdf_72);
                    break;

            }
            if (arquivoOnClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        // A variável position é final
                        arquivoOnClickListener.onLongClickArquivo(holder.itemView, position);
                        return false;
                    }
                });
            }
            holder.imgviewoptions.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {

                }
            });


            /*if(mDataSet.get(position).getExtensao().toLowerCase().equals("jpg")||mDataSet.get(position).getExtensao().toLowerCase().equals("jpge"))
            {
                Bitmap bitmap = BitmapFactory.decodeByteArray(mDataSet.get(position).getDatablob(), 0, mDataSet.get(position).getDatablob().length);
                if(bitmap!=null)
                holder.imgviewarq.setImageBitmap(bitmap);
            }
            else if (mDataSet.get(position).getExtensao().toLowerCase().equals("pdf"))
            {
                holder.imgviewarq.setImageResource(R.drawable.icone_pdf_72);

            }
            else if(mDataSet.get(position).getExtensao().toLowerCase().equals("zip"))
            {
                holder.imgviewarq.setImageResource(R.drawable.icone_zip_64);
            }*/

        }
    }
    public interface ArquivoOnClickListener {
        public void onLongClickArquivo(View view, int idx);
    }



    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {

        private final TextView txtviewnomearq;
        private final ImageView imgviewarq;
        private final ImageView imgviewoptions;

        public ViewHolder(View itemView) {
            super(itemView);

            txtviewnomearq = (TextView) itemView.findViewById(R.id.txtviewdocname);
            imgviewarq = (ImageView) itemView.findViewById(R.id.imgviewdoc);
            imgviewoptions = (ImageView) itemView.findViewById(R.id.imgviewoptions);









        }


        /*@Override
        public void onClick(View v) {

        }*/

        /*@Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }*/
    }

}

