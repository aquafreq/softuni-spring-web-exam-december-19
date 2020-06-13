package exam.dec.exam.web.controller;

import exam.dec.exam.model.binding.ProductBindingModel;
import exam.dec.exam.model.service.ProductServiceModel;
import exam.dec.exam.model.view.ProductViewModel;
import exam.dec.exam.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    //da sa oprai
    @GetMapping("/add-product")
    public String getAddProduct(Model model) {
//        if (session.getAttribute("username") == null) {
//            attributes.addAttribute("redirectUrl", "/add-product");
//            return "redirect:/users/login";
//        } else
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", new ProductBindingModel());
        }

        return "add-product";
    }

    @PostMapping("/add-product")
    public String postAddProduct(@ModelAttribute ProductBindingModel productBindingModel, BindingResult result,
                                 RedirectAttributes attributes, HttpSession session) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute(productBindingModel);
            return "redirect:/add-product";
        }

        productBindingModel.setUserId(String.valueOf(session.getAttribute("userId")));
        ProductServiceModel productServiceModel = modelMapper.map(productBindingModel, ProductServiceModel.class);
        productService.saveProduct(productServiceModel);
        return "redirect:/home";
    }

    @GetMapping("/product/details/{id}")
    public String getDetailsProduct(@PathVariable String id, Model model) {
        ProductServiceModel productServiceModel = productService.getProductById(id);
        ProductViewModel productViewModel = modelMapper.map(productServiceModel, ProductViewModel.class);
        model.addAttribute("product", productViewModel);
        return "details-product";
    }

    @PostMapping("/details-product")
    public String postDetailsProduct() {
        return "redirect:/details-product";
    }

    @DeleteMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "redirect:/home";
    }
}
