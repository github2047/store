package org.example.store.service;


import org.example.store.pojo.Message;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService{
    public Message lastMessage();
    public List<Message> last5Message();
    public Page<Message> listByPage(int page, int size, String sort);
    public Page<Message> listByPageDesc(int page, int size, String sort);
}
