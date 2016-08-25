package com.esperienza.intranetmall.mobile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import java.util.List;

/**
 * Created by ThinkPad on 18/07/2016.
 */
public class ListaOrdemServicoPAAdapter extends RecyclerView.Adapter<ListaOrdemServicoPAAdapter.ViewHolder> {


    public static List<OrdemServico> mDataSet;
    public int position;
    public  int idOS=0;
    static Context context;

    public ListaOrdemServicoPAAdapter(Context context,List<OrdemServico> dataSet)
    {
        this.context=context;
        this.mDataSet = dataSet;
    }
    public ListaOrdemServicoPAAdapter(List<OrdemServico> dataSet)
    {
        this.mDataSet = dataSet;
    }

    public ListaOrdemServicoPAAdapter(){}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adpter_ordemservico, parent, false);


        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mDataSet!=null&&mDataSet.get(position)!=null)
        {
            holder.getTextViewDataOS().setText(DateHelper.toString(mDataSet.get(position).getDatacad()));
            holder.getTextViewIdOS().setText(String.valueOf(mDataSet.get(position).getId_os()));
            holder.solicitante.setText(mDataSet.get(position).getNomesolicita().equals("anyType{}")?"":mDataSet.get(position).getNomesolicita());
            holder.email.setText(mDataSet.get(position).getEmail());
            holder.descrOS.setText(mDataSet.get(position).getDescricao().equals("anyType{}")?"":mDataSet.get(position).getDescricao());


            switch (mDataSet.get(position).getStatus())
            {
                case 1:
                    holder.imageViewOS.setImageDrawable(getDrawable(R.drawable.status_os_ffffcc, context));
                    break;
                case 2:
                    holder.imageViewOS.setImageDrawable(getDrawable(R.drawable.status_os_b2e0ff, context));
                    //holder.itemView.setBackground(getDrawable(R.drawable.backgrounditem_b2e0ff, context));
                    break;
                case 3:
                    holder.imageViewOS.setImageDrawable(getDrawable(R.drawable.status_os_ffcccc, context));
                    //holder.itemView.setBackground(getDrawable(R.drawable.backgrounditem_ffcccc, context));
                    break;
                case 4:
                    holder.imageViewOS.setImageDrawable(getDrawable(R.drawable.status_os_cccccc, context));
                    break;
                case 5:
                    holder.imageViewOS.setImageDrawable(getDrawable(R.drawable.status_os_c1e0d1, context));
                    break;
                case 6:
                    holder.imageViewOS.setImageDrawable(getDrawable(R.drawable.status_os_ffcc99, context));
                    break;
            }
            switch (mDataSet.get(position).getIdtipo())
            {
                case 1:
                    holder.servicoOS.setText("Outros serviços");
                    break;
                case 2:
                    holder.servicoOS.setText("Contagem de Estoque/Balanço");
                    break;
                case 3:
                    holder.servicoOS.setText("Promoções / Liquidações");
                    break;
                case 4:
                    holder.servicoOS.setText("Fechamento para balanço");
                    break;
                case 5:
                    holder.servicoOS.setText("Adesivos em vitrine");
                    break;
                case 6:
                    holder.servicoOS.setText("Eventos");
                    break;
                case 7:
                    holder.servicoOS.setText("Vendas de ingressos");
                    break;
                case 8:
                    holder.servicoOS.setText("Promoções/Liquidações");
                    break;
                case 9:
                    holder.servicoOS.setText("Merchandising");
                    break;
                case 10:
                    holder.servicoOS.setText("Manutenção de Equipamentos");
                    break;
                case 11:
                    holder.servicoOS.setText("Ar condicionado");
                    break;
                case 12:
                    holder.servicoOS.setText("Manutenção elétrica");
                    break;
                case 13:
                    holder.servicoOS.setText("Troca de lâmpadas ");
                    break;
                case 14:
                    holder.servicoOS.setText("Hidráulica");
                    break;
                case 15:
                    holder.servicoOS.setText("Acesso a DG (telefonia)");
                    break;
                case 16:
                    holder.servicoOS.setText("Manutenção no mall");
                    break;
                case 17:
                    holder.servicoOS.setText("Reforma Loja/Instalação de Móveis");
                    break;
                case 18:
                    holder.servicoOS.setText("Pequenos Reparos/ Pinturas");
                    break;
                case 19:
                    holder.servicoOS.setText("Substituições/Manutenção de luminosos/Letreiros");
                    break;
                case 20:
                    holder.servicoOS.setText("Reformas de lojas");
                    break;
                case 21:
                    holder.servicoOS.setText("Inicio de obras");
                    break;
                case 22:
                    holder.servicoOS.setText("Movimentação de mobiliários do mall");
                    break;
                case 23:
                    holder.servicoOS.setText("Paisagismo");
                    break;
                case 24:
                    holder.servicoOS.setText("Entrega de mercadoria");
                    break;
                case 25:
                    holder.servicoOS.setText("Carga e Descarga");
                    break;
                case 26:
                    holder.servicoOS.setText("Entrada de colaboradores");
                    break;
                case 27:
                    holder.servicoOS.setText("Cadastro de novo funcionário envolvendo Joalherias");
                    break;
                case 28:
                    holder.servicoOS.setText("Acesso ao shopping após o horário permitido");
                    break;
                case 29:
                    holder.servicoOS.setText("Manutenções em CFTV, alarmes");
                    break;
                case 30:
                    holder.servicoOS.setText("Entrada e retirada tapume");
                    break;
                case 31:
                    holder.servicoOS.setText("Entrada e saída de lojas");
                    break;
                case 32:
                    holder.servicoOS.setText("Entrada de Quiosques");
                    break;
                case 33:
                    holder.servicoOS.setText("Saída de Quiosques");
                    break;
                case 34:
                    holder.servicoOS.setText("Entrada de equipamento ( lojas que estão saindo )");
                    break;
                case 35:
                    holder.servicoOS.setText("Saída de equipamento ( lojas que estão saindo )");
                    break;
                case 36:
                    holder.servicoOS.setText("Entrada de mídias");
                    break;
                case 37:
                    //holder.servicoOS.setText("Outros serviços");
                    break;
                case 38:
                    holder.servicoOS.setText("Feirão");
                    break;
                case 39:
                    holder.servicoOS.setText("Entregas e/ou trocas de caçambas");
                    break;
                case 40:
                    holder.servicoOS.setText("Vagas de estacionamento");
                    break;
                case 41:
                    holder.servicoOS.setText("Manutenção geral no estacionamento");
                    break;
                case 42:
                    holder.servicoOS.setText("Solicitação de vistoria");
                    break;
                case 43:
                    holder.servicoOS.setText("Outros serviços");
                    break;
                case 44:
                    holder.servicoOS.setText("Limpeza em Geral de Lojas");
                    break;
                case 45:
                    holder.servicoOS.setText("Entrada de Funcionários para Treinamento");
                    break;
                case 47:
                    holder.servicoOS.setText("Promoções/Liquidações");
                    break;
                case 48:
                    holder.servicoOS.setText("Adesivo no mall");
                    break;
                case 49:
                    holder.servicoOS.setText("Entrada Especial");
                    break;
                case 52:
                    holder.servicoOS.setText("Entrada de funcionario temporario");
                    break;
                case 53:
                    holder.servicoOS.setText("Projetos Especiais");
                    break;
                case 54:
                    holder.servicoOS.setText("Evento de Marketing");
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
    public OrdemServico getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataSet.get(position).getId_os();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

        private final CardView cardViewOS;
        private final TextView dataOS;
        private final TextView idOS;
        private final TextView servicoOS;
        private final TextView descrOS;
        private final TextView email;
        private final TextView solicitante;
        private final ImageView imageViewOS;

        public ViewHolder(View v) {
            super(v);

            cardViewOS = (CardView) v.findViewById(R.id.card_viewos);
            dataOS = (TextView) v.findViewById(R.id.textViewDataOScard);
            idOS = (TextView) v.findViewById(R.id.textViewOScard);
            descrOS = (TextView) v.findViewById(R.id.textViewDescrOS);
            servicoOS = (TextView) v.findViewById(R.id.textViewServicoOS);
            email = (TextView) v.findViewById(R.id.textViewemailOS);
            solicitante = (TextView) v.findViewById(R.id.textViewSolicitanteOS);
            imageViewOS = (ImageView) v.findViewById(R.id.imgviewos);
            v.setClickable(true);


            v.setOnClickListener(this);

            //set onContextListener
            v.setOnCreateContextMenuListener(this);
        }
        public TextView getTextViewDataOS(){return dataOS;}
        public TextView getTextViewIdOS(){return idOS;}

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
    public static Drawable getDrawable(Integer id, Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ContextCompat.getDrawable(context,id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }
    public void setFilter(List<OrdemServico> models) {

        if (mDataSet != null) {
            mDataSet.clear();
            mDataSet.addAll(models);
        }
        else {
            mDataSet = models;
        }
        notifyDataSetChanged();
        //notifyAll();
    }
}


