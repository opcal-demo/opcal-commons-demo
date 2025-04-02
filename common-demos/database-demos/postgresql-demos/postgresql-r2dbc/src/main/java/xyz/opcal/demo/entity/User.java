package xyz.opcal.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_tb")
public record User(@Id Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String firstName, String lastName, String gender, Integer age) {

}
