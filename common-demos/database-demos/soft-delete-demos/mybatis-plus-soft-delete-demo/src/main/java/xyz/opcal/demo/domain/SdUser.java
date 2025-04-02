package xyz.opcal.demo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "sd_user", autoResultMap = true)
@Data
@NoArgsConstructor
public class SdUser implements Serializable {

	private static final long serialVersionUID = 4447256607699370956L;

	@TableId(type = IdType.AUTO)
	private Long id;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createdDate;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime modifiedDate;

	private String firstName;
	private String lastName;
	private String gender;
	private Integer age;

	@TableLogic
	private Integer isDeleted = 0;

}
