package co.agpaytech;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AgpaytechTaskApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	/** To Do
	 * Test to ensure /countries endpoint return list of countries by page and size query parameter
	 * */
	@Test
	public void shouldGetCountriesByPageNoAndSize() throws Exception {
//		mockMvc.perform(post("/countries").
//				accept(MediaType.APPLICATION_FORM_URLENCODED).
//				contentType(MediaType.APPLICATION_JSON).
//				queryParam("pgNo","0").
//				queryParam("pgSize","1")).andExpect(status().is2xxSuccessful());
	}

	/**
	 * To Do:
	 * Test /addCountry/{countryName} Endpoint
	 * **/
	@Test
	public void shouldAddCountry(){

	}

	@Test
	public void shouldAddCountryByPartialName()
	{

	}

}
