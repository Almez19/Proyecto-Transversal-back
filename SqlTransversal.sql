drop database if exists pr_transversal;
Create database pr_transversal;
use pr_transversal;


create table Gimnasios(
	id CHAR(36) PRIMARY KEY, -- UUID
    ubicacion varchar (150) NOT NULL UNIQUE,
    ciudad varchar (30) NOT NULL,
	nombre varchar(50) NOT NULL,
	estado boolean NOT NULL DEFAULT TRUE
);

CREATE TABLE usuarios (
  id CHAR(36) PRIMARY KEY, -- UUID
  nombre VARCHAR(50) NOT NULL,
  apellido1 VARCHAR(50) NOT NULL,
  apellido2 VARCHAR(50) NOT NULL,
  DNI_NIE VARCHAR(9) NOT NULL UNIQUE,
  contrasena VARCHAR(255) NOT NULL,
  ROL ENUM('admin','empleado','entrenador') NOT NULL,
  gimnasio_id CHAR(36) NOT NULL, 
  estado BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT usuario_gimnasio_fk
    FOREIGN KEY (gimnasio_id) REFERENCES Gimnasios(id)
);

create table clientes (
  id CHAR (36) PRIMARY KEY, -- UUID
  nombre varchar(50) NOT NULL,
  apellido1 varchar(50) NOT NULL,
  apellido2 varchar(50) NOT NULL,
  DNI_NIE varchar (9) NOT NULL UNIQUE,
  contrasena VARCHAR(255) NOT NULL,
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

CREATE TABLE reservas (
  id CHAR(36) PRIMARY KEY, -- UUID
  cliente_id CHAR(36) NOT NULL, -- UUID
  clase_id CHAR(36) NOT NULL, -- UUID
  estado BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_reservas_clase
    FOREIGN KEY (clase_id) REFERENCES clases(id),
  CONSTRAINT fk_reservas_cliente
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
  UNIQUE (cliente_id, clase_id) -- evita doble reserva del mismo cliente a la misma clase
);

CREATE TABLE membresias (
  id CHAR(36) PRIMARY KEY, -- UUID
  fecha_inicio DATE NOT NULL,
  fecha_final DATE NOT NULL,
  estado BOOLEAN NOT NULL,
  duracion ENUM ('diario','semanal','mensual','trimestral','anual') NOT NULL,
  calidad ENUM ('comfort','premium','ultimate') NOT NULL,
  precio DECIMAL(7,2) NOT NULL,
  cliente_id CHAR(36) NOT NULL,
  CONSTRAINT fk_membresias_cliente
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
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

CREATE TABLE rutinas (
  id CHAR(36) PRIMARY KEY, -- UUID
  nombre VARCHAR(50) NOT NULL,
  cliente_id_ru CHAR(36) NOT NULL, -- UUID
  CONSTRAINT fk_rutinas_cliente
    FOREIGN KEY (cliente_id_ru) REFERENCES clientes(id)
);

create table ejercicios (
	id CHAR(36) PRIMARY KEY, -- UUID
    nombre VARCHAR(50) NOT NULL,
	rutina_id_e CHAR(36), -- UUID
    maquina_id_e CHAR(36), -- UUID
    constraint rutina_id_e foreign key (rutina_id_e) References rutinas(id),
    constraint maquina_id_e foreign key (maquina_id_e) References maquinas(id)

);



