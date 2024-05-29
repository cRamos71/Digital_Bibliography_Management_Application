package edu.ufp.inf.person_user;

import java.util.Objects;
import edu.ufp.inf.Util.Date;

/**
 * Represents a user entity with extended attributes such as username, password, email, etc.
 */
public class User extends Person{
    /**
    * username
     */
    private String username;
    /**
     * password
     */
    private String password;
    /**
     * email
     */
    private String email;
    /**
     * zip
     */
    private String zip;
    /**
     * phoneNumber
     */
    private Integer phoneNumber;
    /**
     * countryCode ex: US
     */
    private String countryCode;
    public User() {
    }

    public User(Integer idNumber, Date birthDate, String name, String address, String username, String password, String email, String zip, Integer phoneNumber, String countryCode) {
        super(idNumber, birthDate, name, address);
        this.username = username;
        this.password = password;
        this.email = email;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(zip, user.zip) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(countryCode, user.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, email, zip, phoneNumber, countryCode);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", zip='" + zip + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

}
