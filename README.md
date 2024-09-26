# Evaluación Técnica SmartJob

Esta es la resolución de la Evalución Técnica

### Pre-requisitos 📋

El proyecto fue construido con java 17

## Instalación 🔧

Crear imagen docker

```bash
docker build -t smatjob/techical_test_tpdh .
```

## Despliegue 📦

Iniciar Docker

```bash
docker run -d -p 8080:8080 --name technical_test_jpdh smatjob/techical_test_tpdh
```

Escribir en el navegador para ver la ui de swagger

```url
http://localhost:8080/swagger-ui
```

Escribir en el navegador para ver la consola de h2 database

```url
http://localhost:8080/h2-console
```
Datos conexión consola
```properties
jdbc-url= jdbc:h2:mem:testdb
user = sa
password = password
```


##  Utilizar 🚀

Al cargar la aplicación se crean un usuario por defecto, el cual se puede utilizar para obtener el token de autenticación.

```properties
 email  admin@mail.com
 clave  Abc123dF
```
    

### Opción 1
Probar el servicio mediante una collection de postman que se encuentra en la carpeta postman, la cual tiene la posibilidad de obtener el token para utilizar los endpoint, que esta protegido por jwt.

### Opción 2
Probar el servicio mediante la ui de swagger, la cual tiene la posibilidad de obtener el token para utilizar los endpoint.

### Opción 3
Probar el mediante curl, para obtener el token
```bash
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
  "email":"admin@mail.com",
  "password":"Abc123dF"
}'
```

Para crear los usuarios
```bash
curl --location 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer token creado en el paso anterior' \
--data-raw '{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2S",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
}'
```
## Autor ✒️

Juan Pablo Durán Herrera [Linkedin](https://www.linkedin.com/in/jpduranhe/)
