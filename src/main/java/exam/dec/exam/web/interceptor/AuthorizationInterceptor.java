package exam.dec.exam.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
//    private final Pattern pattern = Pattern.compile("home|logout|product");
//    private final List<String> keyWordURLPathsForLoggedUser = new ArrayList<>() {{
//        add("home");
//        add("logout");
//        add("product");
//    }};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        boolean isLogged = Arrays.stream(request.getCookies()).anyMatch(c -> c.getName().equals("username"));
        boolean isLogged = request.getSession().getAttribute("username") != null;

        if (isLogged) {
//            Matcher matcher = pattern.matcher(request.getRequestURI());

//            if (matcher.find()) {
                return true;
//            }

//            String path = request.getSession().getAttribute("lastPath").toString();
//            response.sendRedirect(path);
//            return false;
        }

        response.sendRedirect("/users/login");
        return false;
    }
}
