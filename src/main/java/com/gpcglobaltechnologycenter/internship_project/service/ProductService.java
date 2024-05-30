package com.gpcglobaltechnologycenter.internship_project.service;

import com.gpcglobaltechnologycenter.internship_project.controller.ProductController;
import com.gpcglobaltechnologycenter.internship_project.exception.ProductNotFoundException;
import com.gpcglobaltechnologycenter.internship_project.exception.ProductsNotLoadedException;
import com.gpcglobaltechnologycenter.internship_project.model.Product;
import com.gpcglobaltechnologycenter.internship_project.model.Products;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public class ProductService {

    private Products products;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public int readProductsFromFile(String filePath) throws FileNotFoundException, JAXBException {
        File file = new File(filePath);
        if (!file.exists()) {
            logger.error("File not found: " + filePath);
            throw new FileNotFoundException("File not found at path: " + filePath);
        }

        try {
            JAXBContext context = JAXBContext.newInstance(Products.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            this.products = (Products) unmarshaller.unmarshal(file);
            return products.getProducts().size();
        } catch (JAXBException e) {
            logger.error("Failed to load products from file: " + filePath, e);
            throw new JAXBException("Failed to load products from file: " + filePath + e, e);
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
