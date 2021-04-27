package com.example.maven.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author ZhengHao Lou
 * @date 2020/7/27
 */
public class LearnCompletableFuture {
    public static CompletableFuture<Integer> compute() {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }

    public static void main(String[] args) {
        CompletableFuture<Integer> f = compute();
//        f.complete(10 * 1000);
        f.completeExceptionally(new Exception());
    }
}
