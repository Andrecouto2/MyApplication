package com.esperienza.intranetmall.mobile.entidade;

/**
 * Created by ThinkPad on 07/03/2016.
 */
import java.util.Hashtable;


public class Arquivos {

    //public String nome;
    private String extensao;
    private String file;

    public Arquivos(){}
    public Arquivos(String extensao,String file)
    {
        //this.nome=nome;
        this.setExtensao(extensao);
        this.setFile(file);
    }





    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
