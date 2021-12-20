package com.diegotobalina.framework;

import com.beust.jcommander.JCommander;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
  public static void main(String[] argv) {
    var springApplication = new SpringApplication();
    JCommander.newBuilder().addObject(springApplication).build().parse(argv);
    SpringApplication.run(Main.class);
  }
}
