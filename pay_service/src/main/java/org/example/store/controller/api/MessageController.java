package org.example.store.controller.api;


import org.example.store.pojo.Message;
import org.example.store.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class MessageController {
    private MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("query")
    public List<Message> query(
            @RequestParam(name = "size", defaultValue = "5",required = false) Integer size,
            @RequestParam(name = "page", defaultValue = "1",required = false) Integer page) {
        Page<Message> messages = messageService.listByPageDesc(page, size, "id");
        return messages.toList();
    }
}
