package mmtos.practice.springboot.batch.batch.processor;

import mmtos.practice.springboot.batch.dto.Hospital;
import org.springframework.batch.item.ItemProcessor;

public class HospitalItemProcessor implements ItemProcessor<Hospital,Hospital> {

    @Override
    public Hospital process(Hospital hospital) throws Exception {
        return hospital;
    }
}
