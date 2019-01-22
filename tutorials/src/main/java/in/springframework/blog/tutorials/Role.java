package in.springframework.blog.tutorials;

import java.util.HashSet;
import java.util.Set;

public enum Role {
    USER(0x01), // 0 User
    TESTER(0x01 << 1), // 1 Tester
    ADMIN(0x01 << 2), // 2 Admin
    REFRESH(0x01 << 3),
    ANONYMOUS(0x01 << 4),
    NEWUSER(0x01 << 5);

    private final int mask;

    Role(int mask) {
        this.mask = mask;
    }

    public static Set<Role> createFromMask(long mask) {
        Set<Role> output = new HashSet<>();
        for (Role r : values()) {
            if ((r.mask & mask) != 0) {
                output.add(r);
            }
        }
        return output;
    }
}
