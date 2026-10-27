package com.example.hospital.model;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.*;
/** Appointment model preserving the Word exercise fields; status is an extra field. */
@Data @NoArgsConstructor @AllArgsConstructor
public class Appointment {
    private Long appointmentId;
    @NotBlank(message="Patient name is required") private String patientName;
    @NotBlank(message="Doctor name is required") private String doctorName;
    @NotBlank(message="Disease field cannot be empty") private String disease;
    @NotNull(message="Appointment date is required")
    @FutureOrPresent(message="Appointment date cannot be in the past")
    private LocalDate appointmentDate;
    private String status;
}