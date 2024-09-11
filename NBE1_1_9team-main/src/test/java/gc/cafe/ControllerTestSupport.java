package gc.cafe;

import com.fasterxml.jackson.databind.ObjectMapper;
import gc.cafe.api.controller.order.OrderController;
import gc.cafe.api.controller.product.ProductController;
import gc.cafe.api.service.order.OrderService;
import gc.cafe.api.service.product.ProductService;
import gc.cafe.api.service.user.UserService;
import gc.cafe.config.SecurityConfig;
import gc.cafe.global.auth.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = {
    ProductController.class,
    OrderController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected JwtService jwtService;

}
