package uk.co.kleindelao.demo.silverbars;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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
    final Order order = createOrder(1500, BUY, 300);

    // When
    board.registerOrder(order);

    // Then
    then(board.getAllOrders()).containsExactly(order);
  }

  private Order createOrder(final int grams, final OrderType buy, final int pricePerKilogram) {
    return Order.builder()
        .grams(grams)
        .orderType(buy)
        .pricePerKilogram(pricePerKilogram)
        .userId(randomAlphanumeric(12, 24))
        .build();
  }

  @Test
  void shouldReturnUnmodifiableList() {
    // Given
    final Order order = createOrder(1500, BUY, 300);
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
    final Order order = createOrder(1500, BUY, 300);
    board.registerOrder(order);

    // When
    board.cancelOrder(order);

    // Then
    then(board.getAllOrders()).isEmpty();
  }

  @Test
  void shouldReturnOrdersByType() {
    // Given
    final Order buyOrder = createOrder(1500, BUY, 300);
    board.registerOrder(buyOrder);
    final Order sellOrder = createOrder(1500, SELL, 300);
    board.registerOrder(sellOrder);

    // When
    final List<Order> buyOrders = board.getOrdersOfType(BUY);
    final List<Order> sellOrders = board.getOrdersOfType(SELL);

    // Then
    then(buyOrders).containsExactly(buyOrder);
    then(sellOrders).containsExactly(sellOrder);
  }

  @Test
  void shouldSummariseByPrice() {
    // Given
    final int pricePerKilogram1_2 = 300;
    final int grams1 = 1500;
    final Order order1 = createOrder(grams1, SELL, pricePerKilogram1_2);
    final int grams2 = 2000;
    final Order order2 = createOrder(grams2, SELL, pricePerKilogram1_2);
    final int grams3 = 4200;
    final int pricePerKilogram3 = pricePerKilogram1_2 + 5;
    final Order order3 = createOrder(grams3, SELL, pricePerKilogram3);
    board.registerOrder(order1);
    board.registerOrder(order2);
    board.registerOrder(order3);

    // When
    final Map<Integer, Integer> summaries = board.getSummarisedOrdersOfType(SELL);

    // Then
    then(summaries)
        .containsOnly(
            ImmutableMap.of(pricePerKilogram1_2, grams1 + grams2, pricePerKilogram3, grams3)
                .entrySet()
                .toArray(new Map.Entry[0]));
  }

  @Test
  void shouldSortBuySummariesFromHighToLowPrice() {
    // TODO
  }

  @Test
  void shouldSortSellSummariesFromLowToHighPrice() {
    // Given
    final int pricePerKilogram1_2 = 300;
    final int grams1 = 1500;
    final Order order1 = createOrder(grams1, SELL, pricePerKilogram1_2);
    final int grams2 = 2000;
    final Order order2 = createOrder(grams2, SELL, pricePerKilogram1_2);
    final int grams3 = 4200;
    final int pricePerKilogram3 = pricePerKilogram1_2 + 5;
    final Order order3 = createOrder(grams3, SELL, pricePerKilogram3);
    board.registerOrder(order1);
    board.registerOrder(order2);
    board.registerOrder(order3);

    // When
    final Map<Integer, Integer> summaries = board.getSummarisedOrdersOfType(SELL);

    // Then
    then(summaries)
            .containsExactlyEntriesOf(
                    ImmutableMap.of(pricePerKilogram1_2, grams1 + grams2, pricePerKilogram3, grams3));
  }
}
