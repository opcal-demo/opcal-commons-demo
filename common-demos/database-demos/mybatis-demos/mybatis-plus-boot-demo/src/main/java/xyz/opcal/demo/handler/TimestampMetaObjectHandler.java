package xyz.opcal.demo.handler;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

@Component
public class TimestampMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, "createdDate", LocalDateTime::now, LocalDateTime.class);
		this.strictInsertFill(metaObject, "modifiedDate", LocalDateTime::now, LocalDateTime.class);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, "modifiedDate", LocalDateTime::now, LocalDateTime.class);

	}

}
