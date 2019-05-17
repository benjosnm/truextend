package net.truextend.s4api.services;

import net.truextend.s4api.dto.StudentDto;
import net.truextend.s4api.repository.S4StudentRepository;
import net.truextend.s4api.repository.entity.S4StudentEntity;
import net.truextend.s4api.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

public class StudentServiceTests {
    @Mock
    S4StudentRepository s4StudentRepository;

    @InjectMocks
    StudentService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save_student_verify_if_student_is_saved_test() throws Exception {
        StudentDto studentDto = new StudentDto("1", "Juan", "Perez");

        S4StudentEntity s4StudentEntity = new S4StudentEntity();
        s4StudentEntity.setId(1L);
        s4StudentEntity.setFirstName("Juan");
        s4StudentEntity.setLastName("Perez");

        when(s4StudentRepository.findById(1L)).thenReturn(null);
        when(s4StudentRepository.save(isA(S4StudentEntity.class))).thenReturn(s4StudentEntity);
        Response result = service.saveStudent(studentDto);

        StudentDto responseDto = (StudentDto) result.getEntity();

        assertNotNull(result);
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(studentDto.getId(), responseDto.getId());
    }

    @Test
    public void retrieve_all_verify_all_students_are_retrieved_test() throws Exception {
        List<StudentDto> studentDtos = new ArrayList<>();
        studentDtos.add(new StudentDto("1", "Juan", "Perez"));
        studentDtos.add(new StudentDto("2", "Jose", "Perez"));

        S4StudentEntity s4StudentEntity = new S4StudentEntity();
        s4StudentEntity.setId(1L);
        s4StudentEntity.setFirstName("Juan");
        s4StudentEntity.setLastName("Perez");

        S4StudentEntity s4StudentEntity2 = new S4StudentEntity();
        s4StudentEntity.setId(2L);
        s4StudentEntity.setFirstName("Jose");
        s4StudentEntity.setLastName("Perez");

        List<S4StudentEntity> s4StudentEntities = new ArrayList<>();
        s4StudentEntities.add(s4StudentEntity);
        s4StudentEntities.add(s4StudentEntity2);

        when(s4StudentRepository.findAll()).thenReturn(s4StudentEntities);
        Response result = service.retrieveAll(0, 20, null);

        List<StudentDto> studentDtosResult = (List<StudentDto>) result.getEntity();

        assertNotNull(result);
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(2, studentDtosResult.size());
    }

    @Test
    public void retrieve_student_by_code_verify_proper_student_is_returned_test() throws Exception {
        StudentDto studentDto = new StudentDto("1", "Juan", "Perez");

        S4StudentEntity s4StudentEntity = new S4StudentEntity();
        s4StudentEntity.setId(1L);
        s4StudentEntity.setFirstName("Juan");
        s4StudentEntity.setLastName("Perez");

        Optional<S4StudentEntity> resultEntity = Optional.of(s4StudentEntity);

        when(s4StudentRepository.findById(1L)).thenReturn(resultEntity);
        Response result = service.retrieveStudentById(1L);

        StudentDto resultDto = (StudentDto) result.getEntity();

        assertNotNull(result);
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals("1", resultDto.getId());
    }

    @Test
    public void update_student_verify_if_student_is_updated_test() throws Exception {
        StudentDto studentDto = new StudentDto("1", "Juan Up", "Perez Up");

        S4StudentEntity s4StudentEntity = new S4StudentEntity();
        s4StudentEntity.setId(1L);
        s4StudentEntity.setFirstName("Juan Up");
        s4StudentEntity.setLastName("Perez Up");

        Optional<S4StudentEntity> resultEntity = Optional.of(s4StudentEntity);

        when(s4StudentRepository.findById(1L)).thenReturn(resultEntity);
        when(s4StudentRepository.save(isA(S4StudentEntity.class))).thenReturn(s4StudentEntity);
        Response result = service.updateStudent(1L, studentDto);

        StudentDto responseDto = (StudentDto) result.getEntity();

        assertNotNull(result);
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(studentDto.getFirstName(), responseDto.getFirstName());
        assertTrue(responseDto.getLastName().contains("Up"));
    }

    @Test
    public void delete_class_verify_clas_was_deleted_test() throws Exception {
        S4StudentEntity s4StudentEntity = new S4StudentEntity();
        s4StudentEntity.setId(1L);
        s4StudentEntity.setFirstName("Juan Up");
        s4StudentEntity.setLastName("Perez Up");

        Optional<S4StudentEntity> resultEntity = Optional.of(s4StudentEntity);

        when(s4StudentRepository.findById(1L)).thenReturn(resultEntity);
        Response result = service.deleteStudent(1L);

        String responseDto = (String) result.getEntity();

        assertNotNull(result);
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertTrue(responseDto.contains("was deleted"));
    }
}
