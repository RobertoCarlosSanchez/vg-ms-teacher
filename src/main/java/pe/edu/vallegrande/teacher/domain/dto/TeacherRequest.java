package pe.edu.vallegrande.teacher.domain.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequest {

    private String firstName;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private LocalDate dateBirth;
    private String email;
    private String cellPhone;
    private String gender;
    private LocalDate dateHire;

}
