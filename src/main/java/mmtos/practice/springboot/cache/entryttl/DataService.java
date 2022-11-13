package mmtos.practice.springboot.cache.entryttl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataService {
    private final DataRepository dataRepository;

    @Cacheable(value = "cachestore", key = "#cacheKeyVO" )
    public String getSomeDataFromDB(CacheKeyVO cacheKeyVO) {
        return dataRepository.getSomeData(cacheKeyVO);
    }
}
