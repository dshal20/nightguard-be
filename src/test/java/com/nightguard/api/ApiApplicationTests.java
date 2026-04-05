package com.nightguard.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nightguard.api.health.HealthController;

class ApiApplicationTests {

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new HealthController()).build();
	}

	@Test
	void healthReturnsOnline() throws Exception {
		mockMvc.perform(get("/health"))
				.andExpect(status().isOk())
				.andExpect(content().string("Online"));
	}

	@Test
	void healthIgnoresQueryParams() throws Exception {
		mockMvc.perform(get("/health").param("check", "1"))
				.andExpect(status().isOk())
				.andExpect(content().string("Online"));
	}

	@Test
	void healthRejectsPost() throws Exception {
		mockMvc.perform(post("/health"))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	void unknownPathReturnsNotFound() throws Exception {
		mockMvc.perform(get("/not-a-real-endpoint"))
				.andExpect(status().isNotFound());
	}
}
