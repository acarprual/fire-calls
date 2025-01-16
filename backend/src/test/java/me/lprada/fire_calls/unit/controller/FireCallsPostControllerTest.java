package me.lprada.fire_calls.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.lprada.fire_calls.controller.FireCallsPostController;
import me.lprada.fire_calls.payload.FireCallsRequest;
import me.lprada.fire_calls.service.FireCallsSearcher;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Map;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireCallsPostController.class)
class FireCallsPostControllerTest {

	@MockitoBean
	private FireCallsSearcher fireCallsSearcher;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void should_return_bad_request() throws Exception {
		assertResponse(
			"",
			400,
			""
		);
	}

	@Test
	void should_return_aggregation_in_json() throws Exception {
		FireCallsRequest fireCallsRequest = FireCallsRequest
			.builder()
			.type("med")
			.x(47.59815)
			.y(-122.33454)
			.r(50.0)
			.build();

		Map<String, Long> response = Map.of(
			"Multiple Medic Resp 14 Per", 1L,
			"Medic Response", 1L,
			"Medic Response- Overdose", 1L
		);

		shouldSearch(response);

		assertResponse(
			objectMapper.writeValueAsString(fireCallsRequest),
			200,
			objectMapper.writeValueAsString(response)
		);
		shouldHaveSearched();
	}

	@Test
	void should_return_bad_request2() throws Exception {
		FireCallsRequest fireCallsRequest = FireCallsRequest
			.builder()
			.type("med")
			.x(47.59815)
			.y(-122.33454)
			.r(-50.0)
			.build();

		assertResponse(
			objectMapper.writeValueAsString(fireCallsRequest),
			400,
			""
		);
	}

	private void assertResponse(
		String requestBody,
		Integer expectedStatusCode,
		String expectedResponse
	) throws Exception {
		ResultMatcher response = expectedResponse.isEmpty() ?
			content().string("") : content().json(expectedResponse);

		mockMvc.perform(post("/api/fire_calls")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().is(expectedStatusCode))
			.andExpect(response);
	}

	private void shouldSearch(Map<String, Long> response) {
		Mockito.when(fireCallsSearcher.search(ArgumentMatchers.any(FireCallsRequest.class)))
			.thenReturn(response);
	}

	private void shouldHaveSearched() {
		verify(fireCallsSearcher, atLeastOnce()).search(ArgumentMatchers.any(FireCallsRequest.class));
	}
}

