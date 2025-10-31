package io.github.bhuyanp.restapp;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines({"cucumber"})
@SelectClasspathResource("features")
@ConfigurationParameter(key =GLUE_PROPERTY_NAME, value = "io.github.bhuyanp.restapp")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@EnableCaching
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = ProductServiceApplication.class)
public class RunCucumberTests {


}
