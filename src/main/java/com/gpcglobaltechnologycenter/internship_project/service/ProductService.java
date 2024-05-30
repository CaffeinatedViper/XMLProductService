package com.gpcglobaltechnologycenter.internship_project.service;

import com.gpcglobaltechnologycenter.internship_project.controller.ProductController;
import com.gpcglobaltechnologycenter.internship_project.exception.ProductNotFoundException;
import com.gpcglobaltechnologycenter.internship_project.exception.ProductsNotLoadedException;
import com.gpcglobaltechnologycenter.internship_project.model.Product;
import com.gpcglobaltechnologycenter.internship_project.model.Products;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ProductService {

    private Products products;

    public int readProductsFromFile(MultipartFile file) throws IOException, JAXBException {
        if (file.isEmpty()) {
            throw new IOException("Uploaded file is empty.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            JAXBContext context = JAXBContext.newInstance(Products.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            this.products = (Products) unmarshaller.unmarshal(inputStream);
            return products.getProducts().size();
        } catch (JAXBException e) {
            throw new JAXBException("Failed to load products from file: " + e.getMessage(), e);
        }
    }


    public List<Product> getAllProducts() {
        ensureProductsLoaded();
        return products.getProducts();
    }

    public Product getProductByName(String productName) {
        ensureProductsLoaded();
        return products.getProducts().stream()
                .filter(product -> product.getName().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found with name: " + productName));
    }
    private void ensureProductsLoaded() {
        if (products == null) {
            throw new ProductsNotLoadedException("XML file with products must be loaded first");
        }
    }


}
