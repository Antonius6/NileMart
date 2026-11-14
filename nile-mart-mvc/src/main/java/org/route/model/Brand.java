package org.route.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Size(min =2 , max = 50, message = "brand name must be between 2 and 50 char")
    @NotBlank(message = "brand name can not be empty")
    private  String name;
    @NotBlank(message = "slug name can not be empty")
    private   String slug;
    @Builder.Default
    private  boolean active = true;
}
