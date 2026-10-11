# 1. Caso exitoso: pago e inventario OK, orden confirmada

curl -X POST http://localhost:8080/saga/orders \
 -H "Content-Type: application/json" \
 -d '{"customerId":"C1","productId":"P1","quantity":1,"amount":500}'

# 2. Fallo de pago: monto excede el límite, orden cancelada

curl -X POST http://localhost:8080/saga/orders \
 -H "Content-Type: application/json" \
 -d '{"customerId":"C2","productId":"P1","quantity":1,"amount":1500}'

# 3. Fallo de inventario: stock insuficiente, pago reembolsado y orden cancelada

curl -X POST http://localhost:8080/saga/orders \
 -H "Content-Type: application/json" \
 -d '{"customerId":"C3","productId":"P1","quantity":15,"amount":500}'

## Y para consultar el estado de cada orden (reemplazar {id} con el id devuelto en el POST):

curl http://localhost:8080/saga/orders/{id}
