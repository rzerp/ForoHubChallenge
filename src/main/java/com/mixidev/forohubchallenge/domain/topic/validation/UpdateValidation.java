package com.mixidev.forohubchallenge.domain.topic.validation;
import com.mixidev.forohubchallenge.domain.topic.TopicDataUpdate;
import com.mixidev.forohubchallenge.domain.topic.UpdateTopic;
import com.mixidev.forohubchallenge.domain.user.User;

public interface UpdateValidation {
    void validate(TopicDataUpdate data, User user);

    void validate(UpdateTopic data, User user);
}
