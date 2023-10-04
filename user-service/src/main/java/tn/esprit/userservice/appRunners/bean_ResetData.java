package tn.esprit.userservice.appRunners;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;


@Order(value=1)//Register BeanRunnerOne bean
@Slf4j
@Component
public class bean_ResetData implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        log.info("Bean One of Reset Data  run method Started !!");
    }


}