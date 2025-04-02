package xyz.opcal.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("xyz.opcal.demo.mapper")
public class MybatisPlusSDApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusSDApplication.class, args);
	}

}
