package xyz.opcal.demo.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;

import xyz.opcal.demo.domain.User;
import xyz.opcal.demo.domain.dsql.UserDynamicSqlSupport.UserColumn;

@Mapper
public interface UserMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {

	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "row.id")
	int insert(InsertStatementProvider<User> insertStatement);

	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "UserResult", value = { @Result(column = UserColumn.ID, property = User.ID, jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = UserColumn.CREATED_DATE, property = User.CREATED_DATE, jdbcType = JdbcType.TIMESTAMP),
			@Result(column = UserColumn.MODIFIED_DATE, property = User.MODIFIED_DATE, jdbcType = JdbcType.TIMESTAMP),
			@Result(column = UserColumn.FIRST_NAME, property = User.FIRST_NAME, jdbcType = JdbcType.VARCHAR),
			@Result(column = UserColumn.LAST_NAME, property = User.LAST_NAME, jdbcType = JdbcType.VARCHAR),
			@Result(column = UserColumn.GENDER, property = User.GENDER, jdbcType = JdbcType.VARCHAR),
			@Result(column = UserColumn.AGE, property = User.AGE, jdbcType = JdbcType.INTEGER) })
	User selectLastOne(SelectStatementProvider selectStatement);
}
