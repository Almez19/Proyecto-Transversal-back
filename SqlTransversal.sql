drop database if exists pr_transversal;
Create database pr_transversal;
use pr_transversal;


create table Gimnasios(
	id CHAR(36) PRIMARY KEY, -- UUID
    ubicacion varchar (150) NOT NULL UNIQUE,
    URLimagen TEXT,
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
INSERT INTO Gimnasios (id, ubicacion, URLimagen, ciudad, nombre, estado) VALUES
('11111111-1111-1111-1111-111111111111', 'Calle de Atocha 98', 'https://gymfactory.net/wp-content/uploads/2024/12/Basic-Fit.jpg' ,'Madrid', 'BasiFit Atocha', TRUE),
('22222222-2222-2222-2222-222222222222', 'Carrer de Mallorca 401','https://www.regiondigital.com/m/p/745x450/media/files/185822_basicfitmerida-3.jpg','Barcelona', 'BasiFit Sagrada Família', TRUE),
('33333333-3333-3333-3333-333333333333', 'C/ de San Vicente Mártir 82','https://www.vksport.eu/wp-content/uploads/2023/10/2022-11-21-scaled.jpg','Valencia', 'BasiFit Valencia Centro', TRUE),
('44444444-4444-4444-4444-444444444444', 'Avenida de la Constitución 15', 'https://www.basic-fit.com/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dwe3bd8a27/Club%20page%20website-BasicFit%20Tilburg%2026-8-2113386.jpeg', 'Sevilla', 'BasiFit Sevilla Centro', TRUE),
('55555555-5555-5555-5555-555555555555', 'Gran Vía 23', 'https://pcvegadelrey.com/storage/2021/11/Basic-Fit_VegadelRey_1.jpg', 'Bilbao', 'BasiFit Bilbao Gran Vía', FALSE),
('66666666-6666-6666-6666-666666666666', 'Calle Mayor 45', 'https://brand.basic-fit.com/match/KP_Number/5285/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:550', 'Zaragoza', 'BasiFit Zaragoza Centro', TRUE),
('77777777-7777-7777-7777-777777777777', 'Avenida de la Libertad 8', 'https://corporate.basic-fit.com/_next/image?url=https%3A%2F%2Fimages.ctfassets.net%2Fn2vcrsstbkc9%2F4JoY83X4htHay68W6XAlWy%2Fc7cc486be52dcabf9f269789da3c60be%2FTilburg_facade.jpg%3Fq%3D90%26fm%3Dwebp&w=2048&q=75', 'Málaga', 'BasiFit Málaga Norte', FALSE),
('88888888-8888-8888-8888-888888888888', 'Calle Uría 12', 'https://brand.basic-fit.com/match/KP_Number/5208/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:550', 'Santander', 'BasiFit Santander Centro', TRUE),
('99999999-9999-9999-9999-999999999999', 'Paseo de Zorrilla 101', 'https://brand.basic-fit.com/match/KP_Number/5051/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:550', 'Valladolid', 'BasiFit Valladolid Sur', TRUE),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Avenida Juan Carlos I 54', 'https://brand.basic-fit.com/match/KP_Number/5517/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:600', 'Murcia', 'BasiFit Murcia Centro', FALSE),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Calle Colón 7', 'https://brand.basic-fit.com/match/KP_Number/5303/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:600', 'Alicante', 'BasiFit Alicante Playa', TRUE),
('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Rúa do Hórreo 110', 'https://brand.basic-fit.com/match/KP_Number/5521/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:600', 'Santiago de Compostela', 'BasiFit Santiago Centro', TRUE),
('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Calle León y Castillo 200', 'https://brand.basic-fit.com/match/KP_Number/5114/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:600', 'Las Palmas', 'BasiFit Las Palmas', FALSE);


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
 '¡Ya está abierta la nueva zona de fuerza! Hemos ampliado el espacio destinado al entrenamiento con peso libre para que puedas entrenar con mayor comodidad y seguridad. Ahora disponemos de nuevos bancos ajustables, mancuernas hasta 40 kg, discos olímpicos adicionales y más jaulas para sentadilla. Además, se ha mejorado la ventilación de la sala y la iluminación para un entorno más agradable. Recuerda colocar el material en su sitio al terminar y respetar los turnos en horas punta.',
 'https://www.basic-fit.com/dw/image/v2/BDFP_PRD/on/demandware.static/-/Sites-master-catalog/default/dw7379b3ac/images/ClubsES/2023/alcorc%C3%B3n_extra.jpg?q=100',
 '2026-02-05 09:30:00',
 '11111111-1111-1111-1111-111111111111'),
('c2c2c2c2-cccc-cccc-cccc-ccccccccccc2',
 'Yoga express al mediodía',
 'Incorporamos una nueva clase de Yoga Express a las 12:00 h con una duración de 45 minutos, pensada especialmente para quienes entrenan durante la pausa del trabajo. Esta sesión combina movilidad, respiración y estiramientos dinámicos para ayudarte a liberar tensión y mejorar tu postura. Las plazas son limitadas, por lo que recomendamos reservar desde la app con antelación. Llega 5 minutos antes para preparar tu esterilla y comenzar puntualmente.',
 'https://images.ctfassets.net/ztnn01luatek/2mkRC2FYL2T0mWws2mYcxl/3663873c0b123ada46941eb989da5f8e/webimage-Basicfit-groepslessen-20-10-2514147.png',
 '2026-02-06 12:15:00',
 '22222222-2222-2222-2222-222222222222'),
('c3c3c3c3-cccc-cccc-cccc-ccccccccccc3',
 'Mantenimiento de cardio (sábado)',
 'Este sábado realizaremos tareas de mantenimiento preventivo en varias máquinas de la zona de cardio entre las 07:00 y las 09:00 h. Durante este tiempo algunas cintas y bicicletas podrán estar temporalmente fuera de servicio. Estas revisiones son fundamentales para garantizar tu seguridad y el correcto funcionamiento del equipamiento. El resto de instalaciones permanecerán abiertas con normalidad. Gracias por tu comprensión.',
 'https://palco23.mundodeportivo.com/thumb/eyJ0IjoiZCIsInciOjEyMDAsImgiOjY3NSwibSI6MSwidiI6IjEuMC4xIn0/palco23/files/2025/19-fitness/basic-fit/basic-fit-lateral-1200.png',
 '2026-02-07 18:00:00',
 '33333333-3333-3333-3333-333333333333'),
 ('c4c4c4c4-cccc-cccc-cccc-ccccccccccc4',
 'Nueva clase de HIIT avanzada',
 'A partir de la próxima semana estrenamos una nueva clase de HIIT avanzada dirigida a socios que ya tengan experiencia en entrenamiento de alta intensidad. La sesión combina intervalos de fuerza y cardio con tiempos de recuperación controlados para maximizar el rendimiento. Se recomienda traer toalla y botella de agua. Consulta los horarios disponibles en la app o en recepción.',
 'https://www.basic-fit.com/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dwc76eb19a/Blogs/New%20fitness%20routine/Landscape-Basicfit%20Hometools%2018%20mei%20204277.jpg',
 '2026-02-08 17:30:00',
 '44444444-4444-4444-4444-444444444444'),

('c5c5c5c5-cccc-cccc-cccc-ccccccccccc5',
 'Ampliación de horario en fin de semana',
 'A partir del próximo mes ampliaremos nuestro horario de fin de semana para adaptarnos mejor a vuestros entrenamientos. Los sábados abriremos desde las 07:00 hasta las 22:00 h y los domingos de 08:00 a 20:00 h. Queremos ofrecer mayor flexibilidad para que puedas organizar tu rutina sin prisas y evitar aglomeraciones en horas punta. Seguimos trabajando para mejorar tu experiencia en el gimnasio.',
 'https://www.basic-fit.com/dw/image/v2/BDFP_PRD/on/demandware.static/-/Sites-master-catalog/default/dwec7d375b/images/ClubsES/2024/molinadesegura_entrada.jpeg?q=100',
 '2026-02-09 08:45:00',
 '55555555-5555-5555-5555-555555555555');

INSERT INTO noticias (id, titulo, cuerpo, urlImagen, fecha, gimnasio_id) VALUES

('c6c6c6c6-cccc-cccc-cccc-cccccccccc6',
 'Renovación de vestuarios',
 'Nos complace anunciar que hemos completado la renovación integral de los vestuarios. Se han instalado nuevas taquillas con cierre digital, duchas con agua caliente mejorada y secadores de pelo en todos los puntos. El suelo antideslizante nuevo garantiza mayor seguridad. También se ha ampliado la zona de bancos para que puedas cambiarte con mayor comodidad. Esperamos que disfrutes de estas mejoras tanto como nosotros hemos disfrutado haciéndolas realidad.',
 'https://www.basic-fit.com/dw/image/v2/BDFP_PRD/on/demandware.static/-/Sites-master-catalog/default/dwec7d375b/images/ClubsES/2024/molinadesegura_entrada.jpeg?q=100',
 '2026-02-10 10:00:00',
 '66666666-6666-6666-6666-666666666666'),

('c7c7c7c7-cccc-cccc-cccc-cccccccccc7',
 'Nuevo reto mensual: 30 días de constancia',
 'Lanzamos nuestro primer reto mensual para todos los socios. Durante 30 días consecutivos, cada visita al gimnasio sumará puntos que podrás canjear por descuentos en tu próxima membresía. Además, los 3 socios más constantes recibirán un mes gratis. Regístrate en recepción o desde la app antes del día 15. Las reglas completas están disponibles en el tablón de anuncios y en nuestra web. ¡Anímate y demuestra tu constancia!',
 'https://www.basic-fit.com/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dwc76eb19a/Blogs/New%20fitness%20routine/Landscape-Basicfit%20Hometools%2018%20mei%20204277.jpg',
 '2026-02-11 09:00:00',
 '77777777-7777-7777-7777-777777777777'),

('c8c8c8c8-cccc-cccc-cccc-cccccccccc8',
 'Clases de boxeo fitness para principiantes',
 'Incorporamos al horario semanal clases de boxeo fitness orientadas a personas sin experiencia previa. Esta disciplina combina técnica de boxeo con cardio intenso, mejorando la coordinación, la resistencia y la confianza en uno mismo. Las sesiones duran 50 minutos y están impartidas por un entrenador certificado. Se proporcionan guantes y vendas en recepción. Los martes y jueves a las 19:30 h. Plazas limitadas, reserva desde la app.',
 'https://images.ctfassets.net/ztnn01luatek/2mkRC2FYL2T0mWws2mYcxl/3663873c0b123ada46941eb989da5f8e/webimage-Basicfit-groepslessen-20-10-2514147.png',
 '2026-02-12 11:30:00',
 '88888888-8888-8888-8888-888888888888'),

('c9c9c9c9-cccc-cccc-cccc-cccccccccc9',
 'Actualización de la app BasiFit',
 'Hemos lanzado una nueva versión de nuestra aplicación con importantes mejoras. Ahora podrás ver en tiempo real la ocupación del gimnasio antes de salir de casa, reservar clases con un solo toque y recibir notificaciones personalizadas sobre tus entrenamientos. También se ha rediseñado la sección de rutinas para que sea más intuitiva. Actualiza desde la App Store o Google Play y cuéntanos qué te parece en recepción o en nuestras redes sociales.',
 'https://palco23.mundodeportivo.com/thumb/eyJ0IjoiZCIsInciOjEyMDAsImgiOjY3NSwibSI6MSwidiI6IjEuMC4xIn0/palco23/files/2025/19-fitness/basic-fit/basic-fit-lateral-1200.png',
 '2026-02-13 16:00:00',
 '99999999-9999-9999-9999-999999999999'),

('cacacacac-cccc-cccc-cccc-cccccccccca',
 'Taller de nutrición deportiva gratuito',
 'El próximo viernes 20 de febrero organizamos un taller gratuito de nutrición deportiva impartido por una dietista-nutricionista colegiada. Se abordarán temas como la importancia del desayuno antes del entrenamiento, la recuperación muscular con alimentación y cómo organizar tus comidas semanales sin complicarte. El aforo es limitado a 20 personas. Inscríbete en recepción indicando tu nombre y número de socio antes del miércoles 18.',
 'https://gymfactory.net/wp-content/uploads/2024/12/Basic-Fit.jpg',
 '2026-02-14 08:30:00',
 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),

('cbcbcbcb-cccc-cccc-cccc-cccccccccccb',
 'Nueva zona de estiramiento y movilidad',
 'Hemos habilitado una zona exclusiva para estiramientos y trabajo de movilidad equipada con colchonetas, rodillos de foam, bandas elásticas y bloques de yoga. Este espacio está disponible durante todo el horario de apertura sin necesidad de reserva previa. Te recomendamos dedicar al menos 10 minutos al final de cada sesión para reducir el riesgo de lesiones y mejorar tu flexibilidad progresivamente. Consulta con nuestros entrenadores si necesitas orientación sobre rutinas de movilidad.',
 'https://www.basic-fit.com/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dw3c383df2/BasicFit%20Tilburg%2026-8-2114144%20(1).jpg',
 '2026-02-15 13:00:00',
 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),

('cccccccc-1111-cccc-cccc-ccccccccccc',
 'Incorporación de nuevo entrenador personal',
 'Nos alegra presentar a Carlos Mendoza, nuestro nuevo entrenador personal especializado en pérdida de peso y rehabilitación postural. Carlos cuenta con más de 8 años de experiencia trabajando con todo tipo de perfiles, desde atletas hasta personas que se inician por primera vez en el gimnasio. Si estás interesado en sesiones de entrenamiento personalizado, pásate por recepción o contacta directamente con él a través de la app. La primera sesión de valoración es completamente gratuita.',
 'https://www.soydemadrid.com/images/thumbs/basic-fit-abre-un-nuevo-gym-en-alcorcon-0102808_1200.jpeg',
 '2026-02-16 10:00:00',
 'cccccccc-cccc-cccc-cccc-cccccccccccc'),

('cdcdcdcd-cccc-cccc-cccc-ccccccccccd',
 'Mantenimiento general del 20 al 21 de febrero',
 'Os informamos que los días 20 y 21 de febrero realizaremos un mantenimiento general de las instalaciones. Durante estos dos días el gimnasio permanecerá cerrado para poder revisar toda la maquinaria, renovar el sistema de climatización y realizar la limpieza profunda anual. Pedimos disculpas por las molestias ocasionadas. A partir del 22 de febrero abriremos con el horario habitual y con las instalaciones al 100%. Gracias por vuestra comprensión y fidelidad.',
 'https://www.vksport.eu/wp-content/uploads/2023/10/2022-11-21-scaled.jpg',
 '2026-02-17 09:00:00',
 'dddddddd-dddd-dddd-dddd-dddddddddddd');




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