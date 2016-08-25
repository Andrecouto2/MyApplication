package com.esperienza.intranetmall.mobile.entidade;

/**
 * Created by ThinkPad on 18/11/2015.
 */
public class RegraOrdemServico {

    private int idosregra;
    private int idshop;
    private int dia_semana;
    private int permissao_dia;
    private String hora_limite;
    private int soma_dia_hora_ate_limite;
    private String horario_ate_limite;
    private int soma_dia_hora_apos_limite;
    private String horario_apos_limite;
    private String horario_dia_posterior;



    public int getIdosregra() {
        return idosregra;
    }

    public void setIdosregra(int idosregra) {
        this.idosregra = idosregra;
    }

    public int getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(int dia_semana) {
        this.dia_semana = dia_semana;
    }

    public int getPermissao_dia() {
        return permissao_dia;
    }

    public void setPermissao_dia(int permissao_dia) {
        this.permissao_dia = permissao_dia;
    }

    public String getHora_limite() {
        return hora_limite;
    }

    public void setHora_limite(String hora_limite) {
        this.hora_limite = hora_limite;
    }

    public int getSoma_dia_hora_ate_limite() {
        return soma_dia_hora_ate_limite;
    }

    public void setSoma_dia_hora_ate_limite(int soma_dia_hora_ate_limite) {
        this.soma_dia_hora_ate_limite = soma_dia_hora_ate_limite;
    }

    public String getHorario_ate_limite() {
        return horario_ate_limite;
    }

    public void setHorario_ate_limite(String horario_ate_limite) {
        this.horario_ate_limite = horario_ate_limite;
    }

    public int getSoma_dia_hora_apos_limite() {
        return soma_dia_hora_apos_limite;
    }

    public void setSoma_dia_hora_apos_limite(int soma_dia_hora_apos_limite) {
        this.soma_dia_hora_apos_limite = soma_dia_hora_apos_limite;
    }

    public String getHorario_apos_limite() {
        return horario_apos_limite;
    }

    public void setHorario_apos_limite(String horario_apos_limite) {
        this.horario_apos_limite = horario_apos_limite;
    }

    public String getHorario_dia_posterior() {
        return horario_dia_posterior;
    }

    public void setHorario_dia_posterior(String horario_dia_posterior) {
        this.horario_dia_posterior = horario_dia_posterior;
    }


    public int getIdshop() {
        return idshop;
    }

    public void setIdshop(int idshop) {
        this.idshop = idshop;
    }
}
