package com.yanggc.Controller;

import com.yanggc.pojo.MemberPO;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author: YangGC
 */
@RestController
public class JobLauncherController {


    @RequestMapping("/jobLauncher")
    public SimpleJobLauncher jobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        // 设置jobRepository
//        jobLauncher.setJobRepository(new MemberJob());
        return jobLauncher;
    }

    @RequestMapping("/reader")
    public FlatFileItemReader<MemberPO> reader() {
        // 使用FlatFileItemReader去读cvs文件，一行即一条数据
        FlatFileItemReader<MemberPO> reader = new FlatFileItemReader<>();
        // 设置文件处在路径
        reader.setResource(new ClassPathResource("member.csv"));
        // entity与csv数据做映射
        reader.setLineMapper(new DefaultLineMapper<MemberPO>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[]{"id", "name", "age", "gender"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<MemberPO>() {
                    {
                        setTargetType(MemberPO.class);
                    }
                });
            }
        });
        return reader;
    }
}
