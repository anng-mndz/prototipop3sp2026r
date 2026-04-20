CREATE TABLE  Peliculas (
    idPelicula INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(45),
    clasificacion VARCHAR(45),
    genero VARCHAR(45),
    subtitulado VARCHAR(45),
    idioma VARCHAR (45),
    precio DOUBLE
    )

INSERT INTO Peliculas (nombre, clasificacion, genero, subtitulado, idioma, precio) VALUES ("Avengers", "PG-13", "Acción", "Sí", "Inglés", 35.50);