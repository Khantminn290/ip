package ronaldo.task;

/**
 * Represents the priority levels for tasks.
 * Each priority has a short code for compact representation.
 */
public enum Priority {
    /**
     * Low priority task. Short code: {@code "L"}.
     */
    LOW("L"),

    /**
     * Medium priority task. Short code: {@code "M"}.
     */
    MEDIUM("M"),

    /**
     * High priority task. Short code: {@code "H"}.
     */
    HIGH("H");

    /** The short code representation of the priority. */
    private final String shortCode;

    /**
     * Constructs a {@code Priority} with the given short code.
     *
     * @param shortCode the single-character code representing the priority
     */
    Priority(String shortCode) {
        this.shortCode = shortCode;
    }

    /**
     * Returns the short code for this priority.
     *
     * @return the short code string (e.g., {@code "L"}, {@code "M"}, {@code "H"})
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     * Parses a string into a {@code Priority}.
     * <p>
     * The input can be either the full name (e.g., {@code "LOW"}, {@code "MEDIUM"}, {@code "HIGH"})
     * or the short code (e.g., {@code "L"}, {@code "M"}, {@code "H"}). The parsing is case-insensitive
     * and trims leading/trailing spaces.
     *
     * @param input the string to parse
     * @return the matching {@code Priority}
     * @throws IllegalArgumentException if the input is {@code null} or does not match any priority
     */
    public static Priority fromString(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        String normalized = input.trim().toUpperCase();
        for (Priority p : values()) {
            if (p.name().equals(normalized) || p.shortCode.equals(normalized)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Invalid priority: " + input);
    }

    /**
     * Returns a human-friendly string representation of the priority.
     * <p>
     * For example, {@code LOW} becomes {@code "Low"} instead of {@code "LOW"}.
     *
     * @return the capitalized name of the priority
     */
    @Override
    public String toString() {
        String lower = name().toLowerCase();
        return lower.substring(0, 1).toUpperCase() + lower.substring(1);
    }
}
