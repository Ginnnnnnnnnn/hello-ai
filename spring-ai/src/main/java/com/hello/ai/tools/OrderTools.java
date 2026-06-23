package com.hello.ai.tools;

import cn.hutool.core.collection.ListUtil;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Order
 *
 * @author Gin
 * @since 2026-06-23
 */
@Slf4j
public class OrderTools {

    @Tool(description = "查询用户订单")
    public List<Order> findOrderByUserId(
            @ToolParam(description = "用户ID") String userId
    ) {
        return orders
                .stream()
                .filter(item -> item.getUserId().equals(userId))
                .toList();
    }

    @Tool(description = "用户退款")
    public void refund(
            @ToolParam(description = "用户ID") String userId,
            @ToolParam(description = "订单ID") String orderId,
            @ToolParam(description = "订单ID") String orderDetailId
    ) {
        // 修改子订单状态
        for (Order order : orders) {
            if (!order.getUserId().equals(userId) || !order.getOrderId().equals(orderId)) {
                continue;
            }
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                if (!orderDetail.getOrderId().equals(orderDetailId)) {
                    continue;
                }
                orderDetail.setOrderDetailStatus(OrderDetailStatusEnum.REFUNDED);
            }
        }
        for (Order order : orders) {
            if (!order.getUserId().equals(userId) || !order.getOrderId().equals(orderId)) {
                continue;
            }
            boolean result = order.getOrderDetails().stream()
                    .allMatch(item -> item.getOrderDetailStatus().equals(OrderDetailStatusEnum.REFUNDED));
            if (result) {
                order.setOrderStatus(OrderStatusEnum.CLOSED);
            }
        }
    }

    private static final List<Order> orders = new ArrayList<>();

    static {
        Order order1 = new Order();
        order1.setOrderId("ORDER20250108123");
        order1.setUserId("USER0000001");
        order1.setAmount(new BigDecimal("10599"));
        order1.setOrderStatus(OrderStatusEnum.PENDING_PAYMENT);
        order1.setCreateTime("2025-01-08 12:12:12");
        order1.setOrderDetails(ListUtil.of(
                new OrderDetail(
                        "ORDER20250108123-1", "ORDER20250108123", "iPhone 17 pro max 1TB",
                        1, new BigDecimal("10599"), new BigDecimal("10599"), OrderDetailStatusEnum.PENDING_SHIPMENT,
                        "2025-01-08 12:12:12"
                )
        ));
        orders.add(order1);
        Order order2 = new Order();
        order2.setOrderId("ORDER20260225456");
        order2.setUserId("USER0000001");
        order2.setAmount(new BigDecimal("299"));
        order2.setOrderStatus(OrderStatusEnum.PENDING_SHIPMENT);
        order2.setCreateTime("2026-02-25 14:12:58");
        order2.setOrderDetails(ListUtil.of(
                new OrderDetail(
                        "ORDER20260225456-1", "ORDER20260225456", "泰国金枕榴莲",
                        1, new BigDecimal("299"), new BigDecimal("299"), OrderDetailStatusEnum.PENDING_SHIPMENT,
                        "2026-02-25 14:12:58"
                )
        ));
        orders.add(order2);
        Order order3 = new Order();
        order3.setOrderId("ORDER20260530789");
        order3.setUserId("USER0000001");
        order3.setAmount(new BigDecimal("98"));
        order3.setOrderStatus(OrderStatusEnum.CLOSED);
        order3.setCreateTime("2026-05-30 09:30:01");
        order3.setOrderDetails(ListUtil.of(
                new OrderDetail(
                        "ORDER20260530789-1", "ORDER20260530789", "飘柔洗发水", 2,
                        new BigDecimal("49"), new BigDecimal("98"), OrderDetailStatusEnum.PENDING_SHIPMENT,
                        "2026-05-30 09:30:01"
                )
        ));
        orders.add(order3);
    }

    @Data
    public static class Order {
        private String orderId;
        private String userId;
        private BigDecimal amount;
        private OrderStatusEnum orderStatus;
        private String createTime;
        private List<OrderDetail> orderDetails;
    }

    @Data
    public static class OrderDetail {
        private String orderDetailId;
        private String orderId;
        private String name;
        private Integer count;
        private BigDecimal price;
        private BigDecimal amount;
        private OrderDetailStatusEnum orderDetailStatus;
        private String createTime;

        public OrderDetail(
                String orderDetailId, String orderId, String name, Integer count, BigDecimal price, BigDecimal amount,
                OrderDetailStatusEnum orderDetailStatus, String createTime
        ) {
            this.orderDetailId = orderDetailId;
            this.orderId = orderId;
            this.name = name;
            this.count = count;
            this.price = price;
            this.amount = amount;
            this.orderDetailStatus = orderDetailStatus;
            this.createTime = createTime;
        }
    }

    @Getter
    public enum OrderStatusEnum {

        PENDING_PAYMENT(1, "待支付"),
        PENDING_SHIPMENT(2, "待发货"),
        SHIPPED(3, "已发货"),
        COMPLETED(4, "已完成"),
        CLOSED(5, "已关闭"),
        ;

        OrderStatusEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        private final Integer code;

        private final String name;

    }

    @Getter
    public enum OrderDetailStatusEnum {

        PENDING_SHIPMENT(1, "待发货"),
        SHIPPED(2, "已发货"),
        REFUNDED(3, "已退款"),
        ;

        OrderDetailStatusEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        private final Integer code;

        private final String name;

    }

}
