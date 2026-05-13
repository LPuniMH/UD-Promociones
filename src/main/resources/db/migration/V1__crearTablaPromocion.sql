CREATE TABLE promocion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    porcentaje_descuento DOUBLE NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    activa BOOLEAN DEFAULT TRUE
);