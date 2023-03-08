package com.elktibi.TFEecommerce2022.controllers;

import com.elktibi.TFEecommerce2022.DTO.ProductFiltersDTO;
import com.elktibi.TFEecommerce2022.Utils.UtilsFunctions;
import com.elktibi.TFEecommerce2022.models.product.*;
import com.elktibi.TFEecommerce2022.models.shop.CartItem;
import com.elktibi.TFEecommerce2022.services.ColorService;
import com.elktibi.TFEecommerce2022.services.ProductService;
import com.elktibi.TFEecommerce2022.services.TypeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private TypeProductService typeProductService;

    @GetMapping("/all-products")
    public String getAllArticles(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "shopping/showcase-page";
    }

    @GetMapping("/by-category")
    public String getProductsByCategory(Model model, @RequestParam(name = "category", defaultValue = "") String category) {
        List<Product> productsByCategory = productService.getProductsByCategory(category);
        List<Color> colorList = colorService.findAllColor();
        List<TypeProduct> typeProductList = typeProductService.findAllTypeProductByCategory(category);
        ProductFiltersDTO productFiltersDTO = new ProductFiltersDTO();

        model.addAttribute("productFiltersDTO", productFiltersDTO);
        model.addAttribute("products", productsByCategory);
        model.addAttribute("colorList", colorList);
        model.addAttribute("typeProdList", typeProductList);
        model.addAttribute("category", category);
        return "shopping/showcase-page";
    }

    @PostMapping("/by-filters")
    public String getProductsByFilter(Model model,
                                      ProductFiltersDTO productFiltersDTO,
                                      @RequestParam(name = "category") String category,
                                      BindingResult bindingResult) {
        List<Product> products;
        products = productService.findProductsByFilters(productFiltersDTO, category);

        List<Color> colorList = colorService.findAllColor();
        List<TypeProduct> typeProductList = typeProductService.findAllTypeProductByCategory(category);

        model.addAttribute("productFiltersDTO", productFiltersDTO);
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        model.addAttribute("colorList", colorList);
        model.addAttribute("typeProdList", typeProductList);

        return "shopping/showcase-page";
    }

    @GetMapping("/product-page")
    public String getProductPage(Model model, @RequestParam(name = "id") Long id) {
        Product product = productService.findProductById(id);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);

        model.addAttribute("product", cartItem.getProduct());
        model.addAttribute("cartItem", cartItem);

        List<String> sizeList = new ArrayList<>();
        if(product.getTypeProduct().getCategory().getSizingType().equals("Clothing")) {
            sizeList = Arrays.asList(UtilsFunctions.clothingSize);
        }
        if(product.getTypeProduct().getCategory().getSizingType().equals("Shoes")) {
            sizeList = Arrays.asList(UtilsFunctions.shoeSizing);
        }

        if(product.getTypeProduct().getCategory().getSizingType().equals("None")) {
            sizeList = Arrays.asList(UtilsFunctions.noSizing);
        }

        model.addAttribute("sizeList", sizeList);
        return "shopping/product-page";
    }
}
