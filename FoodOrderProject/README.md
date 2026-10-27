# Online Food Order System

Spring Boot REST API based on the first project in `Basic Spring Boot Exercises.docx`. Required fields: `orderId`, `customerName`, `foodItem`, `quantity`, `price`. `status` is an extra field for PATCH practice.

## Run in STS
Import with **File → Import → Maven → Existing Maven Projects**, select this folder, wait for Maven, then run `OnlineFoodOrderApplication.java` as Spring Boot App. Java 17, Spring Boot 3.4.4, port 8086.

Base URL: `http://localhost:8086/api/orders`

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/orders` | Get all orders |
| GET | `/api/orders/{id}` | Get order by ID |
| POST | `/api/orders` | Add order |
| PUT | `/api/orders/{id}` | Update order |
| PATCH | `/api/orders/{id}` | Partial update |
| DELETE | `/api/orders/{id}` | Delete order |
| GET | `/api/orders/search?customerName=Asha` | Search by customer |
| GET | `/api/orders/summary/count` | Count orders |
| GET | `/api/orders/summary/value` | Total order value |

POST JSON:
```json
{"customerName":"Meera","foodItem":"Paneer Pizza","quantity":2,"price":250.0}
```
PATCH JSON example:
```json
{"quantity":3,"status":"PREPARING"}
```
Valid statuses: `PLACED`, `PREPARING`, `DELIVERED`, `CANCELLED`. Data is stored in memory and resets on restart.
