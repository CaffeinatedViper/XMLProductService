package com.gpcglobaltechnologycenter.internship_project;

import com.gpcglobaltechnologycenter.internship_project.exception.ProductNotFoundException;
import com.gpcglobaltechnologycenter.internship_project.exception.ProductsNotLoadedException;
import com.gpcglobaltechnologycenter.internship_project.model.Product;
import com.gpcglobaltechnologycenter.internship_project.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;
import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    private MultipartFile validFile;
    private MultipartFile invalidFile;

    @BeforeEach
    public void setUp() throws IOException {
        FileInputStream validFileInputStream = new FileInputStream("src/test/resources/products.xml");
        validFile = new MockMultipartFile("file", "products.xml", "text/xml", validFileInputStream);

        FileInputStream invalidFileInputStream = new FileInputStream("src/test/resources/invalid_structure.xml");
        invalidFile = new MockMultipartFile("file", "invalid_structure.xml", "text/xml", invalidFileInputStream);
    }

    @Test
    public void testReadProductsFromFile_Success() throws IOException, JAXBException {
        int count = productService.readProductsFromFile(validFile);
        assertEquals(3, count, "The number of products should be 3");
    }

    @Test
    public void testReadProductsFromFile_FileNotFound() {
        MultipartFile emptyFile = new MockMultipartFile("file", "empty.xml", "text/xml", new byte[0]);
        assertThrows(IOException.class, () -> {
            productService.readProductsFromFile(emptyFile);
        });
    }

    @Test
    public void testReadProductsFromFile_InvalidStructure() {
        assertThrows(JAXBException.class, () -> {
            productService.readProductsFromFile(invalidFile);
        });
    }

    @Test
    public void testGetAllProducts_Success() throws IOException, JAXBException {
        productService.readProductsFromFile(validFile);
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
    public void testGetProductByName_Success() throws IOException, JAXBException {
        productService.readProductsFromFile(validFile);
        Product product = productService.getProductByName("apple");
        assertEquals("apple", product.getName(), "The name of the product should be 'apple'");
    }

    @Test
    public void testGetProductByName_NotFound() throws IOException, JAXBException {
        productService.readProductsFromFile(validFile);
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductByName("nonexistent");
        });
    }
}
