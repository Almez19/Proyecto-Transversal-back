drop database if exists pr_transversal;
Create database pr_transversal;
use pr_transversal;

create table usuarios (
  id CHAR (36) PRIMARY KEY, -- UUID
  nombre varchar(50) NOT NULL,
  apellido1 varchar(50) NOT NULL,
  apellido2 varchar(50) NOT NULL,
  DNI_NIE varchar (9) NOT NULL UNIQUE,
  ROL enum('admin','empleado','entrenador') NOT NULL,
  estado boolean NOT NULL DEFAULT TRUE
);

create table clientes (
  id CHAR (36) PRIMARY KEY, -- UUID
  nombre varchar(50) NOT NULL,
  apellido1 varchar(50) NOT NULL,
  apellido2 varchar(50) NOT NULL,
  DNI_NIE varchar (9) NOT NULL UNIQUE,
  estado boolean NOT NULL DEFAULT TRUE
);

create table Gimnasios(
	id CHAR(36) PRIMARY KEY, -- UUID
    ubicacion varchar (150) NOT NULL UNIQUE,
    ciudad varchar (30) NOT NULL,
	nombre varchar(50) NOT NULL,
	estado boolean NOT NULL DEFAULT TRUE
);

create table noticias(
	id CHAR(36) PRIMARY KEY, -- UUID
	titulo varchar(50) NOT NULL,
    cuerpo TEXT NOT NULL,
    urlImagen TEXT NOT NULL,
    fecha DATETIME NOT NULL,
	gimnasio_id CHAR(36), -- UUID
	constraint gimnasio_id foreign key (gimnasio_id) References Gimnasios(id)
);

create table salas(
	id CHAR(36) PRIMARY KEY, -- UUID
    numero_sala int NOT NULL , -- FUNCIONA COMO NOMBRE
    gimnasio_id_s CHAR(36), -- UUID
	constraint gimnasio_id_s foreign key (gimnasio_id_s) References Gimnasios(id),
	UNIQUE (numero_sala, gimnasio_id_s)
	
);

create table clases(
	id CHAR(36) PRIMARY KEY, -- UUID
    deporte varchar (50), -- FUNCIONA COMO NOMBRE
    hora_inicio timestamp NOT NULL,
	hora_final timestamp NOT NULL,
    fecha date NOT NULL,
    sala_id CHAR(36), -- UUID
    constraint sala_id_c foreign key (sala_id) References salas(id),
    id_usuarios_c CHAR(36), -- UUID -- ID DEL USUARIO/ENTRENADOR QUE LO CREA
    constraint id_usuarios_c foreign key (id_usuarios_c) References usuarios(id),
    
    CONSTRAINT chk_horas CHECK (hora_inicio < hora_final)
    
);

create table reservas(
	id CHAR(36) PRIMARY KEY, -- UUID
    id_usuarios CHAR(36), -- UUID,
    clase_id CHAR(36), -- UUID
    estado boolean DEFAULT true NOT NULL,
    constraint clase_id_r foreign key (clase_id) References clases(id),
    constraint id_usuarios_r foreign key (id_usuarios) References usuarios(id)
);

create table membresias(
	id CHAR(36) PRIMARY KEY, -- UUID
	fecha_inicio date,
	fecha_final date,
    estado boolean not null,
    duracion enum ('diario','semanal','mensual','trimestral','anual'),
    calidad enum ('comfort','premium','ultimate'),
    precio decimal(5,2),
    usuario_id CHAR(36), -- UUID
    constraint usuario_id foreign key (usuario_id) References usuarios(id),
    
    CONSTRAINT chk_fechas CHECK (fecha_inicio < fecha_final)
    
);

create table maquinas(
	id CHAR(36) PRIMARY KEY, -- UUID
    nombre varchar(50) NOT NULL,
    gimnasios_id CHAR(36), -- UUID
	descripcion TEXT,
	urlImagen TEXT,
	constraint gimnasios_id foreign key (gimnasios_id) References Gimnasios(id)
);

create table rutinas(
	id CHAR(36) PRIMARY KEY, -- UUID
	nombre varchar(50) NOT NULL,
	usuario_id_ru CHAR(36), -- UUID
    constraint usuario_id_ru foreign key (usuario_id_ru) References usuarios(id)
);

create table ejercicios (
	id CHAR(36) PRIMARY KEY, -- UUID
    nombre VARCHAR(50) NOT NULL,
	rutina_id_e CHAR(36), -- UUID
    constraint rutina_id_e foreign key (rutina_id_e) References rutinas(id),
	maquina_id_e CHAR(36), -- UUID
    constraint maquina_id_e foreign key (maquina_id_e) References maquinas(id)

);




