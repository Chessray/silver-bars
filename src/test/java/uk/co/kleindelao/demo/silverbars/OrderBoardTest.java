package uk.co.kleindelao.demo.silverbars;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static uk.co.kleindelao.demo.silverbars.OrderType.BUY;
import static uk.co.kleindelao.demo.silverbars.OrderType.SELL;

class OrderBoardTest {
  private final OrderBoard board = new OrderBoard();

  @Test
  void shouldRegisterOrder() {
    // Given
    final Order order =
        Order.builder()
            .grams(1500)
            .orderType(BUY)
            .pricePerKilogram(300)
            .userId(randomAlphanumeric(12, 24))
            .build();

    // When
    board.registerOrder(order);

    // Then
    then(board.getAllOrders()).containsExactly(order);
  }

  @Test
  void shouldReturnUnmodifiableList() {
    // Given
    final Order order =
            Order.builder()
                    .grams(1500)
                    .orderType(BUY)
                    .pricePerKilogram(300)
                    .userId(randomAlphanumeric(12, 24))
                    .build();
    board.registerOrder(order);

    // When
    final List<Order> orders = board.getAllOrders();

    // Then
    thenThrownBy(orders::clear).isInstanceOf(UnsupportedOperationException.class);
    thenThrownBy(() -> orders.add(order)).isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void shouldCancelOrder() {
    // Given
    final Order order =
            Order.builder()
                    .grams(1500)
                    .orderType(BUY)
                    .pricePerKilogram(300)
                    .userId(randomAlphanumeric(12, 24))
                    .build();
    board.registerOrder(order);

    // When
    board.cancelOrder(order);

    // Then
    then(board.getAllOrders()).isEmpty();
  }

  @Test
  void shouldReturnOrdersByType() {
    // Given
    final Order buyOrder =
            Order.builder()
                    .grams(1500)
                    .orderType(BUY)
                    .pricePerKilogram(300)
                    .userId(randomAlphanumeric(12, 24))
                    .build();
    board.registerOrder(buyOrder);
    final Order sellOrder =
            Order.builder()
                    .grams(1500)
                    .orderType(SELL)
                    .pricePerKilogram(300)
                    .userId(randomAlphanumeric(12, 24))
                    .build();
    board.registerOrder(sellOrder);

    // When
    final List<Order> buyOrders = board.getOrdersOfType(BUY);
    final List<Order> sellOrders = board.getOrdersOfType(SELL);

    // Then
    then(buyOrders).containsExactly(buyOrder);
    then(sellOrders).containsExactly(sellOrder);
  }
}
