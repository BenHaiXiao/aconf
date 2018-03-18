package com.github.bh.aconf.filter.operator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author xiaobenhai
 * Date: 2017/4/1
 * Time: 15:49
 */
@Component("stringRegexOperator")
public class StringRegexOperator extends AbstractOperator<String, String> {
    @Override
    public boolean op(String actual, String expect) {
        if (StringUtils.isAnyEmpty(actual, expect)) {
            return false;
        }
        return Pattern.matches(expect, actual);
    }

    @Override
    public String getName() {
        return "string-regex-operator";
    }

    @Override
    public String getAlias() {
        return "正则匹配";
    }

    @Override
    public String getSymbol() {
        return "match";
    }
}
