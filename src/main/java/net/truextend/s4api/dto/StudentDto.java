package net.truextend.s4api.dto;

import java.util.List;

public class StudentDto {
    private String id;
    private String firstName;
    private String lastName;
    private List<ClassDto> s4Classes;

    public StudentDto(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ClassDto> getS4Classes() {
        return s4Classes;
    }

    public void setS4Classes(List<ClassDto> s4Classes) {
        this.s4Classes = s4Classes;
    }
}
