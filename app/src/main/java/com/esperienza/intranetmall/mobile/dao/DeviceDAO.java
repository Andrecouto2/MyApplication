package com.esperienza.intranetmall.mobile.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.esperienza.intranetmall.mobile.entidade.Dispositivo;


/**
 * Created by ThinkPad on 28/12/2015.
 */
public class DeviceDAO extends DAO {

    private final String DQL_GET_DEVICEUSERSHOP= "SELECT * FROM TBDEVICE WHERE iduser = ? and idshop = ?";
    private final String DQL_GET_DEVICE= "SELECT * FROM TBDEVICE WHERE iddispositivo = ?";

    public static DeviceDAO getNewInstance(){return new DeviceDAO();}
    public Dispositivo getDispositivo(Integer iduser, Integer idshop){
        Dispositivo d = null;
        Cursor c = getDb().rawQuery(DQL_GET_DEVICEUSERSHOP, new String[]{iduser.toString(),idshop.toString()});
        try {
            if (c.getCount() > 0) {
                c.moveToNext();
                d = new Dispositivo();
                d.setIddispositivo(c.getInt(c.getColumnIndex("iddispositivo")));
                d.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                d.setIduser(c.getInt(c.getColumnIndex("iduser")));
                d.setIddeviceregistration(c.getString(c.getColumnIndex("iddeviceregistration")));

            }
        }finally {
            c.close();
        }

        return d;
    }
    public Dispositivo getDispositivo(Integer iddispositivo){
        Dispositivo d = null;
        Cursor c = getDb().rawQuery(DQL_GET_DEVICE, new String[]{iddispositivo.toString()});
        try {
            if (c.getCount() > 0) {
                c.moveToNext();
                d = new Dispositivo();
                d.setIddispositivo(c.getInt(c.getColumnIndex("iddispositivo")));
                d.setIdshop(c.getInt(c.getColumnIndex("idshop")));
                d.setIduser(c.getInt(c.getColumnIndex("iduser")));
                d.setIddeviceregistration(c.getString(c.getColumnIndex("iddeviceregistration")));

            }
        }finally {
            c.close();
        }

        return d;
    }
    public void save(Dispositivo dispositivo) {
        Dispositivo old = getDispositivo(dispositivo.getIduser(),dispositivo.getIdshop());
        ContentValues vl = new ContentValues();
        vl.put("iddispositivo", dispositivo.getIddispositivo());
        vl.put("idshop", dispositivo.getIdshop());
        vl.put("iduser", dispositivo.getIduser());
        vl.put("iddeviceregistration", dispositivo.getIddeviceregistration());


        if(old == null){
            //vl.put("iddispositvo", dispositivo.getIddispositivo());
            getDb().insert("TBDEVICE", "", vl);
        }else{
            getDb().update("TBDEVICE", vl, "iddispositivo = ?", new String[]{String.valueOf(dispositivo.getIddispositivo())});
        }

    }
    public void removeAll(){
        getDb().delete("TBDEVICE", "", null);
    }
}
