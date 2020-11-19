DROP DATABASE IF EXISTS notas_droid_flor;
CREATE DATABASE notas_droid_flor;
USE notas_droid_flor;


DROP TABLE IF EXISTS ciclos;
CREATE TABLE ciclos (
idciclo int AUTO_INCREMENT,
nombre VARCHAR(200) NOT NULL,
siglas VARCHAR(20),
curso int NOT NULL,
PRIMARY KEY (idciclo)
);

DROP TABLE IF EXISTS alumnos;
CREATE TABLE alumnos (
idalumno int AUTO_INCREMENT,
nombre VARCHAR(200) NOT NULL,
email VARCHAR(200) NOT NULL,
contrasenia VARCHAR(200) NOT NULL,
ciclo int NOT NULL,
PRIMARY KEY (idalumno),
FOREIGN KEY (ciclo) REFERENCES ciclos(idciclo)
);

DROP TABLE IF EXISTS asignaturas;
CREATE TABLE asignaturas (
idasignatura int(4) PRIMARY KEY,
nombre VARCHAR(200) NOT NULL,
siglas VARCHAR(20)
);

DROP TABLE IF EXISTS ciclo_asignatura;
CREATE TABLE ciclo_asignatura (
idasignatura int(4) NOT NULL,
idciclo int(4) NOT NULL,
FOREIGN KEY (idasignatura) REFERENCES asignaturas(idasignatura),
FOREIGN KEY (ciclo) REFERENCES ciclos(idciclo)
);

DROP TABLE IF EXISTS pruebas;
CREATE TABLE pruebas (
idprueba int AUTO_INCREMENT,
idalumno int NOT NULL,
idasignatura int NOT NULL,
titulo VARCHAR(200) NOT NULL,
descripcion VARCHAR(200),
fecha DATE,
realizada BOOLEAN DEFAULT FALSE,
calificacion DOUBLE,
PRIMARY KEY (idprueba),
FOREIGN KEY (idalumno) REFERENCES alumnos(idalumno),
FOREIGN KEY (idasignatura) REFERENCES asignaturas(idasignatura)
);


INSERT INTO ciclos (nombre,siglas,curso)
	VALUES ('Administración de Sistemas Informáticos en Red','ASIR', 1);
INSERT INTO ciclos (nombre,siglas,curso)
	VALUES ('Administración de Sistemas Informáticos en Red','ASIR', 2);
INSERT INTO ciclos (nombre,siglas,curso)
	VALUES ('Desarrollo de Aplicaciones Multiplataforma','DAM', 1);
INSERT INTO ciclos (nombre,siglas,curso)
	VALUES ('Desarrollo de Aplicaciones Multiplataforma','DAM', 2);
INSERT INTO ciclos (nombre,siglas,curso)
	VALUES ('Desarrollo de Aplicaciones Web','DAW', 1);
INSERT INTO ciclos (nombre,siglas,curso)
	VALUES ('Desarrollo de Aplicaciones Web','DAW', 2);
	
	
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0022,'Inglés técnico para ciclos de grado superior de la familia de informética y comunicaciones', 'ING');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0369,'Implantación de Sistemas Operativos', 'ISO');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0370,'Planificación y Administración de Redes', 'PAR');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0371,'Fundamentos de Hardware', 'FH');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0372,'Gestión de Bases de Datos', 'GB');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0373,'Lenguajes de Marcas y Sistemas de Gestión de Información', 'LMSGI');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0374,'Administración de Sistemas Operativos', 'ASO');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0375,'Servicios de Red e Internet', 'SRI');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0376,'Implantación de Aplicaciones Web', 'IAQ');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0377,'Administración de Sistemas Gestores de Bases de Datos', 'ASGBD');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0378,'Seguridad y Alta Disponibilidad', 'SAD');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0380,'Formación y Orientación Laboral', 'FOL');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0381,'Empresa e Iniciativa Emprendedora', 'EIE');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0483,'Sistemas Informáticos', 'SISIN');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0484,'Bases de Datos', 'BDD');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0485,'Programación', 'PROG');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0487,'Entornos de Desarrollo', 'ENDES');			
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0493,'Formación y Orientación Laboral', 'FOL');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0486,'Acceso a Datos', 'AD');		
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0488,'Desarrollo de Interfaces', 'DI');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0489,'Programación Multimedia y Dispositivos Móviles', 'PMDM');		
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0490,'Programación de Servicios y Procesos', 'PSP');		
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0491,'Sistemas de Gestión Empresarial', 'SGE');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0494,'Empresa e Iniciativa Emprendedora', 'EIE');		
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0617,'Formación y Orientación Laboral', 'FOL');		
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0612,'Desarrollo Web en Entorno Cliente', 'DWEC');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0613,'Desarrollo Web en Entorno Servidor', 'DEWS');	
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0614,'Despliegue de Aplicaciones Web', 'DAW');
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0615,'Diseño de Interfaces Web', 'DIW');		
INSERT INTO asignaturas (idasignatura,nombre,siglas)
	VALUES (0618,'Empresa e iniciativa Emprenderora', 'EIE');		


INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0022,1);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0022,3);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0022,5);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0369,1);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0370,1);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0371,1);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0372,1);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0373,1);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0373,3);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0373,5);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0374,2);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0375,2);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0376,2);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0377,2);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0378,2);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0380,1);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0381,2);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0483,3);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0483,5);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0484,3);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0484,5);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0485,3);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0485,5);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0487,3);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0487,5);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0493,3);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0486,4);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0488,4);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0489,4);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0490,4);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0491,4);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0494,4);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0617,5);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0612,6);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0613,6);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0614,6);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0615,6);
INSERT INTO ciclo_asignatura (idasignatura,idciclo) VALUES (0618,6);

-- INSERT INTO alumnos (nombre,email,contrasenia,ciclo,foto)
--  	VALUES ('Flor','fsantosb05@educastillalamancha.es','pwd','4',null);

-- INSERT INTO pruebas (idalumno,idasignatura,titulo,descripcion,fecha,realizada,calificacion)
--  	VALUES (1,22,'Reading','Examen sobre comprensión de la lectura en inglés',null,default,null);

COMMIT;
