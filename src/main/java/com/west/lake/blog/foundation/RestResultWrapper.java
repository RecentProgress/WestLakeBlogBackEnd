package com.west.lake.blog.foundation;

import com.west.lake.blog.annotation.RestSkip;
import com.west.lake.blog.model.RestResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger2.web.Swagger2Controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 返回Rest风格的数据
 *
 * @author futao
 * Created on 2018/9/22-20:24.
 */
@RestControllerAdvice
public class RestResultWrapper implements ResponseBodyAdvice<Object> {


    /**
     * 不需要拦截的类路径，这里写的是Class
     * 如果该类所在项目没有相关的依赖，可以换成String-类的全路径
     */
    private static final List<Class<?>> SKIP_CLASS_LIST = new ArrayList<>(2);

    static {
        //Swagger
        SKIP_CLASS_LIST.add(ApiResourceController.class);
        //Swagger
        SKIP_CLASS_LIST.add(Swagger2Controller.class);
    }

    /**
     * 可指定针对某些返回值的类型才进行rest风格的封装
     *
     * @param returnType    返回值类型
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (SKIP_CLASS_LIST.contains(returnType.getDeclaringClass())) {
            return false;
        }
        Method returnTypeMethod = returnType.getMethod();
        if (returnTypeMethod != null) {
            return !returnTypeMethod.isAnnotationPresent(RestSkip.class);
        }
        return true;
    }

    /**
     * 封装正常返回的数据
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (MediaType.IMAGE_JPEG.getType().equalsIgnoreCase(selectedContentType.getType())) {
            return body;
        }
        return new RestResult(true, RestResult.SUCCESS_CODE, body, null);
    }
}
