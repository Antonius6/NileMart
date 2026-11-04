package org.route;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Zone {
    GREATER_CAIRO(50, true),
    ALEX_DELTA(65, true),
    CANAL(70, true),
    UPPER_EGYPT(85, true),
    REMOTE(120, false);

    private final int fee;
    private final boolean freeShipping;

}
