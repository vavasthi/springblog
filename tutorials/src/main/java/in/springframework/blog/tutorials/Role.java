package in.springframework.blog.tutorials;

public enum Role {
    USER("user"), // 0 User
    TESTER("tester"), // 1 Tester
    ADMIN("admin"), // 2 Admin
    SUPERADMIN("superadmin"), //3 Super Admin
    DEVICE("device"), // 4
    REFRESH("refresh"); // 5 Only for internal use for refresh token.

    private final String value;
    private final String role;

    Role(String value) {
        this.value = value;
        this.role = "\"hasRole('" + value + "')\")";
    }

    public static Role createFromString(String value) {
        for (Role r : Role.values()) {
            if (r.value.equalsIgnoreCase(value)) {
                return r;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid value");
    }

    public final String getValue() {
        return value;
    }

    public final String getRole() {
        return role;
    }

    public final String getRoleAnnotation() {
        return "hasRole('" + value + "')";
    }
}
