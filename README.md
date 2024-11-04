# series-ranking

## Descripción

Esta es una aplicación de Spring Boot para gestionar rankings de series de televisión. Permite a los usuarios agregar series, calificar series y ver un ranking basado en las valoraciones.

## Requisitos

- Docker instalado en el sistema.
- Conexión a internet (para descargar la imagen de base de datos, si se utiliza otra en lugar de H2).

## Estructura del Proyecto

- `src/`: Código fuente de la aplicación.
- `doc/`: Diagramas de Análisis y Diseño.
- `doc/evidencias`: Capturas de pantalla con evidencias de los requisitos funcionales en POSTMAN.
- `Dockerfile`: Archivo de configuración para construir la imagen Docker.
- `pom.xml`: Archivo de configuración de Maven para gestionar las dependencias.

## Construcción de la Imagen

Ejecuta los siguientes comandos para construir y ejecutar la imagen Docker:

```bash
docker build -t series-ranking
docker run -d -p 8081:8081 --name series-ranking series-ranking
```

