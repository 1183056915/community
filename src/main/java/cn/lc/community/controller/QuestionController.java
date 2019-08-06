package cn.lc.community.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class QuestionController {
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id){
        return "question";
    }
}
