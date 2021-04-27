package com.example.maven.guava;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * @date 2020/06/19
 */
@Slf4j
public class GuavaStringTest {

    /**
     * Fluent风格的Joiner让连接字符串更简单
     */
    @Test
    public void testJoiner() {
        String str = Joiner.on("; ").skipNulls().join("Harry", null, "Ron", "Hermione");
        Assert.assertEquals("Harry; Ron; Hermione", str);

        String str2 = Joiner.on(",").join(Arrays.asList(1, 5, 7)); // returns "1,5,7"
        Assert.assertEquals("1,5,7", str2);
    }

    /**
     * 拆分器
     */
    @Test
    public void testSplitter() {
        // JDK自带的split函数
        String[] stringSplit = ",a,,b,".split(","); // [, a, , b]
        log.info("{}", Arrays.asList(stringSplit));

        // Guava拆分器
        Iterable<String> stringIterable = Splitter.on(',')
                .omitEmptyStrings()
                .trimResults()
                .omitEmptyStrings()
                .split("foo,bar,,   qux"); // [foo, bar, qux]
        log.info("{}", stringIterable);
    }

    @Test
    public void testCharMatcher() {


        // 判定型函数, 判断 CharMacher 和入参字符串的匹配关系
        Assert.assertTrue(CharMatcher.is('a').matchesAllOf("aaa"));//true
        Assert.assertTrue(CharMatcher.is('a').matchesAnyOf("aba"));//true
        Assert.assertFalse(CharMatcher.is('a').matchesNoneOf("aba")); //false

        // 计数型函数，查找入参字符串中第一次、最后一次出现目标字符的位置，或者目标字符出现的次数
        Assert.assertEquals(3, CharMatcher.is('a').countIn("aaa")); // 3
        Assert.assertEquals(1, CharMatcher.is('a').indexIn("java")); // 1


        // 对匹配字符的操作
        String theDigits = CharMatcher.inRange('0', '9').retainFrom("dsk9nb3bbai03jl`z:"); // 9303
        Assert.assertEquals("9303", theDigits);

        String spaced = CharMatcher.whitespace().trimAndCollapseFrom(" a b ", ' '); // a b
        Assert.assertEquals("a b", spaced);

        String noDigits = CharMatcher.inRange('0', '9').replaceFrom("0ea9bn", "*"); // *ea*bn
        Assert.assertEquals("*ea*bn", noDigits);

        String lowerAndDigit = CharMatcher.inRange('0', '9').or(CharMatcher.inRange('a', 'z'))
                .retainFrom("DEnd899esEdO"); // nd899esd
        Assert.assertEquals("nd899esd", lowerAndDigit);
    }

    /**
     * Charsets字符集
     *
     * Charsets provides constant references to the six standard Charset
     * implementations guaranteed to be supported by all Java platform implementations.
     */
    @Test
    public void testCharSet() {
        // old way, need handle exception
        try {
            byte[] bytes = "火星".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // how can this possibly happen?
            throw new AssertionError(e);
        }

        byte[] bytes = "火星".getBytes(Charsets.UTF_8);
    }

    /**
     * CaseFormat is a handy little class for converting between ASCII case conventions
     */
    @Test
    public void testCaseFormat() {
        // UPPER_UNDERSCORE下划线分割的单词
        String lowerCaseStr = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME"); // returns "constantName"
        Assert.assertEquals("constantName", lowerCaseStr);

        String upperCamelStr = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "CONSTANT_NAME"); // ConstantName
        Assert.assertEquals("ConstantName", upperCamelStr);

        String lowerHyphen = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, "CONSTANT_NAME"); // constant-name
        Assert.assertEquals("constant-name", lowerHyphen);
    }

    @Test
    public void testStrings() {
        // 去掉前后空格，如果最后为空字符串则返回null
        Assert.assertNull(Strings.trimToNull(""));  // null
        Assert.assertFalse(Strings.isEmpty(" "));   // false
        Assert.assertTrue(Strings.isBlank(" "));     // true
        Assert.assertEquals("01", Strings.left("0123", 2));     // "01"
    }

}
