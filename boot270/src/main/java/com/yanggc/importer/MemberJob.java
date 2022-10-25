package com.yanggc.importer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
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
public class MemberJob {

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
    public Job importMemberJob() {
        return jobBuilderFactory.get("importMemberJob")
                //执行step
                .start(importMemberJobStep()).build();
    }

    @Bean
    public Step importMemberJobStep() {
        return stepBuilderFactory.get("importMemberJobStep").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext){
                System.out.println("SpringBatch Hello World！");
                // 返回的状态
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

}
