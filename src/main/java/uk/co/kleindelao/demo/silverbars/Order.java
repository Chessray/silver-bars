package uk.co.kleindelao.demo.silverbars;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import static lombok.AccessLevel.PACKAGE;

/**
 * An order for silver bars. Every Order contains a non-null user ID, an {@link OrderType}, an
 * amount in grams and the price per kilogram.
 */
@Value
@Builder(access = PACKAGE)
public class Order {
  @NonNull private String userId;
  private int grams;
  private int pricePerKilogram;
  @NonNull private OrderType orderType;
}
