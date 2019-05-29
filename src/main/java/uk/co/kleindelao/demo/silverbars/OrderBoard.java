package uk.co.kleindelao.demo.silverbars;

import com.google.common.collect.ImmutableSortedMap;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.*;
import static uk.co.kleindelao.demo.silverbars.OrderType.SELL;

/**
 * Live Order Board that allows users to {@link #registerOrder(String, OrderType, int, int)
 * register} or {@link #cancelOrder(Order) cancel} {@link Order orders} for silver bars.
 */
public class OrderBoard {
  private final List<Order> orders;

  public OrderBoard() {
    orders = new LinkedList<>();
  }

  void registerOrder(final Order order) {
    orders.add(order);
  }

  public List<Order> getAllOrders() {
    return unmodifiableList(orders);
  }

  public void cancelOrder(final Order order) {
    orders.remove(order);
  }

  public List<Order> getOrdersOfType(final OrderType orderType) {
    return orders.stream().filter(order -> order.getOrderType() == orderType).collect(toList());
  }

  /**
   * Returns a summary of all {@link Order orders} with type {@code orderType}, sorted by {@link
   * Order#getPricePerKilogram() price per kilogram}. Orders with the same {@link
   * Order#getPricePerKilogram() price per kilogram} will be added up into a single figure. For
   * {@code orderType} {@link OrderType#SELL SELL}, the returned Map will be sorted in ascending,
   * for {@code orderType} {@link OrderType#BUY BUY} in descending price per kilogram.
   *
   * @param orderType The {@link OrderType}.
   * @return A summary of all {@link Order orders} with type {@code orderType}, sorted by {@link
   *     Order#getPricePerKilogram() price per kilogram} (ascending or descending depending on
   *     {@code orderType}).
   */
  public SortedMap<Integer, Integer> getSummarisedOrdersOfType(final OrderType orderType) {
    return ImmutableSortedMap.copyOf(
        orders.stream()
            .filter(order -> order.getOrderType() == orderType)
            .collect(groupingBy(Order::getPricePerKilogram, summingInt(Order::getGrams))),
        orderType == SELL ? naturalOrder() : reverseOrder());
  }

  /**
   * Creates and registers an {@link Order} with the given values.
   *
   * @param userId The {@link Order#getUserId() user ID}.
   * @param orderType The {@link OrderType}.
   * @param grams The order amount in grams.
   * @param pricePerKg The order's price per kilogram.
   * @return The registered {@link Order}.
   */
  public Order registerOrder(
      final String userId, final OrderType orderType, final int grams, final int pricePerKg) {
    final Order order =
        Order.builder()
            .userId(userId)
            .orderType(orderType)
            .grams(grams)
            .pricePerKilogram(pricePerKg)
            .build();
    registerOrder(order);
    return order;
  }
}
