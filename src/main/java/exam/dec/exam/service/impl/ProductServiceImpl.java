package exam.dec.exam.service.impl;

import exam.dec.exam.model.entity.Product;
import exam.dec.exam.model.entity.User;
import exam.dec.exam.model.service.ProductServiceModel;
import exam.dec.exam.repository.ProductRepository;
import exam.dec.exam.service.ProductService;
import exam.dec.exam.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public void saveProduct(ProductServiceModel productServiceModel) {
        Product product = modelMapper.map(productServiceModel, Product.class);
        User user = userService.getUserById(productServiceModel.getUserId());
        product.setUser(user);
//        userService.addProduct();
        productRepository.save(product);
    }

    @Override
    public List<ProductServiceModel> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(p -> modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel getProductById(String id) {
        Product one = productRepository.getOne(id);
//        Product one = productRepository.findById(id);
        return modelMapper.map(one, ProductServiceModel.class);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
