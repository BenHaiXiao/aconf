package com.github.bh.aconf.filter.operator;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiaobenhai
 * Date: 2017/3/8
 * Time: 16:19
 */
public abstract class AbstractOperator<T, V> implements Operator<T, V> {

    @Override
    public boolean isMe(String symbol) {
        return StringUtils.equalsIgnoreCase(symbol, getSymbol());
    }

}
