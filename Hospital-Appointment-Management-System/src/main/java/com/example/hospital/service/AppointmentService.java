package com.example.hospital.service;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.hospital.model.Appointment;
import com.example.hospital.exception.ResourceNotFoundException;
/** Business logic for hospital appointments; data is held in memory. */
@Service
public class AppointmentService {
    private final List<Appointment> appointments=new ArrayList<>();
    private final AtomicLong ids=new AtomicLong(100);
    public AppointmentService() {
        appointments.add(new Appointment(ids.incrementAndGet(),"Arun","Dr. Sharma","Fever",LocalDate.now().plusDays(1),"BOOKED"));
        appointments.add(new Appointment(ids.incrementAndGet(),"Meera","Dr. Rao","Dental pain",LocalDate.now().plusDays(2),"BOOKED"));
    }
    public List<Appointment> getAll() { return new ArrayList<>(appointments); }
    public Appointment getById(Long id) {
        return appointments.stream().filter(a -> a.getAppointmentId().equals(id)).findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: "+id));
    }
    public Appointment create(Appointment a) {
        a.setAppointmentId(ids.incrementAndGet()); a.setStatus(normalize(a.getStatus())); appointments.add(a); return a;
    }
    public Appointment update(Long id, Appointment input) {
        Appointment a=getById(id); a.setPatientName(input.getPatientName()); a.setDoctorName(input.getDoctorName());
        a.setDisease(input.getDisease()); a.setAppointmentDate(input.getAppointmentDate());
        if(input.getStatus()!=null && !input.getStatus().isBlank()) a.setStatus(normalize(input.getStatus()));
        return a;
    }
    public Appointment updateStatus(Long id,String status) { Appointment a=getById(id); a.setStatus(normalize(status)); return a; }
    public void delete(Long id) { appointments.remove(getById(id)); }
    public List<Appointment> searchDoctor(String doctor) {
        if(doctor==null || doctor.isBlank()) throw new IllegalArgumentException("Doctor name is required");
        return appointments.stream().filter(a -> a.getDoctorName().toLowerCase().contains(doctor.toLowerCase())).collect(Collectors.toList());
    }
    public List<Appointment> searchPatient(String patient) {
        if(patient==null || patient.isBlank()) throw new IllegalArgumentException("Patient name is required");
        return appointments.stream().filter(a -> a.getPatientName().toLowerCase().contains(patient.toLowerCase())).collect(Collectors.toList());
    }
    public long count() { return appointments.size(); }
    public long countActive() { return appointments.stream().filter(a -> !"CANCELLED".equals(a.getStatus())).count(); }
    private String normalize(String status) {
        if(status==null || status.isBlank()) return "BOOKED";
        String s=status.trim().toUpperCase();
        if(!s.equals("BOOKED") && !s.equals("COMPLETED") && !s.equals("CANCELLED"))
            throw new IllegalArgumentException("Status must be BOOKED, COMPLETED, or CANCELLED");
        return s;
    }
}