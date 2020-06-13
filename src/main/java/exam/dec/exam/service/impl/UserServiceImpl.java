package exam.dec.exam.service.impl;

import exam.dec.exam.model.entity.User;
import exam.dec.exam.model.service.LoggedUserModel;
import exam.dec.exam.model.service.UserLoginServiceModel;
import exam.dec.exam.model.service.UserRegisterServiceModel;
import exam.dec.exam.repository.UserRepository;
import exam.dec.exam.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserRegisterServiceModel registerUser(UserRegisterServiceModel serviceModel) {
        Optional<User> optionalUser = userRepository.getByUsername(serviceModel.getUsername());

        if (optionalUser.isPresent()) {
            throw new EntityExistsException();
        }

        serviceModel.setPassword(encodePassword(serviceModel.getPassword()));

        User repoUser = userRepository.save(modelMapper.map(serviceModel, User.class));

        return modelMapper.map(repoUser, UserRegisterServiceModel.class);
    }

    @Override
    public Optional<LoggedUserModel> logUser(UserLoginServiceModel serviceModel) {
        Optional<User> optionalUser = userRepository.getByUsername(serviceModel.getUsername());
        final boolean[] suchUserExists = {false};
        optionalUser.ifPresent(user ->
                suchUserExists[0] = passwordEncoder.matches(serviceModel.getPassword(), user.getPassword()));

        return suchUserExists[0] ?
                Optional.of(modelMapper.map(optionalUser.get(), LoggedUserModel.class)) : Optional.empty();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.getOne(id);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
