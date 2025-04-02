package xyz.opcal.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public record User(@Id Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String firstName, String lastName, String gender, Integer age) {

}
