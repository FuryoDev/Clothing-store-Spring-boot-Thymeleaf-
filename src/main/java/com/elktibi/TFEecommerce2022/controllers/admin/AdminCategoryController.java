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
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private TypeProductService typeProductService;

    @GetMapping("/categoryForm")
    public String categoryForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        Category category = new Category();
        if(id != null) {
            category = categoryService.findCategoryById(id);
        }
        model.addAttribute("category", category);
        return "admin/forms/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(Model model, @Valid Category category, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                categoryService.saveCategory(category);
                model.addAttribute("message", "Category saved successfully");

            }
            catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "admin/forms/category";
            }

            List<Category> categoryList = categoryService.findAllCategory();
            List<Color> colorList = colorService.findAllColor();
            List<TypeProduct> typeProductList = typeProductService.findAllTypeProduct();

            model.addAttribute("categoryList", categoryList);
            model.addAttribute("colorList", colorList);
            model.addAttribute("typeProductList", typeProductList);
            return "admin/dashboards/cat-type-col-admin";
        }
        return "admin/forms/category";
    }

    @GetMapping("/deleteCategory")
    public String deleteCategory(Model model, @RequestParam("id") Long id) {
        categoryService.deleteCategory(id);
        List<Category> categoryList = categoryService.findAllCategory();
        List<Color> colorList = colorService.findAllColor();
        List<TypeProduct> typeProductList = typeProductService.findAllTypeProduct();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("typeProductList", typeProductList);
        model.addAttribute("message", "Category deleted successfully");
        return "admin/dashboards/cat-type-col-admin";
    }
}
