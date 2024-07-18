package app.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class UserCart {
    private Map<String, Integer> items;
}
