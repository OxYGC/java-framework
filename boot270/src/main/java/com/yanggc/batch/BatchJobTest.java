package com.yanggc.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author: YangGC
 */

@Configuration
@EnableBatchProcessing
public class BatchJobTest {
    /**
     * 创建任务对象
     */
    @Resource
    private JobBuilderFactory jobBuilderFactory;

    /**
     * 执行任务对象
     */
    @Resource
    private StepBuilderFactory stepBuilderFactory;

    /**
     * 创建任务
     *
     * @return
     */
    @Bean
    public Job helloWorld() {
        return jobBuilderFactory.get("helloWorld")
                //执行step
                .start(step()).build();
    }

    @Bean
    public Step step() {
        try {
            return stepBuilderFactory.get("step").tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext){
                    System.out.println("SpringBatch Hello World！");
                    // 返回的状态
                    return RepeatStatus.FINISHED;
                }
            }).build();

        }catch (Exception e){
         e.printStackTrace();
        }
        return null;
    }

}
