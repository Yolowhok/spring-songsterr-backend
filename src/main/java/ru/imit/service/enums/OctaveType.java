package ru.imit.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OctaveType {
    SUB_CONTRA(0),
    CONTRA(1),
    GREAT(2),
    SMALL(3),
    ONE_LINE(4),
    TWO_LINE(5),
    THREE_LINE(6),
    FOUR_LINE(7),
    FIVE_LINE(8);


    private final int value;
}