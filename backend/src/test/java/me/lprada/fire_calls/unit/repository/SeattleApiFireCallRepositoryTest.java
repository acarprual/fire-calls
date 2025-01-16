package me.lprada.fire_calls.unit.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.lprada.fire_calls.model.FireCall;
import me.lprada.fire_calls.payload.FireCallsRequest;
import me.lprada.fire_calls.repository.FireCallRepository;
import me.lprada.fire_calls.repository.Row;
import me.lprada.fire_calls.repository.SeattleApiFireCallRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(SeattleApiFireCallRepository.class)
public class SeattleApiFireCallRepositoryTest {

	@Autowired
	private FireCallRepository fireCallRepository;

	@Autowired
	private MockRestServiceServer server;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${fire-calls.endpoint}")
	private String endpoint;


	@Test
	void should_load_repository() {
		assertThat(fireCallRepository).isNotNull();
	}

	@Test
	void should_return_aggregated_search_with_3_entries() throws JsonProcessingException {
		shouldCall(endpoint + "?$select=count(*)", objectMapper.writeValueAsString(createRowArray()));
		shouldCall(endpoint +
				"""
					?$select=type\
					&$limit=1997514\
					&$where=within_circle(report_location,%2047.598150,%20-122.334540,%2050.000000)\
					%20AND%20contains(upper(type),'MED')""",
			objectMapper.writeValueAsString(createFireCallArrayWith3Entries())
		);

		List<FireCall> response = fireCallRepository.search(
			FireCallsRequest
				.builder()
				.type("med")
				.x(47.59815)
				.y(-122.334540)
				.r(50.0)
				.build()
		);

		List<FireCall> expectedResponse = List.of(
			FireCall
				.builder()
				.type("Multiple Medic Resp 14 Per")
				.build(),
			FireCall
				.builder()
				.type("Medic Response")
				.build(),
			FireCall
				.builder()
				.type("Medic Response- Overdose")
				.build()
		);

		assertResponse(expectedResponse, response);
	}

	private void shouldCall(String request, String response) {
		server.expect(requestTo(request))
			.andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
	}

	private void assertResponse(List<FireCall> expectedResponse, List<FireCall> response) {
		assertThat(expectedResponse).usingRecursiveComparison().isEqualTo(response);
	}

	private Row[] createRowArray() {
		return new Row[]{
			Row
				.builder()
				.count("1997514")
				.build()
		};
	}

	private FireCall[] createFireCallArrayWith3Entries() {
		return new FireCall[]{
			FireCall
				.builder()
				.type("Multiple Medic Resp 14 Per")
				.build(),
			FireCall
				.builder()
				.type("Medic Response")
				.build(),
			FireCall.
				builder().
				type("Medic Response- Overdose").
				build()
		};
	}
}
