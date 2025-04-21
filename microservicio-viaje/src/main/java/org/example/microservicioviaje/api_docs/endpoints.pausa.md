# API de Gestión de Pausas

Este repositorio contiene la implementación de una API RESTful para gestionar las pausas en los viajes de monopatines. La API permite crear, obtener, eliminar pausas y administrar viajes.

## Endpoints

### 1. **Crear una pausa**
- **URL**: `/api/pausas/altaPausa/{viajeId}`
- **Método HTTP**: `POST`
- **Descripción**: Crea una pausa para un viaje en específico, registrando el inicio y el fin de la pausa.
- **Parámetros de consulta**:
    - `horaInicio` (requerido): Hora de inicio de la pausa (formato: `yyyy-MM-dd'T'HH:mm:ss`).
    - `horaFin` (requerido): Hora de fin de la pausa (formato: `yyyy-MM-dd'T'HH:mm:ss`).
- **Respuesta exitosa**:
    - **Código de estado**: `201 Created`
    - **Cuerpo**: La pausa recién creada.
- **Respuesta con error**:
    - **Código de estado**: `400 Bad Request` si los parámetros de fecha no son válidos.
    - **Código de estado**: `404 Not Found` si el viaje no existe.

**Ejemplo de solicitud**:
``json
  {
  "viajeId": 5,
  "horaInicio": "2024-11-14T10:00:00",
  "horaFin": "2024-11-14T10:30:00"
  }


### 2. Obtener un viaje por ID
- **URL**: `/api/pausas/{pausaId}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de un pausa específico por su ID.
- **Parámetros de ruta**:
  - `id` (Long) - ID del pausa.
- **Código de respuesta**: `200 OK` - Retorna el objeto 

---

### 3. Actualizar un viaje
- **URL**: `/api/pausas/{pausaId}`
- **Método**: `PUT`
- **Descripción**: Actualiza un pausa existente.
- **Parámetros de ruta**:
  - `id` (Long) - ID del pausa a actualizar.
- **Cuerpo de la solicitud**:
   
    ```
- **Código de respuesta**: `200 OK` - .

---

### 4. Eliminar un pausa
- **URL**: `/api/pausas/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina una pausa por su ID.
- **Parámetros de ruta**:
  - `id` (Long) - ID del pausa a eliminar.
- **Código de respuesta**: `204 No Content` - .

---