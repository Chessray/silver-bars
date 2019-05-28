package uk.co.kleindelao.demo.silverbars;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Order {
    @NonNull
    private String userId;
    private int grams;
    private int pricePerKilogram;
    @NonNull
    private OrderType orderType;
}
