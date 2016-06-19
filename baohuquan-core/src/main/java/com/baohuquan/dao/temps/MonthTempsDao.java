package com.baohuquan.dao.temps;

import com.baohuquan.model.MonthTemps;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by wangdi5 on 2016/6/19.
 */
@Repository
public interface MonthTempsDao {

    static final String INSERT_COLUMNS = "baby_id,year,month,month_temps,create_time";
    static final String COLUMNS = "id,baby_id AS babyId,year,month,month_temps AS monthTemps,create_time AS createTime";


    @Options(useGeneratedKeys = true,keyProperty="id")
    @Insert("INSERT INTO t_temps (" + INSERT_COLUMNS + ") values(#{babyId},#{year},#{month},#{monthTemps},#{createTime})")
    public int  MonthTemps(MonthTemps temps);


    @Select("SELECT"+COLUMNS+" FROM t_month_temps WHERE year= #{year} and month = #{month} and baby_id= #{babyId}")
    public MonthTemps getMonthTemps(@Param("year") int year,
                                    @Param("month") int month,
                                    @Param("babyId") int babyId
                                    );

    @Update("UPDATE t_month_temps SET month_temps=#{monthTemps} WHERE id=#{id}")
    public int updateMonthTemps(@Param("id") int id,
                                @Param("monthTemps") String monthTemps);

}
