package com.search.trek.infrastructure.client;

import com.search.trek.infrastructure.client.ai.minimax.MiniMaxApiClient;
import com.search.trek.infrastructure.client.ai.minimax.MiniMaxApi;
import com.search.trek.infrastructure.client.ai.minimax.constant.MinimaxConstant;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.redisson.api.RSearch;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {
    protected static MiniMaxApi miniMaxApi;
    protected static MiniMaxApiClient client;
    @Autowired
    protected RedissonClient redisson;
    protected RSearch rSearch;

    @Before
    public void before() {
        client = MiniMaxApiClient.builder()
                .init()
                .apiHost(MinimaxConstant.MINI_MAX_API)
                .build();
        miniMaxApi = client.getMiniMaxApi();
        rSearch = redisson.getSearch();
    }

}
