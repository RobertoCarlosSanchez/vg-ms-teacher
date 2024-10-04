package pe.edu.vallegrande.teacher.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.edu.vallegrande.teacher.domain.entity.Teacher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TeacherRepository extends ReactiveMongoRepository<Teacher, String> {

    Flux<Teacher> findAllByOrderByCreationDateDesc();
    Flux<Teacher> findByStatusOrderByCreationDateDesc(String status);
    Mono<Teacher> findByDocumentNumber(String documentNumber);


}
