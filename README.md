# 📚 Proyecto Gestión de Películas y Libros – Spring Boot

Este proyecto es una aplicación web que permite gestionar un catálogo de libros y películas. Ofrece funcionalidades como registro e inicio de sesión, gestión de contenido (admin), añadir a wishlist y crear reseñas.

---

## 🛠 Tecnologías utilizadas

- **Backend:** Java 17, Spring Boot, Spring MVC, Spring Data JPA, H2 (base de datos en memoria)
- **Frontend:** HTML, CSS, JavaScript (JS puro)
- **Testing:** JUnit 5, TestRestTemplate
- **Otros:** Maven, Lombok, H2 Console

---

## 📂 Estructura del Proyecto

**`src/main/java/com/paulabonets/peliculas/`**
- `controller/` → Controladores REST
- `model/` → Entidades JPA
- `repository/` → Interfaces JPA (acceso a datos)
- `service/` → Lógica de negocio
- `util/` → Clases de utilidad (helpers)
- `records/` → Clases DTO (para login, registro, etc.)
- `seeder/` → Carga inicial de datos (admin, libros, pelis)

**`src/main/resources/static/`**
- `index.html` → Página principal (catálogo)
- `login.html` → Inicio de sesión
- `register.html` → Registro
- Archivos CSS y JS

**`src/test/java/com/paulabonets/peliculas/`**
- `unit/` → Pruebas unitarias
- `integration/` → Tests de integración
- `e2e/` → Pruebas end-to-end

---

## 🔐 Autenticación y roles

- Inicio de sesión por email y contraseña
- Roles: `USER` y `ADMIN`
- Se maneja la sesión mediante una **cookie HTTP**

---

## 📌 Endpoints disponibles

### 📁 Autenticación (`/api/auth`)

| Método | Endpoint          | Descripción                         |
|--------|-------------------|-------------------------------------|
| POST   | `/register`       | Registro de usuario                 |
| POST   | `/login`          | Inicio de sesión (devuelve cookie) |
| POST   | `/logout`         | Cierre de sesión                    |

---

### 🎞 Contenido (`/api/content`)

| Método | Endpoint             | Descripción                                |
|--------|----------------------|--------------------------------------------|
| GET    | `/`                  | Listar todos los contenidos                |
| GET    | `/?type=BOOK`        | Filtrar por tipo (BOOK o MOVIE)           |
| GET    | `/?title=Matrix`     | Filtrar por título                        |
| GET    | `/{id}`              | Obtener contenido por ID                  |
| DELETE | `/{id}`              | Eliminar contenido (ADMIN)                |

---

### 📚 Libros (`/api/books`)

| Método | Endpoint       | Descripción                    |
|--------|----------------|--------------------------------|
| POST   | `/`            | Crear un libro (ADMIN)         |
| PUT    | `/{id}`        | Actualizar un libro (ADMIN)    |

---

### 🎬 Películas (`/api/movies`)

| Método | Endpoint       | Descripción                      |
|--------|----------------|----------------------------------|
| POST   | `/`            | Crear una película (ADMIN)       |
| PUT    | `/{id}`        | Actualizar una película (ADMIN)  |

---

### ⭐ Reseñas (`/api/reviews`)

| Método | Endpoint               | Descripción                                  |
|--------|------------------------|----------------------------------------------|
| POST   | `/`                    | Crear una review (requiere sesión)           |
| GET    | `/content/{id}`        | Obtener reviews por ID de contenido          |
| GET    | `/{id}`                | Obtener review por ID                        |
| DELETE | `/{id}`                | Eliminar review propia                       |

---

### 📌 Wishlist (`/api/wishlist`)

| Método | Endpoint         | Descripción                              |
|--------|------------------|------------------------------------------|
| GET    | `/`              | Obtener la wishlist del usuario          |
| POST   | `/{contentId}`   | Añadir contenido a la wishlist           |
| DELETE | `/{contentId}`   | Eliminar contenido de la wishlist        |

---

## ✅ Funcionalidades principales

### Para usuarios

- Registro e inicio de sesión
- Navegar por el catálogo filtrando por tipo o título
- Ver detalles de un libro o película
- Añadir/quitar contenido de la wishlist
- Escribir y eliminar reseñas

### Para administradores

- Crear y editar libros/películas
- Eliminar contenido

---

## 🧪 Testing

- ✅ **Pruebas unitarias:** validación de modelos y servicios
- ✅ **Pruebas de integración:** lógica entre capas (servicio/repositorio)
- ✅ **Pruebas E2E:** casos completos (login, wishlist, reviews, logout)

Herramientas: JUnit 5, TestRestTemplate, assertions nativas

---

## ▶️ Ejecución local

1. Clona el repositorio
2. Ejecuta con Maven o desde tu IDE
3. Accede a [http://localhost:8080](http://localhost:8080)
4. Login admin: `admin@admin.com` / `admin`

---

## 👩‍💻 Equipo de desarrollo

- **Joel** – Backend principal
- **Alejandra** – Backend, testing y frontend
- **Paula** – Frontend, testing y documentación

---

## 📦 Deploy

La aplicación está desplegada en Render usando un contenedor de Docker.

---

