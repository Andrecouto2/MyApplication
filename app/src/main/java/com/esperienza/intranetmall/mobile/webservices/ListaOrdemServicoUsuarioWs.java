package com.esperienza.intranetmall.mobile.webservices;

import com.esperienza.intranetmall.mobile.entidade.AprovadoresOS;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.Calendario;
import com.esperienza.intranetmall.mobile.entidade.EntidadeRetornoOS;
import com.esperienza.intranetmall.mobile.entidade.ObservacaoOS;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.entidade.OrdemServicoSetor;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.entidade.RegraOrdemServico;
import com.esperienza.intranetmall.mobile.entidade.TipoServico;
import com.esperienza.intranetmall.mobile.entidade.Usuario;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ThinkPad on 12/01/2016.
 */
public class ListaOrdemServicoUsuarioWs extends WebServicesXML {

    @Override
    public void onCreate() {
        setMethodName("Lista_Os");
        addProperty("idShopping");
        addProperty("idUser");
        addProperty("tipo");
    }
    public void setIdShop(String idShop)
    {
        setProperty("idShopping", idShop);
    }

    public void setIdUser(String idUser)
    {
        setProperty("idUser", idUser);
    }

    public void setTipo(String tipo)
    {
        setProperty("tipo",tipo);
    }

    @Override
    public EntidadeRetornoOS getResult()
    {
        EntidadeRetornoOS retornoOS = new EntidadeRetornoOS();
        SoapObject result = null;
        SoapObject soaplistos = null;
        SoapObject soapcalendarios = null;
        SoapObject soapsetores = null;
        SoapObject soapregra = null;
        SoapObject soapuserdestino=null;
        List<OrdemServico> l = new ArrayList<>();
        ArrayList<ObservacaoOS> los;
        ArrayList<ArquivoOS> larqos;
        ArrayList<Usuario> luser = null;
        ArrayList<PessoasAutorizadasOS> lpa;
        ArrayList<AprovadoresOS> lapr;
        ArrayList<Calendario> lcalendario = null;
        ArrayList<RegraOrdemServico> lregra = null;
        ArrayList<OrdemServicoSetor> lsetor = null;

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
        //calendario
        /*try
        {
            soapcalendarios = (SoapObject) result.getProperty("calendarios");
            SoapObject aux1;
            int count=soapcalendarios.getPropertyCount();
            lcalendario = new ArrayList<>();
            Calendario c;
            for(int i=0;i<count;i++)
            {
                aux1= (SoapObject) soapcalendarios.getProperty(i);
                c=new Calendario();
                c.setDutil(Integer.parseInt(aux1.getProperty("dutil").toString()));
                c.setDdata(DateHelper.toDate(aux1.getProperty("ddata").toString()));
                c.setFeriado(Integer.parseInt(aux1.getProperty("feriado").toString()));
                lcalendario.add(c);

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //regras
        try
        {
            soapregra = (SoapObject) result.getProperty("regra");
            SoapObject aux1;
            int count=soapregra.getPropertyCount();
            lregra = new ArrayList<>();
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

                lregra.add(ros);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //setores
        try
        {
            soapsetores = (SoapObject) result.getProperty("setores");
            soapsetores = (SoapObject) soapsetores.getProperty("Setores");
            SoapObject aux1;
            SoapObject aux2;
            SoapObject aux3;
            int count=soapsetores.getPropertyCount();
            lsetor = new ArrayList<>();
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
                aux2 =(SoapObject) aux1.getProperty("tipoServico");

                int count1=aux2.getPropertyCount();
                for (int j=0;j<count1;j++)
                {
                 ts= new TipoServico();
                    aux3=(SoapObject) aux2.getProperty(j);
                    ts.setIdtipo(Integer.parseInt(aux3.getProperty("idtipo").toString()));
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
        //UsuariosDestino
        /*try
        {
            soapuserdestino =(SoapObject) result.getProperty("solicitantesdestino");
            int countd=soapuserdestino.getPropertyCount();
            SoapObject aux1;
            Usuario uos;
            for(int r=0;r<countd;r++)
            {
                uos = new Usuario();
                aux1=(SoapObject) soapuserdestino.getProperty(r);
                uos.setIduser(Integer.parseInt(aux1.getProperty("iduser").toString()));
                uos.setNomeresponsavel(aux1.getProperty("nome").toString());
                uos.setLogin(aux1.getProperty("login").toString());
                uos.setEmpresa(!aux1.getProperty("empresa").toString().equals("anyType{}") ? aux1.getProperty("empresa").toString():"");
                uos.setLuc(!aux1.getProperty("luc").toString().equals("anyType{}") ? aux1.getProperty("luc").toString() : "");
                uos.setTipo(Integer.parseInt(aux1.getProperty("tipo").toString()));
                luser.add(uos);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        retornoOS.setListaordemdeservico(l);
        //retornoOS.setCalendarios(lcalendario);
        //retornoOS.setRegras(lregra);
        //retornoOS.setSetores(lsetor);
        //retornoOS.setListausuariodestino(luser);
        return retornoOS;
    }

}
