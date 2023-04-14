package com.example.demo.domain.order.entity.items;

public enum CartItemLimit {
    SELF_SALAD(5);


    private final int maxCount;

    private CartItemLimit(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getMaxCount() {
        return maxCount;
    }
}
