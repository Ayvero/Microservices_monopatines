# API Endpoints de Paradas

## Descripción general
Este microservicio gestiona las paradas para los monopatines. Los endpoints permiten crear, obtener, actualizar y eliminar paradas, así como consultar todas las paradas disponibles.

---

## Endpoints de Paradas

### 1. Registrar una nueva parada
- **URL**: `/api/paradas/registrarParada`
- **Método**: `POST`
- **Descripción**: Registra una nueva parada en el sistema.
- **Cuerpo de la solicitud**:
    - Se espera un objeto `ParadaDTO` con los siguientes campos:
        - `latitud`: Latitud de la parada (tipo `double`).
        - `longitud`: Longitud de la parada (tipo `double`).
- **Código de respuesta**: `201 Created`
```angular2html
    {
        "latitud": -37.1234,
        "longitud": -59.1234
    }
```
---

### 2. Agregar múltiples paradas
- **URL**: `/api/paradas/agregarParadas`
- **Método**: `POST`
- **Descripción**: Agrega múltiples paradas al sistema.
- **Cuerpo de la solicitud**:
    - Se espera una lista de objetos `ParadaDTO`, cada uno con los siguientes campos:
        - `latitud`: Latitud de la parada (tipo `double`).
        - `longitud`: Longitud de la parada (tipo `double`).
- **Código de respuesta**: `201 Created`

---

### 3. Obtener una parada por ID
- **URL**: `/api/paradas/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de una parada específica por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la parada a obtener.
- **Código de respuesta**: `200 OK`

---

### 4. Actualizar una parada
- **URL**: `/api/paradas/{id}`
- **Método**: `PUT`
- **Descripción**: Actualiza los detalles de una parada existente.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la parada a actualizar.
- **Cuerpo de la solicitud**:
    - Se espera un objeto `ParadaDTO` con los siguientes campos:
        - `latitud`: Nueva latitud de la parada (tipo `double`).
        - `longitud`: Nueva longitud de la parada (tipo `double`).
- **Código de respuesta**: `200 OK`

---

### 5. Eliminar una parada
- **URL**: `/api/paradas/quitarParada/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina una parada por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la parada a eliminar.
- **Código de respuesta**: `204 No Content`

---

### 6. Obtener todas las paradas
- **URL**: `/api/paradas`
- **Método**: `GET`
- **Descripción**: Obtiene todas las paradas registradas en el sistema.
- **Código de respuesta**: `200 OK`

---
