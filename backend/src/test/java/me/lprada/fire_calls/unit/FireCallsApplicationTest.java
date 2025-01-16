package me.lprada.fire_calls.unit;


import me.lprada.fire_calls.FireCallsApplication;
import org.junit.jupiter.api.Test;

class FireCallsApplicationTest {

	@Test
	void should_run_app() {
		FireCallsApplication.main(new String[]{"--spring.profiles.active=default"});
	}
}
