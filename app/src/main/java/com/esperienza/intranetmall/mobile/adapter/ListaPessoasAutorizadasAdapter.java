package com.esperienza.intranetmall.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.fragment.FragmentAberturaOrdemServicoSequencia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 18/02/2016.
 */
public class ListaPessoasAutorizadasAdapter extends RecyclerView.Adapter<ListaPessoasAutorizadasAdapter.ViewHolder> {

    public static List<PessoasAutorizadasOS> mDataSet;
    public int position;
    public int idOS = 0;
    static Context context;
    private ArrayList<Boolean> positionArray;
    public static ArrayList<Integer> copypositionArray = new ArrayList<>();;

    public ListaPessoasAutorizadasAdapter(Context context, List<PessoasAutorizadasOS> dataSet) {
        this.context = context;
        this.mDataSet = dataSet;

        positionArray = new ArrayList<>(dataSet.size());
        for(int i =0;i<dataSet.size();i++) {
            positionArray.add(i,false);
        }

        for(int i =0;i<copypositionArray.size();i++)
        {

            positionArray.set(copypositionArray.get(i), true);
        }
    }

    public ListaPessoasAutorizadasAdapter(List<PessoasAutorizadasOS> dataSet) {
        this.mDataSet = dataSet;
    }

    public ListaPessoasAutorizadasAdapter() {
    }


    @Override
    public ListaPessoasAutorizadasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_row_pessoasautorizadas, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(mDataSet!=null&&mDataSet.get(position)!=null) {
            holder.txtviewnome.setText(mDataSet.get(position).getNome());
            holder.txtviewrg.setText(mDataSet.get(position).getRg());
            holder.txtviewempresa.setText(mDataSet.get(position).getEmpresa());

            holder.cbescolha.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(holder.cbescolha.isChecked())
                    {
                        positionArray.set(position, true);
                        copypositionArray.add(position);
                        FragmentAberturaOrdemServicoSequencia.os.getPessoas().add(mDataSet.get(position));
                    }
                    else
                    {
                        positionArray.set(position, false);
                        Integer integer = new Integer(position);
                        copypositionArray.remove(integer);
                        FragmentAberturaOrdemServicoSequencia.os.getPessoas().remove(mDataSet.get(position));
                    }
                }

            });
            holder.cbescolha.setChecked(positionArray.get(position));

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
    public PessoasAutorizadasOS getItem(int position) {
        return mDataSet.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        private final TextView txtviewnome;
        private final TextView txtviewrg;
        private final TextView txtviewempresa;
        private final CheckBox cbescolha;

        public ViewHolder(View itemView) {
            super(itemView);

            txtviewnome = (TextView) itemView.findViewById(R.id.tvPA2);
            txtviewrg = (TextView) itemView.findViewById(R.id.tvRG2);
            txtviewempresa = (TextView) itemView.findViewById(R.id.tvEmpresa2);
            cbescolha = (CheckBox) itemView.findViewById(R.id.chkSelected);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }
        public TextView getTextViewNome(){return txtviewnome;}
        public TextView getTextViewRg(){return txtviewrg;}
        public TextView getTextViewEmpresa(){return txtviewempresa;}
        public CheckBox getCheckBoxEscolha(){return cbescolha;}

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
    public void setFilter(List<PessoasAutorizadasOS> models) {

        notifyItemChanged(0);
        mDataSet.addAll(models);
        notifyDataSetChanged();
    }
}
