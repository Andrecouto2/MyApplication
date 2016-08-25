package com.esperienza.intranetmall.mobile.fragment;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.esperienza.intranetmall.mobile.R;
import com.esperienza.intranetmall.mobile.fonts.RoundedImageView;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.Util;

/**
 * Created by ThinkPad on 09/05/2016.
 */
public class Fragment_Quadrados extends Fragment {
    private RoundedImageView rimg_os;
    private RoundedImageView rimg_circular;
    private RoundedImageView rimg_funcionario;
    private ScrollView imageViewNew;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_quadrados, container, false);

            rimg_os = (RoundedImageView) rootview.findViewById(R.id.roundedimgos);
            rimg_circular = (RoundedImageView) rootview.findViewById(R.id.roundedimgcircular);
            rimg_funcionario = (RoundedImageView) rootview.findViewById(R.id.roundedimgfnc);
            rimg_os.setPaintColor(getResources().getColor(R.color.paletagreen));
            rimg_circular.setPaintColor(getResources().getColor(R.color.paletalaranja));
            rimg_funcionario.setPaintColor(getResources().getColor(R.color.paletarosa));
            imageViewNew = (ScrollView) rootview.findViewById(R.id.scrollViewNew);
            Util.setInsets(getActivity(),imageViewNew);
            //rimg.setBackgroundColor(getResources().getColor(R.color.purple_800));




        return rootview;
    }
}
