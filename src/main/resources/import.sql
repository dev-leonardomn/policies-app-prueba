-- 1. Tipos de Póliza
INSERT INTO policy_type (name) VALUES ('Salud');
INSERT INTO policy_type (name) VALUES ('Vida');
INSERT INTO policy_type (name) VALUES ('Vehiculo');

-- 2. Pólizas disponibles
INSERT INTO policies (id_policy_type, description) VALUES (1, 'Póliza Global de Salud');
INSERT INTO policies (id_policy_type, description) VALUES (2, 'Póliza Global de Vida');
INSERT INTO policies (id_policy_type, description) VALUES (3, 'Seguro Todo Riesgo Autos');
INSERT INTO policies (id_policy_type, description) VALUES (1, 'Plan Salud Familiar Plus');
INSERT INTO policies (id_policy_type, description) VALUES (1, 'Plan Salud Empresarial');
INSERT INTO policies (id_policy_type, description) VALUES (1, 'Cobertura Médica Premium');
INSERT INTO policies (id_policy_type, description) VALUES (2, 'Vida Individual Básica');
INSERT INTO policies (id_policy_type, description) VALUES (2, 'Vida Familiar Integral');
INSERT INTO policies (id_policy_type, description) VALUES (2, 'Vida Protección Total');
INSERT INTO policies (id_policy_type, description) VALUES (3, 'Seguro Terceros Básico');
INSERT INTO policies (id_policy_type, description) VALUES (3, 'Todo Riesgo Premium');
INSERT INTO policies (id_policy_type, description) VALUES (3, 'Cobertura Robo y Daños Parciales');

-- 3. Clientes
INSERT INTO clients (identification_type, identification_number, name, last_name, email, phone_number, birth_date, created_at, updated_at) VALUES ('CC', '123456', 'Luis', 'Mejia', 'luis@mail.com', '300123', '1990-01-01 10:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO clients (identification_type, identification_number, name, last_name, email, phone_number, birth_date, created_at, updated_at) VALUES ('CC', '654321', 'Maria', 'Gomez', 'maria@mail.com', '300456', '1985-03-15 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO clients (identification_type, identification_number, name, last_name, email, phone_number, birth_date, created_at, updated_at) VALUES ('CC', '789123', 'Carlos', 'Rodriguez', 'carlos@mail.com', '300789', '2000-07-22 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO clients (identification_type, identification_number, name, last_name, email, phone_number, birth_date, created_at, updated_at) VALUES ('CE', 'CE112233', 'Ana', 'Martinez', 'ana@mail.com', '301111', '1992-11-05 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO clients (identification_type, identification_number, name, last_name, email, phone_number, birth_date, created_at, updated_at) VALUES ('CC', '321654', 'Jorge', 'Ramirez', 'jorge@mail.com', '302222', '1978-09-10 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 4. Beneficiarios
INSERT INTO beneficiaries (identification_type, identification_number, name, last_name, birth_date, created_at, updated_at) VALUES ('CC', '987654', 'Andrea', 'Perez', '1995-05-20 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO beneficiaries (identification_type, identification_number, name, last_name, birth_date, created_at, updated_at) VALUES ('TI', '111222', 'Carlos', 'Mejia', '2015-10-10 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO beneficiaries (identification_type, identification_number, name, last_name, birth_date, created_at, updated_at) VALUES ('CC', '222333', 'Laura', 'Gomez', '1998-02-10 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO beneficiaries (identification_type, identification_number, name, last_name, birth_date, created_at, updated_at) VALUES ('CC', '333444', 'Pedro', 'Rodriguez', '1980-12-01 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO beneficiaries (identification_type, identification_number, name, last_name, birth_date, created_at, updated_at) VALUES ('TI', '444555', 'Sofia', 'Ramirez', '2012-06-30 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO beneficiaries (identification_type, identification_number, name, last_name, birth_date, created_at, updated_at) VALUES ('CC', '555666', 'Camila', 'Martinez', '1996-04-18 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO beneficiaries (identification_type, identification_number, name, last_name, birth_date, created_at, updated_at) VALUES ('CE', '777888', 'Mateo', 'Lopez', '2020-01-01 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 5. Vehículos
INSERT INTO vehicles (brand, model, type, registration_certificate_number) VALUES ('Toyota', '2024', 'SUV', 'ABC-123');
INSERT INTO vehicles (brand, model, type, registration_certificate_number) VALUES ('Mazda', '2024', 'Sedan', 'XYZ-789');
INSERT INTO vehicles (brand, model, type, registration_certificate_number) VALUES ('Chevrolet', '2023', 'Hatchback', 'DEF-456');
INSERT INTO vehicles (brand, model, type, registration_certificate_number) VALUES ('Renault', '2022', 'Pickup', 'GHI-321');
INSERT INTO vehicles (brand, model, type, registration_certificate_number) VALUES ('Kia', '2024', 'SUV', 'JKL-654');
INSERT INTO vehicles (brand, model, type, registration_certificate_number) VALUES ('Ford', '2021', 'Truck', 'MNO-987');
INSERT INTO vehicles (brand, model, type, registration_certificate_number) VALUES ('Nissan', '2020', 'Sedan', 'PQR-159');