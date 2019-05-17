package net.truextend.s4api.service;

import net.truextend.s4api.dto.ClassDto;
import net.truextend.s4api.dto.StudentDto;
import net.truextend.s4api.repository.S4StudentRepository;
import net.truextend.s4api.repository.entity.S4ClassEntity;
import net.truextend.s4api.repository.entity.S4StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private S4StudentRepository studentRepository;

    @Transactional
    public Response saveStudent(StudentDto student) {
        Response response;
        if (student == null || student.getId() == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Student information is required").build();
        try {
            Response retrieveStudent = retrieveStudentById(Long.valueOf(student.getId()));
            if (retrieveStudent.getStatus() == Response.Status.OK.getStatusCode()) {
                response = Response.status(Response.Status.BAD_REQUEST).entity("Student already exist").build();
            } else {
                response = persistStudent(student);
            }

        } catch (Exception e) {
            response = Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        return response;
    }

    @Transactional(readOnly = true)
    public Response retrieveAll(int pageIndex, int pageSize, String search) {
        Response response;
        pageSize = pageSize == 0 ? 20 : pageSize;
        Page<S4StudentEntity> allStudents;

        if (search == null || search.isEmpty()) {
            allStudents = studentRepository.findAll(PageRequest.of(pageIndex, pageSize));
        } else {
            allStudents = studentRepository.findAllByCriteria(search, PageRequest.of(pageIndex, pageSize));
        }

        List<StudentDto> allStudentsDto = new ArrayList<>();
        allStudents.getContent().forEach(s4StudentEntity -> {
            StudentDto studentDto = new StudentDto(String.valueOf(
                    s4StudentEntity.getId()),
                    s4StudentEntity.getFirstName(),
                    s4StudentEntity.getLastName());
            addClasses(studentDto, s4StudentEntity);
            allStudentsDto.add(studentDto);
        });

        response = Response.status(Response.Status.OK).entity(allStudentsDto).build();

        return response;
    }

    @Transactional(readOnly = true)
    public Response retrieveStudentById(Long id) {
        Response response;
        try {
            Optional<S4StudentEntity> studentEntity = studentRepository.findById(id);
            if (!studentEntity.isPresent())
                throw new Exception("Student not found");

            StudentDto studentDto = new StudentDto(
                    String.valueOf(studentEntity.get().getId()),
                    studentEntity.get().getFirstName(),
                    studentEntity.get().getLastName());
            addClasses(studentDto, studentEntity.get());

            response = Response.status(Response.Status.OK).entity(studentDto).build();
        } catch (Exception e) {
            response = Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        return response;
    }

    @Transactional(readOnly = true)
    public Response retrieveClassByFirstName(String firstName) {
        List<S4StudentEntity> s4StudentEntities = studentRepository.findAllByFirstName(firstName);
        return buildResponse(s4StudentEntities);
    }

    @Transactional(readOnly = true)
    public Response retrieveClassByLastName(String lastName) {
        List<S4StudentEntity> s4StudentEntities = studentRepository.findAllByLastName(lastName);
        return buildResponse(s4StudentEntities);
    }

    @Transactional(readOnly = true)
    public Response retrieveClassByClassCode(String classCode) {
        List<S4StudentEntity> s4StudentEntities = studentRepository.findAllByClassCode(classCode);
        return buildResponse(s4StudentEntities);
    }

    @Transactional
    public Response updateStudent(Long id, StudentDto studentDto) {
        Response response;
        if (studentDto == null || studentDto.getId() == null || id == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Student information is required").build();
        try {
            Response retrieveStudent = retrieveStudentById(id);
            if (retrieveStudent.getStatus() != Response.Status.OK.getStatusCode()) {
                response = Response.status(Response.Status.BAD_REQUEST).entity("Student not found").build();
            } else {
                response = persistStudent(studentDto);
            }
        } catch (Exception e) {
            response = Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        return response;
    }

    @Transactional
    public Response deleteStudent(Long id) {
        Response response;

        if (id == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Student id is required.").build();
        try {
            Response retrieveStudent = retrieveStudentById(id);

            if (retrieveStudent.getStatus() != Response.Status.OK.getStatusCode()) {
                response = Response.status(Response.Status.BAD_REQUEST).entity("Student not found.").build();
            } else {
                studentRepository.deleteById(id);
                response = Response.status(Response.Status.OK).entity("Student information was deleted.").build();
            }

        } catch (Exception e) {
            response = Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        return response;
    }

    private Response persistStudent(StudentDto studentDto) {
        S4StudentEntity entity = new S4StudentEntity();
        entity.setId(Long.parseLong(studentDto.getId()));
        entity.setFirstName(studentDto.getFirstName());
        entity.setLastName(studentDto.getLastName());

        List<ClassDto> s4Classes = studentDto.getS4Classes();
        if (s4Classes != null) {
            List<S4ClassEntity> subjects = new ArrayList<>();
            s4Classes.forEach(classDto -> subjects.add(
                    new S4ClassEntity(
                            classDto.getCode(),
                            classDto.getTitle(),
                            classDto.getDescription())));
            entity.setS4ClassEntities(subjects);
        }

        entity = studentRepository.save(entity);

        StudentDto stored = new StudentDto(String.valueOf(entity.getId()), entity.getFirstName(), entity.getLastName());
        return Response.status(Response.Status.OK).entity(stored).build();
    }

    private void addClasses(StudentDto studentDto, S4StudentEntity studentEntity) {
        List<S4ClassEntity> s4ClassEntities = studentEntity.getS4ClassEntities();
        if (s4ClassEntities == null || s4ClassEntities.isEmpty())
            return;
        List<ClassDto> s4Classes = new ArrayList<>();
        s4ClassEntities.forEach(s4ClassEntity -> s4Classes.add(
                new ClassDto(
                        s4ClassEntity.getCode(),
                        s4ClassEntity.getTitle(),
                        s4ClassEntity.getDescription())));
        studentDto.setS4Classes(s4Classes);
    }

    private Response buildResponse(List<S4StudentEntity> allStudents) {
        Response response;
        List<StudentDto> allStudentsDto = new ArrayList<>();
        allStudents.forEach(s4StudentEntity -> {
            StudentDto studentDto = new StudentDto(String.valueOf(
                    s4StudentEntity.getId()),
                    s4StudentEntity.getFirstName(),
                    s4StudentEntity.getLastName());
            addClasses(studentDto, s4StudentEntity);
            allStudentsDto.add(studentDto);
        });

        response = Response.status(Response.Status.OK).entity(allStudentsDto).build();

        return response;
    }
}
