package com.github.bh.aconf.filter.operator;

import com.google.common.collect.Range;
import org.springframework.stereotype.Component;

/**
 * long型的范围比较符。
 *
 * @author xiaobenhai
 * Date: 2017/3/5
 * Time: 15:43
 */
@Component("longInOperator")
public class LongInOperator extends AbstractOperator<Long, Range> {

    @Override
    public boolean op(Long actual, Range expect) {
        if (actual == null || expect == null) {
            return false;
        }
        long value = actual.longValue();
        return expect.contains(value);
    }

    @Override
    public String getName() {
        return "long-in-operator";
    }

    @Override
    public String getAlias() {
        return "范围";
    }

    @Override
    public String getSymbol() {
        return "in";
    }

}
