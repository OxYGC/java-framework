package com.yanggc.batch;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description:
 *
 * @author: YangGC
 */
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig extends DefaultBatchConfigurer {

    private final static int CORE_POOL_SIZE = 10;
    private final static int MAX_POOL_SIZE = 200;
    private final static int QUEUE_CAPACITY = 10;
    private final static int KEEP_ALIVE_SECONDS = 60;

    /**
     * jobLauncher定义： job的启动器,绑定相关的jobRepository
     *
     * @return
     * @throws Exception
     */
    @Override
    protected JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.setTaskExecutor(batchExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }


    private TaskExecutor batchExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置线程名称前缀
        taskExecutor.setThreadNamePrefix("import-task-executor-");
        //最小线程数
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        //最大线程数
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        //等待队列
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        //等待时常
        taskExecutor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

}
