import java.util.Objects;

public class Owner {
    private String name;
    private String phoneNumber;
    private String address;

    public Owner(String name, String phoneNumber, String address) {
        setName(name);
        setPhoneNumber(phoneNumber);
        setAddress(address);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        phoneNumber = phoneNumber.trim();

        if (phoneNumber.contains("+") && !phoneNumber.startsWith("+")) {
            throw new IllegalArgumentException("Invalid phone number: '+' can only be at the start");
        }

        String cleanedNumber = phoneNumber.startsWith("+")
                ? "+" + phoneNumber.substring(1).replaceAll("[^0-9]", "")
                : phoneNumber.replaceAll("[^0-9]", "");

        if ((cleanedNumber.startsWith("+") && (cleanedNumber.length() < 12 || cleanedNumber.length() > 13)) ||
                (cleanedNumber.startsWith("07") && cleanedNumber.length() != 11) ||
                (!cleanedNumber.startsWith("+") && !cleanedNumber.startsWith("07") && cleanedNumber.length() != 10)) {
            throw new IllegalArgumentException("Invalid phone number format. Must be 10 digits, start with '07' (11 digits), or start with '+' (12-13 digits)");
        }

        this.phoneNumber = cleanedNumber;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.address = address.trim();
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getFormattedPhoneNumber() {
        if (phoneNumber.startsWith("+")) {
            return "(" + phoneNumber.substring(0, 3) + ") " + phoneNumber.substring(3);
        } else {
            return phoneNumber;
        }
    }

    @Override
    public String toString() {
        return name + " (Phone: " + getFormattedPhoneNumber() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(name, owner.name) &&
                Objects.equals(phoneNumber, owner.phoneNumber) &&
                Objects.equals(address, owner.address);
    }
}
