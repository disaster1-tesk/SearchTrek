package com.search.trek.resource;

import com.search.trek.application.ChatServiceApplication;
import com.search.trek.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/search/trek/chat")
public class ChatResource {

    @Autowired
    private ChatServiceApplication chatServiceApplication;

    @GetMapping("/chatComplete")
    public Result chat(@RequestParam("prompt") String prompt) {
        return chatServiceApplication.chat(prompt);
    }
}
