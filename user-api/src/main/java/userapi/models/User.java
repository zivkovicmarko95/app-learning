package userapi.models;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

    /*
        Class which represents User object
    */

    @Id
    private String id;
    private String userId;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    private Date createdDate;

    private boolean isActive;
    private boolean isNotLocked;

    public User() {
        this.createdDate = new Date();
    }

    public User(String userId, String firstName, String lastName, String username, String password, String email,
            boolean isActive, boolean isNotLocked) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
        this.createdDate = new Date();
    }

    public String getId() {
        return this.id;
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

    public Date getCreatedDate() {
        return this.createdDate;
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

    public boolean isIsNotLocked() {
        return this.isNotLocked;
    }

    public boolean getIsNotLocked() {
        return this.isNotLocked;
    }

    public void setIsNotLocked(boolean isNotLocked) {
        this.isNotLocked = isNotLocked;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", userId='" + getUserId() + "'" + ", firstName='" + getFirstName() + "'"
                + ", lastName='" + getLastName() + "'" + ", username='" + getUsername() + "'" + ", password='"
                + getPassword() + "'" + ", email='" + getEmail() + "'" + ", createdDate='" + getCreatedDate() + "'"
                + ", isActive='" + isIsActive() + "'" + ", isNotLocked='" + isIsNotLocked() + "'" + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userId, user.userId)
                && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName)
                && Objects.equals(username, user.username) && Objects.equals(password, user.password)
                && Objects.equals(email, user.email) && Objects.equals(createdDate, user.createdDate)
                && isActive == user.isActive && isNotLocked == user.isNotLocked;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, firstName, lastName, username, password, email, createdDate, isActive,
                isNotLocked);
    }

}
