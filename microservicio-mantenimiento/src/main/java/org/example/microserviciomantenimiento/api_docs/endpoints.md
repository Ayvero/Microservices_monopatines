# API Endpoints de Mantenimiento

## Descripción general
Este microservicio gestiona el mantenimiento de monopatines. A continuación se describen los endpoints disponibles.

---

## Endpoints de Mantenimiento

### 1. Registrar un nuevo mantenimiento
- **URL**: `/api/mantenimiento`
- 
- **Método**: `POST`
- **Descripción**: Registra un nuevo mantenimiento para un monopatín.
- **Cuerpo de la solicitud**:
    ```json
    {
        "monopatinId": 4,
         "fechaInicioMantenimiento": "2024-11-11T12:00:00",
         "descripcion": "Cambio de llantas"
    }
    ```
----

### 2. Obtener mantenimiento por ID
- **URL**: `/api/mantenimiento/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de un mantenimiento por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del mantenimiento.
---

### 3. Actualizar mantenimiento
- **URL**: `/api/mantenimiento/{id}`
- **Método**: `PUT`
- **Descripción**: Actualiza un mantenimiento existente.
- **Parámetros de ruta**:
    - `id` (Long) - ID del mantenimiento a actualizar.
- **Cuerpo de la solicitud**:
    ```json
    {
       "monopatinId": 4,
        "fechaInicioMantenimiento": "2024-10-11T12:00:00",
       "fechaFinMantenimiento": "2024-11-11T12:00:00",
        "descripcion": "Cambio de llantas"
    }
    ```


### 4. Eliminar mantenimiento
- **URL**: `/api/mantenimiento/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina un mantenimiento específico por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del mantenimiento a eliminar.

---

### 5. Obtener todos los mantenimientos
- **URL**: `/api/mantenimiento`
- **Método**: `GET`
- **Descripción**: Obtiene todos los mantenimientos registrados.
-
---

### 6. Generar reporte de uso de monopatines (con o sin pausa)
- **URL**: `/api/mantenimiento/reporteUso-monopatines`
- **Método**: `GET`
- **Descripción**: Obtiene un reporte de uso de los monopatines, con la opción de incluir o no el tiempo sin pausas.
- **Parámetros de consulta**:
    - `includeSinPausa` (boolean, opcional) - Si se deben incluir los tiempos sin pausas (valor por defecto: `true`).
-
---

### 7. Actualizar estado de los monopatines en mantenimiento
- **URL**: `/api/mantenimiento/actualizar-estado`
- **Método**: `PUT`
- **Descripción**: Actualiza el estado de los monopatines que superen un umbral de kilómetros y el filtro de pausa.
- **Parámetros de consulta**:
    - `includeSinPausa` (boolean, opcional) - Si se deben incluir los tiempos sin pausas (valor por defecto: `true`).
    - `maxKm` (double) - El valor máximo de kilómetros para considerar que un monopatín necesita mantenimiento.
- **Respuesta**:
    - Código de respuesta `200 OK` si se actualizan correctamente los monopatines.
    - En caso de error: `500 Internal Server Error`.

---

### 8. Consultar mantenimientos de un monopatín
- **URL**: `/api/mantenimiento/monopatin/{monopatinId}/mantenimientos`
- **Método**: `GET`
- **Descripción**: Obtiene todos los mantenimientos asociados a un monopatín específico.
- **Parámetros de ruta**:
    - `monopatinId` (Long) - ID del monopatín.
-