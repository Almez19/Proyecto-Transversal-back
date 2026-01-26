drop database if exists pr_transversal;
Create database pr_transversal;
use pr_transversal;

create table usuarios (
  id int PRIMARY KEY,
  nombre varchar(50),
  apellido1 varchar(50),
  apellido2 varchar(50),
  DNI_NIE varchar (9),
  ROL enum('admin','empleado','entrenador','cliente'),
  estado boolean
);

create table Gimnasios(
	id int PRIMARY KEY,
    ubicacion varchar (150),
    ciudad varchar (30),
	nombre varchar(50),
	estado boolean
);

create table noticias(
	id int PRIMARY KEY,
	titulo varchar(50),
    cuerpo TEXT,
    urlImagen TEXT,
    fecha DATETIME,
	gimnasionotices_id int,
	constraint gimnasionotices_id foreign key (gimnasionotices_id) References Gimnasios(id)
);

create table salas(
	id int PRIMARY KEY,
    numero_sala int,
    gimnasio_id int,
	constraint gimnasio_id foreign key (gimnasio_id) References Gimnasios(id)
);

create table clases(
	id int PRIMARY KEY,
    deporte varchar (50),
    hora_inicio timestamp,
	hora_final timestamp,
    fecha date,
    sala_id INT,
    constraint sala_id foreign key (sala_id) References salas(id),
    id_usuarios int,
    constraint id_usuarios foreign key (id_usuarios) References usuarios(id)
);


create table membresias(
	id int PRIMARY KEY,
	fecha_inicio date,
	fecha_final date,
    estado boolean not null,
    duracion enum ('diario','semanal','mensual','trimestral','anual'),
    calidad enum ('comfort','premium','ultimate'),
    precio decimal(5,2),
    rebaja int,
    usuario_id int,
    constraint usuario_id foreign key (usuario_id) References usuarios(id)
);

create table maquinas(
	id int PRIMARY KEY,
    nombre varchar(50),
    gimnasios_id int,
	descripcion TEXT,
	urlImagen TEXT,
	constraint gimnasios_id foreign key (gimnasios_id) References Gimnasios(id)
);





