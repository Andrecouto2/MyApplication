package com.esperienza.intranetmall.mobile.adapter;

/**
 * Created by ThinkPad on 30/11/2015.
 */
import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.entidade.Funcionario;
import com.esperienza.intranetmall.mobile.fonts.RoundedImageView;
import com.esperienza.intranetmall.mobile.logger.Log;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class ListaFuncionarioAdapter extends RecyclerView.Adapter<ListaFuncionarioAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewFuncionarioAdapter";

    public static List<Funcionario> mDataSet;
    //public  RecyclerModal [] modal;
    public int position;
    public  int idFnc=0;
    static Context context;
    ContextMenu.ContextMenuInfo info;


    public ListaFuncionarioAdapter(Context context,List<Funcionario> dataSet) {
        mDataSet = dataSet;
        this.context=context;
    }

    public ListaFuncionarioAdapter() {

    }


    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{
        private final TextView textViewCPF;
        private final ImageView imageViewFoto;
        private final TextView textViewNome;
        private final TextView textViewDataCad;
        private final TextView textViewDataNasc;
        private final TextView textViewStatusFnc;
        private final ImageView imageViewStatusEnvio;


        public ViewHolder(View v) {
            super(v);


            textViewCPF = (TextView) v.findViewById(R.id.textViewCPf);
            imageViewFoto = (ImageView) v.findViewById(R.id.funcfoto);
            imageViewStatusEnvio = (ImageView) v.findViewById(R.id.enviado);
            textViewNome = (TextView) v.findViewById(R.id.textViewNome);
            textViewDataCad = (TextView) v.findViewById(R.id.textViewDataCadastro);
            textViewDataNasc = (TextView) v.findViewById(R.id.textViewDataNascFnc);
            textViewStatusFnc = (TextView) v.findViewById(R.id.textViewStatusFnc);
            //spinnerStatusFnc = (Spinner) v.findViewById(R.id.spinnerStatusFnc);

            v.setClickable(true);
            //set onclickListener
            v.setOnClickListener(this);

            //set onContextListener
            v.setOnCreateContextMenuListener(this);

        }

        public TextView getTextViewCPF() {
            return textViewCPF;
        }
        public TextView getTextViewNome(){return textViewNome;}
        public TextView getTextViewDataCad(){return textViewDataCad;}
        public ImageView getImageViewStatusEnvio(){return imageViewStatusEnvio;}
        public ImageView getImageViewFoto(){return  imageViewFoto;}
        public TextView getTextViewStatusFnc(){return textViewStatusFnc;}
        public TextView getTextViewDataNasc(){return textViewDataNasc;}

        //public Spinner getSpinnerStatusFnc(){return  spinnerStatusFnc;}

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




    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_novo_funcionario, viewGroup, false);


        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        if(mDataSet.get(position)!=null) {
            viewHolder.getTextViewCPF().setText(mDataSet.get(position).getCpf());
            viewHolder.getTextViewNome().setText(mDataSet.get(position).getNome_lojista());
            viewHolder.getTextViewDataCad().setText(DateHelper.toString(mDataSet.get(position).getDatacadastro()));
            viewHolder.getTextViewDataNasc().setText(DateHelper.toString(mDataSet.get(position).getDatanasc()));
            switch (mDataSet.get(position).getStatus()) {
                case 1:
                    viewHolder.getTextViewStatusFnc().setText("Novo Cadastro");
                    break;
                case 2:
                    viewHolder.getTextViewStatusFnc().setText("Aguardando aprovação do Depto. de Segurança");
                    break;
                case 3:
                    viewHolder.getTextViewStatusFnc().setText("Foto fora do padrão.Reenviar");
                    break;
                case 4:
                    viewHolder.getTextViewStatusFnc().setText("Crachá na ADM. Aguardando retirada");
                    break;
                case 5:
                    viewHolder.getTextViewStatusFnc().setText("Funcionário Ativo");
                    break;
                case 6:
                    viewHolder.getTextViewStatusFnc().setText("Funcionário Inativo");
                    break;
                case 7:
                    viewHolder.getTextViewStatusFnc().setText("Aprovado. Aguardando emissão do Crachá.");
                    break;
                case 8:
                    viewHolder.getTextViewStatusFnc().setText("Cadastro reprovado pelo Depto. de Segurança");
                    break;
                case 9:
                    viewHolder.getTextViewStatusFnc().setText("Crachá em emissão");
                    break;
            }
            String img="iVBORw0KGgoAAAANSUhEUgAAAFMAAABvCAYAAACQCvzWAAAABGdBTUEAALGPC/xhBQAAEcdJREFUeF7tnFeoJcUTxo1rjiCKYkYQFDEL4oMJFB8EswuGNWLOAdEHxZwQYQ2oiFnQh8WAOeeI+mT2xZxzDq2/5n6Hmjo1c2bmzCz7v/8z8NHd1dXd1d9UxzP3zvffk7rE/PPPH8r/TxAKW6GMSC+fxoSHwt4gIqcpoaGwM0xjL4wQCjuF98ZpTHAobIUmZE1TQkPh2Cgjaxp7JQiFrbDoooum9ddfP22++eZps802y9hiiy3SBhtskGbMmJEWWGCBsNw0QihsDDzulFNOST/99FPi+eeffzJ4/vjjj3TggQeG5cZF5OleVmc0SKeObgVCYSPIgJdeeimTFz133XVXQXdMowsYVWfTttCvKlORPyQoha0gquy4445LF1xwQbrkkksyLr300hxedtllad99960yohZUNqpnnHqFDuoIhQVUNaI8q+P1xzWyqu5I1qS9cW1zCIVDqNOo1Snr4FJLLVWQ1wFlfd1Kl8nboKpszbpD4RCWW265tMMOO6Q5c+akhx56KN1///0ZxO+777708MMPpyeffDI9/vjj6YknniiE4NFHH01PPfVUeuSRRwZlH3zwwZGg/gceeCDME9CxafS9zAMdgO2k77nnntwH5vZtt90293nhhRfOaPCCQuEQLrroovTOO++kv/76K/3666/p559/ziv3L7/8ktNaxUc90iekjrbw5Wk/yitrBzn47bff0g8//JB+/PHHXMeff/6Zvvzyy7zFs/3v1DOfffbZAWlsdTCI+Lfffpvx+++/Z7JfffXV9MYbbxTw2muvpffffz+Xx1gML+tkFUQA+OKLL7JNeOAVV1yR7r333pz+6KOPsq5/YbYssPls4bAfuwhJH3TQQbnfDffHoXAIDFmRAImCDMWInXbaKTe+zDLLFIBs5syZ6e+//8661BPhu+++G3QQIKMNwu+//z5PF+xl11133dBGYbXVVktHH310uvvuu7PXAUsodaod6lUb8lBG36xZs3JdNT1SCIVDYL6TYRighiFAhnDaicqC3XffPRv59ddf56FFB1Sf7Qh5qhePJ7z++uvT2muv3bRjGSuuuGK6/PLLcz20TTvYr7gHbTPy9t9//7C+EQiFA8h4FheM8GC4aZhj9OGHH573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";
            String img2="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";
            if(mDataSet.get(position).getImagem()!=null&&!mDataSet.get(position).getImagem().equals(img2)&&!mDataSet.get(position).getImagem().equals(img))
            {
                viewHolder.imageViewFoto.setImageBitmap(getCroppedBitmap(AppHelper.decodeBase64(mDataSet.get(position).getImagem())));
            }
            else
            {
                Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.usericon);
                viewHolder.imageViewFoto.setImageBitmap(getCroppedBitmap(icon));
            }

            switch (mDataSet.get(position).getStatusEnvio()) {

                case 1:
                    viewHolder.getImageViewStatusEnvio().setImageResource(R.drawable.icone_atualizar_infos_circulo);
                    break;
                case 2:
                    viewHolder.getImageViewStatusEnvio().setImageResource(R.drawable.icone_atualizar_infos_circulo);
                    break;
                case 3:
                    viewHolder.getImageViewStatusEnvio().setImageBitmap(null);
                    break;
            }
        /*ArrayList<String> arraylist= new ArrayList<>();
        Boolean flag1=false,flag2=false,flag3=false,flag4=false,flag5=false,flag6=false,flag7=false,flag8=false,flag9=false;
        for(int i=0;i<mDataSet.size();i++)
        {
            switch (mDataSet.get(i).getId_cracha_tipo())
            {
                case 1:
                     if(!flag1)
                     {
                         arraylist.add("Novo Cadastro");
                         flag1=true;
                     }
                    break;
                case 2:
                    if(!flag2)
                    {
                        arraylist.add("Aguardando aprovação do Depto. de Segurança");
                        flag2=true;
                    }

                    break;
                case 3:
                    if(!flag3)
                    {
                        arraylist.add("Foto fora do padrão. Reenviar");
                        flag3=true;
                    }

                    break;
                case 4:
                    if(!flag4)
                    {
                        arraylist.add("Crachá na ADM. Aguardando retirada");
                        flag4=true;
                    }

                    break;
                case 5:
                    if(!flag5)
                    {
                        arraylist.add("Funcionário Ativo");
                        flag5=true;
                    }

                    break;
                case 6:
                    if(!flag6)
                    {
                        arraylist.add("Funcionário Inativo");
                        flag6=true;
                    }

                    break;
                case 7:
                    if(!flag7)
                    {
                        arraylist.add("Aprovado. Aguardando Emissão do Crachá");
                        flag7=true;
                    }

                    break;
                case 8:
                    if(!flag8)
                    {
                        arraylist.add("Cadastro reprovado pelo Depto. de Segurança");
                        flag8=true;
                    }

                    break;
                case 9:
                    if(!flag9)
                    {
                        arraylist.add("Crachá em emissão");
                        flag9=true;
                    }

                    break;
            }
        }
        String[] ls = new String[9];
        for(int i=0;i<arraylist.size();i++)
        {
           ls[i]=arraylist.get(i).toString();

        }*/
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, ls);

            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //viewHolder.getSpinnerStatusFnc().setAdapter(adapter);
        }




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public Funcionario getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataSet.get(position).getIdfnc();
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


}