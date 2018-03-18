package com.github.bh.aconf.filter.operator;

import org.springframework.stereotype.Component;

/**
 * long型大于比较符。
 *
 * @author xiaobenhai
 * Date: 2017/3/5
 * Time: 15:43
 */
@Component("longGtOperator")
public class LongGtOperator extends AbstractOperator<Long, Long> {

    @Override
    public boolean op(Long actual, Long expect) {
        if (actual == null || expect == null) {
            return false;
        }
        return actual.longValue() > expect.longValue();
    }

    @Override
    public String getName() {
        return "long-greater-than-operator";
    }

    @Override
    public String getAlias() {
        return "大于";
    }

    @Override
    public String getSymbol() {
        return ">";
    }

}
