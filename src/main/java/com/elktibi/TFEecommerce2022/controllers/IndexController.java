package com.elktibi.TFEecommerce2022.controllers;

import com.elktibi.TFEecommerce2022.models.product.Category;
import com.elktibi.TFEecommerce2022.models.product.Product;
import com.elktibi.TFEecommerce2022.models.shop.Cart;
import com.elktibi.TFEecommerce2022.models.users.User;
import com.elktibi.TFEecommerce2022.services.CartService;
import com.elktibi.TFEecommerce2022.services.CategoryService;
import com.elktibi.TFEecommerce2022.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping({"/", "/index", "/home"})
    public String getIndex(Model model, @AuthenticationPrincipal User user) {
        List<Category> categories = categoryService.findAllCategory();
        List<Product> newProducts = productService.getNewProducts(16);
        model.addAttribute("newProducts", newProducts);
        model.addAttribute("categoriesNav", categories);
        model.addAttribute("message", "Brrrrrr");
        return "index";
    }

    //TODO: Get all the navbar stuff everywhere
    //TODO: Check for some data and their nature like if a String name is only made of numbers and whatever
}
