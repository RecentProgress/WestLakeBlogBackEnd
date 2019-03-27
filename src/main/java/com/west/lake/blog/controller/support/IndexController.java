package com.west.lake.blog.controller.support;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author futao
 * Created on 2019-03-25.
 */
@Controller
@ApiIgnore
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html#");
    }
}
