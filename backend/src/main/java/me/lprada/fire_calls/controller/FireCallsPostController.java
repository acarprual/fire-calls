package me.lprada.fire_calls.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.lprada.fire_calls.payload.FireCallsRequest;
import me.lprada.fire_calls.service.FireCallsSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class FireCallsPostController {

	private final FireCallsSearcher fireCallsSearcher;

	@PostMapping(value = "/api/fire_calls")
	public ResponseEntity<Map<String, Long>> run(@Valid @RequestBody FireCallsRequest fireCallsRequest) {
		return ResponseEntity.ok(fireCallsSearcher.search(fireCallsRequest));
	}
}
