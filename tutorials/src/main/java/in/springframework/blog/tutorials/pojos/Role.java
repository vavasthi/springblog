package in.springframework.blog.tutorials.pojos;

import java.util.HashSet;
import java.util.Set;

public enum Role {
    USER(0x01), // 0 User
    TESTER(0x01 << 1), // 1 Tester
    ADMIN(0x01 << 2), // 2 Admin
    TENANT_ADMIN(0x01 << 3),
    REFRESH(0x01 << 4),
    ANONYMOUS(0x01 << 5),
    NEWUSER(0x01 << 6);

    private final int mask;

    public int getMask() {
        return mask;
    }

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
