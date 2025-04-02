package xyz.opcal.demo.entity;

import org.eclipse.persistence.annotations.AdditionalCriteria;
import org.eclipse.persistence.annotations.Customizer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import xyz.opcal.demo.descriptor.SdUserDescriptorCustomizer;

@Entity
@Table(name = "sd_user")
@Customizer(SdUserDescriptorCustomizer.class)
@AdditionalCriteria("this.isDeleted=0")
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
