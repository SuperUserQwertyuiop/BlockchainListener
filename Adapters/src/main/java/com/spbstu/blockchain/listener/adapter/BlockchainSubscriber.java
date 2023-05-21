package com.spbstu.blockchain.listener.adapter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public interface BlockchainSubscriber<T> {

    void subscribe(Consumer<T> consumer);

    void onStop();

    void testAccessibility() throws ExecutionException, InterruptedException, TimeoutException;
}
