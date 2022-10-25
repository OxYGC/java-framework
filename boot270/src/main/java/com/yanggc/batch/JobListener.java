package com.yanggc.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * Job监听器: 主要为任务开始课结束的的处理
 */
@Component
public class JobListener implements JobExecutionListener {

    Logger logger = LoggerFactory.getLogger(JobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info( "JobStarting...." );
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info( "JobStarted ...." );
        ExitStatus exitStatus = jobExecution.getExitStatus();

        Long taskId = Long.parseLong(jobExecution.getJobParameters().getString("taskId"));
        if (exitStatus.getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
            Object fileUri = jobExecution.getExecutionContext().get("fileUri");
            String fileUriStr = fileUri == null ? "" : fileUri.toString();
            Integer totalNum = jobExecution.getExecutionContext().getInt("record.total");
            Integer successNum = jobExecution.getExecutionContext().getInt("record.success");
            Integer failNum = jobExecution.getExecutionContext().getInt("record.fail");
            System.out.println("文件的URL为: " + fileUriStr + "记录总数量: " + totalNum + "记录成功数量: " + successNum + "记录失败数量: " + failNum);
        }
    }
}
