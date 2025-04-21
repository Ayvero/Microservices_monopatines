# API Endpoints de Cuenta

## Descripción general
Este microservicio gestiona las operaciones relacionadas con las cuentas, incluyendo la creación, actualización, eliminación, y consultas sobre las cuentas de los usuarios.

---

## Endpoints de Cuenta

### 1. Crear múltiples cuentas
- **URL**: `/api/cuentas/crearCuentas`
- **Método**: `POST`
- **Descripción**: Crea múltiples cuentas en el sistema.
- **Cuerpo de la solicitud**:
    ```json
    [
        {
            "fechaAlta": "2024-11-11T10:00:00",
            "saldo": 500.0,
            "idMercadoPago": "mp123",
            "anulada": false
        },
        {
            "fechaAlta": "2024-11-12T11:30:00",
            "saldo": 1000.0,
            "idMercadoPago": "mp456",
            "anulada": false
        }
    ]
    ```
- **Código de respuesta**: `201 Created` - Retorna una lista de las cuentas creadas.

---

### 2. Crear una cuenta
- **URL**: `/api/cuentas`
- **Método**: `POST`
- **Descripción**: Crea una nueva cuenta en el sistema.
- **Cuerpo de la solicitud**:
    ```json
    {
        "fechaAlta": "2024-11-12T12:00:00",
        "saldo": 200.0,
        "idMercadoPago": "mp789",
        "anulada": false
    }
    ```
- **Código de respuesta**: `201 Created` - Retorna la cuenta recién creada.

---

### 3. Obtener cuenta por ID
- **URL**: `/api/cuentas/{id}`
- **Método**: `GET`
- **Descripción**: Obtiene los detalles de una cuenta específica por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la cuenta.
- **Código de respuesta**: `200 OK` - Retorna el objeto `CuentaDTO` correspondiente.

---

### 4. Obtener todas las cuentas
- **URL**: `/api/cuentas`
- **Método**: `GET`
- **Descripción**: Obtiene una lista de todas las cuentas en el sistema.
- **Código de respuesta**: `200 OK` - Retorna una lista de objetos `CuentaDTO`.

---

### 5. Actualizar una cuenta
- **URL**: `/api/cuentas/{id}`
- **Método**: `PUT`
- **Descripción**: Actualiza los detalles de una cuenta existente.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la cuenta a actualizar.
- **Cuerpo de la solicitud**:
    ```json
    {
        "fechaAlta": "2024-11-12T14:00:00",
        "saldo": 1500.0,
        "idMercadoPago": "mp101112",
        "anulada": false
    }
    ```
- **Código de respuesta**: `200 OK` - Retorna la cuenta actualizada.

---

### 6. Eliminar una cuenta
- **URL**: `/api/cuentas/{id}`
- **Método**: `DELETE`
- **Descripción**: Elimina una cuenta del sistema por su ID.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la cuenta a eliminar.
- **Código de respuesta**: `204 No Content` - La cuenta ha sido eliminada correctamente.

---

### 7. Verificar si una cuenta existe
- **URL**: `/api/cuentas/cuenta/{id}`
- **Método**: `GET`
- **Descripción**: Verifica si una cuenta existe en el sistema.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la cuenta.
- **Código de respuesta**: `200 OK` - 

---

### 8. Asociar un usuario a una cuenta
- **URL**: `/api/cuentas/{cuentaId}/usuarios/{usuarioId}`
- **Método**: `POST`
- **Descripción**: Asocia un usuario a una cuenta específica.
- **Parámetros de ruta**:
    - `cuentaId` (Long) - ID de la cuenta.
    - `usuarioId` (Long) - ID del usuario.
- **Código de respuesta**: `204 No Content` - El usuario ha sido asociado correctamente a la cuenta.

---

### 9. Descontar saldo de una cuenta
- **URL**: `/api/cuentas/{id}/descontar`
- **Método**: `PUT`
- **Descripción**: Descuenta una cantidad específica de saldo de la cuenta.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la cuenta.
- **Parámetros de consulta**:
    - `monto` (double) - Monto a descontar de la cuenta.
- **Código de respuesta**:
    - `200 OK` - Retorna un mensaje de éxito si el descuento fue realizado.
    - `404 Not Found` - Si la cuenta no existe.
    - `400 Bad Request` - Si el monto a descontar no es válido.

---

### 10. Anular una cuenta
- **URL**: `/api/cuentas/{id}/anular`
- **Método**: `PUT`
- **Descripción**: Anula una cuenta específica.
- **Parámetros de ruta**:
    - `id` (Long) - ID de la cuenta a anular.
- **Cuerpo de la solicitud**:
    ```json
    {
        "fechaAlta": "2024-11-10T10:00:00",
        "saldo": 0.0,
        "idMercadoPago": "mp123",
        "anulada": true
    }
    ```
- **Código de respuesta**: `204 No Content` - La cuenta ha sido anulada correctamente.

---
