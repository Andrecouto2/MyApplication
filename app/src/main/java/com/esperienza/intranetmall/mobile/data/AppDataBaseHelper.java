package com.esperienza.intranetmall.mobile.data;

import com.esperienza.intranetmall.mobile.util.AppHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class AppDataBaseHelper extends SQLiteOpenHelper implements AppDataBaseHelperInteface{
	private final static String DB_NOME = "coletor.db";
	private final static int DB_VERSION = 4;
	protected static SQLiteDatabase dbInstance;
	private static AppDataBaseHelper instance;

	public AppDataBaseHelper() {

		super(AppHelper.getApplicationContext(), DB_NOME, null, DB_VERSION);
	}
	public static synchronized AppDataBaseHelper getInstance()
	{
		if (instance == null)
			instance = new AppDataBaseHelper();

		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DDL_CREATE_TBUSUARIO);
		db.execSQL(DDL_CREATE_TBFUNCIONARIO);
		db.execSQL(DDL_CREATE_TBORDEMSERVICO);
		db.execSQL(DDL_CREATE_TBCIRCULAR);
        db.execSQL(DDL_CREATE_TBCLIENTE);
		db.execSQL(DDL_CREATE_TBOSREGRA);
		db.execSQL(DDL_CREATE_TBLEITORESCIRCULAR);
		db.execSQL(DDL_CREATE_TBDEVICE);
		db.execSQL(DDL_CREATE_TBHOME);
		db.execSQL(DDL_CREATE_TBPESSOASAUTORIZADASOS);
		db.execSQL(DDL_CREATE_TBOBSERVACAOOS);
		db.execSQL(DDL_CREATE_TBARQUIVOOS);
		db.execSQL(DDL_CREATE_TBAPROVADORES);
		db.execSQL(DDL_CREATE_TBSETOROS);
		db.execSQL(DDL_CREATE_TBTIPOSERVICO);
		db.execSQL(DDL_CREATE_TBCALENDARIO);
		db.execSQL(DDL_CREATE_TBCONFIGHORARIOS);
		db.execSQL(DDL_CREATE_TBCONFIG_FNC);
		/*db.execSQL("insert into TBOSREGRA values (1,0,2,null,null,null,null,null,'22:00')");
		db.execSQL("insert into TBOSREGRA values (2,1,2,null,null,null,null,null,'22:00')");
		db.execSQL("insert into TBOSREGRA values (3,2,0,'16:45',0,'19:30',2,'20:00',null)");
		db.execSQL("insert into TBOSREGRA values (4,3,0,'16:30',0,'22:00',2,'13:00',null)");
		db.execSQL("insert into TBOSREGRA values (5,4,0,'15:00',0,'22:00',1,'13:00',null)");
		db.execSQL("insert into TBOSREGRA values (6,5,0,'14:00',0,'22:00',1,'13:00',null)");
		db.execSQL("insert into TBOSREGRA values (7,6,0,'15:00',0,'22:00',1,'13:00',null)");
		db.execSQL("insert into TBOSREGRA values (8,7,2,null,null,null,null,null,'22:00')");
		db.execSQL("insert into TBSETOROS values (1,1,'Servicos de Manutencao')");
		db.execSQL("insert into TBSETOROS values (2,1,'Solicitacoes de Acesso e Seguranca')");
		db.execSQL("insert into TBSETOROS values (3,1,'Marketing e Eventos')");
		db.execSQL("insert into TBSETOROS values (4,1,'Obras e Reformas')");
		db.execSQL("insert into TBSETOROS values (5,1,'Outros')");
		db.execSQL("insert into TBTIPOSERVICO values(1,0,5,1,1,0,0,'Outros servicos','Detalhe o servico que sera realizado')");
		db.execSQL("insert into TBTIPOSERVICO values(2,0,1,1,1,0,0,'Contagem de Estoque/Balanco','Detalhe o servico que sera realizado')");
		db.execSQL("insert into TBTIPOSERVICO values(4,0,2,1,0,1,0,'Fechamento para balanco','Detalhe o servico que sera realizado')");
		db.execSQL("insert into TBTIPOSERVICO values(5,0,3,0,1,1,1,'Adesivos em vitrine','Detalhe o servico que sera realizado')");
		db.execSQL("insert into TBTIPOSERVICO values(6,0,3,1,0,1,0,'Eventos','Detalhe o servico que sera realizado')");
		db.execSQL("insert into TBTIPOSERVICO values(7,0,3,1,0,0,0,'Vendas de ingressos','Detalhe o servico que sera realizado')");
		db.execSQL("insert into TBTIPOSERVICO values(10,0,1,1,0,1,0,'Manutencao de Equipamentos','Detalhe o servico que sera realizado')");
		db.execSQL("insert into TBTIPOSERVICO values(11,0,1,1,0,0,0,'Ar condicionado','Detalhe o servico que sera realizado')");
		db.execSQL("insert into TBTIPOSERVICO values(20,0,4,1,0,1,0,'Reformas de lojas','Detalhe o servico que sera realizado')");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-12',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-13',0,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-14',0,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-15',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-16',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-17',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-18',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-19',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-20',0,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-21',0,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-22',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-23',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-24',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-25',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-26',1,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-27',0,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-28',0,0)");
		db.execSQL("insert into TBCALENDARIO  values('2016-02-29',1,0)");
		db.execSQL("insert into TBCONFIGHORARIOS values(1,'10:00','22:00',0,30)");*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	

		/*if(oldVersion < 3){
			db.execSQL("ALTER TABLE REGRA ADD COLUMN min_valor_emprestimo decimal(10, 2)");
			db.execSQL("ALTER TABLE REGRA ADD COLUMN min_valor_parcela decimal(10, 2)");
		}*/




	}
	
	public static SQLiteDatabase factory(){
		Log.d("Teste","db entrou... ");

		AppDataBaseHelper a = new AppDataBaseHelper().getInstance();

		Log.d("Teste","db passou 1... "+dbInstance + " - "+AppHelper.getApplicationContext());
		if(dbInstance == null){
			Log.d("Teste","db passou 2... "+dbInstance + " - "+AppHelper.getApplicationContext());
			dbInstance = a.getWritableDatabase();
			Log.d("Teste","db passou 3... "+dbInstance + " - "+AppHelper.getApplicationContext());
			
		}else if( !dbInstance.isOpen()){
			Log.d("Teste","db passou 4... "+dbInstance + " - "+AppHelper.getApplicationContext());
		
			dbInstance = a.getWritableDatabase();
			Log.d("Teste","db passou 5... "+dbInstance + " - "+AppHelper.getApplicationContext());

		}
		return dbInstance;
	}
	@Override
	public synchronized void close() {
		if(dbInstance != null)
			dbInstance.close();
		super.close();
	}

}