drop database if exists pr_transversal;
Create database pr_transversal;
use pr_transversal;

create table usuarios (
  id CHAR (36) PRIMARY KEY, -- UUID
  nombre varchar(50) NOT NULL,
  apellido1 varchar(50) NOT NULL,
  apellido2 varchar(50) NOT NULL,
  DNI_NIE varchar (9) NOT NULL UNIQUE,
  ROL enum('admin','empleado','entrenador','cliente') NOT NULL,
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
	constraint gimnasio_id foreign key (gimnasio) References Gimnasios(id)
);

create table salas(
	id CHAR(36) PRIMARY KEY, -- UUID
    numero_sala int NOT NULL , -- FUNCIONA COMO NOMBRE
    gimnasio_id CHAR(36), -- UUID
	UNIQUE (numero_sala, gimnasio_id),
	constraint gimnasio_id foreign key (gimnasio_id) References Gimnasios(id)
);

create table clases(
	id CHAR(36) PRIMARY KEY, -- UUID
    deporte varchar (50), -- FUNCIONA COMO NOMBRE
    hora_inicio timestamp,
	hora_final timestamp,
    fecha date,
    sala_id CHAR(36), -- UUID
    constraint sala_id foreign key (sala_id) References salas(id),
    id_usuarios CHAR(36), -- UUID
    constraint id_usuarios foreign key (id_usuarios) References usuarios(id)
);


create table membresias(
	id CHAR(36) PRIMARY KEY, -- UUID
	fecha_inicio date,
	fecha_final date,
    estado boolean not null,
    duracion enum ('diario','semanal','mensual','trimestral','anual'),
    calidad enum ('comfort','premium','ultimate'),
    precio decimal(5,2),
    rebaja int,
    usuario_id CHAR(36), -- UUID
    constraint usuario_id foreign key (usuario_id) References usuarios(id)
);

create table maquinas(
	id CHAR(36) PRIMARY KEY, -- UUID
    nombre varchar(50),
    gimnasios_id CHAR(36), -- UUID
	descripcion TEXT,
	urlImagen TEXT,
	constraint gimnasios_id foreign key (gimnasios_id) References Gimnasios(id)
);





