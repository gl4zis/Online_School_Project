package ru.spring.school.online.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.school.online.model.course.Subject;
import ru.spring.school.online.repository.SubjectRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepo;

    public boolean allSubjectsExists(Set<String> names) {
        Iterable<Subject> existsSubjects = subjectRepo.findAll();
        Set<String> existsNames = new HashSet<>();
        existsSubjects.forEach(sub -> existsNames.add(sub.getName()));
        for (String name : names) {
            if (!existsNames.contains(name))
                return false;
        }
        return true;
    }
}
