package org.batch;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableBatchProcessing
@SpringBootApplication
@RequiredArgsConstructor
public class Application implements ApplicationRunner {

  private final JobLauncher jobLauncher;

  private final JobRegistry jobRegistry;

  private static final String WORK_DATE = "workDate";

  private static final String START_DATE = "startDate";

  private static final String STEP_START_DATE = "stepStartDate";

  private static final String STEP_END_DATE = "stepEndDate";


  public static void main(String[] args){
    System.exit(
        SpringApplication.exit(
            SpringApplication.run(Application.class, args)
        )
    );
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Job job = jobRegistry.getJob(args.getOptionValues("jobName").get(0));
    JobParameters jobParameters = getJobParameters(args);
    JobExecution execution = jobLauncher.run(job, jobParameters);
  }

  @NotNull
  private static JobParameters getJobParameters(ApplicationArguments args){
    JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
    // String jobName = args.getOptionValues("jobName").get(0);
    jobParametersBuilder.addString(WORK_DATE, args.getOptionValues(WORK_DATE).get(0));
    jobParametersBuilder.addString(START_DATE, args.getOptionValues(START_DATE).get(0));

    return jobParametersBuilder.toJobParameters();
  }
}
