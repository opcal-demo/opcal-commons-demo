package xyz.opcal.demo.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User implements Serializable {

	public static final String ID = "id";
	public static final String CREATED_DATE = "createdDate";
	public static final String MODIFIED_DATE = "modifiedDate";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String GENDER = "gender";
	public static final String AGE = "age";

	@Serial
	private static final long serialVersionUID = 4447256607699370956L;

	private Long id;

	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private String firstName;
	private String lastName;
	private String gender;
	private Integer age;

}
