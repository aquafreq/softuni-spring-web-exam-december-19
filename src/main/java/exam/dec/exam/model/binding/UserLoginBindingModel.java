package exam.dec.exam.model.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserLoginBindingModel {
    private String username;
    private String password;
}
