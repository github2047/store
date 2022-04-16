package org.example.store.service.impl;


import org.example.store.dao.MessageDao;
import org.example.store.pojo.Message;
import org.example.store.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageDao messageDao;


    @Override
    public Message lastMessage() {
        return messageDao.findLast();
    }

    @Override
    public List<Message> last5Message() {
        return listByPage(1, 5, "id", Sort.Direction.DESC).toList();
    }

    public Page<Message> listByPage(int page, int size, String sort) {
        return listByPage(page, size, sort, Sort.Direction.ASC);
    }

    public Page<Message> listByPageDesc(int page, int size, String sort) {
        return listByPage(page, size, sort, Sort.Direction.DESC);
    }

    public Page<Message> listByPage(int page, int size, String sort, Sort.Direction direction) {
        return messageDao.findAll(PageRequest.of(page - 1, size, Sort.by(direction, sort)));
    }

}
