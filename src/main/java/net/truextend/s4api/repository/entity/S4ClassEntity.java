package net.truextend.s4api.repository.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "s4classes")
public class S4ClassEntity {
    @Id
    private String code;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @ManyToMany
    @JoinTable(
            name = "class_student",
            joinColumns = @JoinColumn(name = "class_code"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<S4StudentEntity> students;

    public S4ClassEntity() {
    }

    public S4ClassEntity(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<S4StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<S4StudentEntity> students) {
        this.students = students;
    }
}
