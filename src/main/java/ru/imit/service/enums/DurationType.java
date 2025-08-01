package ru.imit.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс хранит список длительностей
 * **/
@Getter
@AllArgsConstructor
public enum DurationType {
    WHOLE(1.0),
    HALF(1.0/2),
    QUARTER(1.0/4),
    EIGHTH(1.0/8),
    SIXTEENTH(1.0/16),
    THIRTY_SECOND(1.0/32),
    SIXTY_FOUR(1.0/64);
    private final double value;
}
