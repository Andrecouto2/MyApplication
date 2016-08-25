package com.esperienza.intranetmall.mobile.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.activity.AberturaOSActivity;
import com.esperienza.intranetmall.mobile.activity.AberturaOSSequenciaActivity;
import com.esperienza.intranetmall.mobile.dao.CalendarioDAO;
import com.esperienza.intranetmall.mobile.dao.OrdemServicoSetorDAO;
import com.esperienza.intranetmall.mobile.dao.RegraOrdemServicoDAO;
import com.esperienza.intranetmall.mobile.dao.TipoServicoDAO;
import com.esperienza.intranetmall.mobile.entidade.Calendario;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.entidade.OrdemServicoSetor;
import com.esperienza.intranetmall.mobile.entidade.RegraOrdemServico;
import com.esperienza.intranetmall.mobile.entidade.TipoServico;
import com.esperienza.intranetmall.mobile.fonts.RangeTimePickerDialog;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;
import com.esperienza.intranetmall.mobile.util.Util;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ThinkPad on 03/02/2016.
 */
public class FragmentAberturaOrdemServico extends Fragment {


    private TextView fromDateEtxt;
    private TextView toDateEtxt;
    private TextView fromTimeETxt;
    private TextView toTimeEtxt;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private TimePickerDialog fromTimePickerDialog;
    private TimePickerDialog toTimePickerDialog;
    private static final int TIME_PICKER_INTERVAL = 30;
    private boolean mIgnoreEvent = false;

    private SimpleDateFormat dateFormatter;
    private ScrollView scrollView;
    private LinearLayout linearmain;
    private LinearLayout linearmaster;
    private LinearLayout linearcontinuar;
    private int checked = 0;
    private RadioGroup rg;
    private RadioButton rbatual;
    private EditText edt;
    //private Button btncontinuar;
    private String datainicio = "";
    private String horainicio = "";
    private TipoServico tiposervico;
    private OrdemServico os;
    private boolean checkVisibility =false;
    private static ArrayList<Boolean> lvisibility = new ArrayList<>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_abertura_ordemservico, container, false);
        fromDateEtxt = (TextView) rootview.findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.clearFocus();
        fromDateEtxt.setEnabled(false);
        toDateEtxt = (TextView) rootview.findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        toDateEtxt.clearFocus();
        toDateEtxt.setEnabled(false);
        toTimeEtxt = (TextView) rootview.findViewById(R.id.etxt_totime);
        toTimeEtxt.setInputType(InputType.TYPE_NULL);
        toTimeEtxt.clearFocus();
        toTimeEtxt.setEnabled(false);
        fromTimeETxt = (TextView) rootview.findViewById(R.id.etxt_fromtime);
        fromTimeETxt.setInputType(InputType.TYPE_NULL);
        fromTimeETxt.clearFocus();
        fromTimeETxt.setEnabled(false);

        scrollView = (ScrollView) rootview.findViewById(R.id.scrollViewAberturaOS);
        //Util.setInsets(getActivity(), scrollView);
        rg = new RadioGroup(getActivity()); //create the RadioGroup
        rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
        edt = new EditText(getActivity());
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        // convert the DP into pixel
        int pixel =  (int)(16 * scale + 0.5f);
        TableRow.LayoutParams layoutParams2 = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(pixel,0,0,pixel);

        edt.setLayoutParams(layoutParams2);

        linearcontinuar = (LinearLayout) rootview.findViewById(R.id.linearcontinuar);
        linearmain = (LinearLayout) rootview.findViewById(R.id.linearmain);
        linearmaster = (LinearLayout) rootview.findViewById(R.id.linearmaster);


        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        setDateTimeField();
        buildcards();
        init();



        return rootview;
    }

    public void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                if(!fromTimeETxt.getText().toString().equals("--")&&!fromTimeETxt.getText().toString().equals(horainicio))
                {
                    if(ismin()&&!DateHelper.comparelimithour(fromTimeETxt.getText().toString(),horainicio))
                        fromTimeETxt.setText("--");
                }


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.setTitle("Data Início");
        //set data minima



        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                if(ismintodate(dateFormatter.format(newDate.getTime())))
                {
                    toTimeEtxt.setText("--");
                }
                if(DateHelper.pastlimitdate(dateFormatter.format(newDate.getTime()),fromDateEtxt.getText().toString()))
                {
                    toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                    toTimeEtxt.setEnabled(true);
                }
                else
                {
                    Toast.makeText(getActivity(),"Escolha data igual ou superior "+fromDateEtxt.getText().toString(),Toast.LENGTH_SHORT).show();
                }



            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog.setTitle("Data Término");


        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);

        fromTimePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (mIgnoreEvent)
                    return;
                if (selectedMinute % TIME_PICKER_INTERVAL != 0) {
                    int minuteFloor = selectedMinute - (selectedMinute % TIME_PICKER_INTERVAL);
                    selectedMinute = minuteFloor + (selectedMinute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
                    if (selectedMinute == 60)
                        selectedMinute = 0;

                    mIgnoreEvent = true;
                    timePicker.setCurrentMinute(selectedMinute);
                    mIgnoreEvent = false;
                }
                String hour=(String.valueOf(selectedHour).length() > 1 ? selectedHour : "0" + selectedHour) + ":" + (selectedMinute == 0 ? "00" : "" + selectedMinute);
                if(!ismin()|| AppHelper.getUsuario().getTipo()==1)
                {
                    fromTimeETxt.setText(hour);
                }
                else
                {
                    if(hour.equals(horainicio)||DateHelper.comparelimithour(hour,horainicio))
                    {
                        fromTimeETxt.setText(hour);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Selecione horário igual ou superior às "+horainicio,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, hour, minute, true);
        fromTimePickerDialog.setTitle("Hora Início");


        toTimePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (mIgnoreEvent)
                    return;
                if (selectedMinute % TIME_PICKER_INTERVAL != 0) {
                    int minuteFloor = selectedMinute - (selectedMinute % TIME_PICKER_INTERVAL);
                    selectedMinute = minuteFloor + (selectedMinute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
                    if (selectedMinute == 60)
                        selectedMinute = 0;

                    mIgnoreEvent = true;
                    timePicker.setCurrentMinute(selectedMinute);
                    mIgnoreEvent = false;
                }
                String hour=(String.valueOf(selectedHour).length() > 1 ? selectedHour : "0" + selectedHour) + ":" + (selectedMinute == 0 ? "00" : "" + selectedMinute);
                if(fromDateEtxt.getText().toString().equals(toDateEtxt.getText().toString())) {
                    if (DateHelper.comparelimithour(hour, fromTimeETxt.getText().toString())) {
                        toTimeEtxt.setText(hour);
                    } else {
                        Toast.makeText(getActivity(), "Selecione horário superior às " + fromTimeETxt.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    toTimeEtxt.setText(hour);
                }

            }
        }, hour, minute, true);
        toTimePickerDialog.setTitle("Hora Término");


    }

    public void init() {

        toDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toDateEtxt.isEnabled())
                {
                    toDatePickerDialog.show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Escolha o tipo de serviço.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fromDateEtxt.isEnabled()) {
                    fromDatePickerDialog.show();
                } else {
                    Toast.makeText(getActivity(), "Escolha o tipo de serviço.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        toTimeEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toTimeEtxt.isEnabled())
                {
                    toTimePickerDialog.show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Escolha a data de término.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        fromTimeETxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromTimeETxt.isEnabled()) {
                    fromTimePickerDialog.show();
                } else {
                    Toast.makeText(getActivity(), "Escolha o tipo de serviço.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void buildcards() {
        List<OrdemServicoSetor> oss = OrdemServicoSetorDAO.getNewInstance().getListaOrdemServicoSetorComTipos(AppHelper.getIdShop());
        int tam = OrdemServicoSetorDAO.getNewInstance().getListaOrdemServicoSetorComTipos(AppHelper.getIdShop()).size();
        LinearLayout cardInner = null;
        LinearLayout cardInnerMaster = null;
        LinearLayout card = null;
        List<OrdemServicoSetor> oss2 = new ArrayList<>();
        oss2.addAll(oss);
        for (int i = 0; i < tam; i++) {
           if(oss2.get(i).getTipoServicos()==null)
           {
               oss.remove(oss2.get(i));
           }
        }
        int tam2=oss.size();
        for (int i = 0; i < tam2; i++) {



            card = new LinearLayout(getActivity());
            card.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            card.setOrientation(LinearLayout.VERTICAL);
            card.setId(i);
            //card.setRadius(12.0f);
            //card.setCardElevation(6.0f);
            //card.setUseCompatPadding(true);
            //
            final float scale = getActivity().getResources().getDisplayMetrics().density;
            // convert the DP into pixel
            int pixel =  (int)(21 * scale + 0.5f);
            lvisibility.add(i, false);
            //
            final TextView tv = new TextView(getActivity());

            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


                layoutParams2.setMargins(0,pixel,0,0);

            /*else
            {
                layoutParams2.setMargins(pixel,pixel,0,0);
            }*/


            //tv.setLayoutParams(layoutParams2);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setText(oss.get(i).getTitulo().toUpperCase().replace("\n",""));
            tv.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.aba_closed_24_24, 0, 0, 0);
            cardInnerMaster = new LinearLayout(getActivity());
            cardInnerMaster.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            cardInnerMaster.setOrientation(LinearLayout.VERTICAL);

            cardInner = new LinearLayout(getActivity());

           TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            //layoutParams.setMargins(pixel,0,0,0);
            cardInner.setOrientation(LinearLayout.VERTICAL);
            cardInner.setLayoutParams(layoutParams);
            /*cardInner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    220));*/
            cardInner.setId(i);
            cardInner.setVisibility(View.GONE);
            //
            cardInnerMaster.addView(tv);
            cardInnerMaster.setLayoutParams(layoutParams2);
            createRadioButton(oss.get(i).getTipoServicos(), cardInner);

            final LinearLayout finalCardInner = cardInner;
            final LinearLayout finalCard = card;
            final LinearLayout finalCardInnerMaster = cardInnerMaster;
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!lvisibility.get(finalI).booleanValue()) {
                        finalCardInner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        finalCardInner.setVisibility(View.VISIBLE);
                        finalCardInnerMaster.setVisibility(View.VISIBLE);
                        finalCard.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        tv.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.aba_open_24_24, 0, 0, 0);
                        lvisibility.set(finalI, true);
                    } else {
                        finalCard.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        finalCardInner.setVisibility(View.GONE);
                        tv.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.aba_closed_24_24, 0, 0, 0);
                        lvisibility.set(finalI, false);
                    }


                }
            });
            cardInnerMaster.addView(cardInner);
            card.addView(cardInnerMaster);

            linearmain.addView(card);

        }
        //card = new CardView(new ContextThemeWrapper(getActivity(), R.style.CardViewStyle), null, 0);
        //card.setRadius(12.0f);
        //card.setCardElevation(6.0f);
        //card.setUseCompatPadding(true);

        //btncontinuar = new Button(new ContextThemeWrapper(getActivity(), R.style.button), null, 0);
        //btncontinuar.setText("Continuar");
        linearcontinuar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(rbatual==null)
                {
                    Toast.makeText(getActivity(),"Escolha tipo de serviço para continuar.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(fromTimeETxt.getText().toString().equals("--"))
                    {
                        Toast.makeText(getActivity(),"Escolha horário de início.",Toast.LENGTH_SHORT).show();
                    }
                    else if(toDateEtxt.getText().toString().equals("--"))
                    {
                        Toast.makeText(getActivity(),"Escolha data de término.",Toast.LENGTH_SHORT).show();
                    }
                    else if(toTimeEtxt.getText().toString().equals("--"))
                    {
                        Toast.makeText(getActivity(),"Escolha horário de término.",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(tiposervico.getIdtipo()==1&&edt.getText().toString().equals(""))
                        {
                            Toast.makeText(getActivity(),"Descreva o tipo de serviço.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            os=new OrdemServico();
                            os.setDatainicio(DateHelper.toDate(fromDateEtxt.getText().toString()));
                            os.setDatafim(DateHelper.toDate(toDateEtxt.getText().toString()));
                            os.setHorainicio(fromTimeETxt.getText().toString());
                            os.setHorafim(toTimeEtxt.getText().toString());
                            Intent intent = new Intent(getContext(), AberturaOSSequenciaActivity.class);
                            intent.putExtra("os", Parcels.wrap(os));
                            intent.putExtra("ts", Parcels.wrap(tiposervico));
                            intent.putExtra("outros",edt.getText().toString());
                            startActivity(intent);
                            getActivity().finish();
                        }

                    }

                }

            }
        });
        //btncontinuar.setGravity(Gravity.BOTTOM);
        //card.addView(btncontinuar);
        //linearmain.addView(btncontinuar);


    }

    private void createRadioButton(List<TipoServico> lts, final LinearLayout linear) {
        int tam = lts.size();
        final RadioButton[] rb = new RadioButton[tam];
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        int pixel =  (int)(20 * scale + 0.5f);
        for (int i = 0; i < tam; i++) {
            // convert the DP into pixel

            lvisibility.add(i, false);
            //


            TableRow.LayoutParams layoutParams2 = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            //layoutParams2.setMargins(pixel,0,0,0);

            rb[i] = new RadioButton(getActivity());
            rb[i].setLayoutParams(layoutParams2);
            rb[i].setId(lts.get(i).getIdtipo());
            rb[i].setTag(lts.get(i).getIdtipo());
            rb[i].setText(lts.get(i).getDescricao());


            /*rb[i].setOnClickListener(new RadioButton.OnClickListener() {
                public void onClick(View v) {
                    View rootview = linearmain.getRootView();
                    List<RadioButton> lrb = getRadioButtons(rootview);
                    int count = lrb.size();
                    for (int i = 0; i < count; i++) {
                        if (lrb.get(i).isChecked())
                        {
                            lrb.get(i).setChecked(true);
                        }
                        else
                        {
                            lrb.get(i).setChecked(false);
                        }
                    }
                    RadioButton rb1 = (RadioButton) rootview.findViewWithTag(1);
                    rb1.setText("oi");
                }
            });*/
            rb[i].setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            View rootview = linearmain.getRootView();
                            List<RadioButton> lrb = getRadioButtons(rootview);
                            int count = lrb.size();
                            if (rbatual != null) rbatual.setChecked(false);
                            for (int i = 0; i < count; i++) {
                                if (lrb.get(i).isChecked()) {
                                    rbatual = lrb.get(i);
                                }
                            }

                            if (!rbatual.getTag().toString().equals("1")) {
                                if (edt.getVisibility() == View.VISIBLE)
                                    edt.setVisibility(View.GONE);
                            } else {
                                edt.setVisibility(View.VISIBLE);
                            }
                            /*String test=rbatual.getTag().toString();
                            for (int i = 0; i < count; i++)
                            {
                                String test2=lrb.get(i).getTag().toString();
                                if(!rbatual.getTag().toString().equals(lrb.get(i).getTag().toString()))
                                {
                                    lrb.get(i).setChecked(false);
                                }
                            }*/
                            tiposervico=TipoServicoDAO.getNewInstance().getTipoServico(Integer.parseInt(rbatual.getTag().toString()),AppHelper.getIdShop());
                            fromTimeETxt.setEnabled(true);
                            fromDateEtxt.setEnabled(true);
                            toDateEtxt.setEnabled(true);
                            regraos(TipoServicoDAO.getNewInstance().getTipoServico(Integer.parseInt(rbatual.getTag().toString()),AppHelper.getIdShop()));

                        }


                    });


            linear.addView(rb[i]);
            if (lts.get(i).getIdtipo() == 1) {
                edt.setVisibility(View.GONE);
                linear.addView(edt);
            }


        }

        //you add the whole RadioGroup to the layout

    }

    public List<RadioButton> getRadioButtons(View root) {
        List<RadioButton> rbs = new ArrayList<>();
        List<OrdemServicoSetor> los = OrdemServicoSetorDAO.getNewInstance().getListaOrdemServicoSetorComTipos(AppHelper.getIdShop());

        RadioButton rb;
        int count = los.size();
        int count1 = 0;
        List<OrdemServicoSetor> oss2 = new ArrayList<>();
        oss2.addAll(los);
        int tam=los.size();
        for (int i = 0; i < tam; i++) {
            if(oss2.get(i).getTipoServicos()==null)
            {
                los.remove(oss2.get(i));
            }
        }
        int count2=los.size();
        for (int i = 0; i < count2; i++) {

            count1 = los.get(i).getTipoServicos().size();
            for (int j = 0; j < count1; j++) {
                rb = (RadioButton) root.findViewWithTag(los.get(i).getTipoServicos().get(j).getIdtipo());
                if (rb != null)
                    rbs.add(rb);

            }
        }


        return rbs;

    }

    public void regraos(TipoServico tipoServico) {

        if(AppHelper.getUsuario().getTipo()==1)
        {
            try
            {
                Calendar newCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                newCalendar.set(Calendar.MINUTE, newCalendar.getMinimum(Calendar.MINUTE));
                newCalendar.set(Calendar.SECOND, newCalendar.getMinimum(Calendar.SECOND));
                newCalendar.set(Calendar.MILLISECOND, newCalendar.getMinimum(Calendar.MILLISECOND));
                fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                toDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                fromDateEtxt.setText(DateHelper.getDateTime());

                return;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return;
            }

        }

        Calendario calendario = CalendarioDAO.getNewInstance().getCalendario(DateHelper.currentDate(),AppHelper.getIdShop());

        RegraOrdemServico ros;

        if(calendario.getFeriado()<1)//se nao for feriado
        {
            ros = RegraOrdemServicoDAO.getNewInstance().getRegraOSPordiaSemana(DateHelper.diadasemanaatual(),AppHelper.getIdShop());
        }
        else//se for feriado
        {
            ros = RegraOrdemServicoDAO.getNewInstance().getRegraOSPordiaSemana(0,AppHelper.getIdShop());
        }

        if(ros.getPermissao_dia()>0)
        {
            datainicio=DateHelper.toString(CalendarioDAO.getNewInstance().getCalendarioDiaUtil(DateHelper.currentDate(),AppHelper.getIdShop()).getDdata());
            horainicio=ros.getHorario_dia_posterior();
            //set texts
            fromTimeETxt.setText(horainicio);
            fromDateEtxt.setText(datainicio);
            //limit calendario
            Calendar newCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            newCalendar.set(Calendar.MINUTE, newCalendar.getMinimum(Calendar.MINUTE));
            newCalendar.set(Calendar.SECOND, newCalendar.getMinimum(Calendar.SECOND));
            newCalendar.set(Calendar.MILLISECOND, newCalendar.getMinimum(Calendar.MILLISECOND));
            newCalendar.add(Calendar.DAY_OF_MONTH, ros.getPermissao_dia());
            fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
            toDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());

        }
        else
        {
            if (!DateHelper.comparelimithour(DateHelper.horaatual(), ros.getHora_limite()))
            {
                datainicio=DateHelper.toString(DateHelper.toDate(DateHelper.addDays(DateHelper.toDate(DateHelper.getDateTime()), ros.getSoma_dia_hora_ate_limite())));
                horainicio=ros.getHorario_ate_limite();
                fromTimeETxt.setText(horainicio);
                fromDateEtxt.setText(datainicio);
                Calendar newCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                newCalendar.set(Calendar.MINUTE, newCalendar.getMinimum(Calendar.MINUTE));
                newCalendar.set(Calendar.SECOND, newCalendar.getMinimum(Calendar.SECOND));
                newCalendar.set(Calendar.MILLISECOND, newCalendar.getMinimum(Calendar.MILLISECOND));
                newCalendar.add(Calendar.DAY_OF_MONTH, ros.getSoma_dia_hora_ate_limite());
                fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                toDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
            }
            else
            {
                if(tipoServico.getForafuncionamento()>0)
                {
                    datainicio=DateHelper.toString(DateHelper.toDate((DateHelper.addDays(DateHelper.toDate(DateHelper.getDateTime()), ros.getSoma_dia_hora_apos_limite()))));
                    horainicio=ros.getHorario_ate_limite();
                    fromTimeETxt.setText(horainicio);
                    fromDateEtxt.setText(datainicio);
                    Calendar newCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    newCalendar.set(Calendar.MINUTE, newCalendar.getMinimum(Calendar.MINUTE));
                    newCalendar.set(Calendar.SECOND, newCalendar.getMinimum(Calendar.SECOND));
                    newCalendar.set(Calendar.MILLISECOND, newCalendar.getMinimum(Calendar.MILLISECOND));
                    newCalendar.add(Calendar.DAY_OF_MONTH, ros.getSoma_dia_hora_apos_limite());
                    fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                    toDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                    toTimeEtxt.setText("--");


                }
                else
                {
                    datainicio=DateHelper.toString(DateHelper.toDate(DateHelper.addDays(DateHelper.toDate(DateHelper.getDateTime()), ros.getSoma_dia_hora_apos_limite())));
                    horainicio=ros.getHorario_apos_limite();
                    fromTimeETxt.setText(horainicio);
                    fromDateEtxt.setText(datainicio);
                    Calendar newCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                    newCalendar.set(Calendar.MINUTE, newCalendar.getMinimum(Calendar.MINUTE));
                    newCalendar.set(Calendar.SECOND, newCalendar.getMinimum(Calendar.SECOND));
                    newCalendar.set(Calendar.MILLISECOND, newCalendar.getMinimum(Calendar.MILLISECOND));
                    newCalendar.add(Calendar.DAY_OF_MONTH, ros.getSoma_dia_hora_apos_limite());
                    fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                    toDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                }
            }
        }



            //SE FORA DE FUNCIONAMENTO==1
            /*if (tipoServico.getForafuncionamento() == 1)
            {
               if(ros.getPermissao_dia()<1)
               {
                   datainicio=DateHelper.currentDate();
                   horainicio=ros.getHorario_ate_limite();
                   horafim="00:00";
                   fromTimeETxt.setText(horainicio);
                   fromDateEtxt.setText(datainicio);
                   Calendar newCalendar = Calendar.getInstance();
                   newCalendar.add(Calendar.DAY_OF_MONTH, ros.getPermissao_dia());
                   fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
               }
                else
               {
                   datainicio=DateHelper.addDays(DateHelper.toDate(DateHelper.currentDate()), ros.getPermissao_dia());
                   horainicio=ros.getHorario_apos_limite();
                   horafim=ros.getHorario_ate_limite();
                   fromTimeETxt.setText(horainicio);
                   fromDateEtxt.setText(datainicio);
                   Calendar newCalendar = Calendar.getInstance();
                   newCalendar.add(Calendar.DAY_OF_MONTH, ros.getPermissao_dia());
                   fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
               }
            }
            else//SE DENTRO DE FUNCIONAMENTO
            {
                if(ros.getPermissao_dia()<1)
                {
                    datainicio=DateHelper.currentDate();
                    horainicio=ros.getHorario_apos_limite();
                    horafim="00:00";
                    fromTimeETxt.setText(horainicio);
                    fromDateEtxt.setText(datainicio);
                    Calendar newCalendar = Calendar.getInstance();
                    newCalendar.add(Calendar.DAY_OF_MONTH, ros.getPermissao_dia());
                    fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                }
                else
                {
                    datainicio=DateHelper.addDays(DateHelper.toDate(DateHelper.currentDate()),ros.getPermissao_dia());
                    horainicio="00:00";
                    horafim="00:00";
                    fromTimeETxt.setText(horainicio);
                    fromDateEtxt.setText(datainicio);
                    Calendar newCalendar = Calendar.getInstance();
                    newCalendar.add(Calendar.DAY_OF_MONTH, ros.getPermissao_dia());
                    fromDatePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
                }
            }



            /*if (ros.getPermissao_dia() < 0) {
                if (DateHelper.comparelimithour(DateHelper.horaatual(), ros.getHora_limite())) {
                    horainicio = ros.getHorario_ate_limite();
                    datainicio = DateHelper.currentDate();
                } else {
                    horainicio = ros.getHorario_apos_limite();
                    datainicio = DateHelper.currentDate();
                }
            } else {
                horainicio = ros.getHorario_dia_posterior();
                //encontrar próximo dia util
                datainicio = CalendarioDAO.getNewInstance().getCalendarioDiaUtil(DateHelper.currentDate()).getDdata().toString();
            }*/




    // ros=RegraOrdemServicoDAO.getNewInstance().getRegraOSPordiaSemana(0);



    }
    public boolean ismin()
    {
        boolean retorno=false;
        String datemin = DateHelper.convertDate(fromDatePickerDialog.getDatePicker().getMinDate());
        try {

            if(fromDateEtxt.getText().toString().equals(datemin))
            {
                retorno=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return retorno;
    }
    public boolean ismintodate(String date)
    {
        boolean retorno=false;
        String datemin = DateHelper.convertDate(toDatePickerDialog.getDatePicker().getMinDate());
        try {

            if(date.equals(datemin))
            {
                retorno=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }


}

