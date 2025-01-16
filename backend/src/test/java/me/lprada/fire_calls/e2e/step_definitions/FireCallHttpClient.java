package me.lprada.fire_calls.e2e.step_definitions;

import me.lprada.fire_calls.payload.FireCallsRequest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class FireCallHttpClient {
	private final String SERVER_URL = "http://localhost";
	private final String API_LOCATION = "/api/fire_calls";
	@LocalServerPort
	private int port;
	private final RestTemplate restTemplate = new RestTemplate();
	static Integer latestCode = null;
	static Map latestFireCalls = null;

	public void getFireCalls(FireCallsRequest fireCallsRequest) {
		ResponseEntity<Map> response = restTemplate.exchange(endpoint(),
			HttpMethod.POST, getRequest(fireCallsRequest), Map.class);
		latestCode = response.getStatusCode().value();
		latestFireCalls = response.getBody();
	}

	private String endpoint() {
		return SERVER_URL + ":" + port + API_LOCATION;
	}

	private HttpEntity<FireCallsRequest> getRequest(FireCallsRequest fireCallsRequest) {
		return new HttpEntity<>(fireCallsRequest, getEmptyHeaders());
	}

	private HttpHeaders getEmptyHeaders() {
		return new HttpHeaders();
	}
}
