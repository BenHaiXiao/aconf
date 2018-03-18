package com.github.bh.aconf.filter;

import com.github.bh.aconf.filter.operator.Operator;
import com.github.bh.aconf.filter.operator.StringEqualsOperator;
import com.github.bh.aconf.filter.operator.StringRegexOperator;
import com.google.common.collect.Lists;
import com.github.bh.aconf.filter.operator.StringNotEqualsOperator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 字符串型过滤器。
 *
 * @author xiaobenhai
 * Date: 2017/3/5
 * Time: 10:49
 */
@Component
public abstract class AbstractStringFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractStringFilter.class);

    private static final String ALL_VALUE = "*";

    @Autowired
    private StringEqualsOperator stringEqualsOperator;
    @Autowired
    private StringNotEqualsOperator stringNotEqualsOperator;
    @Autowired
    private StringRegexOperator stringRegexOperator;

    protected boolean check(String actual, String operatorSymbol, String boundary) {
        if (stringEqualsOperator.isMe(operatorSymbol)) {
            return check(stringEqualsOperator, actual, BoundaryUtils.splitJson(boundary));
        }
        if (stringNotEqualsOperator.isMe(operatorSymbol)) {
            return check(stringNotEqualsOperator, actual, BoundaryUtils.splitJson(boundary));
        }
        if (stringRegexOperator.isMe(operatorSymbol)) {
            return check(stringRegexOperator, actual, BoundaryUtils.splitJson(boundary));
        }
        LOGGER.warn("there are not operators for : {}", operatorSymbol);
        return true; // 没有匹配到运算符
    }

    private boolean check(Operator operator, String actual, List<String> expects) {
        if (CollectionUtils.isEmpty(expects)) {
            return true; // 没有过滤条件，返回true！
        }
        LOGGER.debug("check by operator : {}", operator.getName());
        for (String expect : expects) {
            if (StringUtils.equals(ALL_VALUE, expect)) {
                return true; // 全局通用匹配！
            }
            boolean pass = operator.op(actual, expect);
            LOGGER.debug("check result : {} {} {} ---> {}", actual, operator.getSymbol(), expect, pass);
            if (pass) {
                return true; // 短路，只要符合一个条件即可
            }
        }
        return false; // 所有条件都不符合！
    }

    @Override
    public List<? extends Operator> getSupportOperators() {
        return Lists.newArrayList(stringEqualsOperator, stringNotEqualsOperator, stringRegexOperator);
    }
}
