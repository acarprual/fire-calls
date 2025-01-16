package me.lprada.fire_calls.repository;

import me.lprada.fire_calls.model.FireCall;
import me.lprada.fire_calls.payload.FireCallsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class SeattleApiFireCallRepository implements FireCallRepository {

	private final String rowCountQuery;

	private final String typeQuery;

	private final RestTemplate restTemplate;

	public SeattleApiFireCallRepository(@Value("${fire-calls.endpoint}") String endpoint,
										RestTemplateBuilder restTemplateBuilder) {
		this.rowCountQuery = endpoint + "?$select=count(*)";
		this.typeQuery = endpoint + """
			?$select=type\
			&$limit=%s\
			&$where=within_circle(report_location, %f, %f, %f) AND contains(upper(type),'%s')""";
		restTemplate = restTemplateBuilder.build();
	}

	@Override
	public List<FireCall> search(FireCallsRequest fireCallsRequest) {
		return Arrays
			.stream(
				Objects.requireNonNull(
					restTemplate.getForObject(
						String.format(
							typeQuery,
							Arrays.stream(
									Objects.requireNonNull(
										restTemplate.getForObject(
											rowCountQuery,
											Row[].class
										)
									)
								)
								.toList()
								.get(0)
								.getCount(),
							fireCallsRequest.getX(),
							fireCallsRequest.getY(),
							fireCallsRequest.getR(),
							fireCallsRequest.getType().toUpperCase()
						),
						FireCall[].class
					)
				)
			)
			.toList();
	}
}
