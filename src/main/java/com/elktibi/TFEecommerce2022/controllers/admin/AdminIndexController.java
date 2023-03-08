package com.elktibi.TFEecommerce2022.controllers.admin;

import com.elktibi.TFEecommerce2022.models.delivery.Country;
import com.elktibi.TFEecommerce2022.models.delivery.DeliveryMethod;
import com.elktibi.TFEecommerce2022.models.delivery.PaymentType;
import com.elktibi.TFEecommerce2022.models.product.Category;
import com.elktibi.TFEecommerce2022.models.product.Color;
import com.elktibi.TFEecommerce2022.models.product.Product;
import com.elktibi.TFEecommerce2022.models.product.TypeProduct;
import com.elktibi.TFEecommerce2022.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TypeProductService typeProductService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private DeliveryMethodService deliveryMethodService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private PaymentTypeService paymentTypeService;


    @GetMapping("/index")
    public String getAdminIndex() {
        return "admin/admin-index";
    }

    @GetMapping("/product-admin")
    public String getProductAdminPage(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
        return "admin/dashboards/product-admin";
    }

    @GetMapping("/cat-type-col-admin")
    public String getCatTypColAdminPage(Model model) {
        List<Category> categoryList = categoryService.findAllCategory();
        List<Color> colorList = colorService.findAllColor();
        List<TypeProduct> typeProductList = typeProductService.findAllTypeProduct();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("typeProductList", typeProductList);
        return "admin/dashboards/cat-type-col-admin";
    }

    @GetMapping("/stock-admin")
    public String getStockAdminPage(Model model) {
        List<Product> allProducts = new ArrayList<>();
        allProducts = productService.getAllProducts();
        model.addAttribute("productList", allProducts);
        return "admin/dashboards/stock-admin";
    }

    @GetMapping("/del-pay-count-admin")
    public String getDelPayCountAdminPage(Model model) {
        List<DeliveryMethod> deliveryMethodList = deliveryMethodService.findAllDeliveryMethod();
        List<PaymentType> paymentTypeList = paymentTypeService.findAllPaymentType();
        List<Country> countryList = countryService.findAllCountry();

        model.addAttribute("paymentTypeList", paymentTypeList);
        model.addAttribute("deliveryMethodList", deliveryMethodList);
        model.addAttribute("countryList", countryList);
        return "admin/dashboards/del-pay-count-admin";
    }
}
