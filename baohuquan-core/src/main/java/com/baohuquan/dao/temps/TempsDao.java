package com.baohuquan.dao.temps;

import com.baohuquan.model.Temps;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by wangdi5 on 2016/3/23.
 */
@Repository
public interface TempsDao {

    static final String COLUMNS = "id,b_id AS babyId,temps_date AS tempsDate,temps,high_temp AS highTemp,get_time AS getTime,create_time AS createTime";
    static final String INSERT_COLUMNS = "b_id,temps_date,temps,high_temp,get_time,create_time";


    @Select("SELECT " + COLUMNS + " FROM t_temps where temps_date = #{tempsDate} and  b_id=#{babyId}")
    public Temps getTemp(@Param("babyId") int babyId,@Param("tempsDate") String tempsDate);

    @Options(useGeneratedKeys = true,keyProperty="id")
    @Insert("INSERT INTO t_temps (" + INSERT_COLUMNS + ") values(#{babyId},#{tempsDate},#{temps},#{highTemp},#{getTime},#{createTime})")
    public int saveTemp(Temps temps);

    @Update(" UPDATE t_temps SET temps=#{temps},high_temp=#{highTemp} where id = #{id}")
    public int updateTemps(@Param("id") int id, @Param("temps") String temps, @Param("highTemp") int highTemp);

    @Delete("DELETE FROM t_temps where b_id=#{babyId}")
    public int delByBaby(@Param("babyId") int babyId);

    @Select("SELECT " + COLUMNS + " FROM t_temps where  b_id=#{babyId} ORDER BY get_time DESC")
    public List<Temps> getTempByBaby(@Param("babyId") int babyId);


    @Select("SELECT  " + COLUMNS + " FROM t_temps where get_time >= #{start} and get_time <= #{end} and b_id =#{babyId}")
    public List<Temps> getTempByTime(@Param("babyId") int babyId, @Param("start") Date start, @Param("end") Date end);


}
