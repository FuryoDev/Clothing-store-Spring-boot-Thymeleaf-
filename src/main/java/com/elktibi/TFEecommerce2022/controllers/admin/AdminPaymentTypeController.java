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
public class AdminPaymentTypeController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private DeliveryMethodService deliveryMethodService;

    @GetMapping("/paymentTypeForm")
    public String paymentTypeForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        PaymentType paymentType = new PaymentType();
        if(id != null) {
            paymentType = paymentTypeService.findPaymentTypeById(id);
        }
        model.addAttribute("paymentType", paymentType);
        return "admin/forms/payment-type";
    }

    @PostMapping("/savePaymentType")
    public String savePaymentType(Model model, @Valid PaymentType paymentType, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            try {
                paymentTypeService.savePaymentType(paymentType);
                model.addAttribute("message", "Payment Type saved successfully");

            }
            catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "admin/forms/payment-type";
            }

            List<DeliveryMethod> deliveryMethodList = deliveryMethodService.findAllDeliveryMethod();
            List<PaymentType> paymentTypeList = paymentTypeService.findAllPaymentType();
            List<Country> countryList = countryService.findAllCountry();

            model.addAttribute("paymentTypeList", paymentTypeList);
            model.addAttribute("deliveryMethodList", deliveryMethodList);
            model.addAttribute("countryList", countryList);
            return "admin/dashboards/del-pay-count-admin";
        }
        return "admin/forms/payment-type";
    }

    @GetMapping("/deletePaymentType")
    public String deletePaymentType(Model model, @RequestParam("id") Long id) {
        paymentTypeService.deletePaymentType(id);
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
