package io.github.bhuyanp.restapp.common;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hooks {

    //Global Build Up
    @BeforeAll
    public static void beforeAllScenarios(){
        log.info("Before All Scenarios");
    }
    //Global Tear Down
    @AfterAll
    public static void afterAllScenarios(){
        log.info("After All Scenarios");
    }
}
