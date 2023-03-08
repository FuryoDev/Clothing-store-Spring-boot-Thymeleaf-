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
public class AdminColorController {

    @Autowired
    private ColorService colorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TypeProductService typeProductService;


    @GetMapping("/colorForm")
    public String colorForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        Color color = new Color();
        if(id != null) {
            color = colorService.findColorById(id);
        }
        model.addAttribute("color", color);
        return "admin/forms/color";
    }

    @PostMapping("/saveColor")
    public String saveColor(Model model, @Valid Color color, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                colorService.saveColor(color);
                model.addAttribute("message", "Color saved successfully");

            }
            catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                model.addAttribute("color", color);
                return "admin/forms/color";
            }

            List<Category> categoryList = categoryService.findAllCategory();
            List<Color> colorList = colorService.findAllColor();
            List<TypeProduct> typeProductList = typeProductService.findAllTypeProduct();

            model.addAttribute("categoryList", categoryList);
            model.addAttribute("colorList", colorList);
            model.addAttribute("typeProductList", typeProductList);
            return "admin/dashboards/cat-type-col-admin";
        }
        return "admin/forms/color";
    }

    @GetMapping("/deleteColor")
    public String deleteColor(Model model, @RequestParam("id") Long id) {
        colorService.deleteColor(id);
        List<Category> categoryList = categoryService.findAllCategory();
        List<Color> colorList = colorService.findAllColor();
        List<TypeProduct> typeProductList = typeProductService.findAllTypeProduct();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("colorList", colorList);
        model.addAttribute("typeProductList", typeProductList);
        model.addAttribute("message", "Color deleted successfully");
        return "admin/dashboards/cat-type-col-admin";
    }
}
