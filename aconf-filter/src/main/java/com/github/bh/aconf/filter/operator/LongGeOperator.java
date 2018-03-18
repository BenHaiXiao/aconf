package com.github.bh.aconf.filter.operator;

import org.springframework.stereotype.Component;

/**
 * long型大于等于比较符。
 *
 * @author xiaobenhai
 * Date: 2017/3/8
 * Time: 15:43
 */
@Component("longGeOperator")
public class LongGeOperator extends AbstractOperator<Long, Long> {

    @Override
    public boolean op(Long actual, Long expect) {
        if (actual == null || expect == null) {
            return false;
        }
        return actual.longValue() >= expect.longValue();
    }

    @Override
    public String getName() {
        return "long-greater-than-or-equals-operator";
    }

    @Override
    public String getAlias() {
        return "大于等于";
    }

    @Override
    public String getSymbol() {
        return ">=";
    }

}
