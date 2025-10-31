package io.github.bhuyanp.restapp.common;

import io.cucumber.java.en.And;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RequiredArgsConstructor
public class CommonStepDefinitions {
    private final CommonContext context;
    @And("error message is {string}")
    public void errorMessageIs(String expectedErrorMessage) {
        assertThat(context.getErrorMessage()).isEqualTo(expectedErrorMessage);
    }

    @And("error message contains {string}")
    public void errorMessageContains(String expectedErrorMessage) {
        assertThat(context.getErrorMessage()).contains(expectedErrorMessage);
    }
}
