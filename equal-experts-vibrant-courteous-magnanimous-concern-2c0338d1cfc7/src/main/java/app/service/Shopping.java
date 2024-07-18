package app.service;

import app.controllers.dto.AddToCartDTO;
import app.controllers.dto.UserCart;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface Shopping {
    UserCart addToCart(AddToCartDTO addToCartDTO);

    float findCartPriceIncludingTax(UserCart userCart);

    Map<String, Float> getItemsPrice(UserCart userCart);
}
