package com.example.maven.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeRangeSet;
import com.google.common.primitives.Ints;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * @date 2020/06/19
 */
public class GuavaCollectionTest {

    // 虽然使用final修饰，但是fakeFinalList中的内容是可修改
    private final List<String> fakeFinalList = Lists.newArrayList();

    /**
     * 使用场景：运行期不可变的集合，例如作为过滤条件的集合
     */
    private void testFakeFinalList() {
        fakeFinalList.add("Susan");
        Assert.assertFalse(fakeFinalList.isEmpty());
    }

    /**
     * 不可变集合 的三种构造方式
     */
    @Test
    public void testImmutable() {

        ImmutableSet<String> immutableSet = ImmutableSet.of("Jim", "Mike");

        ImmutableSet<String> immutableSet1 = ImmutableSet.copyOf(Lists.newArrayList("Bob", "Ann"));

        ImmutableSet<String> immutableSet2 = ImmutableSet.<String>builder()
                        .addAll(Lists.newArrayList("Tim"))
                        .add("Chris")
                        .build();
    }

    /**
     * MultiSet 可以多次添加相等的元素
     * * 1. 没有元素顺序限制的ArrayList<E>
     * * 2. Map<E, Integer>，键为元素，值为计数
     */
    @Test
    public void testMultiSet() {
        // 统计单词数量
        List<String> wordList = Lists.newArrayList("a", "b", "a", "c");
        Multiset<String> multiset = HashMultiset.create();
        multiset.addAll(wordList);
        Integer count = multiset.count("a");
        Assert.assertEquals(2, count.intValue());
    }

    /**
     * MultiMap 可以把键映射到任意多个值
     */
    @Test
    public void testMultiMap() {
        Multimap<Integer, String> multimap = ArrayListMultimap.create();
        multimap.put(1, "Jim");
        multimap.put(1, "Tom");
        multimap.put(2, "Mike");
        Assert.assertEquals(3, multimap.size());
        Collection<String> names = multimap.get(1);

        Assert.assertEquals(2, names.size());
        Assert.assertTrue(names.contains("Jim"));
        Assert.assertTrue(names.contains("Tom"));
    }

    /**
     * BiMap 双向映射集合
     * 1. 可以用 inverse()反转BiMap<K, V>的键值映射
     * 2. 保证值是唯一的，因此 values()返回Set而不是普通的Collection
     */
    @Test
    public void testBiMap() {
        BiMap<String, Integer> nameIdMap = HashBiMap.create();
        nameIdMap.put("Jim", 1);
        BiMap<Integer, String> idNameMap = nameIdMap.inverse();
        Assert.assertTrue(idNameMap.containsKey(1));
        Assert.assertTrue(idNameMap.containsValue("Jim"));
    }

    /**
     * 当put已有的值时会抛出@IllegalArgumentException 异常
     */
    @Test
    public void testBiMapThrowException() {
        BiMap<String, Integer> nameIdMap = HashBiMap.create();
        nameIdMap.put("Jim", 1);
        nameIdMap.put("Tom", 1);
    }

    /**
     * BiMap可以使用forcePut覆盖已有的键值
     */
    @Test
    public void testBiMapForcePut() {
        BiMap<String, Integer> nameIdMap = HashBiMap.create();
        nameIdMap.put("Jim", 1);
        nameIdMap.forcePut("Tom", 1);
        Assert.assertEquals(1, nameIdMap.size());
        Assert.assertTrue(nameIdMap.containsKey("Tom"));
    }

    /**
     * RangeSet描述了一组不相连的、非空的区间。当把一个区间添加到可变的RangeSet时，所有相连的区间会被合并，空区间会被忽略
     */
    @Test
    public void testRangeSet() {
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // {[1,10]}
        rangeSet.add(Range.closedOpen(11, 15));//不相连区间:{[1,10], [11,15)}
        rangeSet.add(Range.closedOpen(15, 20)); //相连区间; {[1,10], [11,20)}
        rangeSet.add(Range.openClosed(0, 0)); //空区间; {[1,10], [11,20)}
        rangeSet.remove(Range.open(5, 10)); //分割[1, 10]; {[1,5], [10,10], [11,20)}
    }

    ////////// 集合工具类

    /**
     * 通过静态方法创建集合对象
     */
    @Test
    public void testNewCollection() {
        Set<String> copySet = Sets.newHashSet("Theta");
        List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");
    }

    /**
     * 迭代器工具
     */
    @Test
    public void testIterables() {
        // 串联多个iterables的懒视图
        Iterable<Integer> concatenated = Iterables.concat(
                Ints.asList(1, 2, 3),
                Ints.asList(4, 5, 6)); // concatenated包括元素 1, 2, 3, 4, 5, 6

        // 返回iterable的最后一个元素，若iterable为空则抛出NoSuchElementException
        Set<String> myLinkedHashSet = Sets.newHashSet("1", "2");
        String lastAdded = Iterables.getLast(myLinkedHashSet);
        Assert.assertEquals("2", lastAdded);

        // 获取iterable中唯一的元素，如果iterable为空或有多个元素，则快速失败
        Set<String> thisSetIsDefinitelyASingleton = Sets.newHashSet("1");
        String theElement = Iterables.getOnlyElement(thisSetIsDefinitelyASingleton);
        Assert.assertEquals("1", theElement);
    }



}
