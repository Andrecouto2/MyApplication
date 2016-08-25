package com.esperienza.intranetmall.mobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.esperienza.intranetmall.mobile.fragment.FragmentDadosBasicosFuncionario;
import com.esperienza.intranetmall.mobile.fragment.FragmentDadosFuncionario;
import com.esperienza.intranetmall.mobile.fragment.FragmentEnderecoFuncionario;
import com.esperienza.intranetmall.mobile.fragment.FragmentImagemFuncionario;


/**
 * Created by ThinkPad on 24/11/2015.
 */
public class ViewPageFuncionalidadeAdapter extends FragmentStatePagerAdapter {

    public static final int FRAGMENT_DADOS_BASICOS_FNC = 0;
    public static final int FRAGMENT_ENDERECO_FNC = 1;
    public static final int FRAGMENT_IMAGEM_FNC = 2;

    public  FragmentDadosBasicosFuncionario fragmentDadosBasicosFuncionario;
    public  FragmentEnderecoFuncionario fragmentEnderecoFuncionario;
    public  FragmentImagemFuncionario fragmentImagemFuncionario;
    private String titles[] = new String[] { "Dados básicos", "Endereço","Foto"};



    public ViewPageFuncionalidadeAdapter(FragmentManager fm) {
        super(fm);
        fragmentDadosBasicosFuncionario = new FragmentDadosBasicosFuncionario();
        fragmentEnderecoFuncionario = new FragmentEnderecoFuncionario();
        fragmentImagemFuncionario = new FragmentImagemFuncionario();

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case FRAGMENT_DADOS_BASICOS_FNC:

                return  fragmentDadosBasicosFuncionario;
            case FRAGMENT_ENDERECO_FNC:

                return fragmentEnderecoFuncionario;
            case FRAGMENT_IMAGEM_FNC:

                return fragmentImagemFuncionario;
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return titles.length;
    }
    public Fragment getFragment(int position) {
        return (Fragment)getItem(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
