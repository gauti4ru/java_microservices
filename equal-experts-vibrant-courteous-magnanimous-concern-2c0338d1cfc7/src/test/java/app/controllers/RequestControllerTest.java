package app.controllers;

import app.controllers.dto.AddToCartDTO;
import app.controllers.dto.RequestVerifier;
import app.controllers.dto.UserCart;
import app.service.Shopping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RequestControllerTest {
    private MockMvc mockMvc;
    @Mock
    private RequestVerifier requestVerifier;
    @Mock
    private Shopping shopping;
    @InjectMocks
    private RequestController requestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(requestController).build();
    }

    @Test
    public void testProductHandler_Success() throws Exception {
        AddToCartDTO addToCartDTO = new AddToCartDTO();
        addToCartDTO.setUserId("testUser");
        addToCartDTO.getItem().put("cornflakes", 2);
        UserCart userCart = new UserCart(addToCartDTO.getItem());
        Map<String, String> stock = new HashMap<>();
        stock.put("cornflakes", "298");
        stock.put("weetabix", "199");
        when(requestVerifier.verifyProducts(any(AddToCartDTO.class))).thenReturn(Collections.emptyList());
        when(shopping.addToCart(any(AddToCartDTO.class))).thenReturn(userCart);
        when(requestVerifier.getAllItemsStock()).thenReturn(stock);
        when(shopping.findCartPriceIncludingTax(any(UserCart.class))).thenReturn(16.9f);
        when(shopping.getItemsPrice(any(UserCart.class))).thenReturn(Collections.singletonMap("cornflakes", 2.52f));
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/cartload").contentType(MediaType.APPLICATION_JSON).content("{\"userId\": \"testUser\", \"item\": {\"cornflakes\": 2}}")).andReturn();
        assertEquals(HttpStatus.ACCEPTED.value(), mvcResult.getResponse().getStatus());
        String expectedResponse = "{\"existingCart\":{\"items\":{\"cornflakes\":2}}," + "\"existingStock" +
                "\":{\"cornflakes\":\"298\",\"weetabix\":\"199\"},\"productsRate\":{\"cornflakes\":2" + ".52}," +
                "\"totalIncTax\":16.9}";
        assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testProductHandler_ProductNotFound() throws Exception {
        AddToCartDTO addToCartDTO = new AddToCartDTO();
        addToCartDTO.setUserId("testUser");
        addToCartDTO.getItem().put("cornflakes", 2);
        when(requestVerifier.verifyProducts(any(AddToCartDTO.class))).thenReturn(Collections.singletonList(
                "nonExistentProduct"));
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/cartload").contentType(MediaType.APPLICATION_JSON).content("{\"userId\": \"testUser\", \"item\": {\"cornflakes\": 2}}")).andReturn();
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
        String expectedResponse =
                "{\"errorCode\":\"NOT_FOUND\",\"errorMessage\":\"[nonExistentProduct] - we don't " + "sell these!\"}";
        assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString());
    }
}
