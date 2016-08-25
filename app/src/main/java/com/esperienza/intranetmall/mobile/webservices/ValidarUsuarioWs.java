package com.esperienza.intranetmall.mobile.webservices;

import com.esperienza.intranetmall.mobile.entidade.Calendario;
import com.esperienza.intranetmall.mobile.entidade.CamposObrigatoriosFnc;
import com.esperienza.intranetmall.mobile.entidade.Destaque;
import com.esperienza.intranetmall.mobile.entidade.Dispositivo;
import com.esperienza.intranetmall.mobile.entidade.EntidadeLogin;
import com.esperienza.intranetmall.mobile.entidade.Home;
import com.esperienza.intranetmall.mobile.entidade.OrdemServicoSetor;
import com.esperienza.intranetmall.mobile.entidade.RegraOrdemServico;
import com.esperienza.intranetmall.mobile.entidade.TipoServico;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.gcm.IntentUtils;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 18/11/2015.
 */
public class ValidarUsuarioWs extends WebServicesXML {

    @Override
    public void onCreate() {
        setMethodName("AutenticaUsuario");
        addProperty("usuario");
        addProperty("senha");
        addProperty("idShopping");
        addProperty("registrationDevice");
        addProperty("ipDevice");
        addProperty("imei");
        addProperty("altitude");
        addProperty("longitude");
        addProperty("latitude");
        addProperty("descricao");
        addProperty("gmt");
        addProperty("hora_dispositivo");
        addProperty("velocidade");
        addProperty("precisao");


    }
    public void setUsuario(String usuario){
        setProperty("usuario", usuario);
    }
    public String getUsuario(){
        return getProperty("usuario");
    }
    public void setSenha(String senha){
        setProperty("senha", senha);
    }
    public String getSenha(){
        return  getProperty("senha");
    }
    public void setIdShopping(String idShopping){
        setProperty("idShopping", idShopping);
    }
    public String getIdShopping(){
        return getProperty("idShopping");
    }
    public String getIdDevice(){return  getProperty("registrationDevice");}
    public void setIdDevice(String idDevice){setProperty("registrationDevice",idDevice);}
    public void setIpDevice(String ipDevice){setProperty("ipDevice",ipDevice);}
    public String getIpDevice(){return  getProperty("ipDevice");}
    public void setImei(String imei){setProperty("imei",imei);}
    public void setAltitude(String altidude){setProperty("altitude",altidude);}
    public void setLongitude(String longitude){setProperty("longitude",longitude);}
    public void setLatitude(String latitude){setProperty("latitude",latitude);}
    public void setDescricao(String descr){setProperty("descricao",descr);}
    public void setGmt(String gmt){setProperty("gmt",gmt);}
    public void setDataDispositivo(String data){setProperty("hora_dispositivo",data);}
    public void setVelocidade(String vel){setProperty("velocidade",vel);}
    public void setPrecisao(String precisao){setProperty("precisao",precisao);}

    @Override
    public EntidadeLogin getResult() {

        EntidadeLogin entidadeLogin = new EntidadeLogin();
        Usuario u = new Usuario();
        Home home = new Home();
        Dispositivo dispositivo = new Dispositivo();
        SoapObject result=null;
        SoapObject raizp=null;
        try {
            result=getRetorno();
            //raizp = (SoapObject) result.getProperty("AutenticaUsuarioResult");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            SoapObject raiz = (SoapObject) result.getProperty("usuario");
            if(raiz.getPropertyCount()==1)
            {
                u.setIduser(0);
                u.setNomeresponsavel(raiz.getProperty("descricaoErro").toString());
            }
            else
            {
                if(raiz.hasProperty("idUser"))
                     u.setIduser(!raiz.getProperty("idUser").toString().equals("anyType{}") ? Integer.parseInt(raiz.getProperty("idUser").toString()) : null);
                if(raiz.hasProperty("empresa"))
                     u.setEmpresa(!raiz.getProperty("empresa").toString().equals("anyType{}") ? raiz.getProperty("empresa").toString() : "");
                if(raiz.hasProperty("luc"))
                     u.setLuc(!raiz.getProperty("luc").toString().equals("anyType{}") ? raiz.getProperty("luc").toString() : "");
                if(raiz.hasProperty("contrato"))
                u.setContrato(!raiz.getProperty("contrato").toString().equals("anyType{}") ? raiz.getProperty("contrato").toString() : "");
                if(raiz.hasProperty("login"))
                u.setLogin(!raiz.getProperty("login").toString().equals("anyType{}") ? raiz.getProperty("login").toString() : "");
                if(raiz.hasProperty("senha"))
                u.setSenha(!raiz.getProperty("senha").toString().equals("anyType{}") ? raiz.getProperty("senha").toString() : "");
                //u.setAcesso(Integer.parseInt(result.getAttribute("acesso").toString()));
                if(raiz.hasProperty("dataacesso"))
                u.setDataacesso(!raiz.getProperty("dataacesso").toString().equals("anyType{}")?DateHelper.toDate(raiz.getProperty("dataacesso").toString()):null);
                if(raiz.hasProperty("horaacesso"))
                u.setHoraacesso(!raiz.getProperty("horaacesso").toString().equals("anyType{}")?raiz.getProperty("horaacesso").toString():"");
                //u.setAtivo_inativo(Integer.parseInt(result.getAttribute("ativo_inativo").toString()));
                if(raiz.hasProperty("nomeUser"))
                u.setNomeresponsavel(!raiz.getProperty("nomeUser").toString().equals("anyType{}")?raiz.getProperty("nomeUser").toString():"");
                if(raiz.hasProperty("telefone"))
                u.setTelefone1(!raiz.getProperty("telefone").toString().equals("anyType{}")?raiz.getProperty("telefone").toString():"");
                if(raiz.hasProperty("tipoUser"))
                u.setTipo(!raiz.getProperty("tipoUser").toString().equals("anyType{}")?Integer.parseInt(raiz.getProperty("tipoUser").toString()):0);
                //u.setTelefone2(result.getAttribute("telefone2").toString());
                if(raiz.hasProperty("emailUser"))
                u.setEmail(!raiz.getProperty("emailUser").toString().equals("anyType{}")?raiz.getProperty("emailUser").toString():"");
                if(raiz.hasProperty("emailUser2"))
                u.setEmail2(!raiz.getProperty("emailUser2").toString().equals("anyType{}")?raiz.getProperty("emailUser2").toString():"");
                if(raiz.hasProperty("rsocial"))
                u.setRsocial(!raiz.getProperty("rsocial").toString().equals("anyType{}")?raiz.getProperty("rsocial").toString():"");
                if(raiz.hasProperty("piso"))
                u.setPiso(!raiz.getProperty("piso").toString().equals("anyType{}")?raiz.getProperty("piso").toString():"");
                if(raiz.hasProperty("depto"))
                u.setDepto(!raiz.getProperty("depto").toString().equals("anyType{}")?raiz.getProperty("depto").toString():"");
                if(raiz.hasProperty("primeiroacesso"))
                u.setPrimeiroacesso(!raiz.getProperty("primeiroacesso").toString().equals("anyType{}")?Integer.parseInt(raiz.getProperty("primeiroacesso").toString()):0);
                //u.setId_cracha_tipo(Integer.parseInt(result.getAttribute("idcrachatipo").toString()));
                //u.setCodpessoa(Integer.parseInt(result.getAttribute("codpessoa").toString()));
                if(raiz.hasProperty("imgShopp"))
                u.setImglogoshop(!raiz.getProperty("imgShopp").toString().equals("anyType{}")?raiz.getProperty("imgShopp").toString():null);

                u.setIdshop(Integer.parseInt(getIdShopping()));
            }
          entidadeLogin.setUsuario(u);

        }catch (Exception e)
        {
            e.printStackTrace();
            u = null;
        }
        //HOME-------------------------------------------------
        try{
            if(u.getIduser()>0) {
                SoapObject raiz = (SoapObject) result.getProperty("home");
                home.setIdshop(Integer.parseInt(getIdShopping()));
                home.setIduser(Integer.parseInt(raiz.getProperty("iduser").toString()));
                //home.setImagembanner(raiz.getProperty("imagembanner").toString());
                home.setCircularnaolida(Integer.parseInt(raiz.getProperty("circularnaolida").toString()));
                home.setAutorizacao(Integer.parseInt(raiz.getProperty("autorizacao").toString()));
                home.setEmexecucao(Integer.parseInt(raiz.getProperty("emexecucao").toString()));
                home.setNaoautorizado(Integer.parseInt(raiz.getProperty("naoautorizado").toString()));
                home.setAguardandoaprovacao(Integer.parseInt(raiz.getProperty("aguardandoaprovacao").toString()));
                home.setQtdfuncionario(Integer.parseInt(raiz.getProperty("qtdfuncionario").toString()));

            }
            else
            {
               home=null;
            }
            entidadeLogin.setHome(home);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //DEVICE --------------------------------------------
        try{
            if(u.getIduser()>0) {
                SoapObject raiz = (SoapObject) result.getProperty("device");
                if (!raiz.getProperty("idDevice").toString().equals("0")) {
                    dispositivo.setIddispositivo(Integer.parseInt(raiz.getProperty("idDevice").toString()));
                    dispositivo.setIdshop(Integer.parseInt(raiz.getProperty("idShopp").toString()));
                    dispositivo.setIduser(Integer.parseInt(raiz.getProperty("idUser").toString()));
                    dispositivo.setIddeviceregistration(raiz.getProperty("registrationDevice").toString());
                } else {
                    dispositivo = null;
                }
            }
            else
            {
                dispositivo=null;
            }
            entidadeLogin.setDispositivo(dispositivo);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //destaques
        try
        {
            SoapObject raiz = (SoapObject) result.getProperty("carrossel");
            SoapObject aux;
            int count = raiz.getPropertyCount();
            home.setDestaque(new ArrayList<Destaque>());
            Destaque destaque;
            for (int i=0;i<count;i++)
            {
                destaque = new Destaque();
                aux=(SoapObject) raiz.getProperty(i);
                destaque.setId(Integer.parseInt(aux.getProperty("id").toString()));
                destaque.setUrl(aux.getProperty("url").toString());
                if(aux.hasProperty("arquivo"))
                destaque.setLink(!aux.getProperty("arquivo").toString().equals("anyType{}")?aux.getProperty("arquivo").toString():"");
                if(aux.hasProperty("ordem"))
                destaque.setOrdem(Integer.parseInt(aux.getProperty("ordem").toString()));
                if(aux.hasProperty("tipo"))
                destaque.setTipo(Integer.parseInt(aux.getProperty("tipo").toString()));
                home.getDestaque().add(destaque);
            }
            AppHelper.setDestaque(home.getDestaque());
            entidadeLogin.setHome(home);
            entidadeLogin.getCalendarioList();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //calendario
        try
        {
            if(result.hasProperty("calendario"))
            {
                SoapObject soapcalendarios = (SoapObject) result.getProperty("calendario");
                SoapObject aux1;
                int count=soapcalendarios.getPropertyCount();
                List<Calendario> lcalendario = new ArrayList<>();
                Calendario c;
                for(int i=0;i<count;i++)
                {
                    aux1= (SoapObject) soapcalendarios.getProperty(i);
                    c=new Calendario();
                    c.setDutil(Integer.parseInt(aux1.getProperty("dutil").toString()));
                    c.setDdata(DateHelper.toDate(aux1.getProperty("ddata").toString()));
                    c.setFeriado(Integer.parseInt(aux1.getProperty("feriado").toString()));
                    c.setIdshop(Integer.parseInt(getIdShopping()));
                    lcalendario.add(c);

                }
                entidadeLogin.setCalendarioList(lcalendario);
            }



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //regras
        try
        {
            if(result.hasProperty("osRegra"))
            {
                SoapObject soapregra = (SoapObject) result.getProperty("osRegra");
                SoapObject aux1;
                int count=soapregra.getPropertyCount();
                List<RegraOrdemServico> lregra = new ArrayList<>();
                RegraOrdemServico ros;

                for(int i=0;i<count;i++)
                {
                    aux1=(SoapObject) soapregra.getProperty(i);
                    ros=new RegraOrdemServico();
                    ros.setIdosregra(Integer.parseInt(aux1.getProperty("id_ordemservico_regra").toString()));
                    ros.setDia_semana(Integer.parseInt(aux1.getProperty("dia_semana").toString()));
                    ros.setPermissao_dia(Integer.parseInt(aux1.getProperty("permissao_dia").toString()));
                    ros.setHora_limite(aux1.getProperty("hora_limite").toString());
                    ros.setSoma_dia_hora_ate_limite(Integer.parseInt(aux1.getProperty("soma_dia_hora_ate_limite").toString()));
                    ros.setSoma_dia_hora_apos_limite(Integer.parseInt(aux1.getProperty("soma_dia_hora_apos_limite").toString()));
                    ros.setHorario_ate_limite(aux1.getProperty("horario_ate_limite").toString());
                    ros.setHorario_apos_limite(aux1.getProperty("horario_apos_limite").toString());
                    ros.setHorario_dia_posterior(aux1.getProperty("horario_dia_posterior").toString());
                    ros.setIdshop(Integer.parseInt(getIdShopping()));
                    lregra.add(ros);
                }
                entidadeLogin.setRegraOrdemServicoList(lregra);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //setores
        try
        {
            if(result.hasProperty("listsetor"))
            {
                SoapObject soapsetores = (SoapObject) result.getProperty("listsetor");
                //soapsetores = (SoapObject) soapsetores.getProperty("setorCs");
                SoapObject aux1;
                SoapObject aux2;
                SoapObject aux3;
                int count=soapsetores.getPropertyCount();
                List<OrdemServicoSetor> lsetor = new ArrayList<>();
                OrdemServicoSetor oss;
                TipoServico ts;
                for(int i=0;i<count;i++)
                {
                    aux1 = new SoapObject();
                    aux1 = (SoapObject) soapsetores.getProperty(i);
                    oss= new OrdemServicoSetor();
                    oss.setTipoServicos(new ArrayList<TipoServico>());
                    oss.setIdordemservicosetor(Integer.parseInt(aux1.getProperty("idsetor").toString()));
                    oss.setTitulo(aux1.getProperty("descricao").toString());
                    oss.setAtivo(1);
                    oss.setIdshop(Integer.parseInt(getIdShopping()));
                    aux2 =(SoapObject) aux1.getProperty("tipoServico");

                    int count1=aux2.getPropertyCount();
                    for (int j=0;j<count1;j++)
                    {
                        ts= new TipoServico();
                        aux3=(SoapObject) aux2.getProperty(j);
                        ts.setIdtipo(Integer.parseInt(aux3.getProperty("idtipo").toString()));
                        ts.setIdshop(Integer.parseInt(getIdShopping()));
                        ts.setDescricao(aux3.getProperty("descricao").toString());
                        ts.setIddepto(Integer.parseInt(aux3.getProperty("id_depto").toString()));
                        ts.setIdordemservicosetor(Integer.parseInt(aux3.getProperty("id_ordemservico_setor").toString()));
                        ts.setObs(aux3.getProperty("obs").toString());
                        ts.setObrigatorioobs(Integer.parseInt(aux3.getProperty("obrigatorio_obs").toString()));
                        ts.setObrigatorioanexo(Integer.parseInt(aux3.getProperty("obrigatorio_anexo").toString()));
                        ts.setAtivo(Integer.parseInt(aux3.getProperty("ativo").toString()));
                        ts.setForafuncionamento(Integer.parseInt(aux3.getProperty("fora_funcionamento").toString()));
                        oss.getTipoServicos().add(ts);

                    }
                    lsetor.add(oss);
                }
                entidadeLogin.setOrdemServicoSetorList(lsetor);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //campos obrigatórios funcionário
        try
        {
            if(result.hasProperty("formularioCadFuncionario"))
            {
                SoapObject soapfnc = (SoapObject) result.getProperty("formularioCadFuncionario");
                int count =soapfnc.getPropertyCount();
                CamposObrigatoriosFnc cof;
                List<CamposObrigatoriosFnc> listcof=new ArrayList<>();
                for(int i=0;i<count;i++)
                {
                    SoapObject aux=(SoapObject)soapfnc.getProperty(i);
                    cof=new CamposObrigatoriosFnc();
                    cof.setId(Integer.parseInt(aux.getProperty("id_config_cad_funcionario").toString()));
                    cof.setCampo(aux.getProperty("campo").toString());
                    cof.setObrigatorio(Integer.parseInt(aux.getProperty("obrigatorio").toString()));
                    //cof.setIduser(AppHelper.getUsuario().getIduser());
                    //cof.setIdshop(AppHelper.getIdShop());
                    listcof.add(cof);
                }

             entidadeLogin.setCamposObrigatoriosFncList(listcof);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //UsuariosDestino
        try
        {
            if(result.hasProperty("solicitantesdestino"))
            {
                SoapObject soapuserdestino =(SoapObject) result.getProperty("solicitantesdestino");
                int countd=soapuserdestino.getPropertyCount();
                SoapObject aux1;
                Usuario uos;
                List<Usuario> luser=new ArrayList<>();
                for(int r=0;r<countd;r++)
                {
                    uos = new Usuario();
                    aux1=(SoapObject) soapuserdestino.getProperty(r);
                    uos.setIduser(Integer.parseInt(aux1.getProperty("iduser").toString()));
                    uos.setNomeresponsavel(aux1.getProperty("nome").toString());
                    uos.setLogin(aux1.getProperty("login").toString());
                    uos.setEmpresa(!aux1.getProperty("empresa").toString().equals("anyType{}") ? aux1.getProperty("empresa").toString() : "");
                    uos.setLuc(!aux1.getProperty("luc").toString().equals("anyType{}") ? aux1.getProperty("luc").toString() : "");
                    uos.setTipo(Integer.parseInt(aux1.getProperty("tipo").toString()));
                    uos.setIdshop(Integer.parseInt(getIdShopping()));
                    luser.add(uos);
                }
                entidadeLogin.setUsuarioList(luser);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return entidadeLogin;
    }
}
