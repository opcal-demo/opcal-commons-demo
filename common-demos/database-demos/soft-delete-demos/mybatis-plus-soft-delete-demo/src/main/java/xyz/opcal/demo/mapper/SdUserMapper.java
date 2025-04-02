package xyz.opcal.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import xyz.opcal.demo.domain.SdUser;

@Mapper
public interface SdUserMapper extends BaseMapper<SdUser> {

}
