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
```
```bash
docker run -d -p 8081:8081 --name series-ranking series-ranking
```

## Requisitos funcionales (Casos de uso)
![image](https://github.com/user-attachments/assets/56cc21fc-08d7-4f78-93a3-e7956aed29a9)

## Modelado de datos
![image](https://github.com/user-attachments/assets/31558077-2db0-4f2d-9629-0c050e98188e)

## Arquitectura de la Aplicación
![image](https://github.com/user-attachments/assets/e2cfcb16-bc95-4e73-bb43-3e10aa73fd09)

