CREATE DATABASE IF NOT EXISTS clinica_dental CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE clinica_dental;
CREATE TABLE IF NOT EXISTS pacientes (
 id_paciente INT AUTO_INCREMENT PRIMARY KEY,
 nombres VARCHAR(80) NOT NULL,
 apellidos VARCHAR(80) NOT NULL,
 dni CHAR(8) NOT NULL UNIQUE,
 telefono VARCHAR(15) NOT NULL,
 correo VARCHAR(120),
 fecha_registro DATE NOT NULL,
 observaciones VARCHAR(255),
 CONSTRAINT chk_paciente_dni CHECK (dni REGEXP '^[0-9]{8}$')
);
INSERT INTO pacientes(nombres,apellidos,dni,telefono,correo,fecha_registro,observaciones)
VALUES
('Maria Fernanda','Lopez Ramos','75841236','987654321','maria@example.com','2026-11-13','Primera evaluacion'),
('Juan Carlos','Perez Soto','70451238','956321478','juan@example.com','2026-11-13','Control dental'),
('Ana Lucia','Torres Diaz','73214568','945678123','ana@example.com','2026-11-13','Paciente nuevo')
ON DUPLICATE KEY UPDATE dni = VALUES(dni);
SELECT * FROM pacientes;
