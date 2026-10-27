# Restaurant Table Booking System

Spring Boot REST API using Controller → Service → Model, a separate exception package, validation, custom exceptions, and an in-memory list. Java 17, Spring Boot 3.4.4.

## Run in STS
Import using **File → Import → Maven → Existing Maven Projects**, select this folder, wait for dependencies, then run `RestaurantTableBookingApplication.java` as Spring Boot App. Port: `8087`.

Base URL: `http://localhost:8087/api/bookings`

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/bookings` | List bookings |
| GET | `/api/bookings/{id}` | Find by ID |
| POST | `/api/bookings` | Create |
| PUT | `/api/bookings/{id}` | Update |
| PATCH | `/api/bookings/{id}/status` | Update status |
| DELETE | `/api/bookings/{id}` | Delete |
| GET | `/api/bookings/search?customerName=Priya` | Search customer |
| GET | `/api/bookings/date?date=2026-11-05` | Search date |
| GET | `/api/bookings/summary/count` | Count all |
| GET | `/api/bookings/summary/confirmed` | Count confirmed |

POST JSON:
```json
{"customerName":"Sanjay","phoneNumber":"9876543212","bookingDate":"2026-11-05","bookingTime":"19:30:00","numberOfGuests":4}
```
Status defaults to `CONFIRMED`. Allowed status values: `CONFIRMED`, `CANCELLED`. Data resets on restart because no database is configured.
