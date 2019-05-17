package net.truextend.s4api.services;

import net.truextend.s4api.dto.ClassDto;
import net.truextend.s4api.repository.S4ClassRepository;
import net.truextend.s4api.repository.entity.S4ClassEntity;
import net.truextend.s4api.service.S4ClassService;
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

public class S4ClassServiceTests {

    @Mock
    S4ClassRepository s4ClassRepository;

    @InjectMocks
    S4ClassService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save_class_verify_if_class_is_save_test() {
        ClassDto classDto = new ClassDto("c1", "c1 title", "c1 description");

        S4ClassEntity s4ClassEntity = new S4ClassEntity();
        s4ClassEntity.setCode("c1");
        s4ClassEntity.setTitle("c1 title");
        s4ClassEntity.setDescription("c1 description");

        when(s4ClassRepository.findById("c1")).thenReturn(null);
        when(s4ClassRepository.save(isA(S4ClassEntity.class))).thenReturn(s4ClassEntity);
        Response result = service.saveClass(classDto);

        ClassDto responseDto = (ClassDto) result.getEntity();

        assertNotNull(result);
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(classDto.getCode(), responseDto.getCode());
    }

    @Test
    public void save_class_validate_duplicated_class_on_save_test() {
        ClassDto classDto = new ClassDto("c1", "c1 title", "c1 description");

        Optional<S4ClassEntity> resultEntity = Optional.of(new S4ClassEntity());

        when(s4ClassRepository.findById("c1")).thenReturn(resultEntity);
        Response result = service.saveClass(classDto);

        assertNotNull(result);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), result.getStatus());
    }

    @Test
    public void retrieve_all_verify_all_classes_are_retrieved_test() {
        S4ClassEntity s4ClassEntity = new S4ClassEntity();
        s4ClassEntity.setCode("c1");
        s4ClassEntity.setTitle("c1 title");
        s4ClassEntity.setDescription("c1 description");

        S4ClassEntity s4ClassEntity2 = new S4ClassEntity();
        s4ClassEntity.setCode("c2");
        s4ClassEntity.setTitle("c2 title");
        s4ClassEntity.setDescription("c2 description");

        List<S4ClassEntity> s4ClassEntities = new ArrayList<>();
        s4ClassEntities.add(s4ClassEntity);
        s4ClassEntities.add(s4ClassEntity2);

        when(s4ClassRepository.findAll()).thenReturn(s4ClassEntities);
        Response result = service.retrieveAll(0, 20, null);

        List<?> classDtoResultList = (List<?>) result.getEntity();

        assertNotNull(result);
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(2, classDtoResultList.size());
    }

    @Test
    public void retrieve_class_by_code_verify_proper_class_is_returned_test() {
        S4ClassEntity s4ClassEntity = new S4ClassEntity();
        s4ClassEntity.setCode("c1");
        s4ClassEntity.setTitle("c1 title");
        s4ClassEntity.setDescription("c1 description");

        Optional<S4ClassEntity> resultEntity = Optional.of(s4ClassEntity);

        when(s4ClassRepository.findById("c1")).thenReturn(resultEntity);
        Response result = service.retrieveClassByCode("c1");

        ClassDto resultDto = (ClassDto) result.getEntity();

        assertNotNull(result);
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals("c1", resultDto.getCode());
    }

    @Test
    public void retrieve_class_by_code_verify_verify_no_result_in_response_test() {
        when(s4ClassRepository.findById("c1")).thenReturn(null);
        Response result = service.retrieveClassByCode("c1");

        assertNotNull(result);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getStatus());
    }

    @Test
    public void update_class_verify_if_class_is_updated_test() {
        ClassDto classDto = new ClassDto("c1", "c1 title updated", "c1 description updated");

        S4ClassEntity s4ClassEntity = new S4ClassEntity();
        s4ClassEntity.setCode("c1");
        s4ClassEntity.setTitle("c1 title updated");
        s4ClassEntity.setDescription("c1 description updated");

        Optional<S4ClassEntity> resultEntity = Optional.of(s4ClassEntity);

        when(s4ClassRepository.findById("c1")).thenReturn(resultEntity);
        when(s4ClassRepository.save(isA(S4ClassEntity.class))).thenReturn(s4ClassEntity);
        Response result = service.updateClass("c1", classDto);

        ClassDto responseDto = (ClassDto) result.getEntity();

        assertNotNull(result);
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(classDto.getTitle(), responseDto.getTitle());
        assertTrue(responseDto.getDescription().contains("updated"));
    }

    @Test
    public void update_class_try_to_update_not_found_class_test() {
        ClassDto classDto = new ClassDto("c1", "c1 title updated", "c1 description updated");

        when(s4ClassRepository.findById("c1")).thenReturn(null);
        Response result = service.updateClass("c1", classDto);

        assertNotNull(result);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getStatus());
    }

    @Test
    public void delete_class_verify_class_was_deleted_test() {
        S4ClassEntity s4ClassEntity = new S4ClassEntity();
        s4ClassEntity.setCode("c1");
        s4ClassEntity.setTitle("c1 title updated");
        s4ClassEntity.setDescription("c1 description updated");

        Optional<S4ClassEntity> resultEntity = Optional.of(s4ClassEntity);

        when(s4ClassRepository.findById("c1")).thenReturn(resultEntity);
        Response result = service.deleteS4Class("c1");

        String responseDto = (String) result.getEntity();

        assertNotNull(result);
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertTrue(responseDto.contains("was deleted"));
    }
}
