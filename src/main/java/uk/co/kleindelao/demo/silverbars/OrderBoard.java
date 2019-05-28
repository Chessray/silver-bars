package uk.co.kleindelao.demo.silverbars;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

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
}
