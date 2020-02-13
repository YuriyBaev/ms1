package student.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "college_students")
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class Student
{
    @Id
    private String id;
    private String name;
    private long studentNumber;
    private String email;
    private float gpa;
}
