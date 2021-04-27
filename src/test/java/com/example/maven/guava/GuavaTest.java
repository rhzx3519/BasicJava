package com.example.maven.guava;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author ZhengHao Lou
 * @date 2020/05/25
 */
@Slf4j
public class GuavaTest {

    @Test
    public void testOptional() {
        Optional<Integer> possible = Optional.of(5);
        possible.isPresent(); // returns true
        Assert.assertEquals(5, possible.get().intValue()); // returns 5
    }

    @Test
    public void testPrecondition() {
        int i = 0;
        checkArgument(i >= 0, "Argument was %s but expected nonnegative", i);
        String str = null;
        checkNotNull(str, "Argument was but expected not null.", str);
    }

    @Test
    public void testObject() {
        Objects.equal("a", "a"); // returns true
        Objects.equal(null, "a"); // returns false
        Objects.equal("a", null); // returns false
        Objects.equal(null, null); // returns true
    }

    @Test
    public void testLoadingCacheLoad() {
        LoadingCache<String, String> stringLoadingCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return null;
                    }
                });

        stringLoadingCache.put("k1", "v1");
        String value = stringLoadingCache.getUnchecked("k1");
        log.info(value);
    }

    @Test
    public void testCreate() {

    }

}
