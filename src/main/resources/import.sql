-- Categorías de eventos
INSERT INTO category (name, description) VALUES ('Conciertos', 'Eventos musicales en vivo con artistas nacionales e internacionales');
INSERT INTO category (name, description) VALUES ('Citas Médicas', 'Consultas y procedimientos médicos programados');
INSERT INTO category (name, description) VALUES ('Restaurantes', 'Reservas en establecimientos gastronómicos');
INSERT INTO category (name, description) VALUES ('Cine', 'Proyecciones de películas en diferentes formatos');
INSERT INTO category (name, description) VALUES ('Teatro', 'Presentaciones de obras teatrales y espectáculos');
INSERT INTO category (name, description) VALUES ('Deportes', 'Eventos deportivos de diferentes disciplinas');

-- Usuarios
INSERT INTO users (first_name, last_name, password, create_date, update_date) VALUES ('Carlos', 'Rodríguez', '$2a$10$X7y1u8LPq7dYjWKPi3DHHO1PS0AV.wq3.OJ3TOt7skJ..3jLMDrGy', '2025-04-01', '2025-04-01');
INSERT INTO users (first_name, last_name, password, create_date, update_date) VALUES ('María', 'González', '$2a$10$dP9s96.YBZRmZJ3t1PZ8O.SYCEcDo9DkkRGH02jjOpK3HLH7VoAP2', '2025-04-01', '2025-04-01');
INSERT INTO users (first_name, last_name, password, create_date, update_date) VALUES ('Alejandro', 'Martínez', '$2a$10$7KxB5QY.F3E8Q.p5BnQoJeYFR3W8vS2xGJr52qpEOy4bQbhLEAvJC', '2025-04-01', '2025-04-01');
INSERT INTO users (first_name, last_name, password, create_date, update_date) VALUES ('Sofía', 'López', '$2a$10$qW3Y5D5XKzJm.b0CpGBIJ.dX9ZgZI4.oNS3rnKlG6J9uK81U0TtwO', '2025-04-02', '2025-04-02');
INSERT INTO users (first_name, last_name, password, create_date, update_date) VALUES ('Javier', 'Hernández', '$2a$10$YHaPzxZ.L1C1UlpwpyXN7..mqQdyLw9CU2Vz.XCq8BcMu5HDbPsPW', '2025-04-02', '2025-04-02');

-- Eventos
-- Conciertos
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Concierto de Juanes', 'Presentación del nuevo álbum de Juanes en vivo', '2025-05-15', 'Movistar Arena, Bogotá', 100, 1, '2025-04-02', '2025-04-02');
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Festival Rock al Parque', 'Edición anual del festival de rock más grande de Colombia', '2025-06-20', 'Parque Simón Bolívar, Bogotá', 200, 1, '2025-04-02', '2025-04-02');

-- Citas Médicas
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Jornada de Vacunación', 'Vacunación gratuita contra la influenza', '2025-04-20', 'Hospital Santa Fe, Bogotá', 50, 2, '2025-04-03', '2025-04-03');
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Consulta de Cardiología', 'Evaluaciones gratuitas con cardiólogos especialistas', '2025-04-25', 'Clínica del Country, Bogotá', 30, 2, '2025-04-03', '2025-04-03');

-- Restaurantes
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Noche de Paella', 'Degustación especial de paellas mediterráneas', '2025-04-28', 'Restaurante El Español, Bogotá', 40, 3, '2025-04-04', '2025-04-04');
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Festival Gastronómico', 'Muestra de platos de diferentes regiones colombianas', '2025-05-05', 'Plaza de Bolívar, Bogotá', 150, 3, '2025-04-04', '2025-04-04');

-- Cine
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Estreno: El Gran Héroe', 'Proyección exclusiva de la película más esperada del año', '2025-04-30', 'Cinecolombia Centro Mayor, Bogotá', 80, 4, '2025-04-05', '2025-04-05');
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Ciclo de Cine Colombiano', 'Retrospectiva de las mejores películas colombianas', '2025-05-10', 'Cinemateca Distrital, Bogotá', 60, 4, '2025-04-05', '2025-04-05');

-- Teatro
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Don Quijote de la Mancha', 'Adaptación teatral del clásico de Cervantes', '2025-05-20', 'Teatro Colón, Bogotá', 120, 5, '2025-04-06', '2025-04-06');
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Impro Comedy Show', 'Espectáculo de comedia improvisada', '2025-05-25', 'Teatro Nacional, Bogotá', 90, 5, '2025-04-06', '2025-04-06');

-- Deportes
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Final Copa Colombia', 'Partido final del torneo de fútbol nacional', '2025-06-10', 'Estadio El Campín, Bogotá', 300, 6, '2025-04-07', '2025-04-07');
INSERT INTO event (title, description, event_date, location, capacity, category_id, create_date, update_date) VALUES ('Maratón de Bogotá', 'Carrera anual por las principales calles de la ciudad', '2025-06-15', 'Parque El Virrey, Bogotá', 500, 6, '2025-04-07', '2025-04-07');

-- Reservas (status: CONFIRMADA, PENDIENTE, CANCELADA)
-- Reservas de Carlos Rodríguez
INSERT INTO reservation (reservation_date, status, user_id, event_id) VALUES ('2025-04-05', 'CONFIRMADA', 1, 1);
INSERT INTO reservation (reservation_date, status, user_id, event_id) VALUES ('2025-04-05', 'CONFIRMADA', 1, 7);

-- Reservas de María González
INSERT INTO reservation (reservation_date, status, user_id, event_id) VALUES ('2025-04-06', 'CONFIRMADA', 2, 3);
INSERT INTO reservation (reservation_date, status, user_id, event_id) VALUES ('2025-04-06', 'PENDIENTE', 2, 9);

-- Reservas de Alejandro Martínez
INSERT INTO reservation (reservation_date, status, user_id, event_id) VALUES ('2025-04-07', 'CONFIRMADA', 3, 5);
INSERT INTO reservation (reservation_date, status, user_id, event_id) VALUES ('2025-04-07', 'CANCELADA', 3, 11);

-- Reservas de Sofía López
INSERT INTO reservation (reservation_date, status, user_id, event_id) VALUES ('2025-04-08', 'CONFIRMADA', 4, 2);
INSERT INTO reservation (reservation_date, status, user_id, event_id) VALUES ('2025-04-08', 'CONFIRMADA', 4, 8);

-- Reservas de Javier Hernández
INSERT INTO reservation (reservation_date, status, user_id, event_id) VALUES ('2025-04-09', 'PENDIENTE', 5, 4);
INSERT INTO reservation (reservation_date, status, user_id, event_id) VALUES ('2025-04-09', 'CONFIRMADA', 5, 12);