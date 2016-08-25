package com.esperienza.intranetmall.mobile.webservices;

import com.esperienza.intranetmall.mobile.entidade.AprovadoresOS;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.Calendario;
import com.esperienza.intranetmall.mobile.entidade.ObservacaoOS;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.entidade.OrdemServicoSetor;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.entidade.RegraOrdemServico;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ThinkPad on 18/07/2016.
 */
public class BuscaOsAvancadaWs extends WebServicesXML {
    @Override
    public void onCreate() {
        setMethodName("Lista_Os_Filtro");
        addProperty("idShopping");
        addProperty("idUser");
        addProperty("datainicial");
        addProperty("datafinal");
    }
    public void setIdShop(String idShop){
        setProperty("idShopping", idShop);
    }
    public void setIdUser(String idUser){
        setProperty("idUser",idUser);
    }
    public void setDataInicial(String dataInicial){setProperty("datainicial",dataInicial);}
    public void setDataFinal(String dataFinal){setProperty("datafinal",dataFinal);}

    @Override
    public List<OrdemServico> getResult() {
        SoapObject result = null;
        SoapObject soaplistos = null;
        List<OrdemServico> l = new ArrayList<>();
        ArrayList<ObservacaoOS> los;
        ArrayList<ArquivoOS> larqos;
        ArrayList<Usuario> luser = null;
        ArrayList<PessoasAutorizadasOS> lpa;
        ArrayList<AprovadoresOS> lapr;


        try
        {
            result =  getRetorno();
            soaplistos = (SoapObject) result.getProperty("listas");

            OrdemServico ordemServico;
            int count = soaplistos.getPropertyCount();
            int countobs;
            int countpa;
            int countarq;
            int countaut;
            SoapObject aux;
            SoapObject aux1;

            ObservacaoOS obs;
            ArquivoOS arqos;
            PessoasAutorizadasOS paos;
            Usuario uos;
            AprovadoresOS apr;
            for(int i=0;i<count;i++)
            {
                ordemServico = new OrdemServico();
                los = new ArrayList<>();
                larqos = new ArrayList<>();
                luser =new ArrayList<>();
                lpa = new ArrayList<>();
                lapr = new ArrayList<>();
                ordemServico.setObservacao(los);
                ordemServico.setArquivos(larqos);
                ordemServico.setPessoas(lpa);
                ordemServico.setAprovadores(lapr);


                SoapObject raiz = (SoapObject) soaplistos.getProperty(i);
                ordemServico.setId_os(Integer.parseInt(raiz.getProperty("id").toString()));
                ordemServico.setIduser(Integer.parseInt(raiz.getProperty("iduser").toString()));
                ordemServico.setIdtipo(Integer.parseInt(raiz.getProperty("idtipo").toString()));
                ordemServico.setDatacad(DateHelper.toDate(raiz.getProperty("datacad").toString()));
                ordemServico.setHoracad(raiz.getProperty("horacad").toString());
                ordemServico.setDatainicio(DateHelper.toDate(raiz.getProperty("datainicio").toString()));
                ordemServico.setHorainicio(raiz.getProperty("horainicio").toString());
                ordemServico.setDatafim(DateHelper.toDate(raiz.getProperty("datafim").toString()));
                ordemServico.setHorafim(raiz.getPrimitiveProperty("horafim").toString());
                ordemServico.setStatus(Integer.parseInt(raiz.getProperty("idstatus").toString()));
                ordemServico.setNomelojista(raiz.getProperty("nomelojista").toString());
                ordemServico.setNomesolicita(raiz.getProperty("solicitante").toString());
                ordemServico.setTelefone(!raiz.getProperty("telefone").toString().equals("anyType{}")?raiz.getProperty("telefone").toString():"");
                ordemServico.setEmail(raiz.getProperty("email").toString());
                ordemServico.setDescricao(raiz.getProperty("descricao").toString());
                ordemServico.setInicial(DateHelper.toDate(raiz.getProperty("inicial").toString()));
                ordemServico.setTermino(DateHelper.toDate(raiz.getProperty("final").toString()));
                ordemServico.setIddestino(Integer.parseInt(raiz.getProperty("iddestino").toString()));
                ordemServico.setTelefone(raiz.getProperty("telefone").toString());
                ordemServico.setObservacoes(Integer.parseInt(raiz.getProperty("observacoes").toString()));

                ordemServico.setIdusermobile(AppHelper.getUsuario().getIduser());
                ordemServico.setIdshop(AppHelper.getIdShop());
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateHelper.toDate(raiz.getProperty("datafim").toString()));
                    String mes = String.valueOf(calendar.get(Calendar.MONTH)+1);
                    String ano = String.valueOf(calendar.get(Calendar.YEAR));
                    String dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

                    if (mes.length() != 2) {
                        mes = "0" + mes;
                    }
                    if(dia.length() != 2){
                        dia = "0" + dia;
                    }

                    ordemServico.setAnomesdia(ano+mes+dia);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Usuario Destino
                aux=(SoapObject) raiz.getProperty("usuariodestino");
                ordemServico.setUserdestino(new Usuario());
                ordemServico.getUserdestino().setIduser(Integer.parseInt(aux.getProperty("idUser").toString()));
                ordemServico.getUserdestino().setNomeresponsavel(aux.getProperty("nomeUser").toString());
                ordemServico.getUserdestino().setEmpresa(aux.getProperty("empresa").toString());
                ordemServico.getUserdestino().setLuc(aux.getProperty("luc").toString());
                ordemServico.getUserdestino().setEmail(aux.getProperty("emailUser").toString());
                ordemServico.getUserdestino().setTelefone1(aux.getProperty("telefone").toString());
                ordemServico.getUserdestino().setIdshop(AppHelper.getIdShop());


                //Observações
                aux= (SoapObject) raiz.getProperty("listaobs");
                countobs=aux.getPropertyCount();
                for(int j=0;j<countobs;j++)
                {
                    obs = new ObservacaoOS();
                    aux1= (SoapObject) aux.getProperty(j);
                    obs.setIdcomentario(Integer.parseInt(aux1.getProperty("idcomentario").toString()));
                    obs.setIduser(Integer.parseInt(aux1.getProperty("iduser").toString()));
                    obs.setIdos(Integer.parseInt(aux1.getProperty("idos").toString()));
                    obs.setDatacad(DateHelper.toDate((aux1.getProperty("datacad").toString())));
                    obs.setHoracad(aux1.getProperty("horacad").toString());
                    obs.setObservacoes(aux1.getProperty("observacoes").toString());
                    obs.setIdusermobile(AppHelper.getUsuario().getIduser());
                    obs.setIdshop(AppHelper.getIdShop());

                    SoapObject aux2= (SoapObject) aux1.getProperty("usuario");
                    obs.setUsuario(new Usuario());
                    obs.getUsuario().setIduser(Integer.parseInt(aux2.getProperty("idUser").toString()));
                    obs.getUsuario().setNomeresponsavel(aux2.getProperty("nomeUser").toString());
                    obs.getUsuario().setEmpresa(aux2.getProperty("empresa").toString());
                    obs.getUsuario().setEmail(aux2.getProperty("emailUser").toString());
                    obs.getUsuario().setIdshop(AppHelper.getIdShop());
                    ordemServico.getObservacao().add(obs);


                }
                //Arquivos
                aux = (SoapObject) raiz.getProperty("listaUrlFile");
                countarq = aux.getPropertyCount();
                for(int z=0;z<countarq;z++)
                {
                    arqos = new ArquivoOS();
                    aux1=(SoapObject) aux.getProperty(z);
                    arqos.setIdarquivo(Integer.parseInt(aux1.getProperty("idarquivo").toString()));
                    arqos.setIduser(Integer.parseInt(aux1.getProperty("iduser").toString()));
                    arqos.setIdos(Integer.parseInt(aux1.getProperty("idos").toString()));
                    arqos.setUrlarquivo(aux1.getProperty("url").toString());
                    arqos.setCodgerador(aux1.getProperty("codgerador").toString());
                    arqos.setExtensao(aux1.getProperty("extensao").toString());
                    arqos.setIdusermobile(AppHelper.getUsuario().getIduser());
                    arqos.setIdshop(AppHelper.getIdShop());
                    ordemServico.getArquivos().add(arqos);
                }
                //Pessoas Autorizadas
                aux = (SoapObject) raiz.getProperty("listapessoasautorizadas");
                countpa = aux.getPropertyCount();
                for(int y=0;y<countpa;y++)
                {
                    paos = new PessoasAutorizadasOS();
                    aux1=(SoapObject) aux.getProperty(y);
                    paos.setId(Integer.parseInt(aux1.getProperty("id").toString()));
                    paos.setIdos(Integer.parseInt(aux1.getProperty("idos").toString()));
                    paos.setNome(aux1.getProperty("nome").toString());
                    paos.setRg(aux1.getProperty("rg").toString());
                    paos.setEmpresa(aux1.getProperty("empresa").toString());
                    paos.setIduser(AppHelper.getUsuario().getIduser());
                    paos.setIdshop(AppHelper.getIdShop());
                    ordemServico.getPessoas().add(paos);
                }
                //Aprovadores
                aux = (SoapObject) raiz.getProperty("listaaprovadores");
                countaut = aux.getPropertyCount();
                for(int w=0;w<countaut;w++)
                {
                    apr = new AprovadoresOS();
                    aux1=(SoapObject) aux.getProperty(w);
                    apr.setIduser(Integer.parseInt(aux1.getProperty("iduser").toString()));
                    apr.setIdos(ordemServico.getId_os());
                    apr.setAcao(Integer.parseInt(aux1.getProperty("acao").toString()));
                    apr.setOrdem(Integer.parseInt(aux1.getProperty("ordem").toString()));
                    apr.setAlcadas(Integer.parseInt(aux1.getProperty("alcadas").toString()));
                    apr.setNome(aux1.getProperty("nome").toString());
                    apr.setEmail(aux1.getProperty("email").toString());
                    apr.setSuplente(Integer.parseInt(aux1.getProperty("suplente").toString()));
                    apr.setIdusermobile(AppHelper.getUsuario().getIduser());
                    apr.setIdshop(AppHelper.getIdShop());

                    ordemServico.getAprovadores().add(apr);
                }



                l.add(ordemServico);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return l;
    }
}
