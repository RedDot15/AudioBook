package org.example.audiobook.dto.request.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteCategoryRequest {
    @NotNull(message = "userId cannot be null")
    private UUID userId;

    @NotEmpty(message = "categories cannot be empty")
    private List<CategoryItem> categories;
}

