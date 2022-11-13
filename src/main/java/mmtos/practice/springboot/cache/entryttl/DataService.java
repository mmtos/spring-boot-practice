package mmtos.practice.springboot.cache.entryttl;

import lombok.RequiredArgsConstructor;
import mmtos.practice.springboot.cache.config.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataService {
    private final DataRepository dataRepository;

    @Cacheable(value = CacheConfig.BUS_STOP_CACHE_NAME, key = "#cacheKeyVO" )
    public String getSomeDataFromDB(CacheKeyVO cacheKeyVO) {
        return dataRepository.getSomeData(cacheKeyVO);
    }
}
