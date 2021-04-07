package br.com.amarques.smartcookbook;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class SmartCookbookApplicationTests {

	@Test
	void contextLoads() {
	}

}
