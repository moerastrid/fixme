package fixme.core;

import java.util.Arrays;

public enum Instrument {
    GUITAR,
    PIANO,
    SYNTHESIZER,
    TUBA,
    SAXOPHONE,
    TRIANGLE,
    DRUM,
    BASS,
    FLUTE,
    VIOLIN,
    UKULELE;

    public static boolean exists(String instrument) {
        return Arrays.stream(values()).anyMatch(predicate -> predicate.name().equals(instrument));
    }
}
