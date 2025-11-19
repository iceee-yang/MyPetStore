package com.csu.petstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 6620528781626504362L;
    //private Item item;
    private int quantity;
    private boolean inStock;
    private BigDecimal total;

    public CartItem() {
    }

    public boolean isInStock() {
        return this.inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
        this.calculateTotal();
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.calculateTotal();
    }

    //业务逻辑相关代码
    public void incrementQuantity() {
        ++this.quantity;
        this.calculateTotal();
    }

    private void calculateTotal() {
        if (this.item != null && this.item.getListPrice() != null) {
            this.total = this.item.getListPrice().multiply(new BigDecimal(this.quantity));
        } else {
            this.total = null;
        }

    }
}
