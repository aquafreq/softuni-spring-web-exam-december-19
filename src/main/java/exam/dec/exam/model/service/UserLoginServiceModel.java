package exam.dec.exam.model.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserLoginServiceModel {
    private String id;
    private String password;
    private String username;
}
