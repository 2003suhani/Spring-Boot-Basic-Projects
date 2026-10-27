package com.example.hospital.controller;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital.model.Appointment;
import com.example.hospital.service.AppointmentService;

/**
 * REST controller for hospital appointment management.
 * Receives HTTP requests and returns appropriate responses.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    // Constructor injection of the service class.
    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // GET: Retrieve all appointments.
    @GetMapping
    public ResponseEntity<List<Appointment>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // GET: Retrieve an appointment using its ID.
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    // POST: Create a new appointment.
    @PostMapping
    public ResponseEntity<Appointment> create(
            @Valid @RequestBody Appointment appointment) {

        Appointment created = service.create(appointment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // PUT: Update all required appointment details.
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(
            @PathVariable Long id,
            @Valid @RequestBody Appointment appointment) {

        Appointment updated = service.update(id, appointment);

        return ResponseEntity.ok(updated);
    }

    // PATCH: Update only the appointment status.
    @PatchMapping("/{id}/status")
    public ResponseEntity<Appointment> status(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        String newStatus = request.get("status");

        Appointment updated = service.updateStatus(id, newStatus);

        return ResponseEntity.ok(updated);
    }

    // DELETE: Remove an appointment using its ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    // GET: Search appointments by doctor name.
    @GetMapping("/search/doctor")
    public ResponseEntity<List<Appointment>> doctor(
            @RequestParam String doctor) {

        List<Appointment> appointments =
                service.searchDoctor(doctor);

        return ResponseEntity.ok(appointments);
    }

    // GET: Search appointments by patient name.
    @GetMapping("/search/patient")
    public ResponseEntity<List<Appointment>> patient(
            @RequestParam String patient) {

        List<Appointment> appointments =
                service.searchPatient(patient);

        return ResponseEntity.ok(appointments);
    }

    // GET: Count all appointments.
    @GetMapping("/summary/count")
    public Map<String, Long> count() {

        long total = service.count();

        return Map.of("totalAppointments", total);
    }

    // GET: Count appointments that are not cancelled.
    @GetMapping("/summary/active")
    public Map<String, Long> active() {

        long totalActive = service.countActive();

        return Map.of("activeAppointments", totalActive);
    }
}
