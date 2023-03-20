package datasearch;


import com.github.nyamnyam.DataSearchApplication;
import com.github.nyamnyam.datasearch.service.DataSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(classes = DataSearchApplication.class)
public class DataSearchRepositoryTest {

    @Autowired
    private DataSearchService dataSearchService;

    @Test
    @DisplayName("At the same time request Test")
    public void sameTimeRequestTest() throws InterruptedException {

        String query = "김밥";
        int threadCount = 100;
        AtomicInteger successCount = new AtomicInteger();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    dataSearchService.savePopularSearchTerms(query);
                    successCount.getAndIncrement();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        assertEquals(threadCount, successCount.get());
    }

}
