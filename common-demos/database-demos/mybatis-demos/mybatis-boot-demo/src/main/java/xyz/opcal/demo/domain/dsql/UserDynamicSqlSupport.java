package xyz.opcal.demo.domain.dsql;

import java.sql.JDBCType;
import java.time.LocalDateTime;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDynamicSqlSupport {

	public static final UserTable user = new UserTable();
	public static final SqlColumn<Long> id = user.id;
	public static final SqlColumn<LocalDateTime> createdDate = user.createdDate;
	public static final SqlColumn<LocalDateTime> modifiedDate = user.modifiedDate;
	public static final SqlColumn<String> firstName = user.firstName;
	public static final SqlColumn<String> lastName = user.lastName;
	public static final SqlColumn<String> gender = user.gender;
	public static final SqlColumn<Integer> age = user.age;

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class UserColumn {

		public static final String ID = "id";
		public static final String CREATED_DATE = "created_date";
		public static final String MODIFIED_DATE = "modified_date";
		public static final String FIRST_NAME = "first_name";
		public static final String LAST_NAME = "last_name";
		public static final String GENDER = "gender";
		public static final String AGE = "age";

	}

	public static final class UserTable extends SqlTable {

		public final SqlColumn<Long> id = column(UserColumn.ID, JDBCType.BIGINT);
		public final SqlColumn<LocalDateTime> createdDate = column(UserColumn.CREATED_DATE, JDBCType.TIMESTAMP);
		public final SqlColumn<LocalDateTime> modifiedDate = column(UserColumn.MODIFIED_DATE, JDBCType.TIMESTAMP);
		public final SqlColumn<String> firstName = column(UserColumn.FIRST_NAME, JDBCType.VARCHAR);
		public final SqlColumn<String> lastName = column(UserColumn.LAST_NAME, JDBCType.VARCHAR);
		public final SqlColumn<String> gender = column(UserColumn.GENDER, JDBCType.VARCHAR);
		public final SqlColumn<Integer> age = column(UserColumn.AGE, JDBCType.INTEGER);

		public UserTable() {
			super("user");
		}
	}
}
