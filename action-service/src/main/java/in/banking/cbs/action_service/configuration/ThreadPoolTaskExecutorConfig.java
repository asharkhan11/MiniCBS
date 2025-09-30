package in.banking.cbs.action_service.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class ThreadPoolTaskExecutorConfig {


    @Bean("fileHandlerExecutor")
    public TaskExecutor threadPoolTaskExecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(7);
        executor.setQueueCapacity(50);
        executor.setKeepAliveSeconds(10);
        executor.setThreadGroupName("FileHandler-");

        return executor;
    }

}
