package com.eg.egsc.scp.simulator.component;

import com.google.common.util.concurrent.MoreExecutors;
import io.netty.util.internal.PlatformDependent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class DirectMemReporter {
    private AtomicLong directMem = new AtomicLong();
    private ScheduledExecutorService executor = MoreExecutors.getExitingScheduledExecutorService(
            new ScheduledThreadPoolExecutor(1), 10, TimeUnit.SECONDS);

    public DirectMemReporter() {
        Field field = ReflectionUtils.findField(PlatformDependent.class, "DIRECT_MEMORY_COUNTER");
        field.setAccessible(true);
        try {
            directMem = (AtomicLong) field.get(PlatformDependent.class);
        } catch (IllegalAccessException e) {}
    }

    public void startReport() {
        executor.scheduleAtFixedRate(() -> {
            log.info("netty direct memory size:{}b, max:{}", directMem.get(), PlatformDependent.maxDirectMemory());
        }, 0, 1, TimeUnit.SECONDS);
    }
}