package exam.dec.exam.config;

import exam.dec.exam.web.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfiguration implements WebMvcConfigurer {
    private final AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    public InterceptorsConfiguration(AuthorizationInterceptor authorizationInterceptor) {
        this.authorizationInterceptor = authorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(authorizationInterceptor)
                .addPathPatterns("/users/login", "/", "/users/register", "/css/*", "/img/**");
//                .addPathPatterns("/home", "/add-product", "/details-product", "/logout", "/css/*", "/img/**");
    }
}
