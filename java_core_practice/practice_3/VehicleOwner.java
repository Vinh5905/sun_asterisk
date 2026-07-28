// Requirement 1: Manage the information of a vehicle owner.
public class VehicleOwner {
    private String cmndNumber;
    private String fullName;
    private String email;

    public VehicleOwner(String cmndNumber, String fullName, String email) {
        setCmndNumber(cmndNumber);
        setFullName(fullName);
        setEmail(email);
    }

    public String getCmndNumber() {
        return cmndNumber;
    }

    public void setCmndNumber(String cmndNumber) {
        if (!isValidCmndNumber(cmndNumber)) {
            throw new IllegalArgumentException("CMND number must contain exactly 12 digits.");
        }
        this.cmndNumber = cmndNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name must not be blank.");
        }
        this.fullName = fullName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email address is not valid.");
        }
        this.email = email.trim();
    }

    // Validate that the CMND number contains exactly twelve digits.
    private boolean isValidCmndNumber(String cmndNumber) {
        return cmndNumber != null && cmndNumber.matches("\\d{12}");
    }

    // Validate the vehicle owner's email format.
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public void displayInfo() {
        System.out.println("CMND number: " + getCmndNumber());
        System.out.println("Full name: " + getFullName());
        System.out.println("Email: " + getEmail());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VehicleOwner)) {
            return false;
        }
        VehicleOwner other = (VehicleOwner) object;
        return cmndNumber.equals(other.cmndNumber);
    }

    @Override
    public int hashCode() {
        return cmndNumber.hashCode();
    }
}
