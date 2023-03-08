package com.elktibi.TFEecommerce2022.controllers;

import com.elktibi.TFEecommerce2022.Utils.UtilsFunctions;
import com.elktibi.TFEecommerce2022.models.shop.CartItem;
import com.elktibi.TFEecommerce2022.models.users.User;
import com.elktibi.TFEecommerce2022.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String getCartPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "shopping/cart-page";
    }

    @PostMapping("/addToCart")
    public String addToCart(@Valid CartItem cartItem, BindingResult bindingResult, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        if(bindingResult.hasErrors()) {
            //TODO: If error -> put the cartItem back in the page ->
        }
        if(!cartService.isInStock(cartItem)) {
            int itemLeftInStock = cartService.getMaxAmountOfItemInStock(cartItem);
            model.addAttribute("product", cartItem.getProduct());
            model.addAttribute("cartItem", cartItem);
            List<String> sizeList = new ArrayList<>();
            if(cartItem.getProduct().getTypeProduct().getCategory().getSizingType().equals("Clothing")) {
                sizeList = Arrays.asList(UtilsFunctions.clothingSize);
            }
            if(cartItem.getProduct().getTypeProduct().getCategory().getSizingType().equals("Shoes")) {
                sizeList = Arrays.asList(UtilsFunctions.shoeSizing);
            }

            model.addAttribute("sizeList", sizeList);
            model.addAttribute("notInStockMessage", "Nous n'en avons que " + itemLeftInStock + " restant en stock");
            return "shopping/product-page";
        }
        cartService.addToCart(cartService.getCart(), cartItem);
        return "index";
    }

    @GetMapping("/removeFromCart")
    public String removeFromCart(Model model, @RequestParam(name = "id") Long id) {
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }

    @GetMapping("/changeItemQuantity")
    public String changeItemQuantity(Model model, @RequestParam(name = "id") Long cartItemId, @RequestParam(name = "amount") int amount) {
        CartItem cartItem = cartService.findCartItemById(cartItemId);
        int qtyAfterChange = cartItem.getQuantity() + amount;
        if((cartService.getMaxAmountOfItemInStock(cartItem) - qtyAfterChange) < 0) {
            model.addAttribute("stockMessage", "Il n'y a pas plus de " + cartItem.getProduct().getName() + " restant en stock");
            model.addAttribute("cart", cartService.getCart());
            return "shopping/cart-page";
        }
        cartService.changeItemQuantity(cartService.getCart(), cartItemId, amount);
        return "redirect:/cart";
    }
}
