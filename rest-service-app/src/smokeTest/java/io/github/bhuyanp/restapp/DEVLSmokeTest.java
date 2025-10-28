package io.github.bhuyanp.restapp;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Prasanta Bhuyan
 */


@Tag("smokeTest")
@ActiveProfiles({"devl", "smoketest"})
public class DEVLSmokeTest extends SmokeTests {
    @Test
    void loadContext() {
    }
}
