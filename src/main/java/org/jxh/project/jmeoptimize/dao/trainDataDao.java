package org.jxh.project.jmeoptimize.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.*;
import org.jxh.project.jmeoptimize.pojo.trainData;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface trainDataDao {

//    @Select("SELECT DISTINCT jiQi FROM trainData")
//    int[] jiQis();

    @Select("SELECT xiaolv,  id FROM trainData_1")
    @Results({
            @Result(property = "xiaoLvQuXian",column = "id",
                    many = @Many(select="org.jxh.project.jmeoptimize.dao.trainDataDao.ff1"))
    })
    List<trainData> getData_1();
    @Select("SELECT xiaolv,  id FROM trainData_2")
    @Results({
            @Result(property = "xiaoLvQuXian",column = "id",
                    many = @Many(select="org.jxh.project.jmeoptimize.dao.trainDataDao.ff2"))
    })
    List<trainData> getData_2();

    @Select(
            "SELECT chuli FROM trainData_1 WHERE id=#{id}   union ALL\n" +
            "SELECT shuitou FROM trainData_1 WHERE id=#{id}  "
          )
    String[] ff1(@Param("id") Integer id);
    @Select(
            "SELECT chuli FROM trainData_2 WHERE id=#{id}   union ALL\n" +
            "SELECT shuitou FROM trainData_2 WHERE id=#{id}  "
    )
    String[] ff2(@Param("id") Integer id);

    @Select("SELECT  * FROM trainData_${jiqi}")
    List<JSONObject> getData_shuJuWeiHu(@Param("jiqi")int jiqi);

    @Select("SELECT  * FROM trainData_${jiqi}")
    List<trainData> getDataAll(@Param("jiqi")int jiqi);

    @Delete("DELETE FROM trainData_${jiqi}")
    void delete(@Param("jiqi")int jiqi);

    @Insert("<script>" +
            "INSERT INTO trainData_${jiqi} (   " +
            "<trim suffixOverrides=','>" +
            "<if test=\" xiaolv!=null and xiaolv!='' \" >" +
            "xiaolv," +
            "</if>" +
//            " ORDER_TR, " +
            "<if test=\" shuitou!=null and shuitou!='' \" >" +
            "shuitou," +
            "</if>" +
            "<if test=\" chuli!=null and chuli!='' \">" +
            "chuli," +
            "</if>" +
            "<if test=\" liuLiang!=null and liuLiang!='' \">" +
            "liuLiang" +
            "</if> </trim>" +
            ") VALUES " +
            "<trim prefix='(' suffix=')' suffixOverrides=','>" +
            "<if test=\" xiaolv!=null and xiaolv!='' \">" +
            "#{xiaolv}," +
            "</if>" +
//            "#{orderTR}," +
            "<if test=\" shuitou!=null and shuitou!='' \">" +
            "#{shuitou}," +
            "</if>" +
            "<if test=\" chuli!=null and chuli!='' \" >" +
            "#{chuli}," +
            "</if>" +
            "<if test=\" liuLiang!=null and liuLiang!='' \">" +
            "#{liuLiang}" +
            "</if> " +
            "</trim>"+
            "</script>")
    void addData(@Param("shuitou")Double shuitou,@Param("chuli")Double chuli,@Param("liuLiang")Double liuLiang,@Param("jiqi")int jiqi,@Param("orderTR")Float orderTR,@Param("xiaolv")Integer xiaolv);

    @Select("SELECT * FROM trainData_${jiqi} ")
    List<trainData> excels(@Param("jiqi")Integer jiqi);

    @Select("SELECT xiaolv,  id FROM trainData_1 WHERE id=#{id}")
    @Results({
            @Result(property = "xiaoLvQuXian",column = "id",
                    many = @Many(select="org.jxh.project.jmeoptimize.dao.trainDataDao.ff1"))
    })
     trainData updateSELECT_1(@Param("id")Integer id);
    @Select("SELECT xiaolv,  id FROM trainData_2 WHERE id=#{id}")
    @Results({
            @Result(property = "xiaoLvQuXian",column = "id",
                    many = @Many(select="org.jxh.project.jmeoptimize.dao.trainDataDao.ff2"))
    })
    trainData updateSELECT_2(@Param("id")Integer id);


    @Update("<script>" +
            "UPDATE trainData_${jiqi}  SET " +
            "<trim suffixOverrides=','>" +
            "<if test=\" shuitou!=null and shuitou!='' \">" +
            "shuitou=#{shuitou}," +
            "</if>" +
            "<if test=\"chuli!=null and chuli!='' \">" +
            "chuli=#{chuli}," +
            "</if>" +
            "<if test=\" liuLiang!=null and liuLiang!='' \">" +
            "liuLiang=#{liuLiang}" +
            "</if> </trim>" +
            " WHERE id=#{id}"+
            "</script>")
    void getUpdate(@Param("id")Integer id,@Param("jiqi")int jiqi, @Param("shuitou")Double shuitou,@Param("chuli")Double chuli,@Param("liuLiang")Double liuLiang);

    @Delete("DELETE FROM trainData_${jiqi} WHERE id=#{id}")
    void getDelete(@Param("id")Integer id,@Param("jiqi")int jiqi);

    @Select("SELECT (${shuitou}>max(shuitou)) AS `shuitouInfo`, (${liuLiang}>max(liuLiang)) AS `liuLiangInfo`  FROM trainData")
    JSONObject isOut(@Param("shuitou")Double shuitou,@Param("liuLiang")Double liuLiang);


    @Delete("DELETE FROM trainData")
    void deleteAll();
}
