package me.lprada.fire_calls.e2e.step_definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import me.lprada.fire_calls.payload.FireCallsRequest;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import static me.lprada.fire_calls.e2e.step_definitions.FireCallHttpClient.latestFireCalls;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CucumberStepDefinitions {

	@Autowired
	protected FireCallHttpClient fireCallHttpClient;

	protected JSONParser parser = new JSONParser();

	@When("^I send a POST request to \"/api/fire-calls\" with type (.+) and x (.+) and y (.+) and r (.+)")
	public void I_send_a_POST_request_to_fire_calls(String type, Double x, Double y, Double r) {
		FireCallsRequest fireCallsRequest = FireCallsRequest
			.builder()
			.type(type)
			.x(x)
			.y(y)
			.r(r)
			.build();
		fireCallHttpClient.getFireCalls(fireCallsRequest);
	}

	@Then("^the response status code should be (\\d+)$")
	public void the_response_status_code_should_be(int statusCode) {
		assertThat("status code is incorrect ", FireCallHttpClient.latestCode, is(statusCode));
	}

	@And("^the response should be:")
	public void the_response_should_be(String response) throws ParseException {
		assertThat(new JSONObject(latestFireCalls), is(parser.parse(response)));
	}
}
