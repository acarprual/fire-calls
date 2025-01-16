package me.lprada.fire_calls.unit.service;

import me.lprada.fire_calls.model.FireCall;
import me.lprada.fire_calls.payload.FireCallsRequest;
import me.lprada.fire_calls.repository.FireCallRepository;
import me.lprada.fire_calls.service.FireCallsSearcher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RestClientTest(FireCallsSearcher.class)
class FireCallsSearcherTest {

	@Autowired
	private FireCallsSearcher fireCallsSearcher;

	@MockitoBean
	private FireCallRepository fireCallsRepository;


	@Test
	void should_load_service() {
		Assertions.assertThat(fireCallsSearcher).isNotNull();
	}

	@Test
	void should_return_aggregated_search_with_3_entries() {
		shouldSearch(List.of(
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
		));

		FireCallsRequest fireCallsRequest = FireCallsRequest
			.builder()
			.type("med")
			.x(47.59815)
			.y(-122.334540)
			.r(50.0)
			.build();

		Map<String, Long> response = fireCallsSearcher.search(fireCallsRequest);

		assertResponse(Map.of(
			"Multiple Medic Resp 14 Per", 1L,
			"Medic Response", 1L,
			"Medic Response- Overdose", 1L
		), response);

		shouldHaveSearched(fireCallsRequest);
	}

	private void shouldSearch(List<FireCall> response) {
		Mockito.when(fireCallsRepository.search(ArgumentMatchers.any(FireCallsRequest.class)))
			.thenReturn(response);
	}

	private void shouldHaveSearched(FireCallsRequest fireCallsRequest) {
		verify(fireCallsRepository, atLeastOnce()).search(fireCallsRequest);
	}

	private void assertResponse(Map<String, Long> expectedResponse, Map<String, Long> response) {
		Assertions.assertThat(response).isEqualTo(expectedResponse);
	}
}
