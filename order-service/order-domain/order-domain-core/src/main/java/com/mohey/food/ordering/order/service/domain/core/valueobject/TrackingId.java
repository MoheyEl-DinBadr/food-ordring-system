package com.mohey.food.ordering.order.service.domain.core.valueobject;

import com.mohey.food.ordering.system.common.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
