package dev.gustavoteixeira.filetransformer.config;


import dev.gustavoteixeira.filetransformer.domain.Person;
import dev.gustavoteixeira.filetransformer.mapper.fieldset.PersonFieldSetMapper;
import dev.gustavoteixeira.filetransformer.processor.PersonItemProcessor;
import dev.gustavoteixeira.filetransformer.writer.CustomItemWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
public class BatchConfiguration extends DefaultBatchConfigurer {

    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    //
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public ItemStreamReader<Person> reader(@Value("#{jobParameters['bucketName']}") String bucketName,
                                           @Value("#{jobParameters['objectName']}") String objectName) {
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("THE LOG I WANT TO SEE");
        log.info("bucketName: {}", bucketName);
        log.info("objectName: {}", objectName);


        log.info("BEFORE THING THAT IS GOING TO BREAK FOR SURE");
        return new FlatFileItemReaderBuilder<Person>()
                .name("reader")
                .resource(resourceLoader.getResource(objectName))
                .delimited()
                .names("firstName", "lastName")
                .fieldSetMapper(new PersonFieldSetMapper())
                .build();
    }

    @Bean
    public ItemProcessor<Person, Person> processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public ItemWriter<Person> writer() {
        return new CustomItemWriter();
    }

    @Bean
    public Job ingestJob() {
        return jobBuilderFactory.get("ingestJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("ingest")
                .<Person, Person>chunk(10)
                .reader(reader(null, null))
                .processor(processor())
                .writer(writer())
                .build();
    }

//    @Bean
//    public Step step1() {
//        return stepBuilderFactory.get("step1")
//                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> RepeatStatus.FINISHED)
//                .build();
//    }
//
//    @Bean
//    public Job job(Step step1) throws Exception {
//        log.info("WUBBA LUBBA DUB DUB");
//        return jobBuilderFactory.get("job1")
//                .incrementer(new RunIdIncrementer())
//                .start(step1)
//                .build();
//    }

}

