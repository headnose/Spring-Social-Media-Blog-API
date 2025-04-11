package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            return null;
        }

        if (message.getMessageText().length() > 255) {
            return null;
        }

        if (!accountRepository.existsById(message.getPostedBy())) {
            return null;
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id) {
        return messageRepository.findById(id).orElse(null);
    }

    public boolean deleteMessageById(Integer id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public boolean updateMessageText(Integer id, String newText) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent() && newText != null && !newText.isBlank() && newText.length() <= 255) {
            Message message = optionalMessage.get();
            message.setMessageText(newText);
            messageRepository.save(message);
            return true;
        }

        return false;
    }

    public List<Message> getMessageByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

}
