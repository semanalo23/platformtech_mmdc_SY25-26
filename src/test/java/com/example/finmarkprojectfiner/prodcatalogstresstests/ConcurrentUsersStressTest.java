package com.example.finmarkprojectfiner.prodcatalogstresstests;

import com.example.finmarkprojectfiner.catalog.model.FinmarkProduct;
import com.example.finmarkprojectfiner.catalog.service.FinmarkProdService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ConcurrentUsersStressTest {

    @Autowired
    private FinmarkProdService service;

    @Test
    void testConcurrentAccess() throws Exception {
        int users = 2500;
        ExecutorService executor = Executors.newFixedThreadPool(100);
        List<Callable<List<FinmarkProduct>>> tasks = new ArrayList<>();

        for (int i = 0; i < users; i++) {
            tasks.add(() -> service.getAllProducts());
        }

        List<Future<List<FinmarkProduct>>> results = executor.invokeAll(tasks);

        for (Future<List<FinmarkProduct>> result : results) {
            assertFalse(result.get().isEmpty()); // all users get products
        }

        executor.shutdown();
    }
}
