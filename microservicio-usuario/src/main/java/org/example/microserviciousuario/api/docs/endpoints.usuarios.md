# API Endpoints de Usuario

## Descripción general
Este microservicio gestiona las operaciones relacionadas con los usuarios, incluyendo la creación, actualización, eliminación, consultas y reportes relacionados con monopatines cercanos.

---

## Endpoints de Usuario

### 1. Crear múltiples usuarios
- **URL**: `/api/usuarios/crearUsuarios`
- **Método**: `POST`
- **Descripción**: Crea múltiples usuarios en el sistema.
- **Cuerpo de la solicitud**:
    ```json
    [
        {
            "contrasenia": "password123",
            "nombre": "Juan",
            "apellido": "Perez",
            "celular": "1234567890",
            "email": "juan.perez@example.com",
            "latitud": -37.1234,
            "longitud": -59.4568
        },
        {
            "contrasenia": "password456",
            "nombre": "Maria",
            "apellido": "Lopez",
            "celular": "0987654321",
            "email": "maria.lopez@example.com",
            "latitud": -37.4587,
            "longitud": -59.3549
        }
    ]
     ```
  - **Código de respuesta**: `201 OK`

---

### 2. Obtener todos los usuarios
- **URL**: `/api/usuarios`
- **Método**: `GET`
- **Descripción**: Obtiene una lista de todos los usuarios.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `UsuarioDTO`.

---

### 3. Crear un nuevo usuario
- **URL**: `/api/usuarios`
- **Método**: `POST`
- **Descripción**: Crea un nuevo usuario en el sistema.
- **Cuerpo de la solicitud**:
    ```json
    {
        "contrasenia": "password888",
        "nombre": "Ana",
        "apellido": "Lopez",
        "celular": "987654321",
        "email": "ana.lolo@example.com",
        "latitud": -37.3150,
        "longitud": -59.1350
    }
    ```
---

### 4. Obtener usuario por ID
- **URL**: `/api/usuarios/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de un usuario específico por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del usuario.
- **Código de respuesta**: `200 OK` - Retorna el objeto `UsuarioDTO` correspondiente.

---

### 5. Actualizar usuario
- **URL**: `/api/usuarios/{id}`
- **Método**: `PUT`
- **Descripción**: Actualiza los detalles de un usuario existente.
- **Parámetros de ruta**:
    - `id` (Long) - ID del usuario a actualizar.
- **Cuerpo de la solicitud**:
    ```json
    {
        "contrasenia": "newpassword123",
        "nombre": "Carlos",
        "apellido": "Sanchez",
        "celular": "9876543210",
        "email": "carlos.sanchez@newemail.com",
        "latitud": -37.7129,
        "longitud": -59.0056
    }
    ```
---

### 6. Eliminar usuario
- **URL**: `/api/usuarios/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina un usuario del sistema por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del usuario a eliminar.
- **Código de respuesta**: `204 No Content` - El usuario ha sido eliminado correctamente.

---

### 7. Obtener monopatines cercanos
- **URL**: `/api/usuarios/{usuarioId}/monopatines-cercanos`
- **Método**: `GET`
- **Descripción**: Obtiene una lista de monopatines cercanos a un usuario, dentro de un radio especificado.
- **Parámetros de ruta**:
    - `usuarioId` (Long) - ID del usuario para obtener los monopatines cercanos.
- **Parámetros de consulta**:
    - `radio` (double, opcional, valor predeterminado: `5.0`) - Radio en kilómetros para buscar los monopatines cercanos.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `ReporteMonopatinesCercanosDTO`.

---
