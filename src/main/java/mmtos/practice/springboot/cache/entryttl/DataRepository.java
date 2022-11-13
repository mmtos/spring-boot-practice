package mmtos.practice.springboot.cache.entryttl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class DataRepository {

    public String getSomeData(CacheKeyVO cacheKeyVO) {
        log.info("DataRepository.getSomeData");
        return "data-" + cacheKeyVO.getBusStopId();
    }
}
