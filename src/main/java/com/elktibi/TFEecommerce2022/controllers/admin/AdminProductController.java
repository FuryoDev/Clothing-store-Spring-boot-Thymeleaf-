package com.elktibi.TFEecommerce2022.controllers.admin;

import com.elktibi.TFEecommerce2022.models.delivery.Country;
import com.elktibi.TFEecommerce2022.models.delivery.PaymentType;
import com.elktibi.TFEecommerce2022.models.product.Category;
import com.elktibi.TFEecommerce2022.models.product.Color;
import com.elktibi.TFEecommerce2022.models.product.Product;
import com.elktibi.TFEecommerce2022.models.product.TypeProduct;
import com.elktibi.TFEecommerce2022.services.CategoryService;
import com.elktibi.TFEecommerce2022.services.ColorService;
import com.elktibi.TFEecommerce2022.services.ProductService;
import com.elktibi.TFEecommerce2022.services.TypeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TypeProductService typeProductService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("products-stocks")
    public String getProducts() {
        return "admin/product-stocks";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(Model model, @Valid Product product, BindingResult bindingResult, @RequestParam("image") MultipartFile image) {
        List<TypeProduct> typeProductList = typeProductService.findAllTypeProduct();
        List<Color> colorList = colorService.findAllColor();
        if(!bindingResult.hasErrors()){
            try {
                productService.saveProduct(product, image);
                model.addAttribute("message", "Product saved successfully");

            }
            catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                model.addAttribute("typeProductList", typeProductList);
                model.addAttribute("colorList", colorList);
            }
            List<Product> productList = productService.getAllProducts();
            model.addAttribute("productList", productList);
            return "admin/dashboards/product-admin";
        }
        else {
            model.addAttribute("typeProductList", typeProductList);
            model.addAttribute("colorList", colorList);
            model.addAttribute("product", product);
            return "admin/forms/product-form";
        }
    }

    @GetMapping("product-form")
    public String getProductForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        List<TypeProduct> typeProductList = typeProductService.findAllTypeProduct();
        List<Color> colorList = colorService.findAllColor();
        Product product = new Product();

        if(id != null) {
            product = productService.findProductById(id);
        }

        model.addAttribute("typeProductList", typeProductList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("product", product);
        return "admin/forms/product-form";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(Model model, @RequestParam("id") Long id) {
        productService.deleteProduct(id);
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
        model.addAttribute("message", "Product deleted successfully");
        return "admin/dashboards/product-admin";
    }
}
