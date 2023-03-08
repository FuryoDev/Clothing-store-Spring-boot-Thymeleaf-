package com.elktibi.TFEecommerce2022.controllers;

import com.elktibi.TFEecommerce2022.DTO.UserInfoDTO;
import com.elktibi.TFEecommerce2022.Utils.UtilsFunctions;
import com.elktibi.TFEecommerce2022.models.delivery.Address;
import com.elktibi.TFEecommerce2022.models.delivery.Country;
import com.elktibi.TFEecommerce2022.models.delivery.Order;
import com.elktibi.TFEecommerce2022.models.delivery.PaymentType;
import com.elktibi.TFEecommerce2022.models.users.User;
import com.elktibi.TFEecommerce2022.repositories.CountryRepository;
import com.elktibi.TFEecommerce2022.security.interfaces.UserRepository;
import com.elktibi.TFEecommerce2022.services.AddressService;
import com.elktibi.TFEecommerce2022.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/user-page")
    public String getUserPage(@AuthenticationPrincipal User user, Model model) {
        List<Order> userOrderList;
        userOrderList = orderService.findAllOrdersByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("orders", userOrderList);

        return "user/user-dashboard";
    }

    @GetMapping("/editUserForm")
    public String getUserForm(Model model, @AuthenticationPrincipal User user){
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setName(user.getName());
        userInfo.setLastName(user.getLastName());
        userInfo.setBirthDay(user.getBirthDay());

        model.addAttribute("userDTO", userInfo);
        return "user/user";
    }

    @PostMapping("/saveUserChange")
    public String saveUserChange(@AuthenticationPrincipal User user, Model model, @Valid UserInfoDTO userInfoDTO) {
        user.setLastName(userInfoDTO.getLastName());
        user.setName(userInfoDTO.getName());
        user.setBirthDay(userInfoDTO.getBirthDay());

        userRepository.save(user);
        return "redirect:/user/user-page";
    }

    @GetMapping("/order-page")
    public String orderPage(Model model, @RequestParam(name = "id") Long id) {
        Order order = orderService.findOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("dateOfPurchase", UtilsFunctions.calendarToDateString(order.getDateOfPurchase()));
        return "user/order-page";
    }

    @GetMapping("/addresses")
    public String getUserAddresses(@AuthenticationPrincipal User user, Model model) {
        List<Address> addressList = addressService.findAllAddressByUser(user);
        model.addAttribute("addressList", addressList);
        return "user/addresses-page";
    }

    public String createAddress() {
        return "x";
    }

    @PostMapping("/saveAddress")
    public String saveAddress(Model model,@AuthenticationPrincipal User user, @Valid Address address, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            addressService.saveAddress(user, address);
        }
        return "redirect:/user/addresses";
    }


    @GetMapping("/address-form")
    public String getAddressForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        Address address = new Address();
        List<Country> countries =countryRepository.findAll();
        if(id != null) {
            address = addressService.findAddressById(id);
        }

        model.addAttribute("countries", countries);
        model.addAttribute("address", address);
        return "user/address-form";
    }

    @GetMapping("deleteAddress")
    public String deleteAddress(Model model, @RequestParam(name = "id") Long id) {
        addressService.deleteAddress(id);
        return "redirect:/user/addresses";
    }

}
