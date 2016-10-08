package com.mycompany.converter;

import com.mycompany.domain.Product;
import com.mycompany.dto.ProductDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Anton Fomin
 */
public class ProductConverter {

    public static ProductDTO domainToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        return productDTO;
    }

    public static Set<ProductDTO> domainToDTOSet(Set<Product> products) {
        Set<ProductDTO> productDTOs = new HashSet<ProductDTO>();
        for (Product product : products) {
            productDTOs.add(domainToDTO(product));
        }
        return productDTOs;
    }

    public static Product dtoToDomain(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        return product;
    }


}
