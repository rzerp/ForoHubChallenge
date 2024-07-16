package com.mixidev.forohubchallenge.domain.topic.validation;
import com.mixidev.forohubchallenge.domain.topic.DataTopic;
import com.mixidev.forohubchallenge.domain.topic.UpdateTopic;

public interface ValidationTopical {
    void validate(DataTopic data);

    void validate(UpdateTopic data);
}
