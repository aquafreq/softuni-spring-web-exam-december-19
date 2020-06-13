package exam.dec.exam.service;

import exam.dec.exam.model.entity.User;
import exam.dec.exam.model.service.LoggedUserModel;
import exam.dec.exam.model.service.UserLoginServiceModel;
import exam.dec.exam.model.service.UserRegisterServiceModel;

import java.util.Optional;

public interface UserService {
    UserRegisterServiceModel registerUser(UserRegisterServiceModel serviceModel);

    Optional<LoggedUserModel> logUser(UserLoginServiceModel loginServiceModel);

    User getUserById(String id);
}
