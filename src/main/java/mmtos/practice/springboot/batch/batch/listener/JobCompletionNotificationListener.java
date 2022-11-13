package mmtos.practice.springboot.batch.batch.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    @Override
    public void beforeJob(JobExecution jobExecution){

    }
    @Override
    public void afterJob(JobExecution jobExecution){

        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info(jobExecution.getJobInstance().getJobName()+" : JOB FINISHED!");
        }
    }
}
