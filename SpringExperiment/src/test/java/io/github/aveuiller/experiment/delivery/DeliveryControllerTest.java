package io.github.aveuiller.experiment.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    DeliveryService deliveryService;

    @Test
    void testPostDeliveryThirdPartyMissingField() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> delivery = getValidDelivery();
        delivery.remove("externalId");
        String body = mapper.writeValueAsString(delivery);

        MockHttpServletRequestBuilder accept =
                MockMvcRequestBuilders.post("/delivery")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body);

        mvc.perform(accept).andExpect(status().is4xxClientError());
    }

    @Test
    void testPostDeliveryThirdPartyNullValue() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> delivery = getValidDelivery();
        delivery.replace("externalId", null);
        String body = mapper.writeValueAsString(delivery);

        MockHttpServletRequestBuilder accept =
                MockMvcRequestBuilders.post("/delivery")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body);

        mvc.perform(accept).andExpect(status().is4xxClientError());
    }

    @Test
    void testPostDeliveryLocationNullValue() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> delivery = getValidDelivery();
        delivery.replace("location", null);
        String body = mapper.writeValueAsString(delivery);

        MockHttpServletRequestBuilder accept =
                MockMvcRequestBuilders.post("/delivery")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body);

        mvc.perform(accept)
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testPostDeliveryOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> delivery = getValidDelivery();
        String body = mapper.writeValueAsString(delivery);
        MockHttpServletRequestBuilder accept =
                MockMvcRequestBuilders.post("/delivery")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body);
        mvc.perform(accept).andExpect(status().isOk());
    }

    @Test
    void testPostPersistIssue() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> delivery = getValidDelivery();
        String body = mapper.writeValueAsString(delivery);
        String expected_message = "expected message";
        Mockito.when(deliveryService.create(Mockito.any())).thenThrow(new RuntimeException(expected_message));

        MockHttpServletRequestBuilder accept =
                MockMvcRequestBuilders.post("/delivery")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body);

        mvc.perform(accept).andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", Matchers.is(expected_message)));
    }

    private Map<String, String> getValidDelivery() {
        Map<String, String> delivery = new HashMap<>();
        delivery.put("state", "PENDING");
        delivery.put("location", "Rome");
        delivery.put("thirdParty", "MONDIAL_RELAY");
        delivery.put("externalId", "1234");
        return delivery;
    }
}