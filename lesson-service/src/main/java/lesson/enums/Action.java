package lesson.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Action
{
    CREATED("created"),
    UPDATED("updated"),
    DELETED("deleted");

    private final String value;
}
