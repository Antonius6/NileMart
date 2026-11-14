package org.route.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Size(min =2 , max = 50 , message = "category name must be between 2 and 50 char")
    @NotBlank(message = "category name is required")
    private String name;
    @NotBlank(message = "slug can not be empty ")
    @Pattern(regexp = "^[a-z0-9-]+$",message="slug must be matches [a-z0-9-]")
    private String slug;
    private String parentSlug;
    @Min(value =0 , message="DisplayOrder must be 0 or greater")
    private int displayOrder;
    @Builder.Default
    private boolean active = true;
}
