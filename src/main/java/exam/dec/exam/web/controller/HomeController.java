package exam.dec.exam.web.controller;

import exam.dec.exam.model.view.ProductViewModel;
import exam.dec.exam.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<ProductViewModel> viewModels = productService
                .getAllProducts()
                .stream()
                .map(p -> modelMapper.map(p, ProductViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("products", viewModels);
        return "home";
    }
}
