package com.mixidev.forohubchallenge.domain.topic.validation;

import com.mixidev.forohubchallenge.domain.topic.DataTopic;
import com.mixidev.forohubchallenge.domain.topic.TopicRepository;
import com.mixidev.forohubchallenge.domain.topic.UpdateTopic;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageValidation implements ValidationTopical {
    @Autowired
    TopicRepository topicRepository;

    @Override
    public void validate(DataTopic dataTopic) {
        var message = topicRepository.existsByMessage(dataTopic.message());
        if (message) {
            throw new ValidationException("There is already a topic with that message.");
        }
    }

    @Override
    public void validate(UpdateTopic data) {
        var message = topicRepository.existsByMessage(data.message());
        if (message) {
            throw new ValidationException("There is already a topic with that message.");
        }
    }
}
