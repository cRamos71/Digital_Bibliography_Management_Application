package edu.ufp.inf.person_user;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Person {
    private Long idNumber;

    private LocalDate birthDate;

    private String name;

    private String address;

    public Person() {
    }

    public Person(Long idNumber, LocalDate birthDate, String name, String address) {
        this.idNumber = idNumber;
        this.birthDate = birthDate;
        this.name = name;
        this.address = address;
    }


    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
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
        return idNumber.equals(person.idNumber) && Objects.equals(birthDate, person.birthDate) && Objects.equals(name, person.name) && Objects.equals(address, person.address);
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
        return Period.between(birthDate, now).getYears();
    }

    public static void main(String[] args) {
        LocalDate bdate = LocalDate.of(2000, 10, 10);
        System.out.println(bdate.getYear());

        Person p = new Person(10L, bdate, "ola", "4500-368");
        System.out.println(p.age());

    }

}
