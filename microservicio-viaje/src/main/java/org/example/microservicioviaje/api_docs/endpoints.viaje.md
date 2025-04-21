# API Endpoints de Viaje

## Descripción general
Este microservicio gestiona las operaciones relacionadas con los viajes realizados en monopatines, incluyendo la creación, actualización, eliminación y consulta de viajes, así como el cálculo de costos y tiempos de uso.

---

## Endpoints de Viaje

### 1. Crear un nuevo viaje
- **URL**: `/api/viajes`
- **Método**: `POST`
- **Descripción**: Crea un nuevo viaje.
- **Cuerpo de la solicitud**:
    ```json
    {
        "cuentaId": 123,
        "fechaInicio": "2024-11-11T10:00:00",
        "monopatinId": 456,
        "costoTotal": 50.0,
        "totalKmRecorridos": 15.0,
        "totalTiempo": 1200,
        "totalTiempoUsoSinPausas": 1000
    }
    ```
- **Código de respuesta**: `201 Created` - Retorna el `ViajeDTO` creado.

---

### 2. Iniciar un viaje con monopatín y cuenta de usuario
- **URL**: `/api/viajes/iniciar/monopatin/{monopatinId}/cuenta/{cuentaId}`
- **Método**: `POST`
- **Descripción**: Inicia un viaje para un usuario con un monopatín específico.
- **Parámetros de ruta**:
    - `monopatinId` (Long) - ID del monopatín.
    - `cuentaId` (Long) - ID de la cuenta del usuario.
- **Código de respuesta**: `201 Created` - Retorna el objeto `Viaje` creado.

---

### 3. Obtener un viaje por ID
- **URL**: `/api/viajes/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de un viaje específico por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del viaje.
- **Código de respuesta**: `200 OK` - Retorna el objeto `ViajeDTO` correspondiente.

---

### 4. Actualizar un viaje
- **URL**: `/api/viajes/{id}`
- **Método**: `PUT`
- **Descripción**: Actualiza un viaje existente.
- **Parámetros de ruta**:
    - `id` (Long) - ID del viaje a actualizar.
- **Cuerpo de la solicitud**:
    ```json
    {
        "cuentaId": 123,
        "fechaInicio": "2024-11-11T10:00:00",
        "monopatinId": 456,
        "costoTotal": 60.0,
        "totalKmRecorridos": 20.0,
        "totalTiempo": 1300,
        "totalTiempoUsoSinPausas": 1100
    }
    ```
- **Código de respuesta**: `200 OK` - Retorna el objeto `ViajeDTO` actualizado.

---

### 5. Eliminar un viaje
- **URL**: `/api/viajes/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina un viaje por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del viaje a eliminar.
- **Código de respuesta**: `204 No Content` - El viaje ha sido eliminado correctamente.

---

### 6. Crear múltiples viajes
- **URL**: `/api/viajes/altaViajes`
- **Método**: `POST`
- **Descripción**: Crea múltiples viajes.
- **Cuerpo de la solicitud**:
    ```json
    [
        {
            "cuentaId": 123,
            "fechaInicio": "2024-11-11T10:00:00",
            "monopatinId": 456,
            "costoTotal": 50.0,
            "totalKmRecorridos": 10.0,
            "totalTiempo": 900,
            "totalTiempoUsoSinPausas": 800
        },
        {
            "cuentaId": 124,
            "fechaInicio": "2024-11-12T11:00:00",
            "monopatinId": 457,
            "costoTotal": 40.0,
            "totalKmRecorridos": 8.0,
            "totalTiempo": 800,
            "totalTiempoUsoSinPausas": 700
        }
    ]
    ```
- **Código de respuesta**: `200 OK` - Retorna una lista de los viajes creados.

---

### 7. Obtener todos los viajes
- **URL**: `/api/viajes`
- **Método**: `GET`
- **Descripción**: Obtiene una lista de todos los viajes.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `ViajeDTO`.

---

### 8. Pausar un viaje
- **URL**: `/api/viajes/{id}/pausar`
- **Método**: `POST`
- **Descripción**: Pausa un viaje en curso.
- **Parámetros de ruta**:
    - `id` (Long) - ID del viaje a pausar.
- **Código de respuesta**: `200 OK` - El viaje ha sido pausado correctamente.

---

### 9. Reanudar un viaje
- **URL**: `/api/viajes/{id}/reanudar`
- **Método**: `POST`
- **Descripción**: Reanuda un viaje previamente pausado.
- **Parámetros de ruta**:
    - `id` (Long) - ID del viaje a reanudar.
- **Código de respuesta**: `200 OK` - El viaje ha sido reanudado correctamente.

---

### 10. Ubicar un monopatín en una parada
- **URL**: `/api/viajes/finalizar/{viajeId}/monopatin/{monopatinId}/parada/{paradaId}`
- **Método**: `POST`
- **Descripción**: Finaliza un viaje y ubica un monopatín en una parada específica.
- **Parámetros de ruta**:
    - `viajeId` (Long) - ID del viaje a finalizar.
    - `monopatinId` (Long) - ID del monopatín.
    - `paradaId` (Long) - ID de la parada.
- **Código de respuesta**: `204 No Content` - El monopatín ha sido ubicado en la parada correctamente.

---

### 11. Calcular el costo del viaje
- **URL**: `/api/viajes/{id}/costo`
- **Método**: `GET`
- **Descripción**: Calcula el costo total de un viaje.
- **Parámetros de ruta**:
    - `id` (Long) - ID del viaje.
- **Código de respuesta**: `200 OK` - Retorna el costo total del viaje.

---

### 12. Calcular el tiempo de uso de un viaje
- **URL**: `/api/viajes/{id}/tiempo-uso`
- **Método**: `GET`
- **Descripción**: Calcula el tiempo de uso total de un viaje.
- **Parámetros de ruta**:
    - `id` (Long) - ID del viaje.
- **Código de respuesta**:
    - `200 OK` - Retorna el tiempo de uso total del viaje.
    - `400 Bad Request` - Si las fechas no son válidas.
    - `404 Not Found` - Si el viaje no se encuentra.
    - `500 Internal Server Error` - Para otros errores.

---

### 13. Obtener monopatines con más de X viajes en un año
- **URL**: `/api/viajes/reporte/monopatines`
- **Método**: `GET`
- **Descripción**: Obtiene un reporte de los monopatines con más de X viajes en un determinado año.
- **Parámetros de consulta**:
    - `anio` (int) - Año para filtrar.
    - `minViajes` (long) - Número mínimo de viajes para incluir.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `ReporteMonopatinDTO`.

---

### 14. Obtener el total facturado en un rango de meses de un año
- **URL**: `/api/viajes/facturado`
- **Método**: `GET`
- **Descripción**: Obtiene el total facturado en un rango de meses de un año específico.
- **Parámetros de consulta**:
    - `anio` (int) - Año para el reporte.
    - `mesInicio` (int) - Mes de inicio.
    - `mesFin` (int) - Mes de fin.
- **Código de respuesta**: `200 OK` - Retorna el total facturado en el rango de meses proporcionado.

---

### 15. Obtener los viajes realizados por un monopatín
- **URL**: `/api/viajes/viajesPor/{monopatinId}`
- **Método**: `GET`
- **Descripción**: Obtiene los viajes realizados por un monopatín específico.
- **Parámetros de ruta**:
    - `
