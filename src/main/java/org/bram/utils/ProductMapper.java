package org.bram.utils;

import org.bram.data.models.Product;
import org.bram.data.models.ProductCategory;
import org.bram.data.models.Seller;
import org.bram.dtos.request.AddProductRequest;
import org.bram.dtos.request.UpdateProductRequest;
import org.bram.exceptions.InvalidProductCategory;

public class ProductMapper {

    public static Product mapToProduct(AddProductRequest request, Seller seller, String imageUrl) {
        Product product = new Product();
        product.setProductName(request.getProductName().trim());
        product.setDescription(request.getDescription());
        product.setProductQuantity(request.getProductQuantity());
        product.setPrice(request.getPrice());

        try {
            product.setCategory(ProductCategory.valueOf(request.getProductCategory().toUpperCase()));
        } catch (InvalidProductCategory e) {
            throw new InvalidProductCategory("Invalid product category" + request.getProductCategory());
        }
        product.setImageUrl(imageUrl);
        product.setSeller(seller);
        return product;
    }

    public static Product updateProductMapper(Product product, UpdateProductRequest request) {
        if(request.getProductName() != null) product.setProductName(request.getProductName());
        if(request.getDescription() != null) product.setDescription(request.getDescription());
        if(request.getPrice() > 0) product.setPrice(request.getPrice());
        if(request.getProductQuantity() >=0) product.setProductQuantity(request.getProductQuantity());
        if(request.getCategory() != null) {
            try {
                product.setCategory(ProductCategory.valueOf(request.getCategory().toUpperCase()));
            } catch (InvalidProductCategory e) {
                throw new InvalidProductCategory("Invalid product category" + request.getCategory());
            }}
        return product;
    }
}

