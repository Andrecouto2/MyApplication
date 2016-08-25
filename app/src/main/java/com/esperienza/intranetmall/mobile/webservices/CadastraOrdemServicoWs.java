package com.esperienza.intranetmall.mobile.webservices;

import com.esperienza.intranetmall.mobile.entidade.AprovadoresOS;
import com.esperienza.intranetmall.mobile.entidade.ArquivoOS;
import com.esperienza.intranetmall.mobile.entidade.OrdemServico;
import com.esperienza.intranetmall.mobile.entidade.PessoasAutorizadasOS;
import com.esperienza.intranetmall.mobile.util.AppHelper;
import com.esperienza.intranetmall.mobile.util.DateHelper;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 08/03/2016.
 */
public class CadastraOrdemServicoWs extends WebServicesXML {
    @Override
    public void onCreate() {
        setMethodName("GravaOrdem");
    }

    @Override
    public OrdemServico getResult() {

        OrdemServico os = new OrdemServico();
        ArrayList<PessoasAutorizadasOS> lpa=new ArrayList<>();
        ArrayList<ArquivoOS> larq = new ArrayList<>();
        ArrayList<AprovadoresOS> lapr = new ArrayList<>();
        os.setPessoas(lpa);
        os.setArquivos(larq);
        os.setAprovadores(lapr);

        try
        {
            SoapObject result = getRetorno();
            os.setId_os(Integer.parseInt(result.getProperty("id").toString()));
            os.setIduser(Integer.parseInt(result.getProperty("iduser").toString()));
            os.setIdtipo(Integer.parseInt(result.getProperty("idtipo").toString()));
            os.setDatacad(DateHelper.toDate(result.getProperty("datacad").toString()));
            os.setHoracad(result.getProperty("horacad").toString());
            os.setDatainicio(DateHelper.toDate(result.getProperty("datainicio").toString()));
            os.setHorainicio(result.getProperty("horainicio").toString());
            os.setDatafim(DateHelper.toDate(result.getProperty("datafim").toString()));
            os.setHorafim(result.getPrimitiveProperty("horafim").toString());
            os.setStatus(1);
            os.setNomelojista(result.getProperty("nomelojista").toString());
            os.setNomesolicita(result.getProperty("solicitante").toString());
            os.setTelefone(result.getProperty("telefone").toString());
            os.setEmail(result.getProperty("email").toString());
            os.setDescricao(result.getProperty("descricao").toString());
            os.setInicial(DateHelper.toDate(result.getProperty("inicial").toString()));
            os.setTermino(DateHelper.toDate(result.getProperty("final").toString()));
            os.setIddestino(Integer.parseInt(result.getProperty("iddestino").toString()));
            os.setTelefone(result.getProperty("telefone").toString());
            os.setIdusermobile(AppHelper.getUsuario().getIduser());
            os.setIdshop(AppHelper.getIdShop());
            os.setObservacoes(Integer.parseInt(result.getProperty("observacoes").toString()));//arrumar aki

            //os.setObservacoes(Integer.parseInt(result.getProperty("observacoes").toString()));

            //Arquivos
            SoapObject aux = (SoapObject) result.getProperty("listaUrlFile");
            int countarq = aux.getPropertyCount();
            ArquivoOS arqos;
            for(int z=0;z<countarq;z++)
            {
                arqos = new ArquivoOS();
                SoapObject aux1=(SoapObject) aux.getProperty(z);
                arqos.setIdarquivo(Integer.parseInt(aux1.getProperty("idarquivo").toString()));
                arqos.setIduser(Integer.parseInt(aux1.getProperty("iduser").toString()));
                arqos.setIdos(Integer.parseInt(aux1.getProperty("idos").toString()));
                arqos.setUrlarquivo(aux1.getProperty("url").toString());
                arqos.setCodgerador(aux1.getProperty("codgerador").toString());
                arqos.setExtensao(aux1.getProperty("extensao").toString());
                arqos.setIdusermobile(AppHelper.getUsuario().getIduser());
                arqos.setIdshop(AppHelper.getIdShop());
                os.getArquivos().add(arqos);
            }
            //Pessoas Autorizadas
            aux = (SoapObject) result.getProperty("listapessoasautorizadas");
            int countpa = aux.getPropertyCount();
            PessoasAutorizadasOS paos;
            for(int y=0;y<countpa;y++)
            {
                paos = new PessoasAutorizadasOS();
                SoapObject aux1=(SoapObject) aux.getProperty(y);
                paos.setId(Integer.parseInt(aux1.getProperty("id").toString()));
                paos.setIdos(Integer.parseInt(aux1.getProperty("idos").toString()));
                paos.setNome(aux1.getProperty("nome").toString());
                paos.setRg(aux1.getProperty("rg").toString());
                paos.setEmpresa(aux1.getProperty("empresa").toString());
                paos.setIdshop(AppHelper.getIdShop());
                paos.setIduser(AppHelper.getUsuario().getIduser());
                os.getPessoas().add(paos);
            }
            //Aprovadores
            aux = (SoapObject) result.getProperty("listaaprovadores");
            int countaut = aux.getPropertyCount();
            AprovadoresOS apr;
            for(int w=0;w<countaut;w++)
            {
                apr = new AprovadoresOS();
                SoapObject aux1=(SoapObject) aux.getProperty(w);
                apr.setIduser(Integer.parseInt(aux1.getProperty("iduser").toString()));
                apr.setIdos(os.getId_os());
                apr.setAcao(Integer.parseInt(aux1.getProperty("acao").toString()));
                apr.setOrdem(Integer.parseInt(aux1.getProperty("ordem").toString()));
                apr.setAlcadas(Integer.parseInt(aux1.getProperty("alcadas").toString()));
                apr.setNome(aux1.getProperty("nome").toString());
                apr.setEmail(aux1.getProperty("email").toString());
                apr.setIdshop(AppHelper.getIdShop());
                apr.setIdusermobile(AppHelper.getUsuario().getIduser());

                os.getAprovadores().add(apr);
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
            os=null;
        }
        return os;
    }
}
