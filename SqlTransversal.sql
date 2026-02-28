DROP DATABASE IF EXISTS pr_transversal;
CREATE DATABASE pr_transversal CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE pr_transversal;

-- TABLAS 

CREATE TABLE gimnasios (
  id CHAR(36) PRIMARY KEY,
  ubicacion VARCHAR(150) NOT NULL UNIQUE,
  url_imagen TEXT,
  ciudad VARCHAR(30) NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  estado BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

-- USUARIOS 

CREATE TABLE usuarios (
  id CHAR(36) PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  apellido1 VARCHAR(50) NOT NULL,
  apellido2 VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  dni_nie VARCHAR(9) NOT NULL UNIQUE,
  contrasena VARCHAR(255) NOT NULL,
  rol ENUM('admin','empleado','entrenador') NOT NULL,
  gimnasio_id CHAR(36) NOT NULL,
  estado BOOLEAN NOT NULL DEFAULT TRUE,
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_usuario_gimnasio
    FOREIGN KEY (gimnasio_id) REFERENCES gimnasios(id)
) ENGINE=InnoDB;

-- CLIENTES 

CREATE TABLE clientes (
  id CHAR(36) PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  apellido1 VARCHAR(50) NOT NULL,
  apellido2 VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  dni_nie VARCHAR(9) NOT NULL UNIQUE,
  contrasena VARCHAR(255) NOT NULL,
  telefono VARCHAR(20),
  fecha_nacimiento DATE,
  ciudad VARCHAR(30),
  estado BOOLEAN NOT NULL DEFAULT TRUE,
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- NOTICIAS 

CREATE TABLE noticias (
  id CHAR(36) PRIMARY KEY,
  titulo VARCHAR(80) NOT NULL,
  cuerpo TEXT NOT NULL,
  url_imagen TEXT NOT NULL,
  fecha DATETIME NOT NULL,
  gimnasio_id CHAR(36),
  CONSTRAINT fk_noticia_gimnasio
    FOREIGN KEY (gimnasio_id) REFERENCES gimnasios(id)
) ENGINE=InnoDB;

-- SALAS 

CREATE TABLE salas (
  id CHAR(36) PRIMARY KEY,
  numero_sala INT NOT NULL,
  gimnasio_id CHAR(36) NOT NULL,
  nombre VARCHAR(60),
  estado BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_sala_gimnasio
    FOREIGN KEY (gimnasio_id) REFERENCES gimnasios(id),
  UNIQUE (numero_sala, gimnasio_id)
) ENGINE=InnoDB;

-- CLASES 

CREATE TABLE clases (
  id CHAR(36) PRIMARY KEY,
  nombre VARCHAR(60) NOT NULL,                
  descripcion VARCHAR(255),
  nivel ENUM('principiante','intermedio','avanzado') NOT NULL DEFAULT 'principiante',
  fecha DATE NOT NULL,
  hora_inicio TIME NOT NULL,
  hora_final TIME NOT NULL,
  duracion_minutos INT NOT NULL,
  capacidad INT NOT NULL DEFAULT 20,
  estado ENUM('programada','cancelada','finalizada') NOT NULL DEFAULT 'programada',
  sala_id CHAR(36) NOT NULL,
  entrenador_id CHAR(36) NOT NULL,            
  gimnasio_id CHAR(36) NOT NULL,
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_clase_sala
    FOREIGN KEY (sala_id) REFERENCES salas(id),
  CONSTRAINT fk_clase_entrenador
    FOREIGN KEY (entrenador_id) REFERENCES usuarios(id),
  CONSTRAINT fk_clase_gimnasio
    FOREIGN KEY (gimnasio_id) REFERENCES gimnasios(id),

  CONSTRAINT chk_horas CHECK (hora_inicio < hora_final),
  CONSTRAINT chk_capacidad CHECK (capacidad > 0)
) ENGINE=InnoDB;

-- RESERVAS 

CREATE TABLE reservas (
  id CHAR(36) PRIMARY KEY,
  cliente_id CHAR(36) NOT NULL,
  clase_id CHAR(36) NOT NULL,
  estado ENUM('activa','cancelada') NOT NULL DEFAULT 'activa',
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_cancelacion DATETIME NULL,
  motivo_cancelacion VARCHAR(255) NULL,

  CONSTRAINT fk_reserva_cliente
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
  CONSTRAINT fk_reserva_clase
    FOREIGN KEY (clase_id) REFERENCES clases(id),

  UNIQUE (cliente_id, clase_id)
) ENGINE=InnoDB;

-- MEMBRESIAS 

CREATE TABLE membresias (
  id CHAR(36) PRIMARY KEY,
  fecha_inicio DATE NOT NULL,
  fecha_final DATE NOT NULL,
  estado BOOLEAN NOT NULL,
  duracion ENUM('diario','semanal','mensual','trimestral','anual') NOT NULL,
  calidad ENUM('comfort','premium','ultimate') NOT NULL,
  precio DECIMAL(7,2) NOT NULL,
  cliente_id CHAR(36) NOT NULL,
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_membresia_cliente
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
  CONSTRAINT chk_fechas CHECK (fecha_inicio < fecha_final)
) ENGINE=InnoDB;

-- MAQUINAS 

CREATE TABLE maquinas (
  id CHAR(36) PRIMARY KEY,
  nombre VARCHAR(60) NOT NULL,
  descripcion TEXT,
  url_imagen TEXT,
  grupo_muscular ENUM('pecho','espalda','pierna','hombro','brazo','core','cardio','cuerpo_completo') NOT NULL DEFAULT 'cuerpo_completo',
  estado BOOLEAN NOT NULL DEFAULT TRUE,
  gimnasio_id CHAR(36) NOT NULL,

  CONSTRAINT fk_maquina_gimnasio
    FOREIGN KEY (gimnasio_id) REFERENCES gimnasios(id)
) ENGINE=InnoDB;

-- RUTINAS 

CREATE TABLE rutinas (
  id CHAR(36) PRIMARY KEY,
  nombre VARCHAR(60) NOT NULL,
  objetivo ENUM('perder_grasa','ganar_masa','mantenimiento','salud') NOT NULL DEFAULT 'salud',
  nivel ENUM('principiante','intermedio','avanzado') NOT NULL DEFAULT 'principiante',
  dias_por_semana INT NOT NULL DEFAULT 3,
  notas VARCHAR(255),
  cliente_id CHAR(36) NOT NULL,
  entrenador_id CHAR(36), -- opcional (si la crea/gestiona un entrenador)
  fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_rutina_cliente
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
  CONSTRAINT fk_rutina_entrenador
    FOREIGN KEY (entrenador_id) REFERENCES usuarios(id),
  CONSTRAINT chk_dias CHECK (dias_por_semana BETWEEN 1 AND 7)
) ENGINE=InnoDB;

-- EJERCICIOS 

CREATE TABLE ejercicios (
  id CHAR(36) PRIMARY KEY,
  nombre VARCHAR(80) NOT NULL,
  orden INT NOT NULL DEFAULT 1,
  series INT NOT NULL DEFAULT 3,
  repeticiones INT NOT NULL DEFAULT 10,
  peso DECIMAL(6,2) NULL,
  descanso_segundos INT NOT NULL DEFAULT 60,
  notas VARCHAR(255),

  rutina_id CHAR(36) NOT NULL,
  maquina_id CHAR(36) NULL,

  CONSTRAINT fk_ejercicio_rutina
    FOREIGN KEY (rutina_id) REFERENCES rutinas(id),
  CONSTRAINT fk_ejercicio_maquina
    FOREIGN KEY (maquina_id) REFERENCES maquinas(id),

  CONSTRAINT chk_series CHECK (series > 0),
  CONSTRAINT chk_repeticiones CHECK (repeticiones > 0),
  CONSTRAINT chk_descanso CHECK (descanso_segundos >= 0)
) ENGINE=InnoDB;

-- CATALOGO CLASES 

CREATE TABLE catalogo_clases (
  id CHAR(36) PRIMARY KEY,
  nombre VARCHAR(60) NOT NULL UNIQUE,
  descripcion VARCHAR(255) NOT NULL,
  nivel_recomendado ENUM('principiante','intermedio','avanzado') NOT NULL DEFAULT 'principiante',
  url_imagen TEXT,
  estado BOOLEAN NOT NULL DEFAULT TRUE
);

-- INDICES 

CREATE INDEX idx_clase_fecha ON clases(fecha);
CREATE INDEX idx_clase_nombre ON clases(nombre);
CREATE INDEX idx_reserva_cliente ON reservas(cliente_id);
CREATE INDEX idx_reserva_clase ON reservas(clase_id);
CREATE INDEX idx_rutina_cliente ON rutinas(cliente_id);
CREATE INDEX idx_ejercicio_rutina ON ejercicios(rutina_id);

-- INSERTS 

-- Gimnasios
SET @gym_madrid = UUID();
SET @gym_bcn = UUID();
SET @gym_vlc = UUID();
SET @gym_sev = UUID();
SET @gym_bil = UUID();
SET @gym_zgz = UUID();
SET @gym_mlg = UUID();
SET @gym_ovi = UUID();
SET @gym_vll = UUID();
SET @gym_mur = UUID();
SET @gym_ali = UUID();
SET @gym_scq = UUID();
SET @gym_lpa = UUID();

INSERT INTO gimnasios (id, ubicacion, url_imagen, ciudad, nombre, estado) VALUES
(@gym_madrid, 'Calle de Atocha 98', 'https://gymfactory.net/wp-content/uploads/2024/12/Basic-Fit.jpg', 'Madrid', 'BasiFit Atocha', TRUE),
(@gym_bcn, 'Carrer de Mallorca 401', 'https://www.regiondigital.com/m/p/745x450/media/files/185822_basicfitmerida-3.jpg', 'Barcelona', 'BasiFit Sagrada Família', TRUE),
(@gym_vlc, 'C/ de San Vicente Mártir 82', 'https://www.vksport.eu/wp-content/uploads/2023/10/2022-11-21-scaled.jpg', 'Valencia', 'BasiFit Valencia Centro', TRUE),
(@gym_sev, 'Avenida de la Constitución 15', 'https://www.basic-fit.com/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dwe3bd8a27/Club%20page%20website-BasicFit%20Tilburg%2026-8-2113386.jpeg', 'Sevilla', 'BasiFit Sevilla Centro', TRUE),
(@gym_bil, 'Gran Vía 23', 'https://pcvegadelrey.com/storage/2021/11/Basic-Fit_VegadelRey_1.jpg', 'Bilbao', 'BasiFit Bilbao Gran Vía', FALSE),
(@gym_zgz, 'Calle Mayor 45', 'https://brand.basic-fit.com/match/KP_Number/5285/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:550', 'Zaragoza', 'BasiFit Zaragoza Centro', TRUE),
(@gym_mlg, 'Avenida de la Libertad 8', 'https://corporate.basic-fit.com/_next/image?url=https%3A%2F%2Fimages.ctfassets.net%2Fn2vcrsstbkc9%2F4JoY83X4htHay68W6XAlWy%2Fc7cc486be52dcabf9f269789da3c60be%2FTilburg_facade.jpg%3Fq%3D90%26fm%3Dwebp&w=2048&q=75', 'Málaga', 'BasiFit Málaga Norte', FALSE),
(@gym_ovi, 'Calle Uría 12', 'https://brand.basic-fit.com/match/KP_Number/5208/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:550', 'Santander', 'BasiFit Oviedo Centro', TRUE),
(@gym_vll, 'Paseo de Zorrilla 101', 'https://brand.basic-fit.com/match/KP_Number/5051/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:550', 'Valladolid', 'BasiFit Valladolid Sur', TRUE),
(@gym_mur, 'Avenida Juan Carlos I 54', 'https://brand.basic-fit.com/match/KP_Number/5517/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:600', 'Murcia', 'BasiFit Murcia Centro', FALSE),
(@gym_ali, 'Calle Colón 7', 'https://brand.basic-fit.com/match/KP_Number/5303/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:600', 'Alicante', 'BasiFit Alicante Playa', TRUE),
(@gym_scq, 'Rúa do Hórreo 110', 'https://brand.basic-fit.com/match/KP_Number/5521/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:600', 'Santiago de Compostela', 'BasiFit Santiago Centro', TRUE),
(@gym_lpa, 'Calle León y Castillo 200', 'https://brand.basic-fit.com/match/KP_Number/5114/Club_area/Front_club_outside/Front_club_outside?io=transform:fit,width:600', 'Las Palmas', 'BasiFit Las Palmas', FALSE);

-- Usuarios (admin/empleado/entrenador)
SET @admin = UUID();
SET @empleado = UUID();
SET @entrenador_bcn = UUID();
SET @entrenador_vlc = UUID();
SET @entrenador_mad = UUID();
SET @entrenador_sev = UUID();
SET @entrenador_zgz = UUID();
SET @entrenador_ali = UUID();
SET @entrenador_scq = UUID();

INSERT INTO usuarios (id, nombre, apellido1, apellido2, email, dni_nie, contrasena, rol, gimnasio_id, estado) VALUES
(@admin, 'Sergio', 'García', 'Martínez', 'sergio.garcia@basifit.es', '48273615K', 'BasifitAdmin2026!', 'admin', @gym_madrid, TRUE),
(@empleado, 'Marta', 'López', 'Santos', 'marta.lopez@basifit.es', '39184726M', 'EmpleadoAtocha2026', 'empleado', @gym_madrid, TRUE),
(@entrenador_bcn, 'Iván', 'Pérez', 'Ruiz', 'ivan.perez@basifit.es', '51293847H', 'TrainerBCN2026', 'entrenador', @gym_bcn, TRUE),
(@entrenador_vlc, 'Laura', 'Fernández', 'Gómez', 'laura.fernandez@basifit.es', '27461539J', 'TrainerVLC2026', 'entrenador', @gym_vlc, TRUE),
(@entrenador_mad, 'Daniel', 'Vega', 'Moreno', 'daniel.vega@basifit.es', '19384726A', 'TrainerMAD2026', 'entrenador', @gym_madrid, TRUE),
(@entrenador_sev, 'Carmen', 'Ortega', 'Luna', 'carmen.ortega@basifit.es', '28473615B', 'TrainerSEV2026', 'entrenador', @gym_sev, TRUE),
(@entrenador_zgz, 'Hugo', 'Ramírez', 'Gil', 'hugo.ramirez@basifit.es', '37592846C', 'TrainerZGZ2026', 'entrenador', @gym_zgz, TRUE),
(@entrenador_ali, 'Nuria', 'Serrano', 'Paz', 'nuria.serrano@basifit.es', '46827491D', 'TrainerALI2026', 'entrenador', @gym_ali, TRUE),
(@entrenador_scq, 'Marcos', 'Iglesias', 'Costa', 'marcos.iglesias@basifit.es', '55918273E', 'TrainerSCQ2026', 'entrenador', @gym_scq, TRUE);

-- Clientes 
SET @cli_andrea = UUID();
SET @cli_javier = UUID();
SET @cli_paula = UUID();
SET @cli_lucia = UUID();
SET @cli_alejandro = UUID();
SET @cli_sara = UUID();
SET @cli_diego = UUID();
SET @cli_maria = UUID();
SET @cli_adrian = UUID();
SET @cli_elena = UUID();
SET @cli_pablo = UUID();
SET @cli_claudia = UUID();
SET @cli_raul = UUID();
SET @cli_ines = UUID();
SET @cli_miguel = UUID();

INSERT INTO clientes (id, nombre, apellido1, apellido2, email, dni_nie, contrasena, telefono, fecha_nacimiento, ciudad, estado) VALUES
(@cli_andrea, 'Andrea', 'Romero', 'Díaz', 'andrea.romero@gmail.com', '63827491P', 'Cliente2026Atocha', '600123456', '1998-04-12', 'Madrid', TRUE),
(@cli_javier, 'Javier', 'Sánchez', 'Molina', 'javier.sanchez@gmail.com', '75192846R', 'Cliente2026BCN', '611987654', '1995-09-30', 'Barcelona', TRUE),
(@cli_paula, 'Paula', 'Navarro', 'Iglesias', 'paula.navarro@gmail.com', '40928371T', 'Cliente2026VLC', '622555111', '2001-01-21', 'Valencia', TRUE),
(@cli_lucia, 'Lucía', 'Herrera', 'Soto', 'lucia.herrera@gmail.com', '12345678F', 'Cliente2026Lucia', '600111222', '1999-06-08', 'Madrid', TRUE),
(@cli_alejandro, 'Alejandro', 'Navarro', 'Rey', 'alejandro.navarro@gmail.com', '23456789G', 'Cliente2026Ale', '600222333', '1996-03-14', 'Sevilla', TRUE),
(@cli_sara, 'Sara', 'Campos', 'Vidal', 'sara.campos@gmail.com', '34567890H', 'Cliente2026Sara', '600333444', '2002-11-21', 'Barcelona', TRUE),
(@cli_diego, 'Diego', 'Molina', 'Fuentes', 'diego.molina@gmail.com', '45678901J', 'Cliente2026Diego', '600444555', '1997-01-10', 'Valencia', TRUE),
(@cli_maria, 'María', 'Ruiz', 'López', 'maria.ruiz@gmail.com', '56789012K', 'Cliente2026Maria', '600555666', '1994-09-05', 'Zaragoza', TRUE),
(@cli_adrian, 'Adrián', 'Santos', 'Garrido', 'adrian.santos@gmail.com', '67890123L', 'Cliente2026Adrian', '600666777', '1993-12-02', 'Alicante', TRUE),
(@cli_elena, 'Elena', 'Pérez', 'Suárez', 'elena.perez@gmail.com', '78901234M', 'Cliente2026Elena', '600777888', '2000-02-28', 'Murcia', TRUE),
(@cli_pablo, 'Pablo', 'Gómez', 'Cruz', 'pablo.gomez@gmail.com', '89012345N', 'Cliente2026Pablo', '600888999', '1992-08-19', 'Santiago de Compostela', TRUE),
(@cli_claudia, 'Claudia', 'Díaz', 'Ramos', 'claudia.diaz@gmail.com', '90123456P', 'Cliente2026Claudia', '611111222', '1998-05-17', 'Málaga', TRUE),
(@cli_raul, 'Raúl', 'Torres', 'Núñez', 'raul.torres@gmail.com', '11223344Q', 'Cliente2026Raul', '611222333', '1991-04-09', 'Bilbao', TRUE),
(@cli_ines, 'Inés', 'Morales', 'Prieto', 'ines.morales@gmail.com', '22334455R', 'Cliente2026Ines', '611333444', '2003-07-03', 'Valladolid', TRUE),
(@cli_miguel, 'Miguel', 'Castro', 'Rivas', 'miguel.castro@gmail.com', '33445566S', 'Cliente2026Miguel', '611444555', '1995-10-26', 'Las Palmas', TRUE);

-- Noticias
INSERT INTO noticias (id, titulo, cuerpo, url_imagen, fecha, gimnasio_id) VALUES
(UUID(), 'Nueva zona de fuerza y peso libre',
 '¡Ya está abierta la nueva zona de fuerza! Hemos ampliado el espacio destinado al entrenamiento con peso libre para que puedas entrenar con mayor comodidad y seguridad. Ahora disponemos de nuevos bancos ajustables, mancuernas hasta 40 kg, discos olímpicos adicionales y más jaulas para sentadilla. Además, se ha mejorado la ventilación de la sala y la iluminación para un entorno más agradable. Recuerda colocar el material en su sitio al terminar y respetar los turnos en horas punta.',
 'https://www.basic-fit.com/dw/image/v2/BDFP_PRD/on/demandware.static/-/Sites-master-catalog/default/dw7379b3ac/images/ClubsES/2023/alcorc%C3%B3n_extra.jpg?q=100',
 '2026-02-05 09:30:00', @gym_madrid),
(UUID(), 'Yoga express al mediodía',
 'Incorporamos una nueva clase de Yoga Express a las 12:00 h con una duración de 45 minutos, pensada especialmente para quienes entrenan durante la pausa del trabajo. Esta sesión combina movilidad, respiración y estiramientos dinámicos para ayudarte a liberar tensión y mejorar tu postura. Las plazas son limitadas, por lo que recomendamos reservar desde la app con antelación. Llega 5 minutos antes para preparar tu esterilla y comenzar puntualmente.',
 'https://images.ctfassets.net/ztnn01luatek/2mkRC2FYL2T0mWws2mYcxl/3663873c0b123ada46941eb989da5f8e/webimage-Basicfit-groepslessen-20-10-2514147.png',
 '2026-02-06 12:15:00', @gym_bcn),
(UUID(), 'Mantenimiento de cardio (sábado)',
 'Este sábado realizaremos tareas de mantenimiento preventivo en varias máquinas de la zona de cardio entre las 07:00 y las 09:00 h. Durante este tiempo algunas cintas y bicicletas podrán estar temporalmente fuera de servicio. Estas revisiones son fundamentales para garantizar tu seguridad y el correcto funcionamiento del equipamiento. El resto de instalaciones permanecerán abiertas con normalidad. Gracias por tu comprensión.',
 'https://palco23.mundodeportivo.com/thumb/eyJ0IjoiZCIsInciOjEyMDAsImgiOjY3NSwibSI6MSwidiI6IjEuMC4xIn0/palco23/files/2025/19-fitness/basic-fit/basic-fit-lateral-1200.png',
 '2026-02-07 18:00:00', @gym_vlc),
 (UUID(), 'Nueva clase de HIIT avanzada',
 'A partir de la próxima semana estrenamos una nueva clase de HIIT avanzada dirigida a socios que ya tengan experiencia en entrenamiento de alta intensidad. La sesión combina intervalos de fuerza y cardio con tiempos de recuperación controlados para maximizar el rendimiento. Se recomienda traer toalla y botella de agua. Consulta los horarios disponibles en la app o en recepción.',
 'https://www.basic-fit.com/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dwc76eb19a/Blogs/New%20fitness%20routine/Landscape-Basicfit%20Hometools%2018%20mei%20204277.jpg',
 '2026-02-08 17:30:00', @gym_sev),
(UUID(), 'Ampliación de horario en fin de semana',
 'A partir del próximo mes ampliaremos nuestro horario de fin de semana para adaptarnos mejor a vuestros entrenamientos. Los sábados abriremos desde las 07:00 hasta las 22:00 h y los domingos de 08:00 a 20:00 h. Queremos ofrecer mayor flexibilidad para que puedas organizar tu rutina sin prisas y evitar aglomeraciones en horas punta. Seguimos trabajando para mejorar tu experiencia en el gimnasio.',
 'https://www.basic-fit.com/dw/image/v2/BDFP_PRD/on/demandware.static/-/Sites-master-catalog/default/dwec7d375b/images/ClubsES/2024/molinadesegura_entrada.jpeg?q=100',
 '2026-02-09 08:45:00', @gym_bil),
(UUID(), 'Renovación de vestuarios',
 'Nos complace anunciar que hemos completado la renovación integral de los vestuarios. Se han instalado nuevas taquillas con cierre digital, duchas con agua caliente mejorada y secadores de pelo en todos los puntos. El suelo antideslizante nuevo garantiza mayor seguridad. También se ha ampliado la zona de bancos para que puedas cambiarte con mayor comodidad. Esperamos que disfrutes de estas mejoras tanto como nosotros hemos disfrutado haciéndolas realidad.',
 'https://www.basic-fit.com/dw/image/v2/BDFP_PRD/on/demandware.static/-/Sites-master-catalog/default/dwec7d375b/images/ClubsES/2024/molinadesegura_entrada.jpeg?q=100',
 '2026-02-10 10:00:00', @gym_madrid),
(UUID(), 'Nuevo reto mensual: 30 días de constancia',
 'Lanzamos nuestro primer reto mensual para todos los socios. Durante 30 días consecutivos, cada visita al gimnasio sumará puntos que podrás canjear por descuentos en tu próxima membresía. Además, los 3 socios más constantes recibirán un mes gratis. Regístrate en recepción o desde la app antes del día 15. Las reglas completas están disponibles en el tablón de anuncios y en nuestra web. ¡Anímate y demuestra tu constancia!',
 'https://www.basic-fit.com/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dwc76eb19a/Blogs/New%20fitness%20routine/Landscape-Basicfit%20Hometools%2018%20mei%20204277.jpg',
 '2026-02-11 09:00:00', @gym_bcn),
(UUID(), 'Clases de boxeo fitness para principiantes',
 'Incorporamos al horario semanal clases de boxeo fitness orientadas a personas sin experiencia previa. Esta disciplina combina técnica de boxeo con cardio intenso, mejorando la coordinación, la resistencia y la confianza en uno mismo. Las sesiones duran 50 minutos y están impartidas por un entrenador certificado. Se proporcionan guantes y vendas en recepción. Los martes y jueves a las 19:30 h. Plazas limitadas, reserva desde la app.',
 'https://images.ctfassets.net/ztnn01luatek/2mkRC2FYL2T0mWws2mYcxl/3663873c0b123ada46941eb989da5f8e/webimage-Basicfit-groepslessen-20-10-2514147.png',
 '2026-02-12 11:30:00', @gym_vlc),
(UUID(), 'Actualización de la app BasiFit',
 'Hemos lanzado una nueva versión de nuestra aplicación con importantes mejoras. Ahora podrás ver en tiempo real la ocupación del gimnasio antes de salir de casa, reservar clases con un solo toque y recibir notificaciones personalizadas sobre tus entrenamientos. También se ha rediseñado la sección de rutinas para que sea más intuitiva. Actualiza desde la App Store o Google Play y cuéntanos qué te parece en recepción o en nuestras redes sociales.',
 'https://palco23.mundodeportivo.com/thumb/eyJ0IjoiZCIsInciOjEyMDAsImgiOjY3NSwibSI6MSwidiI6IjEuMC4xIn0/palco23/files/2025/19-fitness/basic-fit/basic-fit-lateral-1200.png',
 '2026-02-13 16:00:00', @gym_sev),
(UUID(), 'Taller de nutrición deportiva gratuito',
 'El próximo viernes 20 de febrero organizamos un taller gratuito de nutrición deportiva impartido por una dietista-nutricionista colegiada. Se abordarán temas como la importancia del desayuno antes del entrenamiento, la recuperación muscular con alimentación y cómo organizar tus comidas semanales sin complicarte. El aforo es limitado a 20 personas. Inscríbete en recepción indicando tu nombre y número de socio antes del miércoles 18.',
 'https://gymfactory.net/wp-content/uploads/2024/12/Basic-Fit.jpg',
 '2026-02-14 08:30:00', @gym_bil),
(UUID(), 'Nueva zona de estiramiento y movilidad',
 'Hemos habilitado una zona exclusiva para estiramientos y trabajo de movilidad equipada con colchonetas, rodillos de foam, bandas elásticas y bloques de yoga. Este espacio está disponible durante todo el horario de apertura sin necesidad de reserva previa. Te recomendamos dedicar al menos 10 minutos al final de cada sesión para reducir el riesgo de lesiones y mejorar tu flexibilidad progresivamente. Consulta con nuestros entrenadores si necesitas orientación sobre rutinas de movilidad.',
 'https://www.basic-fit.com/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dw3c383df2/BasicFit%20Tilburg%2026-8-2114144%20(1).jpg',
 '2026-02-15 13:00:00', @gym_mur),
(UUID(), 'Incorporación de nuevo entrenador personal',
 'Nos alegra presentar a Carlos Mendoza, nuestro nuevo entrenador personal especializado en pérdida de peso y rehabilitación postural. Carlos cuenta con más de 8 años de experiencia trabajando con todo tipo de perfiles, desde atletas hasta personas que se inician por primera vez en el gimnasio. Si estás interesado en sesiones de entrenamiento personalizado, pásate por recepción o contacta directamente con él a través de la app. La primera sesión de valoración es completamente gratuita.',
 'https://www.soydemadrid.com/images/thumbs/basic-fit-abre-un-nuevo-gym-en-alcorcon-0102808_1200.jpeg',
 '2026-02-16 10:00:00', @gym_ali),
(UUID(), 'Mantenimiento general del 20 al 21 de febrero',
 'Os informamos que los días 20 y 21 de febrero realizaremos un mantenimiento general de las instalaciones. Durante estos dos días el gimnasio permanecerá cerrado para poder revisar toda la maquinaria, renovar el sistema de climatización y realizar la limpieza profunda anual. Pedimos disculpas por las molestias ocasionadas. A partir del 22 de febrero abriremos con el horario habitual y con las instalaciones al 100%. Gracias por vuestra comprensión y fidelidad.',
 'https://www.vksport.eu/wp-content/uploads/2023/10/2022-11-21-scaled.jpg',
 '2026-02-17 09:00:00', @gym_zgz);
 
-- Salas
SET @sala_mad_1 = UUID();
SET @sala_mad_2 = UUID();
SET @sala_bcn_1 = UUID();
SET @sala_vlc_1 = UUID();
SET @sala_mad_3 = UUID();
SET @sala_bcn_2 = UUID();
SET @sala_vlc_2 = UUID();
SET @sala_sev_1 = UUID();
SET @sala_zgz_1 = UUID();
SET @sala_ali_1 = UUID();
SET @sala_scq_1 = UUID();

INSERT INTO salas (id, numero_sala, gimnasio_id, nombre, estado) VALUES
(@sala_mad_1, 1, @gym_madrid, 'Sala Ciclo', TRUE),
(@sala_mad_2, 2, @gym_madrid, 'Sala Fuerza', TRUE),
(@sala_bcn_1, 1, @gym_bcn, 'Sala Mind & Body', TRUE),
(@sala_vlc_1, 1, @gym_vlc, 'Sala Funcional', TRUE),
(@sala_mad_3, 3, @gym_madrid, 'Sala Funcional', TRUE),
(@sala_bcn_2, 2, @gym_bcn, 'Sala Cardio', TRUE),
(@sala_vlc_2, 2, @gym_vlc, 'Sala Mind & Body', TRUE),
(@sala_sev_1, 1, @gym_sev, 'Sala HIIT', TRUE),
(@sala_zgz_1, 1, @gym_zgz, 'Sala Ciclo', TRUE),
(@sala_ali_1, 1, @gym_ali, 'Sala Fuerza', TRUE),
(@sala_scq_1, 1, @gym_scq, 'Sala Core', TRUE);

-- Maquinas
SET @maq_cinta = UUID();
SET @maq_remo = UUID();
SET @maq_multipower = UUID();
SET @maq_bici = UUID();
SET @maq_eliptica = UUID();
SET @maq_prensa = UUID();
SET @maq_jalones = UUID();
SET @maq_peckdeck = UUID();
SET @maq_femoral = UUID();
SET @maq_extcuad = UUID();
SET @maq_abductores = UUID();
SET @maq_aductores = UUID();
SET @maq_gemelos = UUID();
SET @maq_pressmilitar = UUID();
SET @maq_polea = UUID();
SET @maq_triceps = UUID();
SET @maq_biceps = UUID();
SET @maq_abdominal = UUID();
SET @maq_hipthrust = UUID();
SET @maq_glute = UUID();
SET @maq_escaladora = UUID();
SET @maq_battle = UUID();
SET @maq_kettlebell = UUID();
SET @maq_mancuernas = UUID();
SET @maq_banco = UUID();
SET @maq_rack = UUID();
SET @maq_cuerda = UUID();
SET @maq_assault = UUID();
SET @maq_step = UUID();

INSERT INTO maquinas (id, nombre, gimnasio_id, descripcion, url_imagen, grupo_muscular, estado) VALUES
(@maq_cinta, 'Cinta de correr Technogym', @gym_madrid, 'Cinta con inclinación automática y programas HIIT.', 'https://images.com', 'cardio', TRUE),
(@maq_remo, 'Remo Concept2 Model D', @gym_bcn, 'Remo de aire con monitor PM5.', 'https://images.com', 'cardio', TRUE),
(@maq_multipower, 'Multipower (Smith Machine)', @gym_vlc, 'Máquina guiada para sentadilla, press y remos.', 'https://images.com', 'cuerpo_completo', TRUE),
(@maq_bici, 'Bicicleta estática LifeFitness', @gym_madrid, 'Bici indoor con resistencia magnética.', NULL, 'cardio', TRUE),
(@maq_eliptica, 'Elíptica Precor', @gym_madrid, 'Elíptica suave para trabajo cardiovascular.', NULL, 'cardio', TRUE),
(@maq_prensa, 'Prensa de piernas 45°', @gym_madrid, 'Prensa inclinada para tren inferior.', NULL, 'pierna', TRUE),
(@maq_jalones, 'Jalón al pecho', @gym_bcn, 'Polea alta para dorsales y bíceps.', NULL, 'espalda', TRUE),
(@maq_peckdeck, 'Pec Deck (Aperturas)', @gym_bcn, 'Aperturas en máquina para pectoral.', NULL, 'pecho', TRUE),
(@maq_femoral, 'Curl femoral tumbado', @gym_bcn, 'Aislamiento de isquiosurales.', NULL, 'pierna', TRUE),
(@maq_extcuad, 'Extensión de cuádriceps', @gym_vlc, 'Trabajo específico de cuádriceps.', NULL, 'pierna', TRUE),
(@maq_abductores, 'Abductores', @gym_vlc, 'Trabajo de glúteo medio.', NULL, 'pierna', TRUE),
(@maq_aductores, 'Aductores', @gym_vlc, 'Trabajo de aductores.', NULL, 'pierna', TRUE),
(@maq_gemelos, 'Gemelo sentado', @gym_sev, 'Elevación de gemelos guiada.', NULL, 'pierna', TRUE),
(@maq_pressmilitar, 'Press militar guiado', @gym_sev, 'Press hombro en máquina.', NULL, 'hombro', TRUE),
(@maq_polea, 'Polea dual ajustable', @gym_sev, 'Poleas para full body y funcional.', NULL, 'cuerpo_completo', TRUE),
(@maq_triceps, 'Fondos asistidos (Tríceps)', @gym_zgz, 'Asistencia para fondos y dominadas.', NULL, 'brazo', TRUE),
(@maq_biceps, 'Curl bíceps en máquina', @gym_zgz, 'Curl guiado para bíceps.', NULL, 'brazo', TRUE),
(@maq_abdominal, 'Crunch abdominal', @gym_ali, 'Abdominales guiados.', NULL, 'core', TRUE),
(@maq_hipthrust, 'Hip Thrust', @gym_ali, 'Empuje de cadera guiado.', NULL, 'pierna', TRUE),
(@maq_glute, 'Patada de glúteo', @gym_ali, 'Extensión de cadera en máquina.', NULL, 'pierna', TRUE),
(@maq_escaladora, 'Escaladora StairMaster', @gym_scq, 'Trabajo intenso cardiovascular.', NULL, 'cardio', TRUE),
(@maq_battle, 'Cuerdas de batalla', @gym_scq, 'Entreno HIIT y potencia.', NULL, 'cuerpo_completo', TRUE),
(@maq_kettlebell, 'Kettlebells (set completo)', @gym_madrid, 'Kettlebells de 4 a 32kg.', NULL, 'cuerpo_completo', TRUE),
(@maq_mancuernas, 'Mancuernas (set completo)', @gym_bcn, 'Mancuernas de 2 a 40kg.', NULL, 'cuerpo_completo', TRUE),
(@maq_banco, 'Banco ajustable', @gym_vlc, 'Banco multiángulo.', NULL, 'cuerpo_completo', TRUE),
(@maq_rack, 'Rack de sentadillas', @gym_madrid, 'Jaula con seguridad para sentadilla.', NULL, 'cuerpo_completo', TRUE),
(@maq_cuerda, 'Cuerda de saltar', @gym_sev, 'Cardio y coordinación.', NULL, 'cardio', TRUE),
(@maq_assault, 'Assault Bike', @gym_zgz, 'Bici de aire (HIIT).', NULL, 'cardio', TRUE),
(@maq_step, 'Step (plataformas)', @gym_ali, 'Plataformas para step y funcional.', NULL, 'cardio', TRUE);

-- Clases (nombre + nivel + capacidad + gimnasio)
SET @clase_spin = UUID();
SET @clase_pump = UUID();
SET @clase_yoga = UUID();
SET @clase_pilates = UUID();
SET @clase_zumba_mad = UUID();
SET @clase_hiit_mad = UUID();
SET @clase_cross_mad = UUID();
SET @clase_box_bcn = UUID();
SET @clase_core_bcn = UUID();
SET @clase_stretch_bcn = UUID();
SET @clase_gap_vlc = UUID();
SET @clase_yoga_vlc2 = UUID();
SET @clase_pilates_vlc2 = UUID();
SET @clase_hiit_sev = UUID();
SET @clase_spin_zgz = UUID();
SET @clase_cross_zgz = UUID();
SET @clase_gap_ali = UUID();
SET @clase_core_scq = UUID();

INSERT INTO clases (id, nombre, descripcion, nivel, fecha, hora_inicio, hora_final, duracion_minutos, capacidad, estado, sala_id, entrenador_id, gimnasio_id) VALUES
(@clase_spin, 'Spinning', 'Sesión de cardio en bici con intervalos.', 'intermedio', '2026-02-10', '18:00:00', '18:50:00', 50, 22, 'programada', @sala_mad_1, @entrenador_bcn, @gym_madrid),
(@clase_pump, 'Body Pump', 'Fuerza con barra y música, full body.', 'intermedio', '2026-02-10', '19:10:00', '20:00:00', 50, 20, 'programada', @sala_mad_2, @entrenador_bcn, @gym_madrid),
(@clase_yoga, 'Yoga', 'Movilidad, respiración y relajación.', 'principiante', '2026-02-11', '12:00:00', '12:45:00', 45, 18, 'programada', @sala_bcn_1, @entrenador_bcn, @gym_bcn),
(@clase_pilates, 'Pilates', 'Core, postura y control del movimiento.', 'principiante', '2026-02-12', '09:30:00', '10:15:00', 45, 16, 'programada', @sala_vlc_1, @entrenador_vlc, @gym_vlc),
(@clase_zumba_mad, 'Zumba', 'Cardio bailando para quemar calorías y mejorar coordinación.', 'principiante', '2026-03-03', '18:00:00', '18:50:00', 50, 25, 'programada', @sala_mad_3, @entrenador_mad, @gym_madrid),
(@clase_hiit_mad, 'HIIT', 'Intervalos de alta intensidad con fuerza y cardio.', 'avanzado', '2026-03-04', '19:10:00', '20:00:00', 50, 18, 'programada', @sala_mad_3, @entrenador_mad, @gym_madrid),
(@clase_cross_mad, 'Cross Training', 'Circuito funcional y trabajo completo.', 'intermedio', '2026-03-06', '18:30:00', '19:20:00', 50, 20, 'programada', @sala_mad_3, @entrenador_mad, @gym_madrid),

(@clase_box_bcn, 'Boxeo Fitness', 'Golpes básicos + rounds de cardio.', 'intermedio', '2026-03-05', '19:30:00', '20:20:00', 50, 20, 'programada', @sala_bcn_2, @entrenador_bcn, @gym_bcn),
(@clase_core_bcn, 'Core Express', 'Core y estabilidad. Ideal como extra de entrenamiento.', 'principiante', '2026-03-07', '12:00:00', '12:35:00', 35, 22, 'programada', @sala_bcn_1, @entrenador_bcn, @gym_bcn),
(@clase_stretch_bcn, 'Stretching', 'Movilidad articular y flexibilidad.', 'principiante', '2026-03-08', '10:30:00', '11:10:00', 40, 25, 'programada', @sala_bcn_1, @entrenador_bcn, @gym_bcn),

(@clase_gap_vlc, 'GAP', 'Tonificación tren inferior y core.', 'principiante', '2026-03-04', '18:10:00', '19:00:00', 50, 20, 'programada', @sala_vlc_1, @entrenador_vlc, @gym_vlc),
(@clase_yoga_vlc2, 'Yoga', 'Movilidad + respiración + relajación.', 'principiante', '2026-03-06', '12:00:00', '12:45:00', 45, 18, 'programada', @sala_vlc_2, @entrenador_vlc, @gym_vlc),
(@clase_pilates_vlc2, 'Pilates', 'Core, postura y control del movimiento.', 'principiante', '2026-03-09', '09:30:00', '10:15:00', 45, 18, 'programada', @sala_vlc_2, @entrenador_vlc, @gym_vlc),

(@clase_hiit_sev, 'HIIT', 'Alta intensidad, técnica y progresión.', 'avanzado', '2026-03-05', '18:00:00', '18:45:00', 45, 16, 'programada', @sala_sev_1, @entrenador_sev, @gym_sev),

(@clase_spin_zgz, 'Spinning', 'Ciclo indoor con intervalos y música.', 'intermedio', '2026-03-06', '19:00:00', '19:50:00', 50, 22, 'programada', @sala_zgz_1, @entrenador_zgz, @gym_zgz),
(@clase_cross_zgz, 'Cross Training', 'Circuitos de fuerza + cardio.', 'intermedio', '2026-03-08', '11:00:00', '11:50:00', 50, 20, 'programada', @sala_zgz_1, @entrenador_zgz, @gym_zgz),

(@clase_gap_ali, 'GAP', 'Glúteos, abdomen y piernas.', 'principiante', '2026-03-07', '18:30:00', '19:20:00', 50, 20, 'programada', @sala_ali_1, @entrenador_ali, @gym_ali),

(@clase_core_scq, 'Core Express', 'Trabajo de core y estabilidad.', 'principiante', '2026-03-09', '19:00:00', '19:35:00', 35, 20, 'programada', @sala_scq_1, @entrenador_scq, @gym_scq);

-- Reservas
INSERT INTO reservas (id, cliente_id, clase_id, estado) VALUES
(UUID(), @cli_andrea, @clase_spin, 'activa'),
(UUID(), @cli_javier, @clase_pump, 'activa'),
(UUID(), @cli_paula, @clase_yoga, 'activa'),
(UUID(), @cli_andrea, @clase_zumba_mad, 'activa'),
(UUID(), @cli_andrea, @clase_hiit_mad, 'activa'),
(UUID(), @cli_andrea, @clase_cross_mad, 'activa'),
(UUID(), @cli_javier, @clase_box_bcn, 'activa'),
(UUID(), @cli_javier, @clase_core_bcn, 'activa'),
(UUID(), @cli_javier, @clase_stretch_bcn, 'activa'),
(UUID(), @cli_paula, @clase_gap_vlc, 'activa'),
(UUID(), @cli_paula, @clase_yoga_vlc2, 'activa'),
(UUID(), @cli_paula, @clase_pilates_vlc2, 'activa'),
(UUID(), @cli_lucia, @clase_zumba_mad, 'activa'),
(UUID(), @cli_lucia, @clase_core_bcn, 'activa'),
(UUID(), @cli_alejandro, @clase_hiit_sev, 'activa'),
(UUID(), @cli_sara, @clase_box_bcn, 'activa'),
(UUID(), @cli_diego, @clase_gap_vlc, 'activa'),
(UUID(), @cli_maria, @clase_spin_zgz, 'activa'),
(UUID(), @cli_adrian, @clase_gap_ali, 'activa'),
(UUID(), @cli_pablo, @clase_core_scq, 'activa'),
(UUID(), @cli_claudia, @clase_stretch_bcn, 'activa'),
(UUID(), @cli_raul, @clase_cross_zgz, 'activa'),
(UUID(), @cli_ines, @clase_yoga_vlc2, 'activa'),
(UUID(), @cli_miguel, @clase_hiit_mad, 'activa'),
(UUID(), @cli_elena, @clase_gap_ali, 'activa'),
(UUID(), @cli_elena, @clase_core_scq, 'activa'),
(UUID(), @cli_maria, @clase_cross_zgz, 'activa'),
(UUID(), @cli_sara, @clase_stretch_bcn, 'activa'),
(UUID(), @cli_diego, @clase_pilates_vlc2, 'activa'),
(UUID(), @cli_pablo, @clase_box_bcn, 'activa'),
(UUID(), @cli_claudia, @clase_core_bcn, 'activa'),
(UUID(), @cli_raul, @clase_spin_zgz, 'activa'),
(UUID(), @cli_ines, @clase_zumba_mad, 'activa'),
(UUID(), @cli_miguel, @clase_cross_mad, 'activa'),
(UUID(), @cli_alejandro, @clase_cross_mad, 'activa'),
(UUID(), @cli_alejandro, @clase_box_bcn, 'activa'),
(UUID(), @cli_adrian, @clase_zumba_mad, 'activa'),
(UUID(), @cli_adrian, @clase_spin_zgz, 'activa'),
(UUID(), @cli_elena, @clase_stretch_bcn, 'activa'),
(UUID(), @cli_claudia, @clase_gap_vlc, 'activa'),
(UUID(), @cli_maria, @clase_hiit_sev, 'activa'),
(UUID(), @cli_lucia, @clase_gap_ali, 'activa'),
(UUID(), @cli_pablo, @clase_spin_zgz, 'activa'),
(UUID(), @cli_ines, @clase_core_scq, 'activa');

-- Membresias
INSERT INTO membresias (id, fecha_inicio, fecha_final, estado, duracion, calidad, precio, cliente_id) VALUES
(UUID(), '2026-02-01', '2026-02-28', TRUE, 'mensual', 'premium', 49.99, @cli_andrea),
(UUID(), '2026-01-01', '2026-01-31', FALSE, 'mensual', 'comfort', 34.99, @cli_javier),
(UUID(), '2026-02-01', '2027-01-31', TRUE, 'anual', 'ultimate', 499.00, @cli_paula);

-- Rutinas (con objetivo, nivel, entrenador opcional)
SET @rutina_full = UUID();
SET @rutina_cardio = UUID();
SET @rutina_hiper = UUID();
SET @rut_1 = UUID();  SET @rut_2 = UUID();  SET @rut_3 = UUID();  SET @rut_4 = UUID();  SET @rut_5 = UUID();
SET @rut_6 = UUID();  SET @rut_7 = UUID();  SET @rut_8 = UUID();  SET @rut_9 = UUID();  SET @rut_10 = UUID();
SET @rut_11 = UUID(); SET @rut_12 = UUID(); SET @rut_13 = UUID(); SET @rut_14 = UUID(); SET @rut_15 = UUID();
SET @rut_16 = UUID(); SET @rut_17 = UUID(); SET @rut_18 = UUID(); SET @rut_19 = UUID(); SET @rut_20 = UUID();
SET @rut_21 = UUID(); SET @rut_22 = UUID(); SET @rut_23 = UUID(); SET @rut_24 = UUID(); SET @rut_25 = UUID();

INSERT INTO rutinas (id, nombre, objetivo, nivel, dias_por_semana, notas, cliente_id, entrenador_id) VALUES
(@rutina_full, 'Full Body Fuerza (3 días)', 'ganar_masa', 'intermedio', 3, 'Prioriza técnica. Aumenta peso progresivo semanal.', @cli_andrea, @entrenador_bcn),
(@rutina_cardio, 'Cardio + Core (2-3 días)', 'perder_grasa', 'principiante', 3, 'Mantén pulsaciones moderadas y constancia.', @cli_javier, @entrenador_bcn),
(@rutina_hiper, 'Hipertrofia tren superior', 'ganar_masa', 'avanzado', 4, 'Volumen alto. Descansos estrictos.', @cli_paula, @entrenador_vlc),
(@rut_1, 'Inicio Full Body (3 días)', 'salud', 'principiante', 3, 'Aprende técnica y controla la postura.', @cli_lucia, @entrenador_mad),
(@rut_2, 'Fuerza Base (4 días)', 'ganar_masa', 'intermedio', 4, 'Progresión semanal: +2.5kg cuando completes reps.', @cli_andrea, @entrenador_mad),
(@rut_3, 'Pérdida de grasa (3 días + cardio)', 'perder_grasa', 'principiante', 3, 'Añade 15-20 min cardio zona 2 al final.', @cli_elena, NULL),
(@rut_4, 'Hipertrofia Push/Pull/Legs', 'ganar_masa', 'avanzado', 6, 'Controla descansos y rango completo.', @cli_paula, @entrenador_vlc),
(@rut_5, 'Mantenimiento express (2-3 días)', 'mantenimiento', 'principiante', 3, 'Rutina corta de 45 min.', @cli_javier, NULL),
(@rut_6, 'Glúteo y Pierna (3 días)', 'ganar_masa', 'intermedio', 3, 'Prioriza técnica en prensa y hip thrust.', @cli_maria, @entrenador_zgz),
(@rut_7, 'Espalda y postura (3 días)', 'salud', 'principiante', 3, 'Trabajo de dorsal + movilidad.', @cli_pablo, @entrenador_scq),
(@rut_8, 'Cardio + Core (4 días)', 'perder_grasa', 'intermedio', 4, 'Combina escaladora y planchas.', @cli_diego, NULL),
(@rut_9, 'Fuerza tren superior (4 días)', 'ganar_masa', 'intermedio', 4, 'Progresión en press y jalones.', @cli_raul, NULL),
(@rut_10, 'Full body funcional', 'salud', 'principiante', 3, 'Poleas + mancuernas + core.', @cli_ines, NULL),
(@rut_11, 'HIIT + fuerza (3 días)', 'perder_grasa', 'avanzado', 3, 'Alta intensidad, vigila recuperación.', @cli_alejandro, @entrenador_sev),
(@rut_12, 'Acondicionamiento general', 'salud', 'principiante', 3, 'Para ganar energía y hábito.', @cli_sara, NULL),
(@rut_13, 'Hipertrofia pierna (4 días)', 'ganar_masa', 'avanzado', 4, 'Volumen alto. Descansos 90-120s.', @cli_miguel, NULL),
(@rut_14, 'Core & movilidad', 'salud', 'principiante', 2, 'Movilidad diaria + core 2 días.', @cli_lucia, NULL),
(@rut_15, 'Fuerza 5x5 (3 días)', 'ganar_masa', 'intermedio', 3, '5x5 en básicos (adaptado).', @cli_andrea, @entrenador_mad),
(@rut_16, 'Cardio suave + tonificación', 'perder_grasa', 'principiante', 3, 'Cinta y elíptica + circuito ligero.', @cli_elena, NULL),
(@rut_17, 'Upper/Lower (4 días)', 'ganar_masa', 'intermedio', 4, 'División superior/inferior.', @cli_javier, NULL),
(@rut_18, 'Full Body + máquinas', 'salud', 'principiante', 3, 'Perfecta para iniciarte con seguridad.', @cli_maria, @entrenador_zgz),
(@rut_19, 'Potencia y acondicionamiento', 'mantenimiento', 'avanzado', 5, 'Cuerdas batalla + assault bike.', @cli_adrian, NULL),
(@rut_20, 'Recomposición (4 días)', 'mantenimiento', 'intermedio', 4, 'Equilibrio fuerza y cardio.', @cli_sara, NULL),
(@rut_21, 'Pierna sin impacto (3 días)', 'salud', 'principiante', 3, 'Prensa, extensiones y abductores.', @cli_paula, NULL),
(@rut_22, 'Espalda fuerte (3 días)', 'ganar_masa', 'intermedio', 3, 'Jalón + remo + core.', @cli_pablo, NULL),
(@rut_23, 'Funcional en casa (2-3 días)', 'salud', 'principiante', 3, 'Peso corporal + cuerda.', @cli_ines, NULL),
(@rut_24, 'Hipertrofia pecho/hombro', 'ganar_masa', 'avanzado', 4, 'Controla técnica en press.', @cli_miguel, NULL),
(@rut_25, 'Cardio y resistencia (3 días)', 'perder_grasa', 'intermedio', 3, 'Z2 + intervalos 1 día.', @cli_diego, NULL);


-- Ejercicios (series/reps/peso/descanso)
INSERT INTO ejercicios (id, nombre, orden, series, repeticiones, peso, descanso_segundos, notas, rutina_id, maquina_id) VALUES
(UUID(), 'Calentamiento en cinta', 1, 1, 10, NULL, 0, 'Ritmo suave 10 minutos.', @rutina_full, @maq_cinta),
(UUID(), 'Sentadilla guiada', 2, 4, 10, 40.00, 90, 'Controla la bajada.', @rutina_full, @maq_multipower),
(UUID(), 'Remo (cardio)', 1, 1, 8, NULL, 0, '8 minutos a intensidad moderada.', @rutina_cardio, @maq_remo),
(UUID(), 'Plancha frontal', 2, 3, 45, NULL, 45, 'Mantén abdomen activo.', @rutina_cardio, NULL),
(UUID(), 'Press banca (barra)', 1, 4, 8, 50.00, 120, 'Sin rebotes, recorrido completo.', @rutina_hiper, NULL),
(UUID(), 'Cinta (calentamiento)', 1, 1, 10, NULL, 0, '10 minutos ritmo suave.', @rut_1, @maq_cinta),
(UUID(), 'Prensa de piernas', 2, 3, 12, 80.00, 90, 'Controla el recorrido.', @rut_1, @maq_prensa),
(UUID(), 'Press banca (mancuernas)', 1, 4, 10, NULL, 90, 'Rango completo y control.', @rut_2, NULL),
(UUID(), 'Jalón al pecho', 2, 4, 10, NULL, 90, 'Escápulas abajo.', @rut_2, @maq_jalones),
(UUID(), 'Elíptica (cardio)', 1, 1, 15, NULL, 0, '15 min zona 2.', @rut_3, @maq_eliptica),
(UUID(), 'Crunch abdominal', 2, 3, 15, NULL, 60, 'No tires del cuello.', @rut_3, @maq_abdominal),
(UUID(), 'Hip Thrust', 1, 4, 8, NULL, 120, 'Pausa 1s arriba.', @rut_4, @maq_hipthrust),
(UUID(), 'Pec Deck', 2, 4, 12, NULL, 90, 'Apertura controlada.', @rut_4, @maq_peckdeck),
(UUID(), 'Bici estática', 1, 1, 12, NULL, 0, '12 min suave.', @rut_5, @maq_bici),
(UUID(), 'Polea (face pull)', 2, 3, 15, NULL, 60, 'Hombros atrás.', @rut_5, @maq_polea),
(UUID(), 'Extensión cuádriceps', 1, 4, 12, NULL, 75, 'Sube fuerte, baja lento.', @rut_6, @maq_extcuad),
(UUID(), 'Curl femoral', 2, 4, 12, NULL, 75, 'Controla la excéntrica.', @rut_6, @maq_femoral),
(UUID(), 'Jalón al pecho', 1, 4, 10, NULL, 90, 'Sin balanceos.', @rut_7, @maq_jalones),
(UUID(), 'Plancha', 2, 3, 45, NULL, 45, 'Core firme.', @rut_7, NULL),
(UUID(), 'Escaladora', 1, 1, 12, NULL, 0, '12 min moderado.', @rut_8, @maq_escaladora),
(UUID(), 'Crunch abdominal', 2, 3, 15, NULL, 60, 'Respira y controla.', @rut_8, @maq_abdominal),
(UUID(), 'Press militar guiado', 1, 4, 10, NULL, 90, 'Codos debajo de muñecas.', @rut_9, @maq_pressmilitar),
(UUID(), 'Curl bíceps en máquina', 2, 3, 12, NULL, 60, 'Sin impulso.', @rut_9, @maq_biceps),
(UUID(), 'Polea (remo)', 1, 3, 12, NULL, 75, 'Espalda neutra.', @rut_10, @maq_polea),
(UUID(), 'Kettlebell swing', 2, 3, 15, NULL, 60, 'Bisagra de cadera.', @rut_10, @maq_kettlebell),
(UUID(), 'Assault Bike (intervalos)', 1, 6, 20, NULL, 40, '20s fuerte / 40s suave.', @rut_11, @maq_assault),
(UUID(), 'Cuerdas de batalla', 2, 6, 20, NULL, 40, 'Técnica rápida.', @rut_11, @maq_battle);

-- Catalogo de clases 
INSERT INTO catalogo_clases (id, nombre, descripcion, nivel_recomendado, url_imagen, estado) VALUES
(UUID(), 'Spinning', 'Clase de cardio en bicicleta con intervalos y música.', 'intermedio', 'https://www.basic-fit.com/dw/image/v2/BDFP_PRD/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dwfb33d197/Roots/Content_Website/grouplessons/Virtual%20Cycling%20Hero%20Header.jpg?sw=1440&sfrm=jpeg', TRUE),
(UUID(), 'Body Pump', 'Entrenamiento de fuerza con barra para todo el cuerpo.', 'intermedio', 'https://images.ctfassets.net/ztnn01luatek/2mkRC2FYL2T0mWws2mYcxl/3663873c0b123ada46941eb989da5f8e/webimage-Basicfit-groepslessen-20-10-2514147.png', TRUE),
(UUID(), 'Yoga', 'Movilidad, respiración y relajación. Apta para todos los niveles.', 'principiante', 'https://i.blogs.es/6de6cc/istock_000076729603_medium/1366_2000.jpg', TRUE),
(UUID(), 'Pilates', 'Trabajo de core, postura y control del movimiento.', 'principiante', 'https://www.basic-fit.com/dw/image/v2/BDFP_PRD/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dw190bb708/Roots/Blog/Blog-Header/Pilates.png?sw=968', TRUE),
(UUID(), 'Zumba', 'Cardio bailando con coreografías sencillas y divertidas.', 'principiante', 'https://www.basic-fit.com/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dwdd7e94e0/1.%20new%20VI%20images/Group%20classes/01.01.2030-group-classes-dance.jpg', TRUE),
(UUID(), 'HIIT', 'Intervalos de alta intensidad combinando fuerza y cardio.', 'avanzado', 'https://www.basic-fit.com/dw/image/v2/BDFP_PRD/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dw359f1944/Roots/Blog/Blog-Header/1088x612/Blog_training_Teddy_Riner_fitness.jpg?sw=968', TRUE),
(UUID(), 'Cross Training', 'Circuitos funcionales con cargas y peso corporal.', 'intermedio', 'https://www.basic-fit.com/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dw2d9b68c3/pregnancy/Square-BasicFit%20Tilburg%2026-8-2114249.jpeg', TRUE),
(UUID(), 'Boxeo Fitness', 'Técnica básica y rounds cardio con saco/sombra.', 'intermedio', 'https://karkoa.com/wp-content/uploads/2024/05/fit-boxing-1024x683.jpg', TRUE),
(UUID(), 'GAP', 'Glúteos, Abdomen y Piernas. Tonificación y resistencia.', 'principiante', 'https://cdn.static.aptavs.com/imagenes/que-es-el-gap-y-cuales-son-los-beneficios-de-este-ejercicio.jpg', TRUE),
(UUID(), 'Core Express', 'Trabajo específico de abdomen y estabilidad lumbo-pélvica.', 'principiante', 'https://stcamaleonmediapro.blob.core.windows.net/web/2021/03/Core.jpg', TRUE),
(UUID(), 'Stretching', 'Flexibilidad y movilidad para prevenir lesiones.', 'principiante', 'https://chironptva.com/wp-content/uploads/2020/01/AdobeStock_132111328-1024x683.jpeg', TRUE),
(UUID(), 'Ciclo Indoor Pro', 'Spinning con bloques de fuerza y sprint.', 'avanzado', 'https://www.basic-fit.com/dw/image/v2/BDFP_PRD/on/demandware.static/-/Library-Sites-basic-fit-shared-library/default/dwfb33d197/Roots/Content_Website/grouplessons/Virtual%20Cycling%20Hero%20Header.jpg?sw=1440&sfrm=jpeg', TRUE);

-- LOGIN EN DESARROLLO

UPDATE usuarios
SET contrasena = CONCAT('{noop}', contrasena)
WHERE contrasena NOT LIKE '{%}%';

UPDATE clientes
SET contrasena = CONCAT('{noop}', contrasena)
WHERE contrasena NOT LIKE '{%}%';