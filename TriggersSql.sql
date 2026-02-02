DELIMITER $$

USE pr_transversal$$

-- =====================================
-- Limpia triggers existentes
-- =====================================
DROP TRIGGER IF EXISTS insert_clase_ocupada$$
DROP TRIGGER IF EXISTS update_clase_ocupada$$
DROP TRIGGER IF EXISTS permisos_insert_clase$$
DROP TRIGGER IF EXISTS permisos_update_clase$$


-- =====================================
-- Comprueba que dos clases no se pisen al INSERTAR
-- =====================================
CREATE TRIGGER insert_clase_ocupada
BEFORE INSERT ON clases
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1
        FROM clases c
        WHERE c.sala_id = NEW.sala_id
          AND c.fecha = NEW.fecha
          AND c.hora_inicio < NEW.hora_final
          AND c.hora_final > NEW.hora_inicio
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La sala ya está ocupada en ese horario';
    END IF;
END$$


-- =====================================
-- Comprueba que dos clases no se pisen al ACTUALIZAR
-- =====================================
CREATE TRIGGER update_clase_ocupada
BEFORE UPDATE ON clases
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1
        FROM clases c
        WHERE c.sala_id = NEW.sala_id
          AND c.fecha = NEW.fecha
          AND c.hora_inicio < NEW.hora_final
          AND c.hora_final > NEW.hora_inicio
          AND c.id <> OLD.id
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La sala ya está ocupada en ese horario';
    END IF;
END$$


-- =====================================
-- Comprueba que el usuario tenga rol entrenador o admin al INSERTAR
-- =====================================
CREATE TRIGGER permisos_insert_clase
BEFORE INSERT ON clases
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1
        FROM usuarios u
        WHERE u.id = NEW.id_usuarios_c
          AND u.ROL NOT IN ('entrenador', 'admin')
    ) THEN
        SIGNAL SQLSTATE '22010'
        SET MESSAGE_TEXT = 'El usuario que crea esta clase no es entrenador ni admin';
    END IF;
END$$


-- =====================================
-- Comprueba que el usuario tenga rol entrenador o admin al ACTUALIZAR
-- =====================================
CREATE TRIGGER permisos_update_clase
BEFORE UPDATE ON clases
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1
        FROM usuarios u
        WHERE u.id = NEW.id_usuarios_c
          AND u.ROL NOT IN ('entrenador', 'admin')
    ) THEN
        SIGNAL SQLSTATE '22010'
        SET MESSAGE_TEXT = 'El usuario que crea esta clase no es entrenador ni admin';
    END IF;
END$$

DELIMITER ;
