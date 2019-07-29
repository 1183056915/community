package cn.lc.community.mapper;

import cn.lc.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tags) values (#{title},#{description},#{gmtCreate}," +
            "#{gmtModified},#{creator},#{tags})")
    void create(Question qusetion);
}
