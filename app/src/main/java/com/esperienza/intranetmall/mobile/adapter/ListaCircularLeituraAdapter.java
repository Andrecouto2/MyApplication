package com.esperienza.intranetmall.mobile.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.Circular;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.logger.Log;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.List;

/**
 * Created by ThinkPad on 17/12/2015.
 */
public class ListaCircularLeituraAdapter extends RecyclerView.Adapter<ListaCircularLeituraAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewCircularAdapter";

    public static List<Circular> mDataSet;
    public int position;
    public  int idCircular=0;
    static Context context;
    ContextMenu.ContextMenuInfo info;


    public ListaCircularLeituraAdapter(Context context,List<Circular> dataSet) {
        this.mDataSet = dataSet;
        this.context=context;
    }
    public ListaCircularLeituraAdapter() {}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nova_circular, parent, false);


        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(mDataSet!=null&&mDataSet.get(position)!=null)
        {
            if(mDataSet.get(position).getFlagleitura()==0)
            {
                holder.getTextViewNomeCircular().setText(mDataSet.get(position).getTitulo());
                holder.getTextViewDataCircular().setText(mDataSet.get(position).getData_cadastro() != null && !mDataSet.get(position).getData_cadastro().equals("null") ? DateHelper.toString(mDataSet.get(position).getData_cadastro()) : "");
                holder.getTextViewNomeCircular().setTypeface(null, Typeface.BOLD);
                holder.getTextViewDataCircular().setTypeface(null, Typeface.BOLD);
            }
            else
            {
                holder.getTextViewNomeCircular().setText(mDataSet.get(position).getTitulo());
                holder.getTextViewDataCircular().setText(mDataSet.get(position).getData_cadastro() != null && !mDataSet.get(position).getData_cadastro().equals("null") ? DateHelper.toString(mDataSet.get(position).getData_cadastro()) : "");
                holder.getTextViewNomeCircular().setTypeface(null, Typeface.NORMAL);
                holder.getTextViewDataCircular().setTypeface(null, Typeface.NORMAL);
            }

            //holder.getCheckBoxLeitura().setChecked(mDataSet.get(position).getFlagleitura()==0?false:true);
            switch (mDataSet.get(position).getTitulo().toLowerCase().charAt(0))
            {
                case 'a':
                        if(mDataSet.get(position).getFlagleitura()==0)
                        {
                            holder.imageViewLeitura.setImageResource(R.mipmap.a_vermelho);
                        }
                        else
                        {
                            holder.imageViewLeitura.setImageResource(R.mipmap.a_azul);
                        }
                    break;
                case 'b':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.b_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.b_azul);
                    }
                    break;
                case 'c':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.c_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.c_azul);
                    }
                    break;
                case 'd':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.d_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.d_azul);
                    }
                    break;
                case 'e':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.e_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.e_azul);
                    }
                    break;
                case 'f':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.f_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.f_azul);
                    }
                    break;
                case 'g':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.g_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.g_azul);
                    }
                    break;
                case 'h':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.h_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.h_azul);
                    }
                    break;
                case 'i':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.i_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.i_azul);
                    }
                    break;
                case 'j':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.j_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.j_azul);
                    }
                    break;
                case 'k':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.k_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.k_azul);
                    }
                    break;
                case 'l':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.l_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.l_azul);
                    }
                    break;
                case 'm':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.m_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.m_azul);
                    }
                    break;
                case 'n':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.n_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.n_azul);
                    }
                    break;
                case 'o':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.o_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.o_azul);
                    }
                    break;
                case 'p':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.p_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.p_azul);
                    }
                    break;
                case 'q':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.q_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.q_azul);
                    }
                    break;
                case 'r':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.r_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.r_azul);
                    }
                    break;
                case 's':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.s_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.s_azul);
                    }
                    break;
                case 't':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.t_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.t_azul);
                    }
                    break;
                case 'u':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.u_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.u_azul);
                    }
                    break;
                case 'v':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.v_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.v_azul);
                    }
                    break;
                case 'x':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.x_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.x_azul);
                    }
                    break;
                case 'y':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.y_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.y_azul);
                    }
                    break;
                case 'w':
                    if(mDataSet.get(position).getFlagleitura()==0)
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.w_vermelho);
                    }
                    else
                    {
                        holder.imageViewLeitura.setImageResource(R.mipmap.w_azul);
                    }
                    break;

            }
        }

    }
    @Override
    public int getItemCount() {
        if (mDataSet==null)
        {
            return 0;
        }
        return mDataSet.size();
    }
    public Circular getItem(int position) {
        return mDataSet.get(position);
    }
    @Override
    public long getItemId(int position) {
        return mDataSet.get(position).getIdcircular();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{

        private final TextView textViewCircular;
        private final TextView textViewData;
        //private final CheckBox checkBoxLeitura;
        private final ImageView imageViewLeitura;




        public ViewHolder(View v) {
            super(v);


            textViewCircular = (TextView) v.findViewById(R.id.textViewNomeCircular);
            textViewData = (TextView) v.findViewById(R.id.textViewDataCircular);
            imageViewLeitura = (ImageView) v.findViewById(R.id.imgviewleitura);
           //checkBoxLeitura = (CheckBox) v.findViewById(R.id.checkBoxCircularLeitura);
            v.setClickable(true);

            v.setOnClickListener(this);

            //set onContextListener
            v.setOnCreateContextMenuListener(this);

        }

        public TextView getTextViewNomeCircular() {return textViewCircular;}
        public TextView getTextViewDataCircular(){return textViewData;}
        public ImageView getImageViewLeitura(){return imageViewLeitura;}
        //public CheckBox getCheckBoxLeitura(){return checkBoxLeitura;}


        @Override
        public void onClick(View v) {
            Log.e("", "");
            //Intent intent = new Intent(context,DetailsActivity.class);

            //context.startActivity(intent);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            new ListaFuncionarioAdapter().info = menuInfo;
            menu.setHeaderTitle("Selecione a ação");


            //menu.add(0, R.id.menu_editar, 0, "Call");//groupId, itemId, order, title
            //menu.add(0, R.id.menu_enviar, 0, "SMS");
        }




    }
}
