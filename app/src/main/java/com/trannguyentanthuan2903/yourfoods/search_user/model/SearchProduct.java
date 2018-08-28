package com.trannguyentanthuan2903.yourfoods.search_user.model;

import com.trannguyentanthuan2903.yourfoods.product.model.Product;

/**
 * Created by Administrator on 10/17/2017.
 */

public class SearchProduct  {
    private Product product;

    public SearchProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}