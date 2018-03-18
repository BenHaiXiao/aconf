package com.github.bh.aconf.filter.operator;

/**
 * 对比算子器.
 *
 * @author xiaobenhai
 * Date: 2017/3/5
 * Time: 13:59
 */
public interface Operator<T, V> {

    boolean op(T actual, V expect);

    /**
     * 名称，易于计算机识别
     */
    String getName();

    /**
     * 别名（友好名称）。用于直接显示在管理后台。
     */
    String getAlias();

    /**
     * 算子的符号表示
     */
    String getSymbol();

    boolean isMe(String symbol);

}
