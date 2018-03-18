package com.github.bh.aconf.filter.operator;

import org.springframework.stereotype.Component;

/**
 * long型小于等于比较符。
 *
 * @author xiaobenhai
 * Date: 2017/3/5
 * Time: 11:43
 */
@Component("longLeOperator")
public class LongLeOperator extends AbstractOperator<Long, Long> {

    @Override
    public boolean op(Long actual, Long expect) {
        if (actual == null || expect == null) {
            return false;
        }
        return actual.longValue() <= expect.longValue();
    }

    @Override
    public String getName() {
        return "long-less-than-or-equals-operator";
    }

    @Override
    public String getAlias() {
        return "小于等于";
    }

    @Override
    public String getSymbol() {
        return "<=";
    }

}
