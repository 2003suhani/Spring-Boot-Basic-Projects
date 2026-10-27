package com.example.restaurant.service;
import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.restaurant.model.TableBooking;
import com.example.restaurant.exception.ResourceNotFoundException;
/** Business logic for bookings; records are kept in memory. */
@Service
public class BookingService {
    private final List<TableBooking> bookings = new ArrayList<>();
    private final AtomicLong ids = new AtomicLong(100);
    public BookingService() {
        bookings.add(new TableBooking(ids.incrementAndGet(),"Rahul","9876543210",LocalDate.now().plusDays(1),LocalTime.of(19,0),4,"CONFIRMED"));
        bookings.add(new TableBooking(ids.incrementAndGet(),"Priya","9876543211",LocalDate.now().plusDays(2),LocalTime.of(20,0),2,"CONFIRMED"));
    }
    public List<TableBooking> getAll() { return new ArrayList<>(bookings); }
    public TableBooking getById(Long id) {
        return bookings.stream().filter(b -> b.getBookingId().equals(id)).findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
    }
    public TableBooking create(TableBooking b) {
        b.setBookingId(ids.incrementAndGet()); b.setStatus(normalize(b.getStatus())); bookings.add(b); return b;
    }
    public TableBooking update(Long id, TableBooking input) {
        TableBooking b = getById(id);
        b.setCustomerName(input.getCustomerName()); b.setPhoneNumber(input.getPhoneNumber());
        b.setBookingDate(input.getBookingDate()); b.setBookingTime(input.getBookingTime());
        b.setNumberOfGuests(input.getNumberOfGuests());
        if (input.getStatus()!=null && !input.getStatus().isBlank()) b.setStatus(normalize(input.getStatus()));
        return b;
    }
    public TableBooking updateStatus(Long id, String status) {
        TableBooking b=getById(id); b.setStatus(normalize(status)); return b;
    }
    public void delete(Long id) { bookings.remove(getById(id)); }
    public List<TableBooking> searchCustomer(String name) {
        if(name==null || name.isBlank()) throw new IllegalArgumentException("Customer name is required");
        return bookings.stream().filter(b -> b.getCustomerName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }
    public List<TableBooking> searchDate(LocalDate date) {
        return bookings.stream().filter(b -> b.getBookingDate().equals(date)).collect(Collectors.toList());
    }
    public long count() { return bookings.size(); }
    public long countConfirmed() { return bookings.stream().filter(b -> "CONFIRMED".equals(b.getStatus())).count(); }
    private String normalize(String status) {
        if(status==null || status.isBlank()) return "CONFIRMED";
        String s=status.trim().toUpperCase();
        if(!s.equals("CONFIRMED") && !s.equals("CANCELLED")) throw new IllegalArgumentException("Status must be CONFIRMED or CANCELLED");
        return s;
    }
}