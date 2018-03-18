package com.github.bh.aconf.filter.operator;

import org.springframework.stereotype.Component;

/**
 * long型相等比较符。
 *
 * @author xiaobenhai
 * Date: 2017/3/8
 * Time: 16:40
 */
@Component("longEqualsOperator")
public class LongEqualsOperator extends AbstractOperator<Long, Long> {

    @Override
    public boolean op(Long actual, Long expect) {
        if (actual == null || expect == null) {
            return false;
        }
        return actual.longValue() == expect.longValue();
    }

    @Override
    public String getName() {
        return "long-equals-operator";
    }

    @Override
    public String getAlias() {
        return "数值相等";
    }

    @Override
    public String getSymbol() {
        return "==";
    }

}
