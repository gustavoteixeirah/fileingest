package dev.gustavoteixeira.filetransformer.config;


import dev.gustavoteixeira.filetransformer.CustomProps;
import dev.gustavoteixeira.filetransformer.domain.Person;
import dev.gustavoteixeira.filetransformer.mapper.fieldset.PersonFieldSetMapper;
import dev.gustavoteixeira.filetransformer.processor.PersonItemProcessor;
import dev.gustavoteixeira.filetransformer.utils.ResourceUtils;
import dev.gustavoteixeira.filetransformer.writer.CustomItemWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@EnableConfigurationProperties({CustomProps.class})

public class BatchConfiguration extends DefaultBatchConfigurer {

    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

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
    public FlatFileItemReader<Person> reader(@Value("#{jobParameters['bucketName']}") String bucketName,
                                             @Value("#{jobParameters['objectName']}") String objectName) {
        log.info("bucketName: {}", bucketName);
        log.info("objectName: {}", objectName);

        var reader = new FlatFileItemReader<Person>();
        reader.setLinesToSkip(1);
        reader.setResource(ResourceUtils.getResource(resourceLoader, "gustavoteixeiradev-scdf", "gustavoteixeiradev-scdf/name-list.csv"));

        var tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("FIRSTNAME", "LASTNAME");

        var mapper = new DefaultLineMapper<Person>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new PersonFieldSetMapper());
        mapper.afterPropertiesSet();

        reader.setLineMapper(mapper);
        return reader;
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

