package net.truextend.s4api.repository;

import net.truextend.s4api.repository.entity.S4ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface S4ClassRepository extends JpaRepository<S4ClassEntity, String> {
    @Query(value = "SELECT * FROM s4classes WHERE title = :title",
            nativeQuery = true)
    List<S4ClassEntity> findAllByTitle(String title);

    @Query(value = "SELECT * FROM s4classes WHERE description = :description",
            nativeQuery = true)
    List<S4ClassEntity> findAllByDescription(String description);

    @Query(value = "SELECT s4classes.code, s4classes.title, s4classes.description " +
            "FROM s4classes , class_student as cs " +
            "WHERE cs.class_code = s4classes.code  and cs.student_id=:studentId",
            nativeQuery = true)
    List<S4ClassEntity> findAllByStudentId(long studentId);
}
