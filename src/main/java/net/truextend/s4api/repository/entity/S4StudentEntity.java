package net.truextend.s4api.repository.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="s4students")
public class S4StudentEntity {

    @Id
    private long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "class_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "class_code"))
    private List<S4ClassEntity> s4ClassEntities;

    public S4StudentEntity() {
    }

    public S4StudentEntity(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<S4ClassEntity> getS4ClassEntities() {
        return s4ClassEntities;
    }

    public void setS4ClassEntities(List<S4ClassEntity> subjects) {
        this.s4ClassEntities = subjects;
    }
}
