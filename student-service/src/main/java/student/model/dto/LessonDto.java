package student.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto
{
    @JsonProperty("id")
    private String id;
    @JsonProperty("studentNumber")
    private long studentNumber;
    @JsonProperty("courseList")
    private List<String> courseList;

}
