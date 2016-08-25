package com.esperienza.intranetmall.mobile.entidade;

/**
 * Created by ThinkPad on 12/02/2016.
 */
public class ConfigHorariosOS {

    private int id_config_horario;
    private String horario_func_inicio;
    private String horario_func_fim;
    private String gmt;
    private int tempo_token;

    public int getId_config_horario() {
        return id_config_horario;
    }

    public void setId_config_horario(int id_config_horario) {
        this.id_config_horario = id_config_horario;
    }

    public String getHorario_func_inicio() {
        return horario_func_inicio;
    }

    public void setHorario_func_inicio(String horario_func_inicio) {
        this.horario_func_inicio = horario_func_inicio;
    }

    public String getHorario_func_fim() {
        return horario_func_fim;
    }

    public void setHorario_func_fim(String horario_func_fim) {
        this.horario_func_fim = horario_func_fim;
    }

    public String getGmt() {
        return gmt;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }

    public int getTempo_token() {
        return tempo_token;
    }

    public void setTempo_token(int tempo_token) {
        this.tempo_token = tempo_token;
    }
}
