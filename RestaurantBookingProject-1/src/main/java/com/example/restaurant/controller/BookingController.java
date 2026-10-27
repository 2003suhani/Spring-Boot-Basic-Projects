package com.example.restaurant.controller;
import java.time.LocalDate;
import java.util.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.restaurant.model.TableBooking;
import com.example.restaurant.service.BookingService;
/** REST endpoints for table bookings. */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService service;
    public BookingController(BookingService service) { this.service=service; }
    @GetMapping public ResponseEntity<List<TableBooking>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}") public ResponseEntity<TableBooking> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @PostMapping public ResponseEntity<TableBooking> create(@Valid @RequestBody TableBooking b) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(b)); }
    @PutMapping("/{id}") public ResponseEntity<TableBooking> update(@PathVariable Long id,@Valid @RequestBody TableBooking b) { return ResponseEntity.ok(service.update(id,b)); }
    @PatchMapping("/{id}/status") public ResponseEntity<TableBooking> status(@PathVariable Long id,@RequestBody Map<String,String> body) { return ResponseEntity.ok(service.updateStatus(id,body.get("status"))); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
    @GetMapping("/search") public ResponseEntity<List<TableBooking>> search(@RequestParam String customerName) { return ResponseEntity.ok(service.searchCustomer(customerName)); }
    @GetMapping("/date") public ResponseEntity<List<TableBooking>> date(@RequestParam LocalDate date) { return ResponseEntity.ok(service.searchDate(date)); }
    @GetMapping("/summary/count") public Map<String,Long> count() { return Map.of("totalBookings",service.count()); }
    @GetMapping("/summary/confirmed") public Map<String,Long> countConfirmed() { return Map.of("confirmedBookings",service.countConfirmed()); }
}