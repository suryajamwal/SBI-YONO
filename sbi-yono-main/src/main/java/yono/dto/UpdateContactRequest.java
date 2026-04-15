package yono.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateContactRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    public UpdateContactRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}