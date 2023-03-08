package com.elktibi.TFEecommerce2022.controllers;

import com.elktibi.TFEecommerce2022.DTO.CheckoutDTO;
import com.elktibi.TFEecommerce2022.models.delivery.*;
import com.elktibi.TFEecommerce2022.models.shop.Cart;
import com.elktibi.TFEecommerce2022.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class CheckoutController {

    @Autowired
    private CountryService countryService;
    @Autowired
    private DeliveryMethodService deliveryMethodService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CartService cartService;

    @GetMapping("/checkout-page")
    public String checkoutPage(Model model, @RequestParam(name = "id") Long id) {
        CheckoutDTO checkoutDTO = new CheckoutDTO();
        Cart cart = cartService.findCartById(id);
        checkoutDTO.setCart(cart);
        List<PaymentType> paymentTypeList = paymentTypeService.findAllPaymentType();
        List<DeliveryMethod> deliveryMethods = deliveryMethodService.findAllDeliveryMethod();
        List<Country> countryList = countryService.findAllCountry();

        Address lastUsedAddress  = addressService.findLastUsedAddress(cart.getUser());
        checkoutDTO.setAddress(lastUsedAddress);

        model.addAttribute("checkoutDTO", checkoutDTO);
        model.addAttribute("lastUsedAddress", lastUsedAddress);
        model.addAttribute("deliveryMethods", deliveryMethods);
        model.addAttribute("paymentTypeList", paymentTypeList);
        model.addAttribute("countryList", countryList);
        return "shopping/checkout-page";
    }

    @PostMapping("/validate-checkout")
    private String validateCheckout(@Valid CheckoutDTO checkoutDTO, Model model, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            orderService.saveOrder(checkoutDTO);
            cartService.emptyAndSaveCart(checkoutDTO.getCart());
            return "index";
        }
        return "index";
    }
}
