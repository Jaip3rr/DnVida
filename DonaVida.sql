-- =========== Creacion de la base de datos =========

CREATE DATABASE DonaVida
USE DonaVida

--SCRIPT PARA CREAR EL LOGIN Y USUARIO NORMAL DE LA BASE DE DATOS
-- Es importante ESTAR UTILIZANDO LA BASE DE DATOS DonaVida para que se crea el usuario normal
-- en dicha base de datos

USE DonaVida
CREATE LOGIN adminEmpresaurios WITH PASSWORD = 'pa$$word1'
CREATE USER db_donavida FOR LOGIN adminEmpresaurios
ALTER SERVER ROLE sysadmin ADD MEMBER adminEmpresaurios

-- HASTA AQUI SE EJECUTA PARA LA CREACION DE LOGIN Y USUARIO NORMAL DE LA BASE DE DATOS

-- =========== Generacion de las tablas ===============

-- Tabla Usuarios
CREATE TABLE Usuarios(
	id INT IDENTITY(1,1) CONSTRAINT pk_usa PRIMARY KEY,
	nombre VARCHAR(30) NOT NULL,
	apellido VARCHAR(30) NOT NULL,
	correo VARCHAR(50) NOT NULL CONSTRAINT chk_correo CHECK (correo LIKE '%@%.%'),
	direccion VARCHAR(50) NOT NULL,
	tipo_usuario VARCHAR(15) DEFAULT 'Navegante',
	contraseña VARCHAR(10) NOT NULL
);

CREATE TRIGGER tr_upd_tipo_usuario
ON Usuarios
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    
    UPDATE U
    SET tipo_usuario = CASE U.contraseña
                            WHEN 'Administra' THEN 'Administrador'
                            ELSE 'Navegante'
                      END
    FROM Usuarios U
    INNER JOIN inserted I ON U.id = I.id;
END;

-- Tabla Hospital
CREATE TABLE Hospitales(
	id INT IDENTITY(1,1) CONSTRAINT pk_hos PRIMARY KEY,
	nombre VARCHAR(30) NOT NULL,
	direccion VARCHAR(50) NOT NULL,
	colonia VARCHAR(50) NOT NULL,
	correo VARCHAR(50) NOT NULL CONSTRAINT chk_correo2 CHECK (correo LIKE '%@%.%'),
	telefono VARCHAR(10) CHECK (telefono LIKE '[0-9]%' AND LEN(telefono) <= 10) NOT NULL
)

-- Tabla Pacientes
CREATE TABLE Pacientes(
	id INT IDENTITY(1,1) CONSTRAINT pk_pac PRIMARY KEY,
	id_hospital INT NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
	sexo VARCHAR(10) NOT NULL,
	direccion VARCHAR(50) NOT NULL,
	colonia VARCHAR(50) NOT NULL,
	tipoSangre VARCHAR(5) CHECK (tipoSangre IN ('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-')) NOT NULL,
	telefono VARCHAR(10) CHECK (telefono LIKE '[0-9]%' AND LEN(telefono) <= 10) NOT NULL,
	fechaRegistro VARCHAR(10) CHECK (fechaRegistro LIKE '__/__/____'),
	
CONSTRAINT fk_pac_hos FOREIGN KEY (id_hospital) REFERENCES Hospitales(id)
)

CREATE TRIGGER tr_insertar_solicitante
ON Pacientes
AFTER INSERT
AS
BEGIN
    -- Insertar los datos en la tabla de Solicitantes
    INSERT INTO Solicitantes (id_hospital, nombre, apellido, sexo, direccion, colonia, tipoSangre, telefono, fechaRegistro)
    SELECT id_hospital, nombre, apellido, sexo, direccion, colonia, tipoSangre, telefono, fechaRegistro
    FROM inserted;
END;

CREATE TRIGGER trg_UpdatePaciente
ON Pacientes
AFTER UPDATE
AS
BEGIN
    UPDATE s
    SET s.nombre = i.nombre,
        s.apellido = i.apellido,
        s.sexo = i.sexo,
        s.direccion = i.direccion,
        s.colonia = i.colonia,
        s.tipoSangre = i.tipoSangre,
        s.telefono = i.telefono,
        s.fechaRegistro = i.fechaRegistro
    FROM Solicitantes s
    INNER JOIN inserted i ON s.id = i.id;
END;
GO

-- Tabla Donadores
CREATE TABLE Donadores(
	id INT IDENTITY(1,1) CONSTRAINT pk_don PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
	correo VARCHAR(50) NOT NULL CONSTRAINT chk_correo3 CHECK (correo LIKE '%@%.%'),
	sexo VARCHAR(10) NOT NULL,
	colonia VARCHAR(50) NOT NULL,
	telefono VARCHAR(10) CHECK (telefono LIKE '[0-9]%' AND LEN(telefono) <= 10) NOT NULL,
	modiCorpo VARCHAR(3) CHECK (modiCorpo IN ('si', 'no', 'SI', 'NO')) NOT NULL,
	tipoSangre VARCHAR(5) CHECK (tipoSangre IN ('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-')) NOT NULL,
	ultimaDon VARCHAR(10) CHECK (ultimaDon LIKE '__/__/____'),
	fechaRegistro VARCHAR(10) CHECK (fechaRegistro LIKE '__/__/____')
)

SELECT * FROM Donadores

-- Tabla solicitantes
CREATE TABLE Solicitantes(
	id INT IDENTITY(1,1) CONSTRAINT pk_sol PRIMARY KEY,
	id_hospital INT NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
	sexo VARCHAR(10) NOT NULL,
	direccion VARCHAR(50) NOT NULL,
	colonia VARCHAR(50) NOT NULL,
	tipoSangre VARCHAR(5) CHECK (tipoSangre IN ('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-')) NOT NULL,
	telefono VARCHAR(10) CHECK (telefono LIKE '[0-9]%' AND LEN(telefono) <= 10) NOT NULL,
	fechaRegistro VARCHAR(10) CHECK (fechaRegistro LIKE '__/__/____'),
	
CONSTRAINT fk_sol_hos FOREIGN KEY (id_hospital) REFERENCES Hospitales(id)
)

SELECT * FROM Usuarios

-- Tabla Citas
CREATE TABLE Citas(
	id INT IDENTITY(1,1) CONSTRAINT pk_cit PRIMARY KEY,
	id_hospital INT NOT NULL,
	id_solicitante INT NOT NULL,
	id_donante INT NOT NULL,
	fechaCita VARCHAR(10) CHECK (fechaCita LIKE '__/__/____'),
	statusCita VARCHAR(15) CHECK (statusCita IN ('completado', 'Completado', 'COMPLETADO', 'Pendiente', 'pendiente', 'PENDIENTE')) NOT NULL,

	CONSTRAINT fk_cit_hos FOREIGN KEY (id_hospital) REFERENCES Hospitales(id),
	CONSTRAINT fk_cit_sol FOREIGN KEY (id_solicitante) REFERENCES Solicitantes(id),
	CONSTRAINT fk_cit_don FOREIGN KEY (id_donante) REFERENCES Donadores(id)
)

select * from Donadores

DROP TABLE Citas
DROP TABLE Pacientes
DROP TABLE Solicitantes
DROP TABLE Donadores
DROP TABLE Usuarios
DROP TABLE Hospitales


-- ======================================= Insercion de datos ======================================

-- Tabla usuarios
INSERT INTO Usuarios(nombre, apellido, correo, direccion, contraseña)
VALUES('José Adrián', 'Terrones Pérez', 'jadrianterrones@outlook.com', 'Loma Bonita', 'Administra');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Juan', 'López', 'juan@example.com', 'Calle 1, Colonia Centro', '123456');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('María', 'García', 'maria@example.com', 'Avenida 2, Colonia Roma', 'qwerty');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Luis', 'Hernández', 'luis@example.com', 'Calle 3, Colonia Del Valle', 'password');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Ana', 'Martínez', 'ana@example.com', 'Avenida 4, Colonia Industrial', 'abcdef');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Carlos', 'Rodríguez', 'carlos@example.com', 'Calle 5, Colonia Obregón', 'qwerty123');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Laura', 'Luna', 'laura@example.com', 'Avenida 6, Colonia La Luz', 'password123');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Pedro', 'Sánchez', 'pedro@example.com', 'Calle 7, Colonia San Juan Bosco', 'abc123');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Gabriela', 'Torres', 'gabriela@example.com', 'Avenida 8, Colonia Los Gavilanes', '987654');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Mario', 'Vargas', 'mario@example.com', 'Calle 9, Colonia Las Palmas', 'pass1234');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Fernanda', 'López', 'fernanda@example.com', 'Avenida 10, Colonia San Isidro', 'abcdefg');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Javier', 'Gómez', 'javier@example.com', 'Calle 11, Colonia Santa Rita', 'qwertyuiop');
INSERT INTO Usuarios (nombre, apellido, correo, direccion, contraseña)
VALUES ('Isabel', 'Ortega', 'isabel@example.com', 'Avenida 12, Colonia La Campiña', 'password2021');

SELECT * FROM Usuarios

-- Tabla Hospitales
INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital General de León', 'Avenida De los Hospitales 222', 'San Pablo', 'hospitalgeneral@gmail.com', '4771234567')
INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital Médica Campestre', 'Boulevard Campestre 345', 'Colonia Campestre', 'medicacampestre@gmail.com', '4779876543')

INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital León', 'Calle 1, Colonia Centro', 'Centro', 'hospitalleon@example.com', '1234567890');

INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital Guanajuato', 'Avenida 2, Colonia Roma', 'Roma', 'hospitalguanajuato@example.com', '9876543210');

INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital Santa Fe', 'Calle 3, Colonia Del Valle', 'Del Valle', 'hospitalsantafe@example.com', '5678901234');

INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital San Francisco', 'Avenida 4, Colonia Industrial', 'Industrial', 'hospitalsanfrancisco@example.com', '4567890123');

INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital San Rafael', 'Calle 5, Colonia Obregón', 'Obregón', 'hospitalsanrafael@example.com', '3456789012');

INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital Nuestra Señora', 'Avenida 6, Colonia La Luz', 'La Luz', 'hospitalnuestraseñora@example.com', '2345678901');

INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital San Juan Bosco', 'Calle 7, Colonia San Juan Bosco', 'San Juan Bosco', 'hospitalsanjuanbosco@example.com', '1234567890');
INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital Los Gavilanes', 'Avenida 8, Colonia Los Gavilanes', 'Los Gavilanes', 'hospitalosgavilanes@example.com', '9876543210');
INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital Las Palmas', 'Calle 9, Colonia Las Palmas', 'Las Palmas', 'hospitallaspalmas@example.com', '5678901234');
INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital San Isidro', 'Avenida 10, Colonia San Isidro', 'San Isidro', 'hospitalsanisidro@example.com', '4567890123');
INSERT INTO Hospitales (nombre, direccion, colonia, correo, telefono)
VALUES ('Hospital Santa Rita', 'Calle 11, Colonia Santa Rita', 'Santa Rita', 'hospitalsantarita@example.com', '3456789012');

SELECT * FROM Hospitales

-- Tabla Pacientes
INSERT INTO Pacientes (id_hospital, nombre, apellido, sexo, direccion, colonia, tipoSangre, telefono)
VALUES
    (1, 'Laura', 'García', 'Femenino', 'Calle Juárez 123', 'Centro', 'A+', '1234567890'),
    (2, 'Roberto', 'Sánchez', 'Masculino', 'Avenida Reforma 456', 'San Jerónimo', 'B-', '2345678901'),
	(3, 'María', 'López', 'Femenino', 'Calle 13, Colonia Industrial', 'Industrial', 'O+', '3456789012'),
    (4, 'Carlos', 'González', 'Masculino', 'Avenida 14, Colonia La Luz', 'La Luz', 'A-', '4567890123'),
    (5, 'Ana', 'Hernández', 'Femenino', 'Calle 15, Colonia Obregón', 'Obregón', 'B+', '5678901234'),
    (6, 'Luis', 'Martínez', 'Masculino', 'Avenida 16, Colonia Del Valle', 'Del Valle', 'AB-', '6789012345'),
    (7, 'Laura', 'González', 'Femenino', 'Calle 17, Colonia Roma', 'Roma', 'O-', '7890123456'),
    (8, 'Roberto', 'Sánchez', 'Masculino', 'Avenida 18, Colonia San Juan Bosco', 'San Juan Bosco', 'A+', '8901234567'),
    (9, 'María', 'García', 'Femenino', 'Calle 19, Colonia Los Gavilanes', 'Los Gavilanes', 'B-', '9012345678'),
    (10, 'Carlos', 'Martínez', 'Masculino', 'Avenida 20, Colonia Las Palmas', 'Las Palmas', 'O+', '0123456789'),
    (11, 'Ana', 'Hernández', 'Femenino', 'Calle 21, Colonia San Isidro', 'San Isidro', 'A-', '9876543210'),
    (12, 'Luis', 'González', 'Masculino', 'Avenida 22, Colonia Santa Rita', 'Santa Rita', 'B+', '8765432109'),
    (13, 'Laura', 'Sánchez', 'Femenino', 'Calle 23, Colonia Centro', 'Centro', 'AB-', '7654321098'),
    (4, 'Roberto', 'García', 'Masculino', 'Avenida 24, Colonia Industrial', 'Industrial', 'O-', '6543210987'),
    (13, 'María', 'López', 'Femenino', 'Calle 25, Colonia La Luz', 'La Luz', 'A+', '5432109876'),
    (1, 'Carlos', 'Hernández', 'Masculino', 'Avenida 26, Colonia Obregón', 'Obregón', 'B-', '4321098765'),
    (7, 'Ana', 'Martínez', 'Femenino', 'Calle 27, Colonia Del Valle', 'Del Valle', 'O+', '3210987654'),
    (8, 'Luis', 'González', 'Masculino', 'Avenida 28, Colonia Roma', 'Roma', 'AB-', '2109876543');;

-- Tabla Donadores
INSERT INTO Donadores (nombre, apellido, correo, sexo, colonia, telefono, modiCorpo, tipoSangre, ultimaDon)
VALUES
    ('Juan', 'Pérez', 'juanperez@example.com', 'Masculino', 'Centro', '1234567890', 'si', 'A+', '01/01/2022'),
    ('María', 'González', 'mariagonzalez@example.com', 'Femenino', 'San Jerónimo', '2345678901', 'no', 'O-', '02/15/2022'),
    ('Carlos', 'López', 'carloslopez@example.com', 'Masculino', 'Lomas de Gran Jardín', '3456789012', 'si', 'B+', '03/20/2022'),
    ('Ana', 'Martínez', 'anamartinez@example.com', 'Femenino', 'La Martinica', '4567890123', 'no', 'AB-', '04/10/2022'),
    ('Luis', 'Hernández', 'luishernandez@example.com', 'Masculino', 'Del Valle', '5678901234', 'no', 'A+', '05/05/2022'),
	('Laura', 'Gómez', 'lauragomez@example.com', 'Femenino', 'San Jerónimo', '6789012345', 'si', 'B-', '06/15/2022'),
    ('Roberto', 'Sánchez', 'robertosanchez@example.com', 'Masculino', 'Lomas de Gran Jardín', '7890123456', 'no', 'O+', '07/20/2022'),
    ('María', 'López', 'marialopez@example.com', 'Femenino', 'La Martinica', '8901234567', 'no', 'AB-', '08/10/2022'),
    ('Carlos', 'González', 'carlosgonzalez@example.com', 'Masculino', 'Del Valle', '9012345678', 'si', 'A+', '09/05/2022'),
    ('Ana', 'Martínez', 'anamartinez@example.com', 'Femenino', 'Centro', '0123456789', 'no', 'O-', '10/01/2022'),
    ('Luis', 'Hernández', 'luishernandez@example.com', 'Masculino', 'San Jerónimo', '9876543210', 'si', 'B+', '11/15/2022'),
    ('Laura', 'Gómez', 'lauragomez@example.com', 'Femenino', 'Lomas de Gran Jardín', '8765432109', 'no', 'A-', '12/20/2022'),
    ('Roberto', 'Sánchez', 'robertosanchez@example.com', 'Masculino', 'La Martinica', '7654321098', 'no', 'AB-', '01/10/2023'),
    ('María', 'López', 'marialopez@example.com', 'Femenino', 'Del Valle', '6543210987', 'si', 'O+', '02/05/2023'),
    ('Carlos', 'González', 'carlosgonzalez@example.com', 'Masculino', 'Centro', '5432109876', 'no', 'B-', '03/01/2023'),
    ('Ana', 'Martínez', 'anamartinez@example.com', 'Femenino', 'San Jerónimo', '4321098765', 'si', 'A+', '04/15/2023'),
    ('Luis', 'Hernández', 'luishernandez@example.com', 'Masculino', 'Lomas de Gran Jardín', '3210987654', 'no', 'O-', '05/20/2023'),
    ('Laura', 'Gómez', 'lauragomez@example.com', 'Femenino', 'La Martinica', '2109876543', 'no', 'AB-', '06/10/2023'),
    ('Roberto', 'Sánchez', 'robertosanchez@example.com', 'Masculino', 'Del Valle', '1098765432', 'si', 'A+', '07/05/2023');

-- Tabla Solicitantes
INSERT INTO Solicitantes (id_hospital, nombre, apellido, sexo, direccion, colonia, tipoSangre, telefono, fechaRegistro)
VALUES (1, 'Juan', 'Perez', 'Masculino', 'Calle 123', 'Colonia Centro', 'A+', '1234567890', '01/01/2023', '21/05/2023');

INSERT INTO Solicitantes (id_hospital, nombre, apellido, sexo, direccion, colonia, tipoSangre, telefono, fechaRegistro)
VALUES (2, 'Maria', 'Lopez', 'Femenino', 'Avenida 456', 'Colonia Norte', 'B-', '9876543210', '05/15/2023', '21/05/2023');

INSERT INTO Solicitantes (id_hospital, nombre, apellido, sexo, direccion, colonia, tipoSangre, telefono, fechaRegistro)
VALUES (3, 'Carlos', 'González', 'Masculino', 'Calle 789', 'Colonia Sur', 'O+', '4567890123', '09/20/2023', '21/05/2023');

INSERT INTO Solicitantes (id_hospital, nombre, apellido, sexo, direccion, colonia, tipoSangre, telefono, fechaRegistro)
VALUES (4, 'Ana', 'Martínez', 'Femenino', 'Avenida 012', 'Colonia Este', 'AB-', '3456789012', '10/10/2023', '21/05/2023');

INSERT INTO Solicitantes (id_hospital, nombre, apellido, sexo, direccion, colonia, tipoSangre, telefono, fechaRegistro)
VALUES (5, 'Luis', 'Hernández', 'Masculino', 'Calle 345', 'Colonia Oeste', 'A+', '2345678901', '11/05/2023', '21/05/2023');
	
-- Tabla Citas
INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita)
VALUES (1, 1, 1, '21/05/2023', 'Pendiente');
INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita)
VALUES (2, 2, 2, '22/05/2023', 'Completado');

INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita)
VALUES (3, 3, 3, '23/05/2023', 'Cancelada');

INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita)
VALUES (4, 4, 4, '24/05/2023', 'Pendiente');

INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita)
VALUES (5, 5, 5, '25/05/2023', 'Completado');

INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita)
VALUES (6, 6, 6, '26/05/2023', 'Pendiente');

INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita)
VALUES (7, 7, 7, '27/05/2023', 'Cancelada');

INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita)
VALUES (8, 8, 8, '28/05/2023', 'Pendiente');

INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita)
VALUES (9, 9, 9, '29/05/2023', 'Completado');

INSERT INTO Citas (id_hospital, id_solicitante, id_donante, fechaCita, statusCita)
VALUES (10, 10, 10, '30/05/2023', 'Pendiente');

Select * from Citas

-- ============================= CODIGOS EXTRAS PARA GUIA DE JAVA ==============================

UPDATE Usuarios
SET contraseña = 'Hola'
WHERE id = 3;

SELECT * FROM Usuarios

SELECT COUNT(*) AS total FROM Empleado WHERE nom_empleado LIKE '%F%' OR apell_empleado LIKE '% P%'

SELECT TOP 7 PERCENT *
FROM Empleado
WHERE nom_empleado LIKE '%F%' OR apell_empleado LIKE '% P%'

SELECT *
FROM Empleado
WHERE nom_empleado LIKE '%F%' OR apell_empleado LIKE '% P%'
ORDER BY id_empleado
OFFSET 0 ROWS
FETCH NEXT 10 ROWS ONLY


SELECT * FROM Empleado WHERE nom_empleado = 'Adrian'
