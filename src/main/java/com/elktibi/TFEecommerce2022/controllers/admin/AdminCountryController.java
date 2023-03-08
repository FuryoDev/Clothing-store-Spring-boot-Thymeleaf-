package com.elktibi.TFEecommerce2022.controllers.admin;

import com.elktibi.TFEecommerce2022.models.delivery.Country;
import com.elktibi.TFEecommerce2022.models.delivery.DeliveryMethod;
import com.elktibi.TFEecommerce2022.models.delivery.PaymentType;
import com.elktibi.TFEecommerce2022.services.CountryService;
import com.elktibi.TFEecommerce2022.services.DeliveryMethodService;
import com.elktibi.TFEecommerce2022.services.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminCountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private DeliveryMethodService deliveryMethodService;

    @GetMapping("/countryForm")
    public String countryForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        Country country = new Country();
        if (id != null) {
            country = countryService.findCountryById(id);
        }
        model.addAttribute("country", country);
        return "admin/forms/country";
    }

    @PostMapping("/saveCountry")
    public String saveCountry(Model model, @Valid Country country, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                countryService.saveCountry(country);
                model.addAttribute("message", "Country saved successfully");

            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "admin/forms/country";
            }
            List<DeliveryMethod> deliveryMethodList = deliveryMethodService.findAllDeliveryMethod();
            List<PaymentType> paymentTypeList = paymentTypeService.findAllPaymentType();
            List<Country> countryList = countryService.findAllCountry();

            model.addAttribute("paymentTypeList", paymentTypeList);
            model.addAttribute("deliveryMethodList", deliveryMethodList);
            model.addAttribute("countryList", countryList);
            return "admin/dashboards/del-pay-count-admin";
        }
        return "admin/forms/country";
    }

    @GetMapping("/deleteCountry")
    public String deleteCountry(Model model, @RequestParam("id") Long id) {
        countryService.deleteCountry(id);
        List<DeliveryMethod> deliveryMethodList = deliveryMethodService.findAllDeliveryMethod();
        List<PaymentType> paymentTypeList = paymentTypeService.findAllPaymentType();
        List<Country> countryList = countryService.findAllCountry();

        model.addAttribute("paymentTypeList", paymentTypeList);
        model.addAttribute("deliveryMethodList", deliveryMethodList);
        model.addAttribute("countryList", countryList);
        model.addAttribute("message", "Country deleted successfully");
        return "admin/dashboards/del-pay-count-admin";
    }
}