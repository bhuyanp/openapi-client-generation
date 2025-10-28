package io.github.bhuyanp.restapp;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Prasanta Bhuyan
 */


@Tag("smokeTest")
@ActiveProfiles({"prod", "smoketest"})
public class PRODSmokeTest extends SmokeTests {
    @Test
    void loadContext(){
    }
}
