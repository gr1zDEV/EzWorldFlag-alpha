package com.ezinnovations.ezworldflags.flag;

/**
 * Represents a world flag state.
 */
public enum FlagState {
    ALLOW,
    DENY,
    DEFAULT;

    /**
     * Parses a value safely.
     *
     * @param raw raw input
     * @return state or null
     */
    public static FlagState fromString(final String raw) {
        if (raw == null) {
            return null;
        }
        try {
            return FlagState.valueOf(raw.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
