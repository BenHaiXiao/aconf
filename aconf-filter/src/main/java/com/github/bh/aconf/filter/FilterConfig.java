package com.github.bh.aconf.filter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.bh.aconf.common.constants.FilterType;

/**
 * 过滤器的规则配置信息。
 * <p>
 * 如后台系统里配置好的频道尾号过滤器规则、通用的正则表达式规则等等。
 *
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 11:30
 */
public class FilterConfig implements Serializable {

    private static final long serialVersionUID = -6729838170188583926L;

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterConfig.class);
    
    private static final String LONG_EQ_SYMBOL = "==";    
    
    private String filterSymbol;
    private String operatorSymbol;
    private String boundary;
    private FilterType filterType;

    private HashSet<Long> values;
    
    public FilterConfig(String filterSymbol, String operatorSymbol, String boundary, FilterType filterType) {
        this.filterSymbol = filterSymbol;
        this.operatorSymbol = operatorSymbol;
        this.boundary = boundary;
        this.filterType = filterType;
        
        if(this.operatorSymbol.equals(LONG_EQ_SYMBOL)){
        	List<String> boundaries = BoundaryUtils.splitJson(boundary);
        	values = new HashSet<Long>();
            for (String eachBoundary : boundaries) {
                try {
                	values.add(Long.parseLong(eachBoundary));
                } catch (NumberFormatException e) {
                    LOGGER.warn("boundary exists non-number : {}", eachBoundary);
                }
            }
        }
    }

    public HashSet<Long> getValues() {
		return values;
	}

	public void setValues(HashSet<Long> values) {
		this.values = values;
	}

	public String getFilterSymbol() {
        return filterSymbol;
    }

    public String getOperatorSymbol() {
        return operatorSymbol;
    }

    public String getBoundary() {
        return boundary;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterConfig that = (FilterConfig) o;

        return (filterSymbol != null ? filterSymbol.equals(that.filterSymbol) : that.filterSymbol == null) && (operatorSymbol != null ? operatorSymbol.equals(that.operatorSymbol) : that.operatorSymbol == null) && (boundary != null ? boundary.equals(that.boundary) : that.boundary == null) && filterType == that.filterType;
    }

    @Override
    public int hashCode() {
        int result = filterSymbol != null ? filterSymbol.hashCode() : 0;
        result = 31 * result + (operatorSymbol != null ? operatorSymbol.hashCode() : 0);
        result = 31 * result + (boundary != null ? boundary.hashCode() : 0);
        result = 31 * result + (filterType != null ? filterType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FilterConfig{" +
                "filterSymbol='" + filterSymbol + '\'' +
                ", operatorSymbol='" + operatorSymbol + '\'' +
                ", filterType=" + filterType +
                ", boundary='" + ( (boundary!=null&&boundary.length()>256) ? boundary.substring(0, 256) : boundary) + '\'' +                
                '}';
    }
}
