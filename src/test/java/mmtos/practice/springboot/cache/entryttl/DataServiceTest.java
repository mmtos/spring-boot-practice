package mmtos.practice.springboot.cache.entryttl;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import mmtos.practice.springboot.cache.config.CacheConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DataServiceTest {
    @Autowired
    DataService dataService;

    @SpyBean
    DataRepository dataRepository;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    void invalidateCache() {
       Cache cache = cacheManager.getCache(CacheConfig.BUS_STOP_CACHE_NAME);
       cache.invalidate();
    }

    @Test
    @DisplayName("15초 이내에 재조회시 캐시 적용되는지 확인")
    void test1(){
        CacheKeyVO cacheKeyVO = new CacheKeyVO("123");
        for(int i=0; i<10; i++){
            dataService.getSomeDataFromDB(cacheKeyVO);
        }
        verify(dataRepository, times(1)).getSomeData(cacheKeyVO);

    }
    @Test
    @DisplayName("15초 이후에 조회시 캐시 만료되는지 확인")
    void test2() throws Exception{
        CacheKeyVO cacheKeyVO = new CacheKeyVO("123");
        for(int i=0; i<10; i++){
            dataService.getSomeDataFromDB(cacheKeyVO);
            Thread.sleep(3000);
        }
        verify(dataRepository, atLeast(2)).getSomeData(cacheKeyVO);
    }

}
