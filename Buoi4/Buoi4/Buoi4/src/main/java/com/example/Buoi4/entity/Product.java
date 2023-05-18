package com.example.Buoi4.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Product {
    private int id;

    @NotBlank(message = "not empty!!!")
    private String name;
    @Length(min=0, max=50, message = "Teen khong qua 50 ky tu")
    private String image;
    @NotNull(message = "Gias ko trong")
    @Min(value = 1, message = "Gia khong nho hon1")
    @Max(value = 999, message = "Gia khong lon hon999")

    private long price;
}
