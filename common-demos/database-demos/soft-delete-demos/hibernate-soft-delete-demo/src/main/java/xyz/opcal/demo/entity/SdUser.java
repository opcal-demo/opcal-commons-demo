package xyz.opcal.demo.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "sd_user")
@SQLDelete(sql = "UPDATE sd_user SET is_deleted=1 WHERE id=?")
@Where(clause = "is_deleted=0")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SdUser extends AbstractEntity {

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "gender")
	private String gender;

	@Column(name = "age")
	private Integer age;

	@Column(name = "is_deleted")
	private Integer isDeleted = 0;

}
