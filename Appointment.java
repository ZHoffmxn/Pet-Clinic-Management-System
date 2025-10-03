import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Appointment {
    private Pet pet;
    private LocalDateTime dateTime;
    private String reason;
    private String veterinarian;
    private int duration;
    private AppointmentStatus status;

    public static final int DEFAULT_DURATION = 30;

    public enum AppointmentStatus {
        SCHEDULED, COMPLETED, CANCELLED
    }

    public Appointment(Pet pet, LocalDateTime dateTime, String reason, String veterinarian, Integer duration) {
        setPet(pet);
        setDateTime(dateTime);
        setReason(reason);
        setVeterinarian(veterinarian);
        setDuration(duration);
        this.status = AppointmentStatus.SCHEDULED;
    }

    public void setPet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        this.pet = pet;
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("Appointment date and time cannot be null");
        }
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment cannot be in the past");
        }
        this.dateTime = dateTime;
    }

    public void setReason(String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Appointment reason cannot be empty");
        }
        this.reason = reason.trim();
    }

    public void setVeterinarian(String veterinarian) {
        if (veterinarian == null || veterinarian.trim().isEmpty()) {
            throw new IllegalArgumentException("Veterinarian name cannot be empty");
        }
        if (!veterinarian.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Veterinarian name cannot contain numbers");
        }
        this.veterinarian = veterinarian.trim();
    }

    public void setDuration(Integer duration) {
        if (duration == null || duration <= 0) {
            this.duration = DEFAULT_DURATION;
        } else {
            this.duration = duration;
        }
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Pet getPet() {
        return pet;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getReason() {
        return reason;
    }

    public String getVeterinarian() {
        return veterinarian;
    }

    public int getDuration() {
        return duration;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public String getFormattedDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return "appointment for " + pet.getName() + " (" + pet.getOwner().getName() + ") on "
                + getFormattedDateTime() + " with " + veterinarian + " - " + status
                + " (duration: " + duration + " mins)\nreason: " + reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return duration == that.duration &&
                Objects.equals(pet, that.pet) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(veterinarian, that.veterinarian);
    }
}
