package com.github.bh.aconf.filter.operator;

import org.springframework.stereotype.Component;

/**
 * 永远返回true的比较符。
 *
 * @author xiaobenhai
 * Date: 2017/4/1
 * Time: 15:49
 */
@Component("trueOperator")
public class TrueOperator extends AbstractOperator<Void, Void> {

    @Override
    public boolean op(Void actual, Void expect) {
        // always true
        return true;
    }

    @Override
    public String getName() {
        return "true-operator";
    }

    @Override
    public String getAlias() {
        return "True";
    }

    @Override
    public String getSymbol() {
        return "t";
    }

}
