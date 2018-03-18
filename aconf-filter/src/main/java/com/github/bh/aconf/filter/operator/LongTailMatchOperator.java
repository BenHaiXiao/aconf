package com.github.bh.aconf.filter.operator;

import org.springframework.stereotype.Component;

/**
 * 尾号匹配比较符。
 *
 * @author xiaobenhai
 * Date: 2017/3/5
 * Time: 12:59
 */
@Component("longTailMatchOperator")
public class LongTailMatchOperator extends AbstractOperator<Long, Long> {

    @Override
    public boolean op(Long actual, Long expect) {
        if (actual == null || expect == null || actual.longValue() <= 0) {
            return false;
        }
        return (actual.longValue() % 10) == expect.longValue();
    }

    @Override
    public String getName() {
        return "long-tail-match-operator";
    }

    @Override
    public String getAlias() {
        return "结尾";
    }

    @Override
    public String getSymbol() {
        return "$n";
    }

}
