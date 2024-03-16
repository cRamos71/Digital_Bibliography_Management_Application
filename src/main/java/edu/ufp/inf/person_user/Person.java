package edu.ufp.inf.person_user;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class Person {

    private int idNumber;

    private Date birthDate;

    private String name;

    private String adress;

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Person(int idNumber, Date birthDate, String name, String adress) {
        this.idNumber = idNumber;
        this.birthDate = birthDate;
        this.name = name;
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return idNumber == person.idNumber && Objects.equals(birthDate, person.birthDate) && Objects.equals(name, person.name) && Objects.equals(adress, person.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber, birthDate, name, adress);
    }

    @Override
    public String toString() {
        return "Person{" +
                "idNumber=" + idNumber +
                ", birthDate=" + birthDate +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                '}';
    }

    public Person() {
    }

    /**
     * Returns the age of a Person.
     * @ No params
     */
    public int age(){
        // Gets the current date
        LocalDate now = LocalDate.now();
        // Creates a date using a instant and a time zone, LocalDate is better to calculate
        LocalDate birth = LocalDate.ofInstant(birthDate.toInstant(), ZoneId.of("Europe/Lisbon"));

        return Period.between(birth, now).getYears();
    }

    public static void main(String[] args) {

    }

}
