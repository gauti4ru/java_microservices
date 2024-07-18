package app.controllers.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AddToCartDTO {
    public Map<String, Integer> Item = new HashMap<>();
    private String userId;
}