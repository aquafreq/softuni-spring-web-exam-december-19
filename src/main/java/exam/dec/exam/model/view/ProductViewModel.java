package exam.dec.exam.model.view;

import exam.dec.exam.model.entity.enums.Category;
import exam.dec.exam.model.entity.enums.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductViewModel {
    private String id;
    private String name;
    private BigDecimal price;
    private String description;
    private Category category;
    private Sex sex;
    private String userId;
}
