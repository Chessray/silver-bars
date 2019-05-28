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

public class OrderBoard {
  private final List<Order> orders;

  public OrderBoard() {
    orders = new LinkedList<>();
  }

  public void registerOrder(final Order order) {
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

  public SortedMap<Integer, Integer> getSummarisedOrdersOfType(final OrderType orderType) {
    return ImmutableSortedMap.copyOf(
        orders.stream()
            .filter(order -> order.getOrderType() == orderType)
            .collect(groupingBy(Order::getPricePerKilogram, summingInt(Order::getGrams))),
        orderType == SELL ? naturalOrder() : reverseOrder());
  }
}
