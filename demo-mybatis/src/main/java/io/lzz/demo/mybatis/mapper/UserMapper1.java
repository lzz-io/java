/*
 * Copyright qq:1219331697
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.lzz.demo.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.lzz.demo.mybatis.entity.User1;

/**
 * @author q1219331697
 *
 */
@Mapper
public interface UserMapper1 {

	@Select("select * from user")
	public List<User1> findAll();

	@Select("select * from user where userId = #{id}")
	public User1 findById(Long id);

	@Insert("insert into user(username, password) values(#{username}, #{password})")
	public void insert(User1 user);

	@Update("update user set username = #{username}, password = #{password} where userId = #{userId}")
	public void update(User1 user);

	@Delete("delete from user where userId = #{id}")
	public void delete(Long id);

}
