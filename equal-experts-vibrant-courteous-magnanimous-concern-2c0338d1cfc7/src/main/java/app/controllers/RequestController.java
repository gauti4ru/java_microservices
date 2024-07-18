package app.controllers;

import app.controllers.dto.*;
import app.service.Shopping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    @Autowired
    RequestVerifier requestVerifier;
    @Autowired
    Shopping shopping;

    @PostMapping("/cartload")
    ResponseEntity<?> productHandler(@RequestBody AddToCartDTO addToCartDTO) {
        logger.info("Received request to add items to cart for user: {}", addToCartDTO.getUserId());
        try {
            //Verifying Products are valid product or not.
            List<String> notExistingProducts = requestVerifier.verifyProducts(addToCartDTO);
            if (!notExistingProducts.isEmpty()) {
                logger.warn("Product verification failed: {}", notExistingProducts);
                ErrorMapping errorMapping = new ErrorMapping(HttpStatus.NOT_FOUND, notExistingProducts + " - we " +
                    "don't" + " sell these!");
                return new ResponseEntity<>(errorMapping, HttpStatus.NOT_FOUND);
            }
            //Adding Items to user cart and getting the current userCart DTO Object
            UserCart userExistingStocks = shopping.addToCart(addToCartDTO);
            //Creating DTO Object to response the User request
            CartLoadResponseDTO cartLoadResponseDTO = new CartLoadResponseDTO(userExistingStocks,
                requestVerifier.getAllItemsStock(), shopping.findCartPriceIncludingTax(userExistingStocks),
                shopping.getItemsPrice(userExistingStocks));
            logger.info("Successfully added items to cart for user: {}", addToCartDTO.getUserId());
            return new ResponseEntity<>(cartLoadResponseDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.error("Exception occurred while adding items to cart for user: {}", addToCartDTO.getUserId(), e);
            ErrorMapping errorMapping = new ErrorMapping(HttpStatus.NOT_FOUND, e.getMessage());
            return new ResponseEntity<>(errorMapping, HttpStatus.NOT_FOUND);
        }
    }
}
