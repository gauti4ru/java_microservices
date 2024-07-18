package app.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class CartLoadResponseDTO {
    UserCart existingCart;
    Map<String, String> existingStock;
    float TotalIncTax;
    Map<String, Float> productsRate;
}
