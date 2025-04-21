# API Endpoints de Administrador

## Descripción general
Este microservicio está diseñado para gestionar las funciones administrativas, como la anulación de cuentas, la generación de reportes, ajustes de tarifas, gestión de monopatines y su ubicación en paradas.

---

## Endpoints de Administrador

### 1. Anular una cuenta
- **URL**: `/api/administrador/cuenta/{cuentaId}/anular`
- **Método**: `PUT`
- **Descripción**: Anula una cuenta de usuario identificada por su `cuentaId`.
- **Parámetros de ruta**:
    - `cuentaId` (Long) - ID de la cuenta a anular.
- **Código de respuesta**: `204 No Content`

---

### 2. Consultar reporte de monopatines con más de X viajes
- **URL**: `/api/administrador/reporte/monopatines`
- **Método**: `GET`
- **Descripción**: Obtiene un reporte de los monopatines que han realizado más de X viajes durante un año específico.
- **Parámetros de consulta**:
    - `anio` (int) - Año del reporte.
    - `minViajes` (long) - Mínimo número de viajes realizados.
- **Código de respuesta**: `200 OK`

---

### 3. Consultar total facturado en un rango de meses
- **URL**: `/api/administrador/reporte/facturado`
- **Método**: `GET`
- **Descripción**: Obtiene un reporte de la cantidad total facturada en un rango de meses durante un año específico.
- **Parámetros de consulta**:
    - `anio` (int) - Año del reporte.
    - `mesInicio` (int) - Mes de inicio (1-12).
    - `mesFin` (int) - Mes de fin (1-12).
- **Código de respuesta**: `200 OK`

---

### 4. Consultar cantidad de monopatines en operación vs en mantenimiento
- **URL**: `/api/administrador/estado-monopatines`
- **Método**: `GET`
- **Descripción**: Consulta el estado actual de los monopatines en operación y en mantenimiento.
- **Código de respuesta**: `200 OK`

---

### 5. Realizar ajuste de tarifas a partir de cierta fecha
- **URL**: `/api/administrador/ajustes/tarifas`
- **Método**: `POST`
- **Descripción**: Ajusta las tarifas base y extra a partir de una fecha especificada.
- **Parámetros de consulta**:
    - `fechaAjuste` (LocalDate) - Fecha en la que se realiza el ajuste de tarifas.
    - `porcentajeBase` (double) - Porcentaje de ajuste sobre la tarifa base por minuto.
    - `porcentajeExtra` (double) - Porcentaje de ajuste sobre la tarifa extra por minuto.
-
---


### 6. Agregar un monopatín
- **URL**: `/api/administrador/monopatines`
- **Método**: `POST`
- **Descripción**: Agrega un nuevo monopatín al sistema.
- **Cuerpo de la solicitud**:
    - Se espera un objeto `MonopatinDTO` con los datos del monopatín:
        - `id`: ID del monopatín (auto-generado).
        - Otros campos relevantes como `modelo`, `estado`, `latitud`, `longitud`, etc.
- **Código de respuesta**: `200 OK`

---

### 7. Eliminar un monopatín
- **URL**: `/api/administrador/monopatines/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina un monopatín del sistema por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del monopatín a eliminar.
- **Código de respuesta**: `204 No Content`

---

### 8. Obtener un monopatín por ID
- **URL**: `/api/administrador/monopatines/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de un monopatín específico por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID del monopatín a consultar.
- **Código de respuesta**: `200 OK`
