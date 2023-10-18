package ru.spring.school.online.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.school.online.repository.SubjectRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepo;

    public boolean allSubjectsExists(Set<String> names) {
        Iterable<String> DbNames = subjectRepo.findAllNames();
        Set<String> existsNames = new HashSet<>();
        DbNames.forEach(existsNames::add);
        for (String name : names) {
            if (!existsNames.contains(name))
                return false;
        }
        return true;
    }

}
