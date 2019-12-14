package org.jxh.project.jmeoptimize.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.*;
import org.jxh.project.jmeoptimize.pojo.trainData;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface trainDataDao {



    @Select("SELECT DISTINCT jiQi FROM trainData")
    int[] jiQis();

    @Select("SELECT xiaolv,  id FROM trainData WHERE jiQi=#{jiqi}")
    @Results({
            @Result(property = "xiaoLvQuXian",column = "id",
            many = @Many(select="org.jxh.project.jmeoptimize.dao.trainDataDao.ff"))
    })
    List<trainData> getData(@Param("jiqi")int jiqi);
    @Select("SELECT chuli FROM trainData WHERE id=#{id} union ALL\n" +
            "SELECT shuitou FROM trainData WHERE id=#{id}")
    String[] ff(@Param("id") int id);

    @Select("SELECT  id, shuitou,chuli,liuLiang FROM trainData WHERE jiQi=#{jiqi}")
    List<JSONObject> getData_shuJuWeiHu(@Param("jiqi")int jiqi);

    @Insert("<script>" +
            "INSERT INTO trainData (   " +
            "<trim suffixOverrides=','> jiqi ,ORDER_TR, " +
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
            "<trim prefix='(' suffix=')' suffixOverrides=','> #{jiqi},#{orderTR}," +
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
    void addData(@Param("shuitou")Double shuitou,@Param("chuli")Double chuli,@Param("liuLiang")Double liuLiang,@Param("jiqi")int jiqi,@Param("orderTR")float orderTR);


    @Select("SELECT xiaolv,  id FROM trainData WHERE id=#{id}")
    @Results({
            @Result(property = "xiaoLvQuXian",column = "id",
                    many = @Many(select="org.jxh.project.jmeoptimize.dao.trainDataDao.ff"))
    })
     trainData updateSELECT(@Param("id")Integer id);


    @Update("<script>" +
            "UPDATE trainData  SET " +
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
    void getUpdate(@Param("id")Integer id,@Param("shuitou")Double shuitou,@Param("chuli")Double chuli,@Param("liuLiang")Double liuLiang);

    @Delete("DELETE FROM trainData WHERE id=#{id}")
    void getDelete(@Param("id")Integer id);

    @Select("SELECT (${shuitou}>max(shuitou)) AS `shuitouInfo`, (${liuLiang}>max(liuLiang)) AS `liuLiangInfo`  FROM trainData")
    JSONObject isOut(@Param("shuitou")Double shuitou,@Param("liuLiang")Double liuLiang);
}
