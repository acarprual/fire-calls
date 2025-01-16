package me.lprada.fire_calls.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java",
	plugin = {"pretty", "html:target/cucumber/task.html"})
public class CucumberIntegrationTest {
}
