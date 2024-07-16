package com.mixidev.forohubchallenge.domain.topic;

import com.mixidev.forohubchallenge.domain.topic.validation.UpdateValidation;
import com.mixidev.forohubchallenge.domain.topic.validation.ValidationTopical;
import com.mixidev.forohubchallenge.domain.user.User;
import com.mixidev.forohubchallenge.domain.user.UserRepository;
import com.mixidev.forohubchallenge.infra.error.IntegrityValidation;
import com.mixidev.forohubchallenge.infra.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTopic {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private List<ValidationTopical> validations;
    @Autowired
    private List<UpdateValidation> updateTopicals;

    public Topic addTopic(DataTopic topic, HttpServletRequest request) {
        var user = getAuthenticatedUser(request);
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new IntegrityValidation("Este id para este usuario no existe");
        }
        validations.forEach(v -> v.validate(topic));
        return topicRepository.save(new Topic(user, topic));
    }

    private User getAuthenticatedUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.replace("Bearer ", "");
        String subject = tokenService.getSubject(token);
        return (User) userRepository.findByEmail(subject);
    }

    public DetailTopical update(Long id, UpdateTopic data, HttpServletRequest request) {
        if (data.title() == null && data.message() == null) {
            throw new IntegrityValidation("No hay nada para editar");
        }
        if (!topicRepository.existsById(id)) {
            throw new IntegrityValidation("No hay topicos con ese id");
        }

        var user = getAuthenticatedUser(request);
        DataTopic dataTopic = new DataTopic(data.title(), data.message(), null);
        validations.forEach(v -> v.validate(dataTopic));
        updateTopicals.forEach(v -> v.validate(data, user));

        var topic = topicRepository.getReferenceById(id);
        if (data.title() != null && data.message() != null) {
            topic.setMessage(data.message());
            topic.setTitle(data.title());
        } else if (data.title() == null) {
            topic.setMessage(data.message());
        } else {
            topic.setTitle(data.title());
        }
        topic.update();
        return new DetailTopical(topic);
    }

    public void delete(Long id, HttpServletRequest request) {
        if (!topicRepository.existsById(id)) {
            throw new IntegrityValidation("No existe el topico");
        }
        var user = getAuthenticatedUser(request);
        TopicDataUpdate data = new TopicDataUpdate(id, null, null);
        updateTopicals.forEach(v -> v.validate(data, user));
        var topic = topicRepository.getReferenceById(id);
        topic.delete();
    }
}
