package com.github.bh.aconf.filter.operator;

import org.springframework.stereotype.Component;

/**
 * long型不相等比较符。
 *
 * @author xiaobenhai
 * Date: 2017/3/5
 * Time: 11:59
 */
@Component("longNotEqualsOperator")
public class LongNotEqualsOperator extends AbstractOperator<Long, Long> {

    @Override
    public boolean op(Long actual, Long expect) {
        if (actual == null || expect == null) {
            return false;
        }
        return actual.longValue() != expect.longValue();
    }

    @Override
    public String getAlias() {
        return "不等于";
    }

    @Override
    public String getName() {
        return "long-not-equals-operator";
    }

    @Override
    public String getSymbol() {
        return "!=";
    }

}
