package com.mycompany.controller;

import com.mycompany.converter.ProductConverter;
import com.mycompany.dao.ProductDAO;
import com.mycompany.domain.Product;
import com.mycompany.dto.ProductDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static com.mycompany.converter.ProductConverter.domainToDTOSet;
import static com.mycompany.converter.ProductConverter.dtoToDomain;

/**
 * @author Anton Fomin
 */
@RestController
@RequestMapping("/products")
public class Controller {

    ProductDAO productDAO = new ProductDAO();

    // Get all products
    @RequestMapping(method = RequestMethod.GET)
    public Set<ProductDTO> getAllProducts() throws Exception {
        return domainToDTOSet(productDAO.getAll());
    }

    // Get product by Id
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ProductDTO getProduct(@PathVariable Integer productId) throws Exception {
        return ProductConverter.domainToDTO(productDAO.get(productId));
    }

    // Create a new product
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO) throws Exception {
        Product product = dtoToDomain(productDTO);
        productDAO.save(product);
        return ProductConverter.domainToDTO(product);
    }

    // Update a product
    @RequestMapping(value = "/{productId}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable int productId, @RequestBody ProductDTO productDTO) throws Exception {
        if (productDAO.get(productId) != null && productDTO.getId() == productId) {
            Product product = dtoToDomain(productDTO);
            productDAO.save(product);
        } else {
            throw new Exception("Product with Id '" + productId + "' doesn't exist, and therefore can't be updated.");
        }
    }

    // Partially update a product
    @RequestMapping(value = "/{productId}", method = RequestMethod.PATCH)
    public void partiallyUpdateProduct(@PathVariable int productId, @RequestBody ProductDTO productDTO) throws Exception {

    }

    // Delete a product
    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable int productId) throws  Exception {
        productDAO.delete(productId);
    }
}
