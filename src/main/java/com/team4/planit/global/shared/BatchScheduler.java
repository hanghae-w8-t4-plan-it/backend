//package com.team4.planit.global.shared;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.HashMap;
//
//@EnableScheduling
//@RequiredArgsConstructor
//@Component
//@Slf4j
//public class BatchScheduler {
//
//
//    private final Job job;
//    private final JobLauncher jobLauncher;
//
//    @Scheduled(cron = "* 50 11 * 1 *")
//    public void startJob() {
//        try {
//            Map<String, JobParameter> jobParametersMap = new HashMap<>();
//
//            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date time = new Date();
//
//            String time1 = format1.format(time);
//
//            jobParametersMap.put("requestDate", new JobParameter(time1));
//
//            JobParameters parameters = new JobParameters(jobParametersMap);
//
//            JobExecution jobExecution = jobLauncher.run(job, parameters);
//
//            while (jobExecution.isRunning()) {
//                log.info("isRunning...");
//            }
//
//        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
//                 JobParametersInvalidException e) {
//            e.printStackTrace();
//        }
//    }
//}
