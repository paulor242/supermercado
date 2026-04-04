# Supermercado API

## Funcionalidades implementadas

---

### 1. Validación de stock suficiente (Error 400)

Al crear un detalle de venta (`POST /api/v1/details`), el sistema verifica que el producto tenga
stock suficiente antes de registrar la venta.

**¿Cómo funciona?**
- Se busca el producto por `idProduct` en la base de datos.
- Se compara `product.stock` con el `amount` enviado en el request.
- Si el stock es menor al amount solicitado, se retorna un error `400 Bad Request` con el mensaje:

```
Stock insuficiente. Disponible: X, solicitado: Y
```

**Ejemplo de request:**
```json
POST /api/v1/details
{
  "idProduct": 1,
  "amount": 50,
  "unitPrice": 5000,
  "subTotal": 250000,
  "sales": 1
}
```

**Respuesta si no hay stock:**
```json
HTTP 400 Bad Request
{
  "message": "Stock insuficiente. Disponible: 10, solicitado: 50"
}
```

---

### 2. Calcular subtotal, IVA 19% y total

Los campos `subTotal`, `vat` (IVA) y `total` de la venta son calculados por el servidor,
no deben ser enviados manualmente por el cliente.

**Fórmulas aplicadas:**
```
subTotal = unitPrice × amount
vat      = subTotal × 0.19
total    = subTotal + vat
```

**Ejemplo:**
- `unitPrice` = 10.000
- `amount` = 3
- `subTotal` = 30.000
- `vat` = 5.700
- `total` = 35.700

---

### 3. Protección de endpoints con JWT (CAJERO y ADMINISTRADOR)

Los endpoints de ventas y detalles de venta están protegidos con JWT.
Solo los usuarios con rol `CAJERO` o `ADMINISTRADOR` pueden acceder.

**Endpoints protegidos:**
- `GET/POST/PUT/DELETE /api/v1/sale`
- `GET/POST/PUT/DELETE /api/v1/details`

**¿Cómo enviar el token?**

Incluir el token JWT en el header de cada petición:
```
Authorization: Bearer <tu_token_jwt>
```

**Respuestas posibles:**
| Situación | Código |
|---|---|
| Token válido con rol correcto | `200 OK` |
| Sin token o token inválido | `401 Unauthorized` |
| Token válido pero rol incorrecto | `403 Forbidden` |

**Los roles deben estar incluidos en el claim `roles` del token JWT**, separados por coma si hay más de uno. Ejemplo: `CAJERO` o `ADMINISTRADOR`.
