package com.elktibi.TFEecommerce2022.controllers.admin;

import com.elktibi.TFEecommerce2022.models.delivery.Country;
import com.elktibi.TFEecommerce2022.models.product.Category;
import com.elktibi.TFEecommerce2022.models.product.Color;
import com.elktibi.TFEecommerce2022.models.product.TypeProduct;
import com.elktibi.TFEecommerce2022.services.CategoryService;
import com.elktibi.TFEecommerce2022.services.ColorService;
import com.elktibi.TFEecommerce2022.services.TypeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminTypeProductController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TypeProductService typeProductService;

    @Autowired
    private ColorService colorService;

    @GetMapping("/typeProductForm")
    public String typeProductForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        TypeProduct typeProduct = new TypeProduct();
        if(id != null) {
            typeProduct = typeProductService.findTypeProductById(id);
        }
        List<Category> categories = categoryService.findAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("typeProduct", typeProduct);
        return "admin/forms/type-product";
    }

    @PostMapping("/saveTypeProduct")
    public String saveTypeProduct(Model model, @Valid TypeProduct typeProduct, BindingResult bindingResult) {
        List<Category> categoryList = categoryService.findAllCategory();
        List<Color> colorList = colorService.findAllColor();
        List<TypeProduct> typeProductList = typeProductService.findAllTypeProduct();

        if (!bindingResult.hasErrors()) {
            try {
                typeProductService.saveTypeProduct(typeProduct);
                model.addAttribute("message", "Type Product saved successfully");
            }
            catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                model.addAttribute("typeProduct", typeProduct);
                model.addAttribute("categories", categoryList);
                return "admin/forms/type-product";
            }

            model.addAttribute("categoryList", categoryList);
            model.addAttribute("colorList", colorList);
            model.addAttribute("typeProductList", typeProductList);
            return "admin/dashboards/cat-type-col-admin";
        }
        model.addAttribute("categories", categoryList);
        return "admin/forms/type-product";
    }

    @GetMapping("/deleteTypeProduct")
    public String deleteTypeProduct(Model model, @RequestParam("id") Long id) {
        typeProductService.deleteTypeProduct(id);
        List<Category> categoryList = categoryService.findAllCategory();
        List<Color> colorList = colorService.findAllColor();
        List<TypeProduct> typeProductList = typeProductService.findAllTypeProduct();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("typeProductList", typeProductList);
        model.addAttribute("message", "Type Product deleted successfully");
        return "redirect:/admin/cat-type-col-admin";
    }
}
