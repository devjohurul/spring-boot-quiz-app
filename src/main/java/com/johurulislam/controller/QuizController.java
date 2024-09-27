package com.johurulislam.controller;

import com.johurulislam.model.QuesFrom;
import com.johurulislam.model.Question;
import com.johurulislam.model.Result;
import com.johurulislam.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizController {
    private final QuizService quizService;
    Result result;
    Boolean submitted;
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }
    @ModelAttribute("result")
    public Result getResult() {
        return result;
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "HomePage";
    }
    @PostMapping("/quiz")
    public String quiz(@RequestParam String username, RedirectAttributes redirectAttributes, Model model) {
        if(username.isEmpty()){
            redirectAttributes.addFlashAttribute("warning", "You must enter a username!");
            return "redirect:/home";
        }
        QuesFrom qForm = new QuesFrom();
        submitted = false;
        result= new Result();
        result.setUsername(username);
        qForm.setQuestions(quizService.getQuestions());
        model.addAttribute("qForm", qForm);
        return "quiz.html";

    }
    @PostMapping("/result")
    public String result(@ModelAttribute QuesFrom qFrom) {
        if(!submitted){
            int totalCorrect = quizService.getResult(qFrom.getQuestions());
            result.setTotalCorrect(totalCorrect);
            submitted = true;
            quizService.saveResult(result);
        }
        return "result.html";
    }
    @GetMapping("/allscore")
    public String score(Model m) {
        List<Result> sList = quizService.getAllResults();
        m.addAttribute("sList", sList);

        return "scoreboard.html";
    }

}
