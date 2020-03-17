package com.coolwe.start.dao;

import java.util.List;
import java.util.Map;

import com.coolwe.start.annotation.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainDao {

  @Update("CREATE DATABASE RUNOOB;")
  public Boolean cc();

  @Query("select table_name from information_schema.tables where table_schema=\"#{database}\"")
  public List<Map<String, Object>> showtables(String req);

  @Query("select * from #{database}.#{tableName}")
  public List<Map<String, Object>> showtable(String req);

  @Query("show databases;")
  public List<Map<String, Object>> showDatabases();

}
