package cn.lc.community.service;

import cn.lc.community.dto.QuestionDTO;
import cn.lc.community.mapper.QuestionMapper;
import cn.lc.community.mapper.UserMapper;
import cn.lc.community.model.Question;
import cn.lc.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question:questions){
          User user = userMapper.findById(question.getCreator());
         QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
