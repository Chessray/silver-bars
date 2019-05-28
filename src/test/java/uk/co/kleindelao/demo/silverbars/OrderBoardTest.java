package uk.co.kleindelao.demo.silverbars;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static uk.co.kleindelao.demo.silverbars.OrderType.BUY;

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
}
