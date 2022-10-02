package com.kindredgroup.unibetlivetest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.kindredgroup.unibetlivetest.utils.Urls;

@SpringBootTest
@AutoConfigureMockMvc
class BetApiTests {

	private static final String ADD_BET_REQ_BODY = "{\"selectionId\":\"4\", \"cote\":20.7, \"mise\":40}";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void addBetTest() throws Exception {
		mockMvc.perform(post(Urls.BASE_PATH + Urls.ADD_BET).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(ADD_BET_REQ_BODY)).andDo(print())
				.andExpect(status().isOk());
	}
}
