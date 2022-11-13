package mmtos.practice.springboot.batch;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BatchDemoApplicationTests {
	@Autowired
	private StringEncryptor jasyptStringEncryptor;

	@Value("${openapi.data.go.kr.key}")
	private String serviceKey;

	@Test
	void contextLoads() {
		System.out.println(jasyptStringEncryptor.encrypt(serviceKey));
	}
}
