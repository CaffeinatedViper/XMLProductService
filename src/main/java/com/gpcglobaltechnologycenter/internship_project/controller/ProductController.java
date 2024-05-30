package com.gpcglobaltechnologycenter.internship_project.controller;
import com.gpcglobaltechnologycenter.internship_project.model.Product;
import com.gpcglobaltechnologycenter.internship_project.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "Upload XML file and load products", description = "Reads the XML file from the given file path and returns the number of records found in the file.")
    public ResponseEntity<String> loadProducts(
            @RequestPart("file") MultipartFile file) throws IOException, JAXBException {
        int numberOfProducts = productService.readProductsFromFile(file);
        return ResponseEntity.ok("Number of records found in file " + numberOfProducts);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products in JSON format")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    @Operation(summary = "Get product by name", description = "Returns a product by the given name in JSON format")
    public ResponseEntity<Product> getProductByName(@RequestParam String name) {
        Product product = productService.getProductByName(name);
        return ResponseEntity.ok(product);
    }


}
