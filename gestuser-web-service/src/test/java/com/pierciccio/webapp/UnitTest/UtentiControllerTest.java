package com.pierciccio.webapp.UnitTest;

import com.pierciccio.webapp.Application;
import com.pierciccio.webapp.repository.UtentiRepository;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = Application.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UtentiControllerTest 
{
    private MockMvc mockMvc;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UtentiRepository utentiRepository;


	@Autowired
	private WebApplicationContext wac;
	
	@BeforeEach
	public void setup() throws JSONException, IOException
	{
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.build();	
	}
	
	String JsonData =  
			"{\n" + 
			"    \"userId\": \"Pierciccio\",\n" +
			"    \"password\": \"1993Pierci\",\n" +
			"    \"attivo\": \"Si\",\n" + 
			"    \"ruoli\": [\n" + 
			"            \"USER\"\n" + 
			"        ]\n" + 
			"}";
	
	@Test
	@Order(1)
	public void A_testInsUtente1() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/utenti/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	@Order(2)
	public void B_testListUserByUserId() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/utenti/cerca/userid/Pierciccio")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				  
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.userId").exists())
				.andExpect(jsonPath("$.userId").value("Pierciccio"))
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.attivo").exists())
				.andExpect(jsonPath("$.attivo").value("Si"))
				  
				.andExpect(jsonPath("$.ruoli[0]").exists())
				.andExpect(jsonPath("$.ruoli[0]").value("USER")) 
				.andDo(print());
		
				assertThat(passwordEncoder.matches("1993Pierci",
						utentiRepository.findByUserId("Pierciccio").getPassword()))
				.isEqualTo(true);
	}
	
	String JsonData2 = 
			"{\n" + 
			"    \"userId\": \"Admin\",\n" + 
			"    \"password\": \"VerySecretPwd\",\n" + 
			"    \"attivo\": \"Si\",\n" + 
			"    \"ruoli\": [\n" + 
			"            \"USER\",\n" + 
			"            \"ADMIN\"\n" + 
			"        ]\n" + 
			"}";
	
	@Test
	@Order(3)
	public void C_testInsUtente2() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/utenti/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData2)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}
	
	String JsonDataUsers = 
			"[\n" + 
			"	{\n" + 
			"	    \"userId\": \"Pierciccio\",\n" +
			"	    \"password\": \"1993Pierci\",\n" +
			"	    \"attivo\": \"Si\",\n" + 
			"	    \"ruoli\": [\n" + 
			"		    \"USER\"\n" + 
			"		]\n" + 
			"	},\n" + 
			"	{\n" + 
			"	    \"userId\": \"Admin\",\n" + 
			"	    \"password\": \"VerySecretPwd\",\n" + 
			"	    \"attivo\": \"Si\",\n" + 
			"	    \"ruoli\": [\n" + 
			"		    \"USER\",\n" + 
			"		    \"ADMIN\"\n" + 
			"		]\n" + 
			"	}\n" + 
			"]";
	
	@Test
	@Order(4)
	public void D_testGetAllUser() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/utenti/cerca/tutti")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				 //UTENTE 1
				.andExpect(jsonPath("$[0].id").exists())
				.andExpect(jsonPath("$[0].userId").exists())
				.andExpect(jsonPath("$[0].userId").value("Pierciccio"))
				.andExpect(jsonPath("$[0].password").exists())
				.andExpect(jsonPath("$[0].attivo").exists())
				.andExpect(jsonPath("$[0].attivo").value("Si"))
				.andExpect(jsonPath("$[0].ruoli[0]").exists())
				.andExpect(jsonPath("$[0].ruoli[0]").value("USER")) 
				 //UTENTE 2
				.andExpect(jsonPath("$[1].id").exists())
				.andExpect(jsonPath("$[1].userId").exists())
				.andExpect(jsonPath("$[1].userId").value("Admin"))
				.andExpect(jsonPath("$[1].password").exists())
				.andExpect(jsonPath("$[1].attivo").exists())
				.andExpect(jsonPath("$[1].attivo").value("Si"))
				.andExpect(jsonPath("$[1].ruoli[0]").exists())
				.andExpect(jsonPath("$[1].ruoli[0]").value("USER")) 
				.andExpect(jsonPath("$[1].ruoli[1]").exists())
				.andExpect(jsonPath("$[1].ruoli[1]").value("ADMIN")) 
				.andReturn();
		
				assertThat(passwordEncoder.matches("VerySecretPwd", 
						utentiRepository.findByUserId("Admin").getPassword()))
				.isEqualTo(true);
	}
	
	
	
	@Test
	@Order(5)
	public void E_testDelUtente1() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/utenti/elimina/Pierciccio")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("200 OK"))
				.andExpect(jsonPath("$.message").value("Eliminazione Utente Pierciccio Eseguita Con Successo"))
				.andDo(print());
	}
	
	@Test
	@Order(6)
	public void F_testDelUtente2() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/utenti/elimina/Admin")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("200 OK"))
				.andExpect(jsonPath("$.message").value("Eliminazione Utente Admin Eseguita Con Successo"))
				.andDo(print());
	}
	
}


