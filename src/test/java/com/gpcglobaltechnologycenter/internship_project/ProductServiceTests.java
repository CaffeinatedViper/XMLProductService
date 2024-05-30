package com.gpcglobaltechnologycenter.internship_project;

import com.gpcglobaltechnologycenter.internship_project.exception.ProductNotFoundException;
import com.gpcglobaltechnologycenter.internship_project.exception.ProductsNotLoadedException;
import com.gpcglobaltechnologycenter.internship_project.model.Product;
import com.gpcglobaltechnologycenter.internship_project.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.xml.bind.JAXBException;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductServiceTests {


    @Autowired
    private ProductService productService;

    private String validFilePath;
    private String invalidFilePath;

    @BeforeEach
    public void setUp() {
        validFilePath = "src/test/resources/products.xml";
        invalidFilePath = "src/test/resources/invalid_products.xml";

    }

    @Test
    public void testReadProductsFromFile_Success() throws FileNotFoundException, JAXBException {
        int count = productService.readProductsFromFile(validFilePath);
        assertEquals(3, count, "The number of products should be 3");
    }

    @Test
    public void testReadProductsFromFile_FileNotFound() {
        assertThrows(FileNotFoundException.class, () -> {
            productService.readProductsFromFile(invalidFilePath);
        });
    }

    @Test
    public void testGetAllProducts_Success() throws FileNotFoundException, JAXBException {
        productService.readProductsFromFile(validFilePath);
        List<Product> products = productService.getAllProducts();
        assertEquals(3, products.size(), "The number of products should be 3");

        Product firstProduct = products.get(0);
        assertEquals("apple", firstProduct.getName(), "The name of the first product should be 'apple'");
    }

    @Test
    public void testGetAllProducts_ProductsNotLoaded() {
        assertThrows(ProductsNotLoadedException.class, () -> {
            productService.getAllProducts();
        });
    }

    @Test
    public void testGetProductByName_Success() throws FileNotFoundException, JAXBException {
        productService.readProductsFromFile(validFilePath);
        Product product = productService.getProductByName("apple");
        assertEquals("apple", product.getName(), "The name of the product should be 'apple'");
    }

    @Test
    public void testGetProductByName_NotFound() throws FileNotFoundException, JAXBException {
        productService.readProductsFromFile(validFilePath);
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductByName("nonexistent");
        });
    }
}
