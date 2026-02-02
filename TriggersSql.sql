DELIMITER $$

use pr_transversal;

-- Comprueba que dos clases no se pisen al insertar una nueva

CREATE TRIGGER insert_clase_ocupada
BEFORE INSERT ON clases
FOR EACH ROW
BEGIN
	IF EXIST (
    
		SELECT 1
        FROM clases c
        WHERE c.sala_id = NEW.sala_id
        AND C.fecha = NEW.fecha
        AND c.hora_inicio < NEW.hora_final
        AND c.hora_final > NEW.hora_inicio
    
    ) THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La sala ya está ocupada en ese horario'
	END IF;

-- Comprueba que dos clases no se pisen al actualizar una de ellas

CREATE TRIGGER update_clase_ocupada
BEFORE UPDATE ON clases
FOR EACH ROW
BEGIN
	IF EXIST (
    
		SELECT 1
        FROM clases c
        WHERE c.sala_id = NEW.sala_id
        AND C.fecha = NEW.fecha
        AND c.hora_inicio < NEW.hora_final
        AND c.hora_final > NEW.hora_inicio
    
    ) THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La sala ya está ocupada en ese horario'
	END IF;
    
    -- Comprueba que al insertar una clase el usuario tenga un rol de entrenador
CREATE TRIGGER permisos_insert_clase
	BEFORE INSERT ON clases
	FOR EACH ROW
	BEGIN
		IF EXIST (
		
			SELECT 1
			FROM usuarios u
			WHERE u.id = NEW.usuarios_id
            AND WHERE u.ROL != 'entrenador' OR u.ROL = 'admin'
		
		) THEN
			SIGNAL SQLSTATE '22010'
			SET MESSAGE_TEXT = 'El usuario que crea esta clase no es un entrenador'
		END IF;
        
	-- Comprueba que al actualizar una clase el usuario tenga un rol de entrenador
        
CREATE TRIGGER permisos_update_clase
	BEFORE UPDATE ON clases
	FOR EACH ROW
	BEGIN
		IF EXIST (
		
			SELECT 1
			FROM usuarios u
			WHERE u.id = NEW.usuarios_id
            AND WHERE u.ROL != 'entrenador' OR u.ROL = 'admin'
		
		) THEN
			SIGNAL SQLSTATE '22010'
			SET MESSAGE_TEXT = 'El usuario que crea esta clase no es un entrenador'
		END IF;        
    
ENDS$$