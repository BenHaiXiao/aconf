package com.github.bh.aconf.filter.operator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 字符串不相等比较符，大小写不敏感。
 *
 * @author xiaobenhai
 * Date: 2017/3/5
 * Time: 13:47
 */
@Component("stringNotEqualsOperator")
public class StringNotEqualsOperator extends AbstractOperator<String, String> {

    @Override
    public boolean op(String actual, String expect) {
        return !StringUtils.equalsIgnoreCase(actual, expect);
    }

    @Override
    public String getName() {
        return "string-not-equals-operator";
    }

    @Override
    public String getAlias() {
        return "不相等";
    }

    @Override
    public String getSymbol() {
        return "neq";
    }

}
