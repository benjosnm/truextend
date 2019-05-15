package net.truextend.s4api.repository;

import net.truextend.s4api.repository.entity.S4StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface S4StudentRepository extends JpaRepository<S4StudentEntity, Long> {
    @Query(value = "SELECT * FROM s4students WHERE first_name = :firstName",
            nativeQuery = true)
    List<S4StudentEntity> findAllByFirstName(String firstName);

    @Query(value = "SELECT * FROM s4students WHERE last_name = :lastName",
            nativeQuery = true)
    List<S4StudentEntity> findAllByLastName(String lastName);

    @Query(value = "SELECT student.id, student.first_name, student.last_name " +
            "FROM s4students as student, class_student as cs " +
            "WHERE cs.class_code = :classCode and student.id=cs.student_id",
            nativeQuery = true)
    List<S4StudentEntity> findAllByClassCode(String classCode);
}
