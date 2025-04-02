package xyz.opcal.demo.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import graphql.schema.DataFetchingEnvironment;

public record UserDTO(Long id, LocalDateTime createdDate, LocalDateTime modifiedDate, String firstName, String lastName, String gender, Integer age) {

	public String getCreatedDate(DataFetchingEnvironment environment) {
		return format(environment, createdDate);
	}

	public String getModifiedDate(DataFetchingEnvironment environment) {
		return format(environment, modifiedDate);
	}

	String format(DataFetchingEnvironment environment, TemporalAccessor dateTime) {
		String dateFormat = environment.getArgument("dateFormat");
		return DateTimeFormatter.ofPattern(dateFormat).format(dateTime);
	}
}
