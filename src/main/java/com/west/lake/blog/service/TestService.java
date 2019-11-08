package com.west.lake.blog.service;

import com.west.lake.blog.model.entity.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * @author futao
 * Created on 2019-04-12.
 */
@Validated
public interface TestService {
    User save(int i);

    void insertDb();

    void param(@NotEmpty(message = "not ") String p1, String p2);
}
