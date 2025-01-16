package me.lprada.fire_calls.service;

import me.lprada.fire_calls.model.FireCall;
import me.lprada.fire_calls.payload.FireCallsRequest;
import me.lprada.fire_calls.repository.FireCallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FireCallsSearcher {

	private final FireCallRepository fireCallRepository;

	public FireCallsSearcher(FireCallRepository fireCallRepository) {
		this.fireCallRepository = fireCallRepository;
	}

	public Map<String, Long> search(FireCallsRequest fireCallsRequest) {
		List<FireCall> fireCallList = fireCallRepository.search(fireCallsRequest);
		return fireCallList
			.stream()
			.collect(
				Collectors.groupingBy(FireCall::getType, Collectors.counting())
			);
	}
}
