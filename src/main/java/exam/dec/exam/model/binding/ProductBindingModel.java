package exam.dec.exam.model.binding;

import exam.dec.exam.model.entity.enums.Category;
import exam.dec.exam.model.entity.enums.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductBindingModel {

    @NonNull
    @NotBlank
    private String name;

    @NonNull
    @NotBlank
    private String description;

    @NonNull
    @NotBlank
    private BigDecimal price;

    @NonNull
    @NotBlank
    private Category category;

    @NonNull
    @NotBlank
    private Sex sex;

    private String userId;
}

