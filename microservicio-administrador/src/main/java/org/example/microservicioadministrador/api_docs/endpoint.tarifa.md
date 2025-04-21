# API Endpoints de Tarifas

## Descripción general
Este microservicio gestiona las tarifas para el uso de monopatines. Los endpoints permiten crear, obtener, actualizar y eliminar tarifas, así como verificar la existencia de una tarifa y obtener la tarifa actual.

---

## Endpoints de Tarifas

### 1. Crear nuevas tarifas
- **URL**: `/api/tarifas/crearTarifas`
- **Método**: `POST`
- **Descripción**: Crea varias tarifas en el sistema.
- **Cuerpo de la solicitud**:
    - Se espera una lista de objetos `TarifaDTO`, cada uno con los siguientes campos:
        - `fecha`: La fecha de la tarifa.
        - `tarifaBasePorMinuto`: La tarifa base por minuto.
        - `tarifaExtraPorMinuto`: La tarifa extra por minuto.
- **Código de respuesta**: `201 Created`
[
{
  "fecha": "2024-11-01",
  "tarifaBasePorMinuto": 1.0,
  "tarifaExtraPorMinuto": 1.5
},
- {
  "fecha": "2024-11-15",
  "tarifaBasePorMinuto": 1.5,
  "tarifaExtraPorMinuto": 2.0
  }
- ]
---

### 2. Crear una tarifa
- **URL**: `/api/tarifas`
- **Método**: `POST`
- **Descripción**: Crea una tarifa en el sistema.
- **Cuerpo de la solicitud**:
    - Se espera un objeto `TarifaDTO` con los siguientes campos:
        - `fecha`: La fecha de la tarifa.
        - `tarifaBasePorMinuto`: La tarifa base por minuto.
        - `tarifaExtraPorMinuto`: La tarifa extra por minuto.
- **Código de respuesta**: `201 Created`

---

### 3. Obtener una tarifa por ID
- **URL**: `/api/tarifas/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de una tarifa específica por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la tarifa a obtener.
- **Código de respuesta**: `200 OK`

---

### 4. Actualizar una tarifa
- **URL**: `/api/tarifas/{id}`
- **Método**: `PUT`
- **Descripción**: Actualiza una tarifa existente en el sistema.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la tarifa a actualizar.
- **Cuerpo de la solicitud**:
    - Se espera un objeto `TarifaDTO` con los siguientes campos:
        - `fecha`: La nueva fecha de la tarifa.
        - `tarifaBasePorMinuto`: La nueva tarifa base por minuto.
        - `tarifaExtraPorMinuto`: La nueva tarifa extra por minuto.
- **Código de respuesta**: `200 OK`

---

### 5. Eliminar una tarifa
- **URL**: `/api/tarifas/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina una tarifa por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la tarifa a eliminar.
- **Código de respuesta**: `204 No Content`

---

### 6. Verificar si una tarifa existe
- **URL**: `/api/tarifas/tarifa/{id}`
- **Método**: `GET`
- **Descripción**: Verifica si una tarifa existe en el sistema por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la tarifa a verificar.
- **Respuesta**:
    - `true` si la tarifa existe.
    - `false` si no existe.
- **Código de respuesta**: `200 OK`

---

### 7. Obtener todas las tarifas
- **URL**: `/api/tarifas`
- **Método**: `GET`
- **Descripción**: Obtiene todas las tarifas registradas en el sistema.
- **Código de respuesta**: `200 OK`

---

### 8. Obtener la tarifa actual
- **URL**: `/api/tarifas/actual`
- **Método**: `GET`
- **Descripción**: Obtiene la tarifa más reciente del sistema.
- **Código de respuesta**: `200 OK`

---
