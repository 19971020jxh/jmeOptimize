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
            "INSERT INTO trainData ( " +
            "<trim suffixOverrides=','>" +
            "<if test='shuitou!=null and shuitou!='''>" +
            "shuitou," +
            "</if>" +
            "<if test='chuli!=null and chuli!='''>" +
            "chuli," +
            "</if>" +
            "<if test='liuLiang!=null and liuLiang!='''>" +
            "liuLiang" +
            "</if></trim>" +
            ") VALUES " +
            "<trim prefix='(' suffix=')' suffixOverrides=','>" +
            "<if test='shuitou!=null and shuitou!='''>" +
            "#{shuitou}," +
            "</if>" +
            "<if test='chuli!=null and chuli!='''>" +
            "#{chuli}," +
            "</if>" +
            "<if test='liuLiang!=null and liuLiang!='''>" +
            "#{liuLiang}" +
            "</if></trim>" +
            "</trim>"+
            "</script>")
    void addData(@Param("shuitou")Integer shuitou,@Param("chuli")Integer chuli,@Param("liuLiang")Integer liuLiang);


    @Select("SELECT xiaolv,  id FROM trainData WHERE id=#{id}")
    @Results({
            @Result(property = "xiaoLvQuXian",column = "id",
                    many = @Many(select="org.jxh.project.jmeoptimize.dao.trainDataDao.ff"))
    })
     trainData updateSELECT(@Param("id")Integer id);


    @Update("<script>" +
            "UPDATE   trainData  SET " +
            "<trim suffixOverrides=','>" +
            "<if test='shuitou!=null and shuitou!='''>" +
            "shuitou=#{shuitou}," +
            "</if>" +
            "<if test='chuli!=null and chuli!='''>" +
            "chuli=#{chuli}," +
            "</if>" +
            "<if test='liuLiang!=null and liuLiang!='''>" +
            "liuLiang=#{liuLiang}" +
            "</if></trim>" +
            " WHERE id=#{id}"+
            "</script>")
    void getUpdate(@Param("id")Integer id,@Param("shuitou")Integer shuitou,@Param("chuli")Integer chuli,@Param("liuLiang")Integer liuLiang);

    @Delete("DELETE FROM trianData WHERE id=#{id}")
    void getDelete(@Param("id")Integer id);
}
