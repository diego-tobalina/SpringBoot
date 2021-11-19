package com.xbidi.spring.config;

import com.xbidi.spring.content.shared.output.ErrorResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/** @author diegotobalina created on 14/08/2020 */
@Configuration
@EnableScheduling
public class SchedulingConfigurerImpl implements SchedulingConfigurer {

  private final ThreadPoolTaskScheduler taskScheduler;

  public SchedulingConfigurerImpl() {
    var newTaskScheduler = new ThreadPoolTaskScheduler();
    newTaskScheduler.setErrorHandler(this::processException);
    newTaskScheduler.setThreadNamePrefix("@scheduled-");
    newTaskScheduler.initialize();
    this.taskScheduler = newTaskScheduler;
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(taskScheduler);
  }

  private void processException(Throwable throwable) {
    new ErrorResponse(throwable).printMessage();
  }
}
