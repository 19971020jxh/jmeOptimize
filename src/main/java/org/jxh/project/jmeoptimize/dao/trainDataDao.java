package org.jxh.project.jmeoptimize.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.*;
import org.jxh.project.jmeoptimize.pojo.trainData;

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
}
