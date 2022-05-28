package com.mohey.food.ordring.order.service.domain.core.valueobject;

import com.mohey.food.ordring.system.common.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
