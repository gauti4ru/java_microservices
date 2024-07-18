package app.controllers.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("singleton")
public class Verifier implements RequestVerifier {
    @Value("#{${productItems}}")
    public Map<String, String> products;
    public Map<String, UserCart> usersDataBase = new HashMap<>();

    @Override
    public List<String> verifyProducts(AddToCartDTO addToCartDTO) {
        List<String> itemPresent = new ArrayList<>();
        addToCartDTO.getItem().forEach((key, value) -> {
            boolean isPresent = false;
            for (String product : products.keySet()) {
                if (Objects.equals(product, key)) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                itemPresent.add(key);
            }
        });
        return itemPresent;
    }

    // Return Current Stock Available Quantity of Products
    @Override
    public int inStock(String product) {
        return Integer.parseInt(products.get(product));
    }

    @Override
    public Map<String, String> getAllItemsStock() {
        return products;
    }

    // Update the unsold stocks
    @Override
    public void updateStock(UserCart userCart) {
        userCart.getItems().forEach((item, quanitity) -> {
            products.put(item, String.valueOf(Integer.parseInt(products.get(item)) - quanitity));
        });
    }

    @Override
    public UserCart updateCustomersMap(String username, UserCart userCart) throws RuntimeException {
        Map<String, Integer> existingUserCart;
        //checking user is existing or new
        if (usersDataBase.containsKey(username)) {
            existingUserCart = usersDataBase.get(username).getItems();
            //Verifying that user's cart should not become negative after updating
            getOverflow(userCart, existingUserCart);
            //Updating users existing cart
            userCart.getItems().forEach((item, quanity) -> {
                if (existingUserCart.containsKey(item)) {
                    existingUserCart.put(item, quanity + existingUserCart.get(item));
                }
            });
            return usersDataBase.get(username);
        }
        //User is new
        else {
            //Verifying that New user's cart should not become negative after updating
            getOverflow(userCart);
            usersDataBase.put(username, userCart);
            return usersDataBase.get(username);
        }
    }

    // Validate the cart overflow
    private void getOverflow(UserCart userCart, Map<String, Integer> existingUserCart) throws RuntimeException {
        int[] overflow = {0};
        userCart.getItems().forEach((item, quantity) -> {
            if (existingUserCart.containsKey(item)) {
                if (existingUserCart.get(item) + quantity < 0) {
                    overflow[0]++;
                }
            }
        });
        if (overflow[0] > 0) {
            throw new RuntimeException("You cannot remove more than existing quantity of your cart");
        }
    }

    private void getOverflow(UserCart userCart) throws RuntimeException {
        int[] overflow = {0};
        userCart.getItems().forEach((item, quantity) -> {
            if (quantity < 0) {
                overflow[0]++;
            }
        });
        if (overflow[0] > 0) {
            throw new RuntimeException("You cannot remove more than existing quantity of your cart");
        }
    }
}




