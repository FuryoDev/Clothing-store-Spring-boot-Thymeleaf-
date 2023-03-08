package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.models.product.Stock;
import com.elktibi.TFEecommerce2022.models.shop.Cart;
import com.elktibi.TFEecommerce2022.models.shop.CartItem;
import com.elktibi.TFEecommerce2022.models.users.User;
import com.elktibi.TFEecommerce2022.repositories.CartItemRepository;
import com.elktibi.TFEecommerce2022.repositories.CartRepository;
import com.elktibi.TFEecommerce2022.repositories.StockRepository;
import com.elktibi.TFEecommerce2022.security.interfaces.UserRepository;
import com.elktibi.TFEecommerce2022.services.interfaces.CartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CartService implements CartServiceInterface {

    public boolean loggedIn = false;
    private Cart cart = new Cart();

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;


    public Cart findCartById(Long id) {
        return cartRepository.findById(id).get();
    }

    @Override
    public Cart getCart() {
        //TODO: Do the temp cart system
        loggedIn = false;
        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loggedIn = true;
            cart = cartRepository.findCartByUser(user).get();
        }
        return cart;
    }

    @Override
    public void addToCart(Cart cart, CartItem cartItem) {
        cartItem.setCart(cart);
        List<CartItem> tempItemList = cart.getItems();

        // For the caritem Id problem, we set a fake item Id.
        if(!loggedIn) {
            //If we're unlucky enough to get the same number twice, it'll break the guest cart
            cartItem.setCartItemId(ThreadLocalRandom.current().nextLong());
        }
        // Set up the items list to not null if the cart is empty
        if (tempItemList == null || tempItemList.isEmpty()) {
            cartItem.setTotalPrice(cartItem.getProduct().getPrice());
            tempItemList.add(cartItem);

        } else {
            // The cart is not empty
            boolean itemIsPresent = false;
            // We search if there's already the same item with the same size
            for (CartItem ci : tempItemList) {
                if (ci.getProduct().getProductId().equals(cartItem.getProduct().getProductId()) && ci.getSize().equals(cartItem.getSize())) {
                    //If there is the same item/size, we juste update the total and item quanitty
                    int itemQuantity = ci.getQuantity() + cartItem.getQuantity();
                    ci.setQuantity(itemQuantity);
                    ci.setTotalPrice(updateCartItemTotal(ci));
                    itemIsPresent = true;
                }
            }
            // If it's not present, we set the total price
            if (!itemIsPresent) {
                cartItem.setTotalPrice(updateCartItemTotal(cartItem));
                tempItemList.add(cartItem);
            }
        }
        // We then put the list with the new item(s) in the cart and we save it.
        cart.setItems(tempItemList);
        cart.setTotal(updateCartTotal(cart));
        if(loggedIn) {
            cartRepository.save(cart);
        }
    }

    public void changeItemQuantity(Cart cart, Long id, int amount) {
        CartItem cartItem = new CartItem();
        for (CartItem ci : cart.getItems()) {
            if (ci.getCartItemId().equals(id)) {
                cartItem = ci;
                break;
            }
        }
        //Once it's found, we give it the new quantity
        int newQuantity = cartItem.getQuantity() + amount;
        //If it's 0, we remove the item from the cart
        if (newQuantity == 0) {
            removeFromCart(id);
            return;
        }
        cartItem.setQuantity(newQuantity);
        // We update the totalPrice
        cartItem.setTotalPrice(updateCartItemTotal(cartItem));
        cart.setTotal(updateCartTotal(cart));
        //if we're logged in we save the item and the cart -> check if we can't save the item just by saving the cart
        if(loggedIn) {
            cartRepository.save(cart);
        }
    }


    public double updateCartItemTotal(CartItem cartItem) {
        double itemTotal = 0;
        for (int i = 0; i < cartItem.getQuantity(); i++) {
            itemTotal = itemTotal + cartItem.getProduct().getPrice();
        }
        return itemTotal;
    }

    @Override
    public void removeFromCart(Long cartItemId) {
        List<CartItem> items = cart.getItems();
        for (CartItem ci : items) {
            if (ci.getCartItemId().equals(cartItemId)) {
                double total = cart.getTotal();
                cart.setTotal(total - ci.getTotalPrice());
                items.remove(ci);
                break;
            }
        }
        if(loggedIn) {
            cartRepository.save(cart);
        }
    }

    public double updateCartTotal(Cart cart) {
        double total = 0;
        for (CartItem ci : cart.getItems()) {
            total = total + updateCartItemTotal(ci);
        }
        cart.setTotal(total);
        return total;
    }

    public void emptyAndSaveCart(Cart cart) {
        //If we're logged in, we get the cart from DB cause of persistence issues (can't save the cart passed in param, don't know why)
        Cart cartToEmpty = cartRepository.findById(cart.getCartId()).get();
        cartToEmpty.getItems().removeAll(cartToEmpty.getItems());
        cartToEmpty.setTotal(0);
        cartRepository.save(cartToEmpty);
    }

    public boolean isInStock(CartItem cartItem) {
        Stock productStock = stockRepository.findBySizeNameAndProduct(cartItem.getSize(), cartItem.getProduct());
        return productStock.getNumberItemLeft() >= cartItem.getQuantity();
    }

    public int getMaxAmountOfItemInStock(CartItem cartItem) {
        Stock productStock = stockRepository.findBySizeNameAndProduct(cartItem.getSize(), cartItem.getProduct());
        return productStock.getNumberItemLeft();
    }

    public CartItem findCartItemById(Long id) {
        if(!loggedIn) {
            for(CartItem ci : cart.getItems()) {
                if(ci.getCartItemId().equals(id)) {
                    return ci;
                }
            }
        }
        return cartItemRepository.findById(id).get();
    }
}
