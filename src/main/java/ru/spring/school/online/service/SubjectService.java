package ru.spring.school.online.service;

import org.springframework.stereotype.Service;
import ru.spring.school.online.model.security.Subject;
import ru.spring.school.online.repository.SubjectRepository;

@Service
public class SubjectService {

    final SubjectRepository subjectRepo;

    public SubjectService(SubjectRepository subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    public Iterable<Subject> allSubjects() {
        return subjectRepo.findAll();
    }
}
