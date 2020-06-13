package exam.dec.exam.model.binding;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@ScriptAssert(lang = "javascript",
        script = "_this.confirmPassword !== null &&_this.password === _this.confirmPassword",
        message = "passwords do not match", reportOn = "confirmPassword")
public class UserRegisterBindingModel {

    @NotBlank(message = "not blank for username annotation op")
    @NonNull
    @Length(min = 3, max = 15, message = "plz longer name")
    private String username;

    @NotBlank(message = "not null or empty errorz for pass")
    @NonNull()
    @Length(min = 2, message = "plz longer pass")
    private String password;

    private String confirmPassword;

    @Email(message = "wrong email format,gg")
    @NotBlank(message = "нот бланк он емайл")
    @NonNull
    private String email;
}
