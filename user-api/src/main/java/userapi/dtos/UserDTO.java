package userapi.dtos;

import java.util.Objects;

public class UserDTO {

    /*
        This class is used as User Data Transfer Object
    */

    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean isActive;

    public UserDTO() {
    }

    public UserDTO(String id, String userId, String firstName, String lastName, String username, String email, boolean isActive) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.isActive = isActive;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIsActive() {
        return this.isActive;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public UserDTO id(String id) {
        setId(id);
        return this;
    }

    public UserDTO userId(String userId) {
        setUserId(userId);
        return this;
    }

    public UserDTO firstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public UserDTO lastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public UserDTO username(String username) {
        setUsername(username);
        return this;
    }

    public UserDTO email(String email) {
        setEmail(email);
        return this;
    }

    public UserDTO isActive(boolean isActive) {
        setIsActive(isActive);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserDTO)) {
            return false;
        }
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(userId, userDTO.userId) && Objects.equals(firstName, userDTO.firstName) && Objects.equals(lastName, userDTO.lastName) && Objects.equals(username, userDTO.username) && Objects.equals(email, userDTO.email) && isActive == userDTO.isActive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, firstName, lastName, username, email, isActive);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", username='" + getUsername() + "'" +
            ", email='" + getEmail() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
    
}
