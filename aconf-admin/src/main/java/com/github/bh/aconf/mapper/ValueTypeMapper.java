package com.github.bh.aconf.mapper;

import com.github.bh.aconf.common.constants.ValueType;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaobenhai
 * Date: 2017/2/16
 * Time: 15:00
 */
@Mapper
public class ValueTypeMapper {
    @Type
    public Byte getValueType(String value) {
        if (StringUtils.isEmpty(value)) {
            return ValueType.VALUE.getValue();
        }
        value = value.trim();
        if (value.endsWith(";")) {
            value = StringUtils.substring(value, 0, -1);
        }
        if (value.startsWith("expression(") && value.endsWith(")")) {
            return ValueType.EXPRESSION.getValue();
        }
        return ValueType.VALUE.getValue();
    }

    @Value
    public String getValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        value = value.trim();
        if (value.endsWith(";")) {
            value = StringUtils.substring(value, 0, -1);
        }
        if (value.startsWith("expression(") && value.endsWith(")")) {
            return StringUtils.substring(value, 11, -1);
        }
        return value;
    }

    @Qualifier
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface TypeTranslator {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Qualifier
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface ValueTranslator {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Value {
    }
}
