package gc.cafe;

import com.fasterxml.jackson.databind.ObjectMapper;
import gc.cafe.api.controller.order.OrderController;
import gc.cafe.api.controller.product.ProductController;
import gc.cafe.api.service.order.OrderService;
import gc.cafe.api.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
}
