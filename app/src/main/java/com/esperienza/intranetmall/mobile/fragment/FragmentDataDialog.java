package com.esperienza.intranetmall.mobile.fragment;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Benedito on 22/09/2014.
 */

public class FragmentDataDialog extends DialogFragment   {
    private View v;
    private OnDateSetListener ondateSet;
    private int year, month, day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return new DatePickerDialog(getActivity() , ondateSet, year, month, day);
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    public FragmentDataDialog() {
    }

    public void setCallBack(OnDateSetListener ondate) {
        ondateSet = ondate;
    }
}