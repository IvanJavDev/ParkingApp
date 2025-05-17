package org.example.parkingapp.config;

import lombok.AllArgsConstructor;
import org.example.parkingapp.model.entity.VehicleEntity;
import org.example.parkingapp.model.enums.VehicleType;
import org.example.parkingapp.repository.VehicleRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;


@Configuration
@AllArgsConstructor
public class BatchConfig {

    @Bean
    public FlatFileItemReader<VehicleEntity> parkingDataReader() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("vehicleNumber", "type", "entryTime", "exitTime");

        DefaultLineMapper<VehicleEntity> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSet -> {
            VehicleEntity vehicle = new VehicleEntity();
            vehicle.setVehicleNumber(fieldSet.readString("vehicleNumber"));

            String typeStr = fieldSet.readString("vehicleType");
            vehicle.setVehicleType(VehicleType.valueOf(typeStr.toUpperCase()));

            String entryTimeStr = fieldSet.readString("entryTime");
            vehicle.setEntryTime(LocalDateTime.parse(entryTimeStr));

            String exitTimeStr = fieldSet.readString("exitTime");
            if (!exitTimeStr.isEmpty()) {
                vehicle.setExitTime(LocalDateTime.parse(exitTimeStr));
            }

            return vehicle;
        });

        return new FlatFileItemReaderBuilder<VehicleEntity>()
                .name("parkingDataReader")
                .resource(new ClassPathResource("data/parking_batch_data.csv"))
                .linesToSkip(1)
                .lineMapper(lineMapper)
                .build();
    }


    @Bean
    public RepositoryItemWriter<VehicleEntity> parkingDataWriter(VehicleRepository vehicleRepository) {
        RepositoryItemWriter<VehicleEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(vehicleRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step processParkingDataStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<VehicleEntity> reader,
            RepositoryItemWriter<VehicleEntity> writer
    ) {
        return new StepBuilder("processParkingDataStep", jobRepository)
                .<VehicleEntity, VehicleEntity>chunk(50, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Job processParkingDataJob(JobRepository jobRepository, Step processParkingDataStep) {
        return new JobBuilder("processParkingDataJob", jobRepository)
                .start(processParkingDataStep)
                .build();
    }
}
