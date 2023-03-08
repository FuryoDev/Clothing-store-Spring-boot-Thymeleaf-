package com.elktibi.TFEecommerce2022.services;

import com.elktibi.TFEecommerce2022.DTO.ProductFiltersDTO;
import com.elktibi.TFEecommerce2022.Utils.UtilsFunctions;
import com.elktibi.TFEecommerce2022.models.product.*;
import com.elktibi.TFEecommerce2022.repositories.*;
import com.elktibi.TFEecommerce2022.services.interfaces.ProductServiceInterface;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.rmi.CORBA.Util;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService implements ProductServiceInterface {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TypeProductRepository typeProductRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private StockRepository stockRepository;

    @Override
    public Product findProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        boolean exists = productOptional.isPresent();
        if (!exists) {
            throw new IllegalStateException("This product doesn't exist in DB");
        }
        return productOptional.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        Optional<Category> category1 = categoryRepository.findCategoryByName(category);
        List<TypeProduct> tpList = category1.get().getTypeProductList();
        List<Product> listProductToMerge = new ArrayList<>();
        for (TypeProduct pl : tpList) {
            listProductToMerge.addAll(pl.getProductList());
        }
        return listProductToMerge;
    }

    public List<Product> findProductsByFilters(ProductFiltersDTO productFiltersDTO, String category) {
        List<Product> products;
        Category category1 = categoryRepository.findCategoryByName(category).get();
        if (productFiltersDTO.getTypeProductId() == null && productFiltersDTO.getColorId() == null) {
            return this.getProductsByCategory(category);
        }

        if (productFiltersDTO.getTypeProductId() == null) {
            Color color = colorRepository.findById(productFiltersDTO.getColorId()).get();
            return productRepository.findByColorAndCategory(color, category1);
        }

        if (productFiltersDTO.getColorId() == null) {
            TypeProduct typeProduct = typeProductRepository.findById(productFiltersDTO.getTypeProductId()).get();
            return productRepository.findAllByTypeProduct(typeProduct);
        }

        TypeProduct typeProduct = typeProductRepository.findById(productFiltersDTO.getTypeProductId()).get();
        Color color = colorRepository.findById(productFiltersDTO.getColorId()).get();

        products = productRepository.findAllByTypeProductAndColor(typeProduct, color);

        return products;
    }

    @Override
    public void saveProduct(Product product, MultipartFile image) {
        if(product.getProductId() == null) {
            Optional<Product> productByName = productRepository.findProductByName(product.getName());
            if(productByName.isPresent()) {
                throw new IllegalStateException("This name is taken");
            }
        }
        String imagePath = saveProductImageAndGetPath(image);
        product.setImagePath(imagePath);
        Calendar todayDate = Calendar.getInstance();
        product.setDateOfCreation(todayDate);
        productRepository.save(product);
        setUpStockForProductAndSave(product);
    }

    public String saveProductImageAndGetPath(MultipartFile image){
        StringBuilder fileName = new StringBuilder();
        Path fileNameAnPath = Paths.get(UtilsFunctions.uploadImagesDirectory, image.getOriginalFilename());
        fileName.append(image.getOriginalFilename());

        String locationAnName = UtilsFunctions.thymeleafFriendlyPath + fileName;

        try {
            Files.write(fileNameAnPath, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return locationAnName;
    }

    @Override
    public void deleteProduct(Long id) {
        boolean exists = productRepository.findById(id).isPresent();
        if (!exists) {
            throw new IllegalStateException("This product wasn't found in DB");
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getNewProducts(int amount) {
        return productRepository.findLastProductCreated(amount);
    }

    public void setUpStockForProductAndSave(Product product) {
        String neededCategorySizing = product.getTypeProduct().getCategory().getSizingType();
        List<Stock> newStockList= new ArrayList<>();
        List<String> sizingList = new ArrayList<>();
        if(neededCategorySizing.equals("Clothing")){
            sizingList = Arrays.asList(UtilsFunctions.clothingSize);
        }
        if(neededCategorySizing.equals("Shoes")){
            sizingList = Arrays.asList(UtilsFunctions.shoeSizing);
        }

        if(neededCategorySizing.equals("None")) {
            sizingList = Arrays.asList(UtilsFunctions.noSizing);
        }

        for (String ls : sizingList) {
            Stock stock = new Stock();
            stock.setProduct(product);
            stock.setNumberItemLeft(5);
            stock.setSizeName(ls);
            newStockList.add(stock);
            stockRepository.save(stock);
        }
        product.setStocks(newStockList);
    }
}
