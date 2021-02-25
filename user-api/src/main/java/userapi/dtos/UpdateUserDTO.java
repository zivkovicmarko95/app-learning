package userapi.dtos;

import java.util.Objects;

public class UpdateUserDTO {

    // used only for updating user object
    
    private String currentUsername;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String isActive;
    private String isNotLocked;

    public UpdateUserDTO() {
    }

    public UpdateUserDTO(String currentUsername, String firstName, String lastName, String username, String password, String email, String isActive, String isNotLocked) {
        this.currentUsername = currentUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }

    public String getCurrentUsername() {
        return this.currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsActive() {
        return this.isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsNotLocked() {
        return this.isNotLocked;
    }

    public void setIsNotLocked(String isNotLocked) {
        this.isNotLocked = isNotLocked;
    }

    public UpdateUserDTO currentUsername(String currentUsername) {
        setCurrentUsername(currentUsername);
        return this;
    }

    public UpdateUserDTO firstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public UpdateUserDTO lastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public UpdateUserDTO username(String username) {
        setUsername(username);
        return this;
    }

    public UpdateUserDTO password(String password) {
        setPassword(password);
        return this;
    }

    public UpdateUserDTO email(String email) {
        setEmail(email);
        return this;
    }

    public UpdateUserDTO isActive(String isActive) {
        setIsActive(isActive);
        return this;
    }

    public UpdateUserDTO isNotLocked(String isNotLocked) {
        setIsNotLocked(isNotLocked);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UpdateUserDTO)) {
            return false;
        }
        UpdateUserDTO updateUserDTO = (UpdateUserDTO) o;
        return Objects.equals(currentUsername, updateUserDTO.currentUsername) && Objects.equals(firstName, updateUserDTO.firstName) && Objects.equals(lastName, updateUserDTO.lastName) && Objects.equals(username, updateUserDTO.username) && Objects.equals(password, updateUserDTO.password) && Objects.equals(email, updateUserDTO.email) && Objects.equals(isActive, updateUserDTO.isActive) && Objects.equals(isNotLocked, updateUserDTO.isNotLocked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentUsername, firstName, lastName, username, password, email, isActive, isNotLocked);
    }

    @Override
    public String toString() {
        return "{" +
            " currentUsername='" + getCurrentUsername() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isNotLocked='" + getIsNotLocked() + "'" +
            "}";
    }

}
