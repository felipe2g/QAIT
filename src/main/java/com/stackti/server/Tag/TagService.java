package com.stackti.server.Tag;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAllByQuestionId(int question_id) {
        return tagRepository.findAllByQuestionId(question_id);
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    public void delete(int id) {
        tagRepository.delete(id);
    }
}
