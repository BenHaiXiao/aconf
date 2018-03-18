package com.github.bh.aconf.filter;

import java.util.HashSet;
import java.util.List;

import com.github.bh.aconf.filter.operator.LongNotEqualsOperator;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.github.bh.aconf.filter.operator.LongEqualsOperator;
import com.github.bh.aconf.filter.operator.LongGeOperator;
import com.github.bh.aconf.filter.operator.LongGtOperator;
import com.github.bh.aconf.filter.operator.LongInOperator;
import com.github.bh.aconf.filter.operator.LongLeOperator;
import com.github.bh.aconf.filter.operator.LongLtOperator;
import com.github.bh.aconf.filter.operator.LongTailMatchOperator;
import com.github.bh.aconf.filter.operator.Operator;

/**
 * 数字型过滤器。
 * <p>
 * boundary之间的值使用“,”分隔，它们之前是OR关系，采用“短路运算”。
 *
 * @author xiaobenhai
 * Date: 2017/3/5
 * Time: 15:49
 */
@Component
public abstract class AbstractNumberFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractNumberFilter.class);

    @Autowired
    private LongEqualsOperator longEqualsOperator;
    @Autowired
    private LongGeOperator longGeOperator;
    @Autowired
    private LongGtOperator longGtOperator;
    @Autowired
    private LongInOperator longInOperator;
    @Autowired
    private LongLeOperator longLeOperator;
    @Autowired
    private LongLtOperator longLtOperator;
    @Autowired
    private LongNotEqualsOperator longNotEqualsOperator;
    @Autowired
    private LongTailMatchOperator longTailMatchOperator;

    //2017-4-11 boundarySets不为空，并且属于in逻辑 则使用boundarySets，否则默认使用boundary
    protected boolean check(long actual, String operatorSymbol, String boundary, HashSet boundarySets) {
        if (longEqualsOperator.isMe(operatorSymbol)) {
        	if(!CollectionUtils.isEmpty(boundarySets)){
        		//in
        		return checkIn(actual, boundarySets);
        	}else{
        		return check(longEqualsOperator, actual, BoundaryUtils.parseForLongList(boundary));
        	}            
        }
        if (longGeOperator.isMe(operatorSymbol)) {
            return check(longGeOperator, actual, BoundaryUtils.parseForLongList(boundary));
        }
        if (longGtOperator.isMe(operatorSymbol)) {
            return check(longGtOperator, actual, BoundaryUtils.parseForLongList(boundary));
        }
        if (longLeOperator.isMe(operatorSymbol)) {
            return check(longLeOperator, actual, BoundaryUtils.parseForLongList(boundary));
        }
        if (longLtOperator.isMe(operatorSymbol)) {
            return check(longLtOperator, actual, BoundaryUtils.parseForLongList(boundary));
        }
        if (longNotEqualsOperator.isMe(operatorSymbol)) {
        	return check(longNotEqualsOperator, actual, BoundaryUtils.parseForLongList(boundary));         
        }
        if (longTailMatchOperator.isMe(operatorSymbol)) {
            return check(longTailMatchOperator, actual, BoundaryUtils.parseForLongList(boundary));
        }
        if (longInOperator.isMe(operatorSymbol)) {
            return checkForRange(longInOperator, actual, BoundaryUtils.parseForRange(boundary));
        }
        LOGGER.warn("there are not operators for : {}, actual : {}, boundary : {}", operatorSymbol, actual, boundary);
        return true; // 没有匹配到运算符
    }

    private boolean checkIn(long actual, HashSet expects) {
    	if (CollectionUtils.isEmpty(expects)) {
            return true; // 没有过滤条件，返回true！
        }    	
    	return expects.contains(Long.valueOf(actual));
    }
    
    private boolean check(Operator operator, long actual, List<Long> expects) {
        if (CollectionUtils.isEmpty(expects)) {
            return true; // 没有过滤条件，返回true！
        }
        LOGGER.debug("check by operator : {}", operator.getName());
        for (Long expect : expects) {
            boolean pass = operator.op(actual, expect);
            LOGGER.debug("check result : {} {} {} ---> {}", actual, operator.getSymbol(), expect, pass);
            if (pass) {
                return true; // 短路，只要符合一个条件即可
            }
        }
        return false; // 所有条件都不符合！
    }

    private boolean checkForRange(Operator operator, long actual, List<Range> expects) {
        if (CollectionUtils.isEmpty(expects)) {
            return true; // 没有过滤条件，返回true！
        }
        LOGGER.debug("check by operator : {}", operator.getName());
        for (Range expect : expects) {
            boolean pass = operator.op(actual, expect);
            LOGGER.debug("check result : {} {} {} ---> {}", actual, operator.getSymbol(), expect, pass);
            if (pass) {
                return true; // 短路，只要符合一个条件即可
            }
        }
        return false; // 所有条件都不符合！
    }

    @Override
    public List<? extends Operator> getSupportOperators() {
        return Lists.newArrayList(longEqualsOperator, longGeOperator, longGtOperator, longLeOperator, longLtOperator,
                longNotEqualsOperator, longTailMatchOperator);
    }
}
