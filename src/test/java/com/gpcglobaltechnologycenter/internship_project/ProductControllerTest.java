package com.gpcglobaltechnologycenter.internship_project;

import com.gpcglobaltechnologycenter.internship_project.controller.ProductController;
import com.gpcglobaltechnologycenter.internship_project.exception.ProductNotFoundException;
import com.gpcglobaltechnologycenter.internship_project.model.Product;
import com.gpcglobaltechnologycenter.internship_project.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadProducts() throws IOException, JAXBException {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test-file.xml", "text/xml", "<products></products>".getBytes());
        when(productService.readProductsFromFile(mockFile)).thenReturn(10);
        ResponseEntity<String> response = productController.loadProducts(mockFile);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Number of records found in file 10", response.getBody());
    }

    @Test
    void testLoadProducts_EmptyFile() throws IOException, JAXBException {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test-file.xml", "text/xml", new byte[0]);
        when(productService.readProductsFromFile(mockFile)).thenThrow(new IOException("Uploaded file is empty."));
        assertThrows(IOException.class, () -> {
            productController.loadProducts(mockFile);
        });
    }

    @Test
    void testLoadProducts_JAXBException() throws IOException, JAXBException {
        MockMultipartFile mockFile = new MockMultipartFile("file", "invalid-file.xml", "text/xml", "<invalid></invalid>".getBytes());
        when(productService.readProductsFromFile(mockFile)).thenThrow(new JAXBException("Invalid XML"));
        assertThrows(JAXBException.class, () -> {
            productController.loadProducts(mockFile);
        });
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(
                new Product(1, "Product1", "Category1", "PN1", "Company1", true),
                new Product(2, "Product2", "Category2", "PN2", "Company2", false)
        );
        when(productService.getAllProducts()).thenReturn(products);
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    void testGetProductByName() {
        String productName = "Product1";
        Product product = new Product(1, "Product1", "Category1", "PN1", "Company1", true);
        when(productService.getProductByName(productName)).thenReturn(product);
        ResponseEntity<Product> response = productController.getProductByName(productName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testGetProductByName_NotFound() {
        String productName = "NonExistentProduct";
        when(productService.getProductByName(productName)).thenThrow(new ProductNotFoundException("Product not found"));
        assertThrows(ProductNotFoundException.class, () -> {
            productController.getProductByName(productName);
        });
    }
}
