import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class PetClinicManagementSystem {
    private static final String CLINIC_NAME = "Happy Paws Clinic";
    private List<Pet> pets = new ArrayList<>();
    private static final String PET_DETAILS_FILE = "PetDetails.txt";
    private List<Appointment> appointments = new ArrayList<>();
    private static final String APPOINTMENT_DETAILS_FILE = "AppointmentDetails.txt";
    private Scanner scanner;

    public PetClinicManagementSystem() {
        this.scanner = new Scanner(System.in);
    }

    public void addPet() {
        try {
            System.out.println("Enter Owner Details:");
            System.out.print("Owner Name: ");
            String ownerName = scanner.nextLine().trim();
            System.out.print("Owner Phone Number: ");
            String ownerPhone = scanner.nextLine().trim();
            System.out.print("Owner Address: ");
            String ownerAddress = scanner.nextLine().trim();

            Owner owner = new Owner(ownerName, ownerPhone, ownerAddress);

            System.out.print("Enter pet name: ");
            String name = scanner.nextLine().trim();
            if (name.length() > 16) {
                System.out.println("Pet name cannot exceed 16 characters.");
                return;
            }

            if (!name.matches("[a-zA-Z0-9 ]+")) {
                System.out.println("Pet name cannot contain special characters.");
                return;
            }

            System.out.print("Enter pet age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter pet colour: ");
            String colour = scanner.nextLine().trim();

            double weight;
            while (true) {
                System.out.print("Enter pet weight (kg): ");
                try {
                    weight = Double.parseDouble(scanner.nextLine().trim());
                    if (weight > 0) {
                        break;
                    } else {
                        System.out.println("Weight must be a positive number. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value for weight.");
                }
            }

            System.out.print("Enter pet type (Cat/Dog/Hamster): ");
            String type = scanner.nextLine().trim();

            Pet newPet;
            if (type.equalsIgnoreCase("Cat")) {
                System.out.print("Enter cat breed: ");
                String breed = scanner.nextLine().trim();
                newPet = new Pet.Cat(name, age, colour, weight, breed, owner);
            } else if (type.equalsIgnoreCase("Dog")) {
                System.out.print("Enter dog breed: ");
                String breed = scanner.nextLine().trim();
                newPet = new Pet.Dog(name, age, colour, weight, breed, owner);
            } else if (type.equalsIgnoreCase("Hamster")) {
                System.out.print("Enter hamster breed: ");
                String breed = scanner.nextLine().trim();
                newPet = new Pet.Hamster(name, age, colour, weight, breed, owner);
            } else {
                throw new IllegalArgumentException("Invalid pet type! Please enter Cat, Dog, or Hamster.");
            }

            pets.add(newPet);
            System.out.println(newPet.getName() + " has been added to the clinic.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number input. Please enter valid numeric values.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void deletePet() {
        System.out.print("Enter pet name to delete: ");
        String name = scanner.nextLine().trim();

        Pet petToRemove = null;
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                petToRemove = pet;
                break;
            }
        }

        if (petToRemove != null) {
            pets.remove(petToRemove);
            System.out.println(petToRemove.getName() + " has been removed from the clinic.");
        } else {
            System.out.println("No pet found with the name " + name);
        }
    }

    public void searchPet() {
        System.out.print("Enter name, colour, breed, or owner name to search: ");
        String searchTerm = scanner.nextLine().trim();
        boolean foundPet = false;

        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(searchTerm) ||
                    pet.getColour().equalsIgnoreCase(searchTerm) ||
                    pet.getBreed().equalsIgnoreCase(searchTerm) ||
                    pet.getOwner().getName().equalsIgnoreCase(searchTerm)) {
                System.out.println("Pet found: " + pet);
                System.out.println(pet.speak());
                System.out.println("Owner: " + pet.getOwner().getName() +
                        " (Phone: " + pet.getOwner().getFormattedPhoneNumber() + ")");
                foundPet = true;
            }
        }

        if (!foundPet) {
            System.out.println("No pets found matching the search criteria.");
        }
    }

    public void modifyPet() {
        if (pets.isEmpty()) {
            System.out.println("No pets available to modify.");
            return;
        }

        System.out.print("Enter the name of the pet you want to modify: ");
        String petName = scanner.nextLine().trim();

        Pet petToModify = null;
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(petName)) {
                petToModify = pet;
                break;
            }
        }

        if (petToModify == null) {
            System.out.println("No pet found with the name " + petName);
            return;
        }

        while (true) {
            System.out.println("\nModify " + petToModify.getName() + ":");
            System.out.println("1. Pet Name");
            System.out.println("2. Pet Age");
            System.out.println("3. Pet Colour");
            System.out.println("4. Pet Weight");
            System.out.println("5. Pet Breed");
            System.out.println("6. Owner Name");
            System.out.println("7. Owner Phone");
            System.out.println("8. Owner Address");
            System.out.println("9. Cancel");
            System.out.print("Choose an attribute to modify: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine().trim();

                        if (newName.length() > 16) {
                            System.out.println("Pet name cannot exceed 16 characters.");
                            return;
                        }

                        if (!newName.matches("[a-zA-Z0-9 ]+")) {
                            System.out.println("Pet name cannot contain special characters.");
                            return;
                        }
                        petToModify.setName(newName);
                        System.out.println("Name updated successfully.");
                        return;

                    case 2:
                        System.out.print("Enter new age: ");
                        int newAge = Integer.parseInt(scanner.nextLine().trim());
                        petToModify.setAge(newAge);
                        System.out.println("Age updated successfully.");
                        return;

                    case 3:
                        System.out.print("Enter new colour: ");
                        String newColour = scanner.nextLine().trim();
                        petToModify.setColour(newColour);
                        System.out.println("Colour updated successfully.");
                        return;

                    case 4:
                        double weight;
                        while (true) {
                            System.out.print("Enter pet weight (kg): ");
                            try {
                                weight = Double.parseDouble(scanner.nextLine().trim());
                                if (weight > 0) {
                                    petToModify.setWeight(weight);
                                    System.out.println("Weight updated successfully.");
                                    return;
                                } else {
                                    System.out.println("Weight must be a positive number. Please try again.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a numeric value for weight.");
                            }
                        }

                    case 5:
                        if (petToModify instanceof Pet.Cat) {
                            System.out.print("Enter new cat breed: ");
                            String newBreed = scanner.nextLine().trim();
                            ((Pet.Cat) petToModify).setBreed(newBreed);
                        } else if (petToModify instanceof Pet.Dog) {
                            System.out.print("Enter new dog breed: ");
                            String newBreed = scanner.nextLine().trim();
                            ((Pet.Dog) petToModify).setBreed(newBreed);
                        }
                        System.out.println("Breed updated successfully.");
                        return;

                    case 6:
                        System.out.print("Enter new owner name: ");
                        String newOwnerName = scanner.nextLine().trim();
                        petToModify.getOwner().setName(newOwnerName);
                        System.out.println("Owner name updated successfully.");
                        return;

                    case 7:
                        System.out.print("Enter new owner phone number: ");
                        String newOwnerPhone = scanner.nextLine().trim();
                        petToModify.getOwner().setPhoneNumber(newOwnerPhone);
                        System.out.println("Owner phone updated successfully.");
                        return;

                    case 8:
                        System.out.print("Enter new owner address: ");
                        String newOwnerAddress = scanner.nextLine().trim();
                        petToModify.getOwner().setAddress(newOwnerAddress);
                        System.out.println("Owner address updated successfully.");
                        return;

                    case 9:
                        System.out.println("Modification cancelled.");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void viewAllPets() {
        if (pets.isEmpty()) {
            System.out.println("No pets available.");
        } else {
            System.out.println("List of pets in the clinic:");
            for (Pet pet : pets) {
                System.out.println(pet);
                System.out.println("Owner: " + pet.getOwner().getName() +
                        " (Phone: " + pet.getOwner().getFormattedPhoneNumber() + ")");
                System.out.println("---");
            }
        }
    }

    private String findMostFrequentString(List<String> list) {
        if (list.isEmpty()) return "None";

        String mostFrequent = list.get(0);
        int maxCount = 1;

        List<String> uniqueStrings = getUniqueStrings(list);
        for (String current : uniqueStrings) {
            int currentCount = countOccurrences(list, current);
            if (currentCount > maxCount) {
                mostFrequent = current;
                maxCount = currentCount;
            }
        }

        return mostFrequent;
    }

    private List<String> getUniqueStrings(List<String> list) {
        List<String> uniqueStrings = new ArrayList<>();

        for (String item : list) {
            boolean isUnique = true;
            for (String unique : uniqueStrings) {
                if (item.equals(unique)) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                uniqueStrings.add(item);
            }
        }

        return uniqueStrings;
    }

    private int countOccurrences(List<String> list, String target) {
        int count = 0;
        for (String item : list) {
            if (item.equals(target)) {
                count++;
            }
        }
        return count;
    }

    public void generateReport() {
        if (pets.isEmpty()) {
            System.out.println("No pets in the clinic.");
            return;
        }

        int dogCount = 0;
        int catCount = 0;
        int hamsterCount = 0;

        List<String> colours = new ArrayList<>();
        List<String> breeds = new ArrayList<>();
        List<String> owners = new ArrayList<>();

        for (Pet pet : pets) {
            if (pet.getType().equalsIgnoreCase("Dog")) {
                dogCount++;
            } else if (pet.getType().equalsIgnoreCase("Hamster")) {
                hamsterCount++;
            } else if (pet.getType().equalsIgnoreCase("Cat")) {
                catCount++;
            }

            colours.add(pet.getColour().toLowerCase());
            breeds.add(pet.getBreed().toLowerCase());
            owners.add(pet.getOwner().getName().toLowerCase());
        }

        String dominantColour = findMostFrequentString(colours);

        String dominantBreed = findMostFrequentString(breeds);

        String dominantOwner = findMostFrequentString(owners);

        String dominantPetType;
        if (dogCount >= catCount && dogCount >= hamsterCount) {
            dominantPetType = "Dog";
        } else if (catCount >= dogCount && catCount >= hamsterCount) {
            dominantPetType = "Cat";
        } else {
            dominantPetType = "Hamster";
        }

        System.out.println("----------------------------");
        System.out.println("Pet Type Distribution:");
        System.out.println("Dogs: " + dogCount);
        System.out.println("Cats: " + catCount);
        System.out.println("Hamsters: " + hamsterCount);

        System.out.println("\nColour Distribution:");
        List<String> uniqueColours = getUniqueStrings(colours);
        for (String uniqueColour : uniqueColours) {
            int count = countOccurrences(colours, uniqueColour);
            System.out.println(uniqueColour + ": " + count);
        }

        System.out.println("\nBreed Distribution:");
        List<String> uniqueBreeds = getUniqueStrings(breeds);
        for (String uniqueBreed : uniqueBreeds) {
            int count = countOccurrences(breeds, uniqueBreed);
            System.out.println(uniqueBreed + ": " + count);
        }

        System.out.println("\nOwner Distribution:");
        List<String> uniqueOwners = getUniqueStrings(owners);
        for (String uniqueOwner : uniqueOwners) {
            int count = countOccurrences(owners, uniqueOwner);
            System.out.println(uniqueOwner + ": " + count);
        }

        System.out.println("\nSummary:");
        System.out.println("Total Pets: " + pets.size());
        System.out.println("Dominant Colour: " + dominantColour);
        System.out.println("Dominant Pet Type: " + dominantPetType);
        System.out.println("Dominant Breed: " + dominantBreed);
        System.out.println("Most Frequent Owner: " + dominantOwner);
    }

    public void addAppointment() {
        try {
            if (pets.isEmpty()) {
                System.out.println("No pets available. Please add a pet first.");
                return;
            }

            Pet selectedPet = selectPet("Select a pet for the appointment:");
            if (selectedPet == null) return;

            boolean validAppointment = false;
            LocalDateTime dateTime = null;
            Integer duration = null;

            while (!validAppointment) {
                System.out.print("Enter appointment date and time (HH:mm dd/MM/yyyy): ");
                dateTime = parseDateTime(scanner.nextLine().trim());

                if (!isWithinClinicHours(dateTime)) {
                    System.out.println("Appointment must be between 8:00 AM and 6:00 PM. Please choose another time.");
                    continue;
                }

                System.out.print("Enter appointment duration in minutes (or press Enter for default 30 minutes): ");
                String durationInput = scanner.nextLine().trim();
                if (!durationInput.isEmpty()) {
                    try {
                        duration = Integer.parseInt(durationInput);
                        if (duration <= 0) {
                            System.out.println("Invalid duration. Using default 30 minutes.");
                            duration = Appointment.DEFAULT_DURATION;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid duration. Using default 30 minutes.");
                        duration = Appointment.DEFAULT_DURATION;
                    }
                }

                int appointmentDuration = (duration != null) ? duration : Appointment.DEFAULT_DURATION;
                if (isAppointmentConflict(dateTime, appointmentDuration)) {
                    System.out.println("This time slot is already booked. Please choose another time.");
                    continue;
                }

                validAppointment = true;
            }

            System.out.print("Enter reason for appointment: ");
            String reason = scanner.nextLine().trim();

            System.out.print("Enter veterinarian name: ");
            String veterinarian = scanner.nextLine().trim();

            Appointment newAppointment = new Appointment(selectedPet, dateTime, reason, veterinarian, duration);
            appointments.add(newAppointment);

            System.out.println("Appointment added successfully:");
            System.out.println(newAppointment);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Error adding appointment: " + e.getMessage());
        }
    }


    private boolean isAppointmentConflict(LocalDateTime newDateTime, int durationMinutes) {
        LocalDateTime newEndTime = newDateTime.plusMinutes(durationMinutes);

        for (Appointment existingAppointment : appointments) {
            if (existingAppointment.getStatus() == Appointment.AppointmentStatus.CANCELLED) {
                continue;
            }

            LocalDateTime existingStartTime = existingAppointment.getDateTime();
            LocalDateTime existingEndTime = existingStartTime.plusMinutes(existingAppointment.getDuration());

            if ((newDateTime.isBefore(existingEndTime) && newEndTime.isAfter(existingStartTime)) ||
                    newDateTime.isEqual(existingStartTime)) {
                System.out.println("Conflict with appointment: " + existingAppointment);
                return true;
            }
        }
        return false;
    }

    private boolean isWithinClinicHours(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        return hour >= 8 && hour < 18;
    }

    public void deleteAppointment() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        Appointment appointmentToRemove = searchAppointment("Select an appointment to delete:");
        if (appointmentToRemove != null) {
            appointments.remove(appointmentToRemove);
            System.out.println("Appointment deleted successfully.");
        }
    }

    public void modifyAppointment() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        Appointment appointmentToModify = searchAppointment("Select an appointment to modify:");
        if (appointmentToModify == null) return;

        while (true) {
            System.out.println("\nModify Appointment:");
            System.out.println("1. Change Date and Time");
            System.out.println("2. Change Reason");
            System.out.println("3. Change Veterinarian");
            System.out.println("4. Change Status");
            System.out.println("5. Change Duration");
            System.out.println("6. Cancel");
            System.out.print("Choose an attribute to modify: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        boolean validDateTime = false;
                        LocalDateTime newDateTime = null;

                        while (!validDateTime) {
                            System.out.print("Enter new date and time (HH:mm dd/MM/yyyy): ");
                            newDateTime = parseDateTime(scanner.nextLine().trim());

                            if (!isWithinClinicHours(newDateTime)) {
                                System.out.println("Appointment must be between 8:00 AM and 6:00 PM. Please choose another time.");
                                continue;
                            }

                            boolean hasConflict = false;
                            for (Appointment other : appointments) {
                                if (other != appointmentToModify && other.getStatus() != Appointment.AppointmentStatus.CANCELLED) {
                                    LocalDateTime otherStart = other.getDateTime();
                                    LocalDateTime otherEnd = otherStart.plusMinutes(other.getDuration());
                                    LocalDateTime newEnd = newDateTime.plusMinutes(appointmentToModify.getDuration());

                                    if ((newDateTime.isBefore(otherEnd) && newEnd.isAfter(otherStart)) ||
                                            newDateTime.isEqual(otherStart)) {
                                        System.out.println("Conflict with appointment: " + other);
                                        hasConflict = true;
                                        break;
                                    }
                                }
                            }

                            if (hasConflict) {
                                System.out.println("This time slot is already booked. Please choose another time.");
                                continue;
                            }

                            validDateTime = true;
                        }

                        appointmentToModify.setDateTime(newDateTime);
                        System.out.println("Date and time updated successfully.");
                        return;

                    case 2:
                        System.out.print("Enter new reason: ");
                        String newReason = scanner.nextLine().trim();
                        appointmentToModify.setReason(newReason);
                        System.out.println("Reason updated successfully.");
                        return;

                    case 3:
                        System.out.print("Enter new veterinarian name: ");
                        String newVeterinarian = scanner.nextLine().trim();
                        appointmentToModify.setVeterinarian(newVeterinarian);
                        System.out.println("Veterinarian updated successfully.");
                        return;

                    case 4:
                        System.out.println("Select new status:");
                        for (Appointment.AppointmentStatus status : Appointment.AppointmentStatus.values()) {
                            System.out.println((status.ordinal() + 1) + ". " + status);
                        }
                        System.out.print("Enter status number: ");
                        int statusChoice = Integer.parseInt(scanner.nextLine().trim());
                        appointmentToModify.setStatus(Appointment.AppointmentStatus.values()[statusChoice - 1]);
                        System.out.println("Status updated successfully.");
                        return;

                    case 5:
                        System.out.print("Enter new duration in minutes (or press Enter for default 30 minutes): ");
                        String durationInput = scanner.nextLine().trim();
                        Integer newDuration = null;
                        if (!durationInput.isEmpty()) {
                            newDuration = Integer.parseInt(durationInput);
                        }

                        LocalDateTime appointmentDateTime = appointmentToModify.getDateTime();
                        int durationToCheck = (newDuration != null) ? newDuration : Appointment.DEFAULT_DURATION;

                        boolean hasConflictWithNewDuration = false;
                        for (Appointment other : appointments) {
                            if (other != appointmentToModify && other.getStatus() != Appointment.AppointmentStatus.CANCELLED) {
                                LocalDateTime otherStart = other.getDateTime();
                                LocalDateTime otherEnd = otherStart.plusMinutes(other.getDuration());
                                LocalDateTime newEnd = appointmentDateTime.plusMinutes(durationToCheck);

                                if ((appointmentDateTime.isBefore(otherEnd) && newEnd.isAfter(otherStart))) {
                                    System.out.println("New duration conflicts with appointment: " + other);
                                    hasConflictWithNewDuration = true;
                                    break;
                                }
                            }
                        }

                        if (hasConflictWithNewDuration) {
                            System.out.println("Cannot update duration due to conflicts with other appointments.");
                            return;
                        }

                        appointmentToModify.setDuration(newDuration);
                        System.out.println("Duration updated successfully.");
                        return;

                    case 6:
                        System.out.println("Modification cancelled.");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        System.out.println("List of Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    public void searchAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        System.out.print("Enter pet name or owner name to search: ");
        String searchTerm = scanner.nextLine().trim().toLowerCase();

        List<Appointment> matchingAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPet().getName().toLowerCase().contains(searchTerm) ||
                    appointment.getPet().getOwner().getName().toLowerCase().contains(searchTerm)) {
                matchingAppointments.add(appointment);
            }
        }

        if (matchingAppointments.isEmpty()) {
            System.out.println("No appointments found matching the search criteria.");
        } else {
            System.out.println("Matching Appointments:");
            for (Appointment appointment : matchingAppointments) {
                System.out.println(appointment);
            }
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date and time format. Use HH:mm dd/MM/yyyy");
        }
    }

    private Pet selectPet(String prompt) {
        System.out.println(prompt);

        for (Pet pet : pets) {
            System.out.println(pet);
        }

        System.out.print("Enter pet name: ");
        String petName = scanner.nextLine().trim();

        List<Pet> matchingPets = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(petName)) {
                matchingPets.add(pet);
            }
        }

        if (matchingPets.isEmpty()) {
            System.out.println("No pet found with the name '" + petName + "'.");
            return null;
        } else if (matchingPets.size() == 1) {
            return matchingPets.get(0);
        } else {
            System.out.println("Multiple pets found with name '" + petName + "'. Please select by owner:");
            for (int i = 0; i < matchingPets.size(); i++) {
                Pet pet = matchingPets.get(i);
                System.out.println((i + 1) + ". " + pet.getName() + " (Owner: " + pet.getOwner().getName() + ")");
            }

            System.out.print("Enter number: ");
            try {
                int selection = Integer.parseInt(scanner.nextLine().trim());
                if (selection < 1 || selection > matchingPets.size()) {
                    System.out.println("Invalid selection.");
                    return null;
                }
                return matchingPets.get(selection - 1);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                return null;
            }
        }
    }

    private Appointment searchAppointment(String prompt) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return null;
        }

        System.out.println(prompt);
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println((i + 1) + ". " + appointments.get(i));
        }
        System.out.print("Enter appointment number: ");

        try {
            int appointmentChoice = Integer.parseInt(scanner.nextLine().trim());
            if (appointmentChoice < 1 || appointmentChoice > appointments.size()) {
                System.out.println("Invalid appointment selection.");
                return null;
            }
            return appointments.get(appointmentChoice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return null;
        }
    }

    public void saveAppointments() {
        try (BufferedWriter appointmentWriter = new BufferedWriter(new FileWriter(APPOINTMENT_DETAILS_FILE))) {
            for (Appointment appointment : appointments) {
                appointmentWriter.write(
                        appointment.getPet().getName() + ", " +
                                appointment.getFormattedDateTime() + ", " +
                                appointment.getReason() + ", " +
                                appointment.getVeterinarian() + ", " +
                                appointment.getDuration() + ", " +
                                appointment.getStatus()
                );
                appointmentWriter.newLine();
            }
            System.out.println("Appointments saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving appointments: " + e.getMessage());
        }
    }

    public void loadAppointments() {
        File appointmentFile = new File(APPOINTMENT_DETAILS_FILE);
        appointments.clear();

        if (!appointmentFile.exists()) {
            System.out.println("No previous appointment data found.");
            return;
        }

        try (BufferedReader appointmentReader = new BufferedReader(new FileReader(appointmentFile))) {
            String line;
            while ((line = appointmentReader.readLine()) != null) {
                try {
                    String[] parts = line.split(", ");

                    Pet pet = pets.stream()
                            .filter(p -> p.getName().equals(parts[0]))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

                    LocalDateTime dateTime = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
                    String reason = parts[2];
                    String veterinarian = parts[3];

                    Integer duration = null;
                    Appointment.AppointmentStatus status;

                    if (parts.length >= 6) {
                        duration = Integer.parseInt(parts[4]);
                        status = Appointment.AppointmentStatus.valueOf(parts[5]);
                    } else {
                        status = Appointment.AppointmentStatus.valueOf(parts[4]);
                    }

                    Appointment appointment = new Appointment(pet, dateTime, reason, veterinarian, duration);
                    appointment.setStatus(status);
                    appointments.add(appointment);
                } catch (Exception e) {
                    System.out.println("Error loading an appointment: " + e.getMessage());
                }
            }
            System.out.println("Appointments loaded successfully. Total: " + appointments.size());
        } catch (IOException e) {
            System.out.println("Error loading appointments: " + e.getMessage());
        }
    }

    public void saveData() {
        try (BufferedWriter petWriter = new BufferedWriter(new FileWriter(PET_DETAILS_FILE))) {
            List<Pet> validPets = new ArrayList<>();

            for (Pet pet : pets) {
                if (pet != null &&
                        pet.getName() != null && !pet.getName().trim().isEmpty() &&
                        pet.age > 0 &&
                        pet.getColour() != null && !pet.getColour().trim().isEmpty() &&
                        pet.weight > 0 &&
                        pet.getBreed() != null && !pet.getBreed().trim().isEmpty() &&
                        pet.getOwner() != null) {
                    validPets.add(pet);
                }
            }

            if (validPets.isEmpty()) {
                System.out.println("No valid pets to save.");
                return;
            }

            for (Pet pet : validPets) {
                petWriter.write(
                        pet.getName() + ", " +
                                pet.age + ", " +
                                pet.getColour() + ", " +
                                pet.weight + ", " +
                                pet.getType() + ": " +
                                pet.getBreed() + ", " +
                                pet.getOwner().getName() + ", " +
                                pet.getOwner().getPhoneNumber() + ", " +
                                pet.getOwner().getAddress()
                );
                petWriter.newLine();
            }
            System.out.println("Pet details saved successfully.");
            System.out.println("Total valid pets saved: " + validPets.size());

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadData() {
        File petFile = new File(PET_DETAILS_FILE);

        pets.clear();

        int successfulLoads = 0;
        int failedLoads = 0;

        if (!petFile.exists()) {
            System.out.println("No previous data found.");
            System.out.println("Successfully loaded 0 pets.");
            System.out.println("Failed to load 0 pets.");
            return;
        }

        try (BufferedReader petReader = new BufferedReader(new FileReader(petFile))) {
            String line;

            while ((line = petReader.readLine()) != null) {
                try {
                    String[] parts = line.split(", ");
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    String colour = parts[2];
                    double weight = Double.parseDouble(parts[3]);
                    String type = parts[4].split(":")[0].trim();
                    String breed = parts[4].split(":")[1].trim();

                    String ownerName = parts[5];
                    String ownerPhone = parts[6];
                    String ownerAddress = parts[7];

                    Owner owner = new Owner(ownerName, ownerPhone, ownerAddress);

                    Pet pet;
                    if (type.equalsIgnoreCase("Cat")) {
                        pet = new Pet.Cat(name, age, colour, weight, breed, owner);
                    } else if (type.equalsIgnoreCase("Dog")) {
                        pet = new Pet.Dog(name, age, colour, weight, breed, owner);
                    } else if (type.equalsIgnoreCase("Hamster")) {
                        pet = new Pet.Hamster(name, age, colour, weight, breed, owner);
                    } else {
                        pet = new Pet.Dog(name, age, colour, weight, breed, owner);
                    }

                    pets.add(pet);
                    successfulLoads++;
                } catch (Exception e) {
                    failedLoads++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        System.out.println("Successfully loaded " + successfulLoads + " pets.");
        System.out.println("Failed to load " + failedLoads + " pets.");
        System.out.println("Total pets after loading: " + pets.size());
    }

    public void displayMenu() {
        System.out.println("\n===============================");
        System.out.println("\tHappy Paws Pet Clinic");
        System.out.println("===============================");
        System.out.println("1. Add Pet");
        System.out.println("2. Delete Pet");
        System.out.println("3. Search for Pet");
        System.out.println("4. View All Pets");
        System.out.println("5. Modify Pet");
        System.out.println("6. Generate Report");
        System.out.println("7. Add Appointment");
        System.out.println("8. Delete Appointment");
        System.out.println("9. Modify Appointment");
        System.out.println("10. View Appointments");
        System.out.println("11. Search Appointments");
        System.out.println("12. Save & Exit");
        System.out.print("Choose an option: ");
    }

    public static void main(String[] args) {
        PetClinicManagementSystem system = new PetClinicManagementSystem();
        system.loadData();
        system.loadAppointments();

        while (true) {
            system.displayMenu();

            try {
                int choice = Integer.parseInt(system.scanner.nextLine().trim());

                switch (choice) {
                    case 1: system.addPet(); break;
                    case 2: system.deletePet(); break;
                    case 3: system.searchPet(); break;
                    case 4: system.viewAllPets(); break;
                    case 5: system.modifyPet(); break;
                    case 6: system.generateReport(); break;
                    case 7: system.addAppointment(); break;
                    case 8: system.deleteAppointment(); break;
                    case 9: system.modifyAppointment(); break;
                    case 10: system.viewAppointments(); break;
                    case 11: system.searchAppointments(); break;
                    case 12:
                        system.saveData();
                        system.saveAppointments();
                        System.out.println("Exiting the system...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 12.");
            }
        }
    }
}