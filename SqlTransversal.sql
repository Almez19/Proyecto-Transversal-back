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
  email VARCHAR(100) NOT NULL UNIQUE,          
  DNI_NIE VARCHAR(9) NOT NULL UNIQUE,
  contrasena VARCHAR(255) NOT NULL,
  ROL ENUM('admin','empleado','entrenador') NOT NULL,
  gimnasio_id CHAR(36) NOT NULL, 
  estado BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT usuario_gimnasio_fk
    FOREIGN KEY (gimnasio_id) REFERENCES Gimnasios(id)
);


CREATE TABLE clientes (
  id CHAR (36) PRIMARY KEY, -- UUID
  nombre varchar(50) NOT NULL,
  apellido1 varchar(50) NOT NULL,
  apellido2 varchar(50) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,        
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


-- Gimnasios
INSERT INTO Gimnasios (id, ubicacion, ciudad, nombre, estado) VALUES
('11111111-1111-1111-1111-111111111111', 'Calle de Atocha 98', 'Madrid', 'BasiFit Atocha', TRUE),
('22222222-2222-2222-2222-222222222222', 'Carrer de Mallorca 401', 'Barcelona', 'BasiFit Sagrada Família', TRUE),
('33333333-3333-3333-3333-333333333333', 'C/ de San Vicente Mártir 82', 'Valencia', 'BasiFit Valencia Centro', TRUE);

-- Usuarios 
INSERT INTO usuarios (id, nombre, apellido1, apellido2, email, DNI_NIE, contrasena, ROL, gimnasio_id, estado) VALUES
('a1a1a1a1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Sergio', 'García', 'Martínez', 'sergio.garcia@basifit.es', '48273615K', 'BasifitAdmin2026!', 'admin',
 '11111111-1111-1111-1111-111111111111', TRUE),

('a2a2a2a2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Marta', 'López', 'Santos', 'marta.lopez@basifit.es', '39184726M', 'EmpleadoAtocha2026', 'empleado',
 '11111111-1111-1111-1111-111111111111', TRUE),

('a3a3a3a3-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'Iván', 'Pérez', 'Ruiz', 'ivan.perez@basifit.es', '51293847H', 'TrainerBCN2026', 'entrenador',
 '22222222-2222-2222-2222-222222222222', TRUE),

('a4a4a4a4-aaaa-aaaa-aaaa-aaaaaaaaaaa4', 'Laura', 'Fernández', 'Gómez', 'laura.fernandez@basifit.es', '27461539J', 'TrainerVLC2026', 'entrenador',
 '33333333-3333-3333-3333-333333333333', TRUE);

-- Clientes
INSERT INTO clientes (id, nombre, apellido1, apellido2, email, DNI_NIE, contrasena, estado) VALUES
('b1b1b1b1-bbbb-bbbb-bbbb-bbbbbbbbbbb1', 'Andrea', 'Romero', 'Díaz', 'andrea.romero@gmail.com', '63827491P', 'Cliente2026Atocha', TRUE),
('b2b2b2b2-bbbb-bbbb-bbbb-bbbbbbbbbbb2', 'Javier', 'Sánchez', 'Molina', 'javier.sanchez@gmail.com', '75192846R', 'Cliente2026BCN', TRUE),
('b3b3b3b3-bbbb-bbbb-bbbb-bbbbbbbbbbb3', 'Paula', 'Navarro', 'Iglesias', 'paula.navarro@gmail.com', '40928371T', 'Cliente2026VLC', TRUE);

-- Noticias 
INSERT INTO noticias (id, titulo, cuerpo, urlImagen, fecha, gimnasio_id) VALUES
('c1c1c1c1-cccc-cccc-cccc-ccccccccccc1',
 'Nueva zona de fuerza y peso libre',
 '¡Ya está abierta la nueva zona de fuerza! Hemos añadido bancos ajustables, mancuernas hasta 40kg y más espacio para trabajo con barra. Recuerda recoger el material al terminar.',
 'https://images.com',
 '2026-02-05 09:30:00',
 '11111111-1111-1111-1111-111111111111'),

('c2c2c2c2-cccc-cccc-cccc-ccccccccccc2',
 'Yoga express al mediodía',
 'Nueva clase de Yoga express a las 12:00 (45 min). Ideal si entrenas en la pausa del trabajo. Reserva desde la app y llega 5 minutos antes para preparar material.',
 'https://images.com',
 '2026-02-06 12:15:00',
 '22222222-2222-2222-2222-222222222222'),

('c3c3c3c3-cccc-cccc-cccc-ccccccccccc3',
 'Mantenimiento de cardio (sábado)',
 'Este sábado de 07:00 a 09:00 h realizaremos mantenimiento preventivo en varias máquinas de cardio. Gracias por tu comprensión; el resto de zonas funcionarán con normalidad.',
 'https://images.com',
 '2026-02-07 18:00:00',
 '33333333-3333-3333-3333-333333333333');

-- Salas
INSERT INTO salas (id, numero_sala, gimnasio_id_s) VALUES
('d1d1d1d1-dddd-dddd-dddd-ddddddddddd1', 1, '11111111-1111-1111-1111-111111111111'),
('d2d2d2d2-dddd-dddd-dddd-ddddddddddd2', 2, '11111111-1111-1111-1111-111111111111'),
('d3d3d3d3-dddd-dddd-dddd-ddddddddddd3', 1, '22222222-2222-2222-2222-222222222222'),
('d4d4d4d4-dddd-dddd-dddd-ddddddddddd4', 1, '33333333-3333-3333-3333-333333333333');

-- Clases 
INSERT INTO clases (id, deporte, hora_inicio, hora_final, fecha, sala_id, id_usuarios_c) VALUES
('e1e1e1e1-eeee-eeee-eeee-eeeeeeeeeee1', 'Spinning',
 '2026-02-10 18:00:00', '2026-02-10 18:50:00', '2026-02-10',
 'd1d1d1d1-dddd-dddd-dddd-ddddddddddd1',
 'a3a3a3a3-aaaa-aaaa-aaaa-aaaaaaaaaaa3'),

('e2e2e2e2-eeee-eeee-eeee-eeeeeeeeeee2', 'Body Pump',
 '2026-02-10 19:10:00', '2026-02-10 20:00:00', '2026-02-10',
 'd2d2d2d2-dddd-dddd-dddd-ddddddddddd2',
 'a3a3a3a3-aaaa-aaaa-aaaa-aaaaaaaaaaa3'),

('e3e3e3e3-eeee-eeee-eeee-eeeeeeeeeee3', 'Yoga',
 '2026-02-11 12:00:00', '2026-02-11 12:45:00', '2026-02-11',
 'd3d3d3d3-dddd-dddd-dddd-ddddddddddd3',
 'a3a3a3a3-aaaa-aaaa-aaaa-aaaaaaaaaaa3'),

('e4e4e4e4-eeee-eeee-eeee-eeeeeeeeeee4', 'Pilates',
 '2026-02-12 09:30:00', '2026-02-12 10:15:00', '2026-02-12',
 'd4d4d4d4-dddd-dddd-dddd-ddddddddddd4',
 'a4a4a4a4-aaaa-aaaa-aaaa-aaaaaaaaaaa4');

-- Reservas
INSERT INTO reservas (id, cliente_id, clase_id, estado) VALUES
('f1f1f1f1-ffff-ffff-ffff-fffffffffff1',
 'b1b1b1b1-bbbb-bbbb-bbbb-bbbbbbbbbbb1',
 'e1e1e1e1-eeee-eeee-eeee-eeeeeeeeeee1', TRUE),

('f2f2f2f2-ffff-ffff-ffff-fffffffffff2',
 'b2b2b2b2-bbbb-bbbb-bbbb-bbbbbbbbbbb2',
 'e2e2e2e2-eeee-eeee-eeee-eeeeeeeeeee2', TRUE),

('f3f3f3f3-ffff-ffff-ffff-fffffffffff3',
 'b3b3b3b3-bbbb-bbbb-bbbb-bbbbbbbbbbb3',
 'e3e3e3e3-eeee-eeee-eeee-eeeeeeeeeee3', TRUE);

-- Membresias 
INSERT INTO membresias (id, fecha_inicio, fecha_final, estado, duracion, calidad, precio, cliente_id) VALUES
('g1g1g1g1-gggg-gggg-gggg-ggggggggggg1',
 '2026-02-01', '2026-02-28', TRUE, 'mensual', 'premium', 49.99,
 'b1b1b1b1-bbbb-bbbb-bbbb-bbbbbbbbbbb1'),

('g2g2g2g2-gggg-gggg-gggg-ggggggggggg2',
 '2026-01-01', '2026-01-31', FALSE, 'mensual', 'comfort', 34.99,
 'b2b2b2b2-bbbb-bbbb-bbbb-bbbbbbbbbbb2'),

('g3g3g3g3-gggg-gggg-gggg-ggggggggggg3',
 '2026-02-01', '2027-01-31', TRUE, 'anual', 'ultimate', 499.00,
 'b3b3b3b3-bbbb-bbbb-bbbb-bbbbbbbbbbb3');

-- Maquinas
INSERT INTO maquinas (id, nombre, gimnasios_id, descripcion, urlImagen) VALUES
('h1h1h1h1-hhhh-hhhh-hhhh-hhhhhhhhhhh1', 'Cinta de correr Technogym',
 '11111111-1111-1111-1111-111111111111',
 'Cinta con inclinación automática, perfiles HIIT y control de ritmo. Revisada semanalmente.',
 'https://images.com'),

('h2h2h2h2-hhhh-hhhh-hhhh-hhhhhhhhhhh2', 'Remo Concept2 Model D',
 '22222222-2222-2222-2222-222222222222',
 'Remo de aire con monitor PM5. Perfecto para cardio y trabajo de potencia.',
 'https://images.com'),

('h3h3h3h3-hhhh-hhhh-hhhh-hhhhhhhhhhh3', 'Multipower (Smith Machine)',
 '33333333-3333-3333-3333-333333333333',
 'Máquina guiada para sentadilla, press y remos. Incluye seguros laterales y topes.',
 'https://images.com');

-- Rutinas
INSERT INTO rutinas (id, nombre, cliente_id_ru) VALUES
('i1i1i1i1-iiii-iiii-iiii-iiiiiiiiiii1', 'Full Body Fuerza (3 días)', 'b1b1b1b1-bbbb-bbbb-bbbb-bbbbbbbbbbb1'),
('i2i2i2i2-iiii-iiii-iiii-iiiiiiiiiii2', 'Cardio + Core (2-3 días)', 'b2b2b2b2-bbbb-bbbb-bbbb-bbbbbbbbbbb2'),
('i3i3i3i3-iiii-iiii-iiii-iiiiiiiiiii3', 'Hipertrofia tren superior', 'b3b3b3b3-bbbb-bbbb-bbbb-bbbbbbbbbbb3');

-- Ejercicios 
INSERT INTO ejercicios (id, nombre, rutina_id_e, maquina_id_e) VALUES
('j1j1j1j1-jjjj-jjjj-jjjj-jjjjjjjjjjj1', 'Calentamiento 10 min (cinta)',
 'i1i1i1i1-iiii-iiii-iiii-iiiiiiiiiii1',
 'h1h1h1h1-hhhh-hhhh-hhhh-hhhhhhhhhhh1'),

('j2j2j2j2-jjjj-jjjj-jjjj-jjjjjjjjjjj2', 'Remo 8 min (moderado)',
 'i2i2i2i2-iiii-iiii-iiii-iiiiiiiiiii2',
 'h2h2h2h2-hhhh-hhhh-hhhh-hhhhhhhhhhh2'),

('j3j3j3j3-jjjj-jjjj-jjjj-jjjjjjjjjjj3', 'Sentadilla guiada 4x10',
 'i1i1i1i1-iiii-iiii-iiii-iiiiiiiiiii1',
 'h3h3h3h3-hhhh-hhhh-hhhh-hhhhhhhhhhh3'),

('j4j4j4j4-jjjj-jjjj-jjjj-jjjjjjjjjjj4', 'Plancha frontal 3x45s',
 'i2i2i2i2-iiii-iiii-iiii-iiiiiiiiiii2',
 NULL),

('j5j5j5j5-jjjj-jjjj-jjjj-jjjjjjjjjjj5', 'Press banca 4x8 (barra)',
 'i3i3i3i3-iiii-iiii-iiii-iiiiiiiiiii3',
 NULL);

-- Para que funcione el login en desarrollo
UPDATE usuarios
SET contrasena = CONCAT('{noop}', contrasena)
WHERE contrasena NOT LIKE '{%}%';

UPDATE clientes
SET contrasena = CONCAT('{noop}', contrasena)
WHERE contrasena NOT LIKE '{%}%';