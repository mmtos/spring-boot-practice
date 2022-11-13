package mmtos.practice.springboot.cache.entryttl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DataController {
    private final DataService dataService;

    @GetMapping("/data")
    public String getSomeData(CacheKeyVO cacheKeyVO) {
      log.info(cacheKeyVO.toString());
      return dataService.getSomeDataFromDB(cacheKeyVO);
    }
}
