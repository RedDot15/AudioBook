package org.example.audiobook.dto.request.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryItem {
    @NotEmpty(message = "category_name cannot be empty")
    private String category_name;

    @NotNull(message = "category_id cannot be null")
    private UUID category_id;
}
