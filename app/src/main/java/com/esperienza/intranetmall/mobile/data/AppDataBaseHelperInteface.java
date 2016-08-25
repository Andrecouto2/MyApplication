package com.esperienza.intranetmall.mobile.data;

public interface AppDataBaseHelperInteface {

	final String DDL_CREATE_TBUSUARIO =   "CREATE TABLE IF NOT EXISTS TBUSUARIO(             "+
										" iduser integer not null ,                          "+
			                            " idshop integer not null ,                          "+
										" luc         varchar(20) ,                          "+
										" contrato    varchar(6)  ,                          "+
										" login       varchar(10) ,                          "+
										" senha       varchar(6)  ,                          "+
										" acessso     integer      ,                         "+
										" dataacesso  varchar(20)  ,                         "+
										" horaacesso  varchar(10)  ,                         "+
										" ativo_inativo  integer   ,                         "+
										" nome        varchar(150) ,                         "+
										" telefone    varchar(30)  ,                         "+
										" telefone2   varchar(30)  ,                         "+
										" email       varchar(150) ,                         "+
										" email2      varchar(150) ,                         "+
										" rsocial     varchar(250) ,                         "+
										" piso        varchar(15)  ,                         "+
										" depto       varchar(15)  ,                         "+
			                            " imglogoshop varchar      ,                         "+
			                            " tipo           integer   ,                         "+
										" primeiroacesso  integer  ,                         "+
			                            " contadordeacesso    integer  ,                     "+
			                            " idcrachatipo    integer  ,                         "+
			                            " empresax     varchar     ,                         "+
			                            " codpessoa       integer  ,                         "+
			                            " primary key (iduser,idshop)                        "+
										")                                                   ";

	final String DDL_CREATE_TBORDEMSERVICO = "CREATE TABLE IF NOT EXISTS TBORDEMSERVICO(  "+
			"	id_os          INTEGER not null     ,                   "+
			"	iduser         INTEGER not null     ,                   "+
			"	idtipo         INTEGER not null     ,                   "+
			"	idusermobile   INTEGER not null     ,                   "+
			"   idshop integer not null             ,                   "+
			"	datacad     varchar(20)             ,                   "+
			"	horacad     VARCHAR(10)             ,                   "+
			"	datainicio  varchar(20)             ,                   "+
			"	horainicio  VARCHAR(10)             ,                   "+
			"	datafim        varchar(20)          ,                   "+
			"	horafim      VARCHAR(10)            ,                   "+
			"	status     INTEGER not null         ,                   "+
			"	nomelojista      VARCHAR(100)       ,                   "+
			"	nomesolicita  VARCHAR(100)          ,                   "+
			"	telefone        varchar(15)         ,                   "+
			"	email         varchar(150)          ,                   "+
			"	descricao       varchar(300)        ,                   "+
			"	inicial     varchar(20)             ,                   "+
			"	final      varchar(20)              ,                   "+
			"	iddestino  integer                  ,                   "+
			"	observacoes   INTEGER               ,                   "+
			"	email2      varchar(150)            ,                   "+
			"	idWiseit varchar(20)                ,                   "+
			"	codItemOcorrencia     varchar(20)   ,                   "+
			"	anomesdia varchar(10)               ,                   "+
			"   primary key (id_os,idusermobile,idshop))                ";

		final String DDL_CREATE_TBCIRCULAR   ="CREATE TABLE IF NOT EXISTS TBCIRCULAR(         "+
											"	idcircula  INTEGER not null,                  "+
											"	titulo  varchar(200) not null,                "+
											"	data_cad  date not null,                      "+
											"	acessos  integer not null,                    "+
			                            	"	nomearquivo varchar(20) not null,             "+
			                             	"	iduser integer,                               "+
				                            "	leitura integer not null,                     "+
				                            "	ano_mes varchar(8),                           "+
				                            "	idusermobile integer not null,                "+
				                            "	idshop integer not null,                      "+
				                            " primary key (idcircula,idusermobile,idshop)     "+
											")                                                ";





		final String DDL_CREATE_TBFUNCIONARIO =  "CREATE TABLE IF NOT EXISTS TBFUNCIONARIO(  "+
												" idfnc Integer not null  ,                  "+
												" nome_lojista varchar(20) ,                 "+
				                                " rg char(20) ,                              "+
												" cpf char(16),                              "+
												" datanasc  varchar(20),                     "+
												" status integer,                            "+
												" iduser integer not null ,                  "+
				                                " idshop integer not null ,                  "+
												" cargo_lojista varchar(200),                "+
												" datacad varchar(20),                       "+
												" codfoto varchar(40),                       "+
				                                " data_dem varchar(20),                      "+
				                                " data_adm varchar(20),                      "+
												" telefone varchar(20),                      "+
												" filiacao_pai varchar(200),                 "+
												" filiacao_mae varchar(200),                 "+
												" naturalidade varchar(200),                 "+
												" endereco varchar(500),                     "+
												" numero integer ,                           "+
												" complemento  varchar(30),                  "+
												" bairro varchar(200),                       "+
												" cep varchar(20),                           "+
												" cidade varchar(50),                        "+
												" uf varchar(2),                             "+
												" modelo varchar(50),                        "+
												" cor varchar(50),                           "+
												" placa  varchar(15),                        "+
												" marca varchar(30),                         "+
												" sexo varchar(9),                           "+
												" validade varchar(20),                      "+
				                                " id_cracha_tipo integer,                    "+
				                                " descricao_cracha varchar(30),              "+
				                                " statusEnvio integer,                       "+
				                                " imagem varchar,                            "+
				                                " primary key (idfnc,iduser,idshop)          "+
											    " )                                          ";
	// " FOREIGN KEY (iduser) REFERENCES TBUSUARIO(iduser),"+


	final String DDL_CREATE_TBCLIENTE =   "CREATE TABLE IF NOT EXISTS TBCLIENTE(             "+
			" idcliente integer not null primary key autoincrement, "+
			" nome                varchar(200) ,                    "+
			" img_cliente         varchar(500) ,                    "+
			" link_cliente        varchar(500)  ,                   "+
			" ativo_cracha        integer ,                         "+
			" ativo               integer ,                         "+
			" data_criacao        varchar(25)                       "+
			")                                                      ";

    final String DDL_CREATE_TBOSREGRA = "CREATE TABLE IF NOT EXISTS TBOSREGRA(             "+
			" idosregra integer not null            ,                "+
			" idshop integer not null               ,                "+
			" dia_semana                integer     ,                "+
			" permissao_dia             integer     ,                "+
			" hora_limite               varchar(7)  ,                "+
			" soma_dia_hora_ate_limite  integer     ,                "+
			" horario_ate_limite        varchar(7)  ,                "+
			" soma_dia_hora_apos_limite integer     ,                "+
			" horario_apos_limite       varchar(7)  ,                "+
			" horario_dia_posterior     varchar(7)  ,                "+
			" primary key(idosregra,idshop)                          "+
			")                                                       ";

	final String DDL_CREATE_TBLEITORESCIRCULAR = "CREATE TABLE IF NOT EXISTS TBLEITORESCIRCULAR(             "+
			" iduser                             integer not null     ,       "+
			" idcircular                         integer not null     ,       "+
			" empresa                            varchar(60) ,                "+
			" nome                               varchar(80) ,                "+
			" idusermobile integer not null                  ,                "+
			" idshop integer not null                        ,                "+
			" data_acessou                       varchar(20) ,                "+
			" primary key(iduser,idcircular,idusermobile,idshop)              "+
			")                                                                ";

	final String DDL_CREATE_TBDEVICE = "CREATE TABLE IF NOT EXISTS TBDEVICE(             "+
			" iddispositivo integer not null primary key autoincrement,        "+
			" iduser                             integer     ,                "+
			" idshop                             integer     ,                "+
			" iddeviceregistration               varchar                      "+
			")                                                                ";
	final String DDL_CREATE_TBHOME = "CREATE TABLE IF NOT EXISTS TBHOME(             "+
			" idhome integer not null primary key autoincrement,              "+
			" iduser                             integer     ,                "+
			" idshop                             integer     ,                "+
			" aguardandoaprovacao                integer     ,                "+
			" autorizacao                        integer     ,                "+
			" naoautorizado                      integer     ,                "+
			" emexecucao                         integer     ,                "+
			" circularnaolida                    integer     ,                "+
			" qtdfnc                             integer     ,                "+
			" imagembanner                       varchar                      "+
			")                                                                ";

	final String DDL_CREATE_TBPESSOASAUTORIZADASOS = "CREATE TABLE IF NOT EXISTS TBPESSOASAUTORIZADASOS(             "+
			" idpa integer not null                           ,                "+
			" idos  integer not null                          ,                "+
			" iduser integer not null                         ,                "+
			" idshop integer not null                         ,                "+
			" nome                               varchar(80)  ,                "+
			" rg                                 varchar(30)  ,                "+
			" empresa                            varchar(120) ,                "+
			" primary key (idpa,idos,iduser,idshop)                            "+
			")                                                                 ";

	final String DDL_CREATE_TBOBSERVACAOOS = "CREATE TABLE IF NOT EXISTS TBOBSERVACAOOS(             "+
			" idcomentario integer not null                           ,     "+
			" idos                               integer  not null    ,     "+
			" iduser                             integer              ,     "+
			" idusermobile integer not null                           ,     "+
			" idshop       integer not null                           ,     "+
			" datacad                            varchar(20)          ,     "+
			" horacad                            varchar(10)          ,     "+
			" observacoes                        text                 ,     "+
			" primary key (idcomentario,idos,idusermobile,idshop)           "+
			")                                                              ";


	final String DDL_CREATE_TBARQUIVOOS = "CREATE TABLE IF NOT EXISTS TBARQUIVOOS(             "+
			" idarquivo    integer not null                           ,     "+
			" idos                               integer not null     ,     "+
			" iduser                             integer              ,     "+
			" idusermobile integer not null                           ,     "+
			" idshop       integer not null                           ,     "+
			" blobdata                           BLOB                 ,     "+
			" codgerador                         varchar(15)          ,     "+
			" urlarquivo                         varchar(100)         ,     "+
			" extensao                           varchar(10)          ,     "+
			" primary key (idarquivo,idos,idusermobile,idshop)              "+
			")                                                              ";

	final String DDL_CREATE_TBAPROVADORES = "CREATE TABLE IF NOT EXISTS TBAPROVADORES(         "+
			" iduser       integer not null                              ,     "+
			" idos         integer not null                              ,     "+
			" idusermobile integer not null                              ,     "+
			" idshop       integer not null                              ,     "+
			" acao         integer                                       ,     "+
			" alcadas      integer                                       ,     "+
			" ordem        integer                                       ,     "+
			" suplente     integer                                       ,     "+
			" nome         varchar(80)                                   ,     "+
			" email        varchar(80)                                   ,     "+
			" primary key (iduser,idos,idusermobile,idshop)                    "+
			")                                                                 ";

	final String DDL_CREATE_TBSETOROS = "CREATE TABLE IF NOT EXISTS TBSETOROS(             "+
			" idsetoros    integer not null                           ,     "+
			" ativo                               integer             ,     "+
			" titulo                              varchar(100)        ,     "+
			" idshop   integer not null                               ,     "+
			" primary key (idsetoros,idshop)                                "+
			")                                                              ";

	final String DDL_CREATE_TBTIPOSERVICO = "CREATE TABLE IF NOT EXISTS TBTIPOSERVICO(         "+
			" idtipo       integer not null                           ,     "+
			" iddepto      integer                                    ,     "+
			" idsetoros    integer                                    ,     "+
			" obrigobs     integer                                    ,     "+
			" obriganexo   integer                                    ,     "+
			" ativo        integer                                    ,     "+
			" funcionamento  integer                                  ,     "+
			" descricao    varchar(80)                                ,     "+
			" obs          varchar(400)                               ,     "+
			" idshop   integer not null                               ,     "+
			" primary key (idtipo,idshop)                                   "+
			")                                                              ";

	final String DDL_CREATE_TBCALENDARIO = "CREATE TABLE IF NOT EXISTS TBCALENDARIO(             "+
			" ddata    date    not null                              ,     "+
			" dutil                              integer             ,     "+
			" feriado                            integer             ,     "+
			" idshop   integer not null                              ,     "+
			" primary key (ddata,idshop)                                   "+
			")                                                             ";

	final String DDL_CREATE_TBCONFIGHORARIOS = "CREATE TABLE IF NOT EXISTS TBCONFIGHORARIOS(             "+
			" id_config_horario      integer not null primary key                  ,     "+
			" horario_func_inicio    varchar(10)                                   ,     "+
			" horario_func_fim       varchar(10)                                   ,     "+
			" gmt                    integer                                       ,     "+
			" tempo_token            integer                                             "+
			")                                                                           ";

	final String DDL_CREATE_TBCONFIG_FNC = "CREATE TABLE IF NOT EXISTS TBCONFIGFUNCIONARIOS(             "+
			" id_config_fnc          integer not null                              ,     "+
			" campo                  varchar(80)                                   ,     "+
			" obrigatorio            integer                                       ,     "+
			" iduser                 integer                                       ,     "+
			" idshop                 integer                                       ,     "+
			" primary key (id_config_fnc,iduser,idshop)                                  "+
			")                                                                           ";

}
