package com.github.bh.aconf.utils;

import java.util.List;

/**
 * markdown文本的构造器。
 * Created by Shildon on 2017/4/1.
 */
public class MDStrBuilder {

    private static final String H1 = "# %s \n";
    private static final String H2 = "## %s \n";
    private static final String ORDERED_LIST = "%d. %s \n";
    private static final String UNORDERED_LIST = "* %s \n";
    private static final String CODE_BLOCK = "```\n%s\n```\n";
    private static final String CODE_BLOCK_TAG = "```\n";
    private static final String CODE_BLOCK_LINE = "%s\n";
    private static final String QUOTINE_TEXT = "> %s\n";

    private StringBuilder content;

    private MDStrBuilder() {
        content = new StringBuilder();
    }

    public static MDStrBuilder newBuilder() {
        MDStrBuilder builder = new MDStrBuilder();
        return builder;
    }

    public MDStrBuilder h1(String s) {
        content.append(String.format(H1, s));
        return this;
    }

    public MDStrBuilder h2(String s) {
        content.append(String.format(H2, s));
        return this;
    }

    public MDStrBuilder orderedList(int order, String s) {
        content.append(String.format(ORDERED_LIST, order, s));
        return this;
    }

    public MDStrBuilder unorderedList(String s) {
        content.append(String.format(UNORDERED_LIST, s));
        return this;
    }

    public MDStrBuilder codeBlock(String s) {
        content.append(String.format(CODE_BLOCK, s));
        return this;
    }

    public MDStrBuilder codeBlocks(List<String> s) {    	
    	content.append(CODE_BLOCK_TAG);
    	for(String line : s){
    		content.append(String.format(CODE_BLOCK_LINE, line));
    	}
    	content.append(CODE_BLOCK_TAG);
        return this;
    }

    public MDStrBuilder quotingBlock(String s) {
        content.append(String.format(QUOTINE_TEXT, s));
        return this;
    }
    
    public String build() {
        return content.toString();
    }

}
