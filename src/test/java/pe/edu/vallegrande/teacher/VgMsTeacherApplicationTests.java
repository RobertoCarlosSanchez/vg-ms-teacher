package pe.edu.vallegrande.teacher;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pe.edu.vallegrande.teacher.application.service.impl.TeacherServiceImpl;
import pe.edu.vallegrande.teacher.domain.dto.TeacherRequest;
import pe.edu.vallegrande.teacher.domain.dto.TeacherResponse;
import pe.edu.vallegrande.teacher.domain.entity.Teacher;
import pe.edu.vallegrande.teacher.domain.mapper.TeacherMapper;
import pe.edu.vallegrande.teacher.domain.repository.TeacherRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class VgMsTeacherApplicationTests {

	@Test
	void contextLoads() {
	}

}