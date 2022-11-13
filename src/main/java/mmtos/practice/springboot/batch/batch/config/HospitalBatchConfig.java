package mmtos.practice.springboot.batch.batch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.net.URI;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import mmtos.practice.springboot.batch.batch.listener.JobCompletionNotificationListener;
import mmtos.practice.springboot.batch.batch.processor.HospitalItemProcessor;
import mmtos.practice.springboot.batch.batch.reader.HospitalItemReader;
import mmtos.practice.springboot.batch.dto.Hospital;
import mmtos.practice.springboot.batch.exception.Not200Exception;
import mmtos.practice.springboot.batch.util.HttpConnectUtil;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@Configuration
@EnableBatchProcessing
public class HospitalBatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public SqlSessionFactory sqlSessionFactoryBean;

    @Value("${openapi.data.go.kr.key}")
    private String serviceKey;

    @Value("${openapi.data.go.kr.hospital.url}")
    private String url;

    private String numOfRows = "1000";

    private String batchJobName = "HospitalBatchJob";

    @Bean
    @StepScope
    public Tasklet getTotalCountTasklet(){

        return new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                log.info("{} : 접속 url - {}",batchJobName,url);
                String totalCount = "";
                URI totalUri = new URIBuilder(url)
                        .addParameter("ServiceKey",serviceKey)
                        .addParameter("numOfRows","1")
                        .addParameter("pageNo","1").build();
                try{
                    HttpGet getMethod = new HttpGet(totalUri);
                    String response = HttpConnectUtil.getResponseText(getMethod);
                    ObjectMapper parser = new XmlMapper();
                    Map<String,Object> resultMap = parser.readValue(response.toString(),Map.class);
                    Map<String,Object> bodyMap = (Map<String,Object>) resultMap.get("body");
                    totalCount = (String) bodyMap.get("totalCount");
                }catch(Not200Exception e){
                    log.error(e.getMessage());
                }
                ExecutionContext stepContext = stepContribution.getStepExecution().getExecutionContext();

                stepContext.put("totalCount",totalCount);
                int pageCount = (Integer.parseInt(totalCount) /  Integer.parseInt(numOfRows) ) + 1;
                stepContext.put("pageCount",pageCount);
                stepContext.put("pageNo", "1");
                if("0".equals(totalCount)){

                    return RepeatStatus.CONTINUABLE;
                }
                log.info("totalCount : {} pageCount : {}",totalCount,pageCount);
                return RepeatStatus.FINISHED;
            }
        };
    }

    @Bean
    @StepScope
    public HospitalItemReader hospitalItemReader(){
        return new HospitalItemReader();
    }

    @Bean
    @StepScope
    public HospitalItemProcessor hospitalItemProcessor(){
        return new HospitalItemProcessor();
    }

    @Bean
    @StepScope
    public MyBatisBatchItemWriter<Hospital> hospitalItemWriter(){
        return new MyBatisBatchItemWriterBuilder<Hospital>()
                .sqlSessionFactory(sqlSessionFactoryBean)
                .statementId("Hospital.insertHospital")
                .build();
    }

    @Bean
    public Job hospitalBatchJob(JobCompletionNotificationListener completedListener) {
        String jobName = "hospitalBatchJob";
        return jobBuilderFactory.get(jobName)
                .incrementer(new RunIdIncrementer())
                .start(hospitalBatchStep1())
                .next(hospitalBatchStep2())
                .next(decider())
                .from(decider())
                    .on("REMAIN_NEXT_PAGE")
                    .to(hospitalBatchStep2())
                .end()
                .listener(completedListener)
                //https://docs.spring.io/spring-batch/docs/current/reference/html/common-patterns.html#passingDataToFutureSteps
                .listener(promotionListener())
                .build();
    }
    @Bean
    public JobExecutionDecider decider() {
        return new JobExecutionDecider() {
            @Override
            public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
                int pageNo = Integer.parseInt(jobExecution.getExecutionContext().get("pageNo").toString());
                int pageCount = Integer.parseInt(jobExecution.getExecutionContext().get("pageCount").toString());

                if(pageNo > pageCount){
                    return new FlowExecutionStatus("COMPLETED");
                }else{
                    return new FlowExecutionStatus("REMAIN_NEXT_PAGE");
                }
            }
        };
    }

    @Bean
    @JobScope
    public Step hospitalBatchStep1() {
        String jobName = "hospitalBatchStep1";
        return stepBuilderFactory.get(jobName)
                .tasklet(getTotalCountTasklet())
                .listener(promotionListener())
                .build();
    }

    @Bean
    @JobScope
    public Step hospitalBatchStep2() {
        return stepBuilderFactory.get("hospitalBatchStep2")
                .<Hospital, Hospital> chunk(Integer.parseInt(numOfRows))
                .reader(hospitalItemReader())
                .processor(hospitalItemProcessor())
                .writer(hospitalItemWriter())
                .listener(hospitalStepListener())
                .listener(promotionListener())
                .build();
    }
    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();

        listener.setKeys(new String[] {"pageNo","totalCount","pageCount"});

        return listener;
    }
    @Bean
    public StepExecutionListener hospitalStepListener() {

        return new StepExecutionListener(){

            @Override
            public void beforeStep(StepExecution stepExecution) {
                log.info("스텝 시작");
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {

                JobExecution jobExecution = stepExecution.getJobExecution();
                String pageNo =  jobExecution.getExecutionContext().get("pageNo").toString();
                String nextPageNo = String.valueOf(Integer.parseInt(pageNo) + 1);
                jobExecution.getExecutionContext().put("pageNo", nextPageNo);
                log.info("다음 스텝 준비: 페이지번호 {}", nextPageNo);
                return ExitStatus.COMPLETED;
            }
        };
    }

}
