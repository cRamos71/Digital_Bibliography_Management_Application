package edu.ufp.inf.person_user;

import edu.ufp.inf.Util.Date;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Represents a Person with basic information such as name, birthdate, and address.
 */
public class Person implements Serializable {
    /**
     * Person ID
     */
    private Integer idNumber;
    /**
     * birthdate
     */
    private Date birthDate;
    /**
     * Name
     */
    private String name;
    /**
     * address
     */
    private String address;

    public Person() {
    }

    public Person(Integer idNumber, Date birthDate, String name, String address) {
        this.idNumber = idNumber;
        this.birthDate = birthDate;
        this.name = name;
        this.address = address;
    }

    public Person(Date birthDate, String name, String address) {
        this.birthDate = birthDate;
        this.name = name;
        this.address = address;
    }


    public Integer getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Integer idNumber) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(idNumber, person.idNumber) && Objects.equals(birthDate, person.birthDate) && Objects.equals(name, person.name) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNumber, birthDate, name, address);
    }

    @Override
    public String toString() {
        return "Person{" +
                "idNumber=" + idNumber +
                ", birthDate=" + birthDate +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }


    /**
     * Returns the age of a Person.
     * @ No params
     */
    public int age(){
        LocalDate now = LocalDate.now();
        LocalDate bdate = LocalDate.of(birthDate.year(),birthDate.month(),birthDate.day());
        return Period.between(bdate, now).getYears();
    }

    public static void main(String[] args) {
        Date bdate = new Date(11,12,2003);
        System.out.println(bdate.year());

        Person p = new Person(10, bdate, "ola", "4500-368");
        System.out.println(p.age());

    }

}
