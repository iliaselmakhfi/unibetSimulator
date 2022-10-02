package com.kindredgroup.unibetlivetest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.kindredgroup.unibetlivetest.utils.Urls;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
class EventApiTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getEventsTest() throws Exception {
		mockMvc.perform(get(Urls.BASE_PATH + Urls.EVENTS)).andExpect(status().isOk()).andExpect(jsonPath("$.*").isArray())
				.andExpect(jsonPath("$.*", hasSize(2)));
	}

	@Test
	void getLiveEventsTest() throws Exception {
		mockMvc.perform(get(Urls.BASE_PATH + Urls.EVENTS).param("isLive", "true")).andExpect(status().isOk())
				.andExpect(jsonPath("$.*").isArray()).andExpect(jsonPath("$.*", hasSize(1)))
				.andExpect(jsonPath("$.[0].name", is("Unibet IT vs Real madrid")));
	}

	@Test
	void getNotLiveEventsTest() throws Exception {
		mockMvc.perform(get(Urls.BASE_PATH + Urls.EVENTS).param("isLive", "false")).andExpect(status().isOk())
				.andExpect(jsonPath("$.*").isArray()).andExpect(jsonPath("$.*", hasSize(1)))
				.andExpect(jsonPath("$.[0].name", is("Psg vs Real madrid")));
	}
}
