package com.esperienza.intranetmall.mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;

import java.util.ArrayList;

/**
 * Created by ThinkPad on 20/01/2016.
 */
public class GridViewOSPAAdapter extends BaseAdapter {

    private ArrayList<PessoasAutorizadasOS> listpa;
    private Activity activity;

    public GridViewOSPAAdapter(Activity activity,ArrayList<PessoasAutorizadasOS> listpa) {
        super();
        this.activity=activity;
        this.listpa=listpa;

    }
    @Override
    public int getCount() {
        return listpa.size();
    }

    @Override
    public PessoasAutorizadasOS getItem(int position) {
        return listpa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder
    {
        public TextView tnome;
        public TextView tempresa;
        public TextView trg;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();
        GridView grid = (GridView)parent;
        //int size = grid.getColumnWidth();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridviewrow_pa, null);
            //convertView.setLayoutParams(new GridView.LayoutParams(size, size));
            view.tnome = (TextView) convertView.findViewById(R.id.tnome);
            view.trg = (TextView) convertView.findViewById(R.id.trg);
            view.tempresa = (TextView) convertView.findViewById(R.id.tempresa);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        view.tnome.setText(listpa.get(position).getNome());
        view.tempresa.setText(listpa.get(position).getEmpresa());
        view.trg.setText(listpa.get(position).getRg());

        return convertView;
    }
}
