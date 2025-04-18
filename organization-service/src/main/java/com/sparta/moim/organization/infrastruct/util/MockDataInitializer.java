package com.sparta.moim.organization.infrastruct.util;

import com.sparta.moim.organization.domain.repository.OrganizationMemberRepository;
import com.sparta.moim.organization.domain.repository.OrganizationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
@Slf4j
@RequiredArgsConstructor
public class MockDataInitializer implements ApplicationRunner {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final DataCreateService dataCreateService;

    private static final int TOTAL_ORGANIZATIONS = 10_000;
    private static final int ORG_BATCH_SIZE = 1_000;
    private static final int THREAD_POOL_SIZE = 10;

    @Override
    public void run(ApplicationArguments args) {
        if (organizationRepository.findAll(0, 1).getTotal()>0) {
            log.info("이미 데이터가 존재합니다.");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        long start = System.currentTimeMillis();

        for (int batch = 0; batch < TOTAL_ORGANIZATIONS / ORG_BATCH_SIZE; batch++) {
            int finalBatch = batch;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
                    dataCreateService.generateOrganizationBatch(finalBatch, ORG_BATCH_SIZE, TOTAL_ORGANIZATIONS), executor);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        long total = System.currentTimeMillis() - start;
        log.info("✅ 모든 조직 및 멤버 데이터 생성 완료 (소요 시간: {}ms)", total);

        executor.shutdown();
    }
}
