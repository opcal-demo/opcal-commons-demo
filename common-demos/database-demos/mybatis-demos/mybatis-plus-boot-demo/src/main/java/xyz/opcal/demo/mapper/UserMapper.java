package xyz.opcal.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import xyz.opcal.demo.domain.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
