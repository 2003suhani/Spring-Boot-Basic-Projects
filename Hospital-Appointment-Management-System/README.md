# Hospital Appointment Management System

Spring Boot REST API aligned with the Hospital Appointment Booking exercise in `Basic Spring Boot Exercises.docx`. Required model fields: `appointmentId`, `patientName`, `doctorName`, `disease`, `appointmentDate`. `status` is an additional field for PATCH practice. Includes Controller → Service → Model, validation, custom exception, global exception handler, and in-memory data.

## Run in STS
Import using **File → Import → Maven → Existing Maven Projects**, select this folder, wait for dependencies, then run `HospitalAppointmentSystemApplication.java` as Spring Boot App. Port: `8088`.

Base URL: `http://localhost:8088/api/appointments`

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/appointments` | List appointments |
| GET | `/api/appointments/{id}` | Find by ID |
| POST | `/api/appointments` | Create |
| PUT | `/api/appointments/{id}` | Update |
| PATCH | `/api/appointments/{id}/status` | Update status |
| DELETE | `/api/appointments/{id}` | Delete |
| GET | `/api/appointments/search/doctor?doctor=Sharma` | Search doctor |
| GET | `/api/appointments/search/patient?patient=Meera` | Search patient |
| GET | `/api/appointments/summary/count` | Count all |
| GET | `/api/appointments/summary/active` | Count non-cancelled |

POST JSON:
```json
{"patientName":"Meera","doctorName":"Dr. Sharma","disease":"General checkup","appointmentDate":"2026-11-05","status":"BOOKED"}
```
Allowed status values: `BOOKED`, `COMPLETED`, `CANCELLED`. Data resets on restart because no database is configured.
