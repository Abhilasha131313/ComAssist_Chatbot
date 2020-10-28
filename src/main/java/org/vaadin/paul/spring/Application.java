package org.vaadin.paul.spring;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.concurrent.Executors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ScheduledExecutorService;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.vaadin.artur.helpers.LaunchUtil;
import org.vaadin.paul.spring.ui.views.ChatMessage;

//import reactor.core.publisher.Flux;
//import reactor.core.publisher.UnicastProcessor;

/**
 * The entry point of the Spring Boot application.
 */

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	
    private final String botPath;

    public Application(@Value("${bot.path}") String botPath) {
        this.botPath = botPath;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ScheduledExecutorService executorService() {
        return Executors.newScheduledThreadPool(10);
    }

    @EventListener
    public void createBots(ApplicationReadyEvent event) {
        File[] directories = new File(botPath + "/bots")
                .listFiles((FileFilter) FileFilterUtils.directoryFileFilter());

        if (directories != null) {
            Arrays.stream(directories)
                    .map(File::getName)
                    .map(name -> new Bot(BotConfiguration.builder()
                            .name(name)
                            .path(botPath)
                            .build()))
                    .forEach(bot -> event.getApplicationContext().getBeanFactory().registerSingleton(bot.getName(), bot));
        }
    }

}
