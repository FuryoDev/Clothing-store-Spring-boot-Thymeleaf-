package com.elktibi.TFEecommerce2022.repositories;

import com.elktibi.TFEecommerce2022.models.shop.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
}
