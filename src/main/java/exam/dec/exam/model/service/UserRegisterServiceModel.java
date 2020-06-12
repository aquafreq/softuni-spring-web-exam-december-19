package exam.dec.exam.model.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRegisterServiceModel {
    private String username;
    private String password;
    private String email;
}
