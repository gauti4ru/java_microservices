package app.service;

import app.controllers.dto.AddToCartDTO;
import app.controllers.dto.RequestVerifier;
import app.controllers.dto.UserCart;
import app.downstreams.Consumers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class Shoppers implements Shopping {
    @Autowired
    RequestVerifier requestVerifier;
    @Autowired
    Consumers consumers;
    @Value("${taxRate}")
    String taxRate;

    @Override
    public UserCart addToCart(AddToCartDTO addToCartDTO) throws RuntimeException {
        isAvaiableInStock(addToCartDTO);
        UserCart userCart = new UserCart(addToCartDTO.getItem());
        requestVerifier.updateStock(userCart);
        userCart = requestVerifier.updateCustomersMap(addToCartDTO.getUserId(), userCart);
        return userCart;
    }

    @Override
    public float findCartPriceIncludingTax(UserCart userCart) {
        AtomicReference<Float> price = new AtomicReference<>(0.0f);
        userCart.getItems().forEach((item, quantity) -> {
            price.updateAndGet(v -> v + consumers.getPrice(item) * quantity);
        });
        float cartTotalValue = price.get();
        float taxRateFloat = Float.parseFloat(taxRate);
        float totalValueWithTax = ((taxRateFloat / 100) * cartTotalValue) + cartTotalValue;
        return Math.round(totalValueWithTax * 100.0f) / 100.0f;
    }

    @Override
    public Map<String, Float> getItemsPrice(UserCart userCart) {
        Map<String, Float> itemPrice = new HashMap<>();
        userCart.getItems().forEach((item, quantity) -> {
            itemPrice.put(item, consumers.getPrice(item));
        });
        return itemPrice;
    }

    //Verifying Users Requested quantity is available in our stock
    private void isAvaiableInStock(AddToCartDTO addToCartDTO) throws RuntimeException {
        Map<String, Integer> limitedStocks = new HashMap<>();
        addToCartDTO.Item.keySet().forEach(item -> {
            if (addToCartDTO.getItem().get(item) > requestVerifier.inStock(item)) {
                limitedStocks.put(item, requestVerifier.inStock(item));
            }
        });
        if (!limitedStocks.isEmpty()) {
            limitedStocks.put("LimitedStock", limitedStocks.size());
            throw new RuntimeException(limitedStocks + " these products are currently out of stocks !");
        }
    }
}
