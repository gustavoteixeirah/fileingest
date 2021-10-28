package dev.gustavoteixeira.filetransformer.config;


import dev.gustavoteixeira.filetransformer.domain.Person;
import dev.gustavoteixeira.filetransformer.mapper.fieldset.PersonFieldSetMapper;
import dev.gustavoteixeira.filetransformer.processor.PersonItemProcessor;
import dev.gustavoteixeira.filetransformer.writer.CustomItemWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

/**
 * Class used to configure the batch job related beans.
 *
 * @author Chris Schaefer
 * @author David Turanski
 */
@Configuration
@Slf4j
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    public ItemStreamReader<Person> reader(@Value("#{jobParameters['bucketName']}") String bucketName,
                                           @Value("#{jobParameters['objectName']}") String objectName) {
        log.info("THE LOG I WANT TO SEE");
        log.info("bucketName: {}", bucketName);
        log.info("objectName: {}", objectName);

        if (!objectName.matches("[a-z]+:.*")) {
            objectName = "file:" + objectName;
        }
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
}

