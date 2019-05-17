package com.west.lake.blog.annotation.impl;

import com.lazyer.foundation.foundation.exception.ApplicationException;
import com.west.lake.blog.annotation.EnumStatus;
import com.west.lake.blog.model.entity.enums.face.IEnum;
import com.west.lake.blog.tools.I18nTools;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 枚举值合法性验证实现部分
 *
 * @author futao
 * Created on 2019-02-22.
 */
public class EnumStatusImpl implements ConstraintValidator<EnumStatus, Object> {

    /**
     * 使用过的枚举缓存
     */
    private static HashMap<Class, String[]> cache = new HashMap<>();

    /**
     * 当前枚举状态集合
     */
    private String[] currentEnumStatus;


    @SuppressWarnings("unchecked")
    @Override
    public void initialize(EnumStatus constraintAnnotation) {
        Class enumClass = constraintAnnotation.value();
        if (cache.containsKey(enumClass)) {
            currentEnumStatus = cache.get(enumClass);
        } else {
            try {
                Method method = enumClass.getMethod(IEnum.class.getMethods()[0].getName());
                Object[] enumConstants = enumClass.getEnumConstants();
                String[] enumStatusArray = new String[enumConstants.length];
                cache.put(enumClass, enumStatusArray);
                for (int i = 0; i < enumConstants.length; i++) {
                    enumStatusArray[i] = method.invoke(enumConstants[i]).toString();
                }
                currentEnumStatus = cache.get(enumClass);
            } catch (Exception e) {
                throw ApplicationException.ae(I18nTools.getMessage("system.enum.must.impl.ienum"));
            }
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return Arrays.asList(currentEnumStatus).contains(value.toString());
    }
}
