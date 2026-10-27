package com.example.restaurant.model;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.validation.constraints.*;
import lombok.*;
/** Model for a restaurant table booking. */
@Data @NoArgsConstructor @AllArgsConstructor
public class TableBooking {
    private Long bookingId;
    @NotBlank(message="Customer name is required")
    private String customerName;
    @NotBlank(message="Phone number is required")
    @Pattern(regexp="^[0-9]{10}$", message="Phone number must contain 10 digits")
    private String phoneNumber;
    @NotNull(message="Booking date is required")
    @FutureOrPresent(message="Booking date cannot be in the past")
    private LocalDate bookingDate;
    @NotNull(message="Booking time is required")
    private LocalTime bookingTime;
    @NotNull(message="Number of guests is required")
    @Min(value=1, message="At least one guest is required")
    private Integer numberOfGuests;
    private String status;
}