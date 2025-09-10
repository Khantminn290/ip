package ronaldo.task;

public enum Priority {
    LOW("L"),
    MEDIUM("M"),
    HIGH("H");

    private final String shortCode;

    Priority(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }

    /** Parse from full word (LOW/MEDIUM/HIGH) or shortcut (L/M/H) */
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

    @Override
    public String toString() {
        // Capitalize nicely instead of ALL CAPS
        String lower = name().toLowerCase();
        return lower.substring(0, 1).toUpperCase() + lower.substring(1);
    }
}
