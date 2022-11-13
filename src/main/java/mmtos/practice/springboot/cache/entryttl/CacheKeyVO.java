package mmtos.practice.springboot.cache.entryttl;

import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CacheKeyVO {
    private final String busStopId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CacheKeyVO that = (CacheKeyVO) o;
        return Objects.equals(busStopId, that.busStopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(busStopId);
    }

    @Override
    public String toString() {
        return "CacheKeyVO{" +
                "busStopId='" + busStopId + '\'' +
                '}';
    }
}
