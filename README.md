# Sistema de Votaciones — API RESTful

API para gestionar una votación: registrar votantes y candidatos, dejar que cada
votante emita un solo voto, y ver los resultados.

## 1. Qué necesitas antes de empezar

- JDK 17 o superior instalado
- Maven instalado
- Una base de datos MongoDB (local o en Atlas)
- VS Code con las extensiones "Extension Pack for Java" y "Spring Boot Extension Pack"

## 2. Pasos para correr el proyecto

1. **Descomprime el proyecto** y ábrelo en VS Code con `File > Open Folder`
   (selecciona la carpeta que tiene el `pom.xml`).
2. **Conecta la base de datos.** Define la variable de entorno `MONGODB_URI` con tu
   conexión de Atlas:

   powershell
   $env:MONGODB_URI="mongodb+srv://usuario:password@cluster.mongodb.net/voting_db"
   
   Si usas el botón de VS Code en vez de la terminal, pon esa misma variable dentro
   de `.vscode/launch.json` (ahí no hereda lo de la terminal).
3. **Corre la app**: botón sobre `VotingApiApplication.java`, o `mvn spring-boot:run`
   en la terminal. Usa solo una forma a la vez para no chocar por el puerto 8080.
4. **Pruébala**: abre `http://localhost:8080/swagger-ui.html` en el navegador — ahí puedes
   probar todos los endpoints sin usar curl ni Postman.

## 3. Cómo funciona el código

El proyecto sigue siempre el mismo camino cuando llega una petición:

Cliente (navegador/curl) → Controller → Service → Repository → MongoDB

- **`model/`** — cómo se ve cada dato guardado en Mongo: `Voter`, `Candidate`, `Vote`.
- **`repository/`** — interfaces que leen/escriben en Mongo. No tienen código propio:
  Spring genera las consultas solo con el nombre del método (ej: `findByEmail`).
- **`service/`** — aquí vive la lógica: valida antes de guardar (email repetido, votante
  que ya votó, etc.) y coordina varios repositorios cuando hace falta.
- **`controller/`** — recibe la petición HTTP (`/voters`, `/candidates`, `/votes`) y
  llama al service correspondiente. No tiene lógica propia, solo traduce HTTP a Java.
- **`dto/`** — el "molde" de lo que entra y sale por HTTP (distinto del `model` que se
  guarda en la base de datos).
- **`exception/`** — cuando algo falla, atrapa el error en un solo lugar y responde
  siempre con el mismo formato JSON.

### Ejemplo: qué pasa cuando emites un voto (`POST /votes`)

1. El `VoteController` recibe el `voterId` y `candidateId`.
2. Le pasa el request al `VoteService`.
3. El service busca al votante — si no existe o ya votó, corta con un error.
4. Busca al candidato — si no existe, corta con un error.
5. Guarda el voto, marca `hasVoted = true` en el votante, y le suma 1 voto al candidato.
6. El controller responde con el voto creado.

## 4. Cómo votar (paso a paso)

1. Abre `http://localhost:8080/swagger-ui.html`.
2. En **POST /voters**, dale "Try it out" y registra un votante:
   ```json
   { "name": "Ana Gomez", "email": "ana.gomez@example.com" }
   ```
   Copia el `"id"` que te devuelve.
3. En **POST /candidates**, registra un candidato:
   ```json
   { "name": "Carlos Ruiz", "party": "Partido Verde" }
   ```
   Copia también su `"id"`.
4. En **POST /votes**, pega los dos ids:
   ```json
   { "voterId": "<id-del-votante>", "candidateId": "<id-del-candidato>" }
   ```
5. En **GET /votes/statistics**, dale "Try it out" y "Execute" (sin body) para ver los resultados.

## 5. Endpoints disponibles

**Votantes**: `POST /voters` · `GET /voters` · `GET /voters/{id}` · `DELETE /voters/{id}`

**Candidatos**: `POST /candidates` · `GET /candidates` · `GET /candidates/{id}` · `DELETE /candidates/{id}`

**Votos**: `POST /votes` · `GET /votes` · `GET /votes/statistics`

## 6. Reglas del sistema

- Una persona no puede ser votante y candidato a la vez.
- Cada votante solo puede votar una vez.
- Los emails de los votantes no se pueden repetir.

## 7. Si algo falla

**Problema**
Error de autenticación con Mongo
**Qué hacer**
Revisa que `MONGODB_URI` tenga tu usuario/contraseña reales, sin `< >` 

**Problema**
`Connection refused` a 127.0.0.1:27017
**Qué hacer**
No se está usando `MONGODB_URI` en la terminal/launch.json correctos

**Problema**
Puerto 8080 ocupado
**Qué hacer**
Ya hay otra instancia corriendo — deténla antes de correr de nuevo

**Problema**
`export` falla en PowerShell
**Qué hacer**
Usa `$env:MONGODB_URI="..."` en vez de `export`

**Problema**
Error 404 en `http://localhost:8080/`
**Qué hacer**
Normal — no hay nada mapeado en la raíz, prueba `/swagger-ui.html`

**Problema**
`409` al registrar un votante (email repetido)
**Qué hacer**
Usa otro email, o borra el votante existente con `DELETE /voters/{id}`

**Problema**
`409` al votar (votante ya votó)
**Qué hacer**
Usa un `voterId` de un votante que no haya votado, o registra uno nuevo

> Los errores `409` son las validaciones de negocio funcionando bien, no fallas del código —
> pasa lo mismo si repites pruebas con los mismos datos.

## 8. Qué falta

No se implementó autenticación (JWT) por el tiempo de entrega — quedaría como mejora futura.
