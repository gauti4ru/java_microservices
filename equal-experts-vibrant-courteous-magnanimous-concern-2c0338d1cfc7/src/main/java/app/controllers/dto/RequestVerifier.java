package app.controllers.dto;

import java.util.List;
import java.util.Map;

public interface RequestVerifier {
    /*
    This Interface is acting as Existing stocks and Existing User Verifier
     */
    List<String> verifyProducts(AddToCartDTO addToCartDTO);

    int inStock(String item);

    Map<String, String> getAllItemsStock();

    void updateStock(UserCart userCart);

    UserCart updateCustomersMap(String userName, UserCart userCart);
}
