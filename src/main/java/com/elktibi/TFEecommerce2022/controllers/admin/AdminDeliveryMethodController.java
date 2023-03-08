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
@RequestMapping("/admin")
public class AdminDeliveryMethodController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private DeliveryMethodService deliveryMethodService;

    @GetMapping("/deliveryMethodForm")
    public String deliveryMethodForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        DeliveryMethod deliveryMethod = new DeliveryMethod();
        if(id != null) {
            deliveryMethod = deliveryMethodService.findDeliveryMethodById(id);
        }
        model.addAttribute("deliveryMethod", deliveryMethod);
        return "admin/forms/delivery-method";
    }

    @PostMapping("/saveDeliveryMethod")
    public String saveDeliveryMethod(Model model, @Valid DeliveryMethod deliveryMethod, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                deliveryMethodService.saveDeliveryMethod(deliveryMethod);
                model.addAttribute("message", "Category saved successfully");

            }
            catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "admin/forms/delivery-method";
            }

            List<DeliveryMethod> deliveryMethodList = deliveryMethodService.findAllDeliveryMethod();
            List<PaymentType> paymentTypeList = paymentTypeService.findAllPaymentType();
            List<Country> countryList = countryService.findAllCountry();

            model.addAttribute("paymentTypeList", paymentTypeList);
            model.addAttribute("deliveryMethodList", deliveryMethodList);
            model.addAttribute("countryList", countryList);
            return "admin/dashboards/del-pay-count-admin";
        }
        return "admin/forms/delivery-method";
    }

    @GetMapping("/deleteDeliveryMethod")
    public String deleteDeliveryMethod(Model model, @RequestParam("id") Long id) {
        deliveryMethodService.deleteDeliveryMethod(id);
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
