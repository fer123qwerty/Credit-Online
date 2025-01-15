--INSERT INTO USERS(id, name, birthdate) VALUES(1, 'Antonio Gonzalez', '2024-11-10T04:15:26.491000000Z');

-- Script de inserción de datos para las tablas 'prestamos' y 'cuentas_debito'

-- Tabla: prestamos
INSERT INTO loans (customer, id, date, amount, status) VALUES ('00103228', 1, '2021-01-10', 37500.00, 'PENDING');
INSERT INTO loans (customer, id, date, amount, status) VALUES ('00103228', 2, '2021-01-19', 725.18, 'PENDING');
INSERT INTO loans (customer, id, date, amount, status) VALUES ('00103228', 3, '2021-01-31', 1578.22, 'PENDING');
INSERT INTO loans (customer, id, date, amount, status) VALUES ('00103228', 4, '2021-02-04', 380.00, 'PENDING');
INSERT INTO loans (customer, id, date, amount, status) VALUES ('70099925', 5, '2021-01-07', 2175.25, 'PAID');
INSERT INTO loans (customer, id, date, amount, status) VALUES ('70099925', 6, '2021-01-13', 499.99, 'PAID');
INSERT INTO loans (customer, id, date, amount, status) VALUES ('70099925', 7, '2021-01-24', 5725.18, 'PENDING');
INSERT INTO loans (customer, id, date, amount, status) VALUES ('70099925', 8, '2021-02-07', 545.55, 'PENDING');
INSERT INTO loans (customer, id, date, amount, status) VALUES ('15000125', 9, '2020-12-31', 15220.00, 'PAID');

-- Tabla: cuentas_debito
INSERT INTO debit_accounts (customer, amount, status) VALUES ('00103228', 15375.28, 'ACTIVE');
INSERT INTO debit_accounts (customer, amount, status) VALUES ('70099925', 3728.51, 'BLOCKED');
INSERT INTO debit_accounts (customer, amount, status) VALUES ('00298185', 0.0, 'CANCELLED');
