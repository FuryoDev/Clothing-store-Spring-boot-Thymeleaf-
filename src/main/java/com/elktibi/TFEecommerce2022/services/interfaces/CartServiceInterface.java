package com.elktibi.TFEecommerce2022.services.interfaces;


import com.elktibi.TFEecommerce2022.models.product.Product;
import com.elktibi.TFEecommerce2022.models.shop.Cart;
import com.elktibi.TFEecommerce2022.models.shop.CartItem;
import com.elktibi.TFEecommerce2022.models.users.User;

public interface CartServiceInterface {

    Cart getCart();
    void addToCart(Cart cart, CartItem cartItem);

    void removeFromCart(Long cartId);

    void changeItemQuantity(Cart cart, Long id, int amount);

}
