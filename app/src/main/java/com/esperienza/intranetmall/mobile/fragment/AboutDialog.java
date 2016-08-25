package com.esperienza.intranetmall.mobile.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.util.AppHelper;

/**
 * Created by ThinkPad on 28/04/2016.
 */
public class AboutDialog extends DialogFragment {

    public static void showAbout(android.support.v4.app.FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        new AboutDialog().show(ft, "dialog_about");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Cria o HTML com o texto de about
        SpannableStringBuilder aboutBody = new SpannableStringBuilder();
        String versionName = AppHelper.getVersionName(getActivity());
        aboutBody.append(Html.fromHtml(getString(R.string.about_dialog_text, versionName)));
        // Infla o layout
        //LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_about, null);
        //LinearLayout linear = (LinearLayout) inflater.inflate(R.id.linearsobre,null);
        TextView text = (TextView) view.findViewById(R.id.textsobre);
        //TextView view = new TextView(getActivity());
        text.setText(aboutBody);
        text.setMovementMethod(new LinkMovementMethod());
        // Cria o dialog customizado
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.about_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();
    }



}
