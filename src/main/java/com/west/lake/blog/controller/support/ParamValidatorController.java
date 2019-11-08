package com.west.lake.blog.controller.support;

import com.west.lake.blog.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author futao
 * Created on 2019/11/7.
 */
@Validated
@RestController
@RequestMapping("/paramValidator")
public class ParamValidatorController {

    @Autowired
    private SmartValidator smartValidator;

    @Autowired
    private TestService testService;

    @GetMapping("/p1")
    public void param(
//            @NotNull(message = "不可为空")
            @RequestParam("p1") String p1,
//            @NotEmpty(message = "不可为空p2")
            @RequestParam("p2")
                    String p2
    ) throws NoSuchMethodException {
//        Validation.buildDefaultValidatorFactory().getValidator().forExecutables().validateParameters(
//                this.getClass(),
//                this.getClass().getMethod("param", String.class, String.class),
//                null, null);
        testService.param(p1, p2);
        System.out.println("p1 = " + p1 + ", p2 = " + p2);
    }
}
