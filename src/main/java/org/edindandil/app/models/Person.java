package org.edindandil.app.models;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Person {

    private int userId;

    @NotNull(message = "Имя не может быть пустым")
    @NotBlank(message = "Имя не может быть пустым")
    private String personName;

    @NotNull(message = "Возраст не может быть пустым")
    @Min(value = 0, message = "Год не может быть отрицательным")
    private String dateOfBirth;

    public Person(int userId, String fullName, String dateOfBirth) {
        this.userId = userId;
        this.personName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public Person() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isPresent() {
        return true;
    }
}
