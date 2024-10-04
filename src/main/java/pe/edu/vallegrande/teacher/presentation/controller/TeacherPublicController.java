package pe.edu.vallegrande.teacher.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.teacher.application.service.ITeacherService;
import pe.edu.vallegrande.teacher.domain.dto.TeacherRequest;
import pe.edu.vallegrande.teacher.domain.dto.TeacherResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class TeacherPublicController {

    private final ITeacherService teacherService;

    @GetMapping("/teachers")
    public Flux<TeacherResponse> getAll() {
        return teacherService.getAll();
    }

    @GetMapping("/teachers/active")
    public Flux<TeacherResponse> getActive() {
        return teacherService.getActive();
    }

    @GetMapping("/teachers/inactive")
    public Flux<TeacherResponse> getInactive() {
        return teacherService.getInactive();
    }

    @GetMapping("/teacher/{id}")
    public Mono<TeacherResponse> getById(@PathVariable String id) {
        return teacherService.getById(id);
    }

    @GetMapping("/teacher/document/{documentNumber}")
    public Mono<TeacherResponse> getByDocumentNumber(@PathVariable String documentNumber) {
        return teacherService.getByDocumentNumber(documentNumber);
    }

    @PostMapping("/teacher/")
    public Mono<TeacherResponse> create(@RequestBody TeacherRequest teacherRequest) {
        return teacherService.create(teacherRequest);
    }

    @PutMapping("/teacher/{id}")
    public Mono<TeacherResponse> update(@PathVariable String id, @RequestBody TeacherRequest teacherRequest) {
        return teacherService.update(id, teacherRequest);
    }

    @PatchMapping("/teacher/password/{id}")
    public Mono<Void> modifyPassword(@PathVariable String id, @RequestBody String password) {
        return teacherService.modifyPassword(id, password);
    }

    @DeleteMapping("/teacher/{id}")
    public Mono<Void> modifyStatus(@PathVariable String id, @RequestBody String status) {
        return teacherService.modifyStatus(id, status);
    }

}
