package com.kindredgroup.unibetlivetest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EventApiTests {

	public static final String BASE_PATH = "/api/v1";
	public static final String EVENT_PATH = "/events";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getEventsTest() throws Exception {
		mockMvc.perform(get(BASE_PATH + EVENT_PATH)).andExpect(status().isOk()).andExpect(jsonPath("$.*").isArray())
				.andExpect(jsonPath("$.*", hasSize(2)));
	}

	@Test
	void getLiveEventsTest() throws Exception {
		mockMvc.perform(get(BASE_PATH + EVENT_PATH).param("isLive", "true")).andExpect(status().isOk())
				.andExpect(jsonPath("$.*").isArray()).andExpect(jsonPath("$.*", hasSize(1)))
				.andExpect(jsonPath("$.[0].name", is("Unibet IT vs Real madrid")));
	}

	@Test
	void getNotLiveEventsTest() throws Exception {
		mockMvc.perform(get(BASE_PATH + EVENT_PATH).param("isLive", "false")).andExpect(status().isOk())
				.andExpect(jsonPath("$.*").isArray()).andExpect(jsonPath("$.*", hasSize(1)))
				.andExpect(jsonPath("$.[0].name", is("Psg vs Real madrid")));
	}
}
