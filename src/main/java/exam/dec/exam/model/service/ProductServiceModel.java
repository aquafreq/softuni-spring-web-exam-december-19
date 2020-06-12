package exam.dec.exam.model.service;


import exam.dec.exam.model.entity.enums.Category;
import exam.dec.exam.model.entity.enums.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductServiceModel {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Category category;
    private Sex sex;
    private String userId;
}
