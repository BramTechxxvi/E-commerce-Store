package org.bram.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bram.data.models.Seller;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddProductRequest {

    @NotBlank(message = "Enter product description")
    private String description;
    @NotBlank(message = "Enter product name")
    private String productName;
    @NotBlank(message = "Enter product price")
    private double price;
    @NotBlank(message = "Enter category")
    private String productCategory;
    @NotBlank(message = "Enter quantity")
    @Min(value= 1, message= "Enter valid product quantity")
    private int productQuantity;
    @NotNull(message="Image file is required")
    private MultipartFile image;
    @DBRef
    private Seller seller;

}
