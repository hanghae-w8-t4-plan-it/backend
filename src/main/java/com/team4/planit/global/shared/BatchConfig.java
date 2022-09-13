package com.team4.planit.global.shared;

import com.team4.planit.statistic.concentration.Concentration;
import com.team4.planit.statistic.concentration.ConcentrationHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    public static final String JOB_NAME = "todoBackupBatch";
    private static final String STEP_NAME = JOB_NAME+"Step";


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Value("${chunkSize:1000}")
    private int chunkSize; // Parameter로 chunkSize를 던지면 해당 값으로, 없으면 1000을 기본으로

    private static String ADDRESS_PARAM = null;

    // Job 생성

    @Bean
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step())
                .build();
    }

    @Bean
    @JobScope
    public Step step() {
        return stepBuilderFactory.get(STEP_NAME)
                .<Concentration, ConcentrationHistory>chunk(chunkSize)
                .reader(reader(ADDRESS_PARAM))
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Concentration> reader (
            @Value("#{jobParameters[startDate]}") String startDate) {

        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("startDate", startDate+"%");

        JpaPagingItemReader<Concentration> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("select s From Concentration s where s.startDate like :startDate");
        reader.setParameterValues(parameters);
        reader.setPageSize(chunkSize);

        return reader;
    }

    public ItemProcessor<Concentration, ConcentrationHistory> processor() {
        return concentration -> new ConcentrationHistory(concentration.getMember(), concentration.getPeriod(), concentration.getConcentrationRate(),concentration.getConcentrationTime(),concentration.getStartDate());
    }

    public JpaItemWriter<ConcentrationHistory> writer() {
        JpaItemWriter<ConcentrationHistory> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}