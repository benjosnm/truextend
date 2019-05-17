package net.truextend.s4api.repository;

import net.truextend.s4api.repository.entity.S4ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface S4ClassRepository extends PagingAndSortingRepository<S4ClassEntity, String> {
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

    @Query(value = "SELECT s4classes.code, s4classes.title, s4classes.description " +
            "FROM s4classes " +
            "WHERE title = :queryCriteria OR " +
            "description = :queryCriteria",
            countQuery = "SELECT count(*) " +
                    "FROM s4classes " +
                    "WHERE title = :queryCriteria OR " +
                    "description = :queryCriteria",
            nativeQuery = true)
    Page<S4ClassEntity> findAllByCriteria(String queryCriteria, Pageable pageable);
}
