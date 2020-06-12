package exam.dec.exam.model.entity;

import exam.dec.exam.annotations.AllowedValues;
import exam.dec.exam.model.entity.enums.Category;
import exam.dec.exam.model.entity.enums.Sex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;

//    @AllowedValues(expectedValues = {"Shirt", "Denim", "Shorts ", "Jacket"}, message = "Invalid values")
//    private String category;

//    @AllowedValues(expectedValues = {"Female", "Male"}, message ="inv vals lol....")
//    private String sex;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
