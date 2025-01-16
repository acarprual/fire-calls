package me.lprada.fire_calls.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FireCallsRequest {

	@NotNull
	private String type;

	@NotNull
	private Double x;

	@NotNull
	private Double y;

	@NotNull
	@Positive
	private Double r;

}
