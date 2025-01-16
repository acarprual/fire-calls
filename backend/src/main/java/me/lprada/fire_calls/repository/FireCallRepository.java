package me.lprada.fire_calls.repository;

import me.lprada.fire_calls.model.FireCall;
import me.lprada.fire_calls.payload.FireCallsRequest;

import java.util.List;

public interface FireCallRepository {

	List<FireCall> search(FireCallsRequest fireCallsRequest);
}
