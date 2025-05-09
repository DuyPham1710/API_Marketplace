package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.requests.CancelOrderRequest;
import vn.Second_Hand.marketplace.dto.responses.CancelledOrderResponse;

public interface IOrderCancellationService {
    CancelledOrderResponse cancelOrder(CancelOrderRequest request);
}
