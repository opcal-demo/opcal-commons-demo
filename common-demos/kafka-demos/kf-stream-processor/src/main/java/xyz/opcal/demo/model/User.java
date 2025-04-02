package xyz.opcal.demo.model;

import java.time.LocalDateTime;

public record User(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String firstName, String lastName, String gender, Integer age) {

}
