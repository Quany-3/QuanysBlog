package com.learnjava.quanysblog;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QuanysBlogApplicationTests {

    @Test
    void applicationClassExists() {
        QuanysBlogApplication app = new QuanysBlogApplication();
        assertNotNull(app);
    }
}
