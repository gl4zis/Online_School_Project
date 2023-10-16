package ru.spring.school.online.service;

import org.springframework.stereotype.Service;
import ru.spring.school.online.exception.IdNotFoundException;
import ru.spring.school.online.model.course.Lesson;
import ru.spring.school.online.repository.LessonRepository;

import java.util.Optional;

@Service
public class LessonService {
    final LessonRepository lessonRepo;


    public LessonService(LessonRepository lessonRepo) {
        this.lessonRepo = lessonRepo;
    }

    public Lesson findLesson(Long id) {
        Optional<Lesson> lessonOpt = lessonRepo.findById(id);
        if (lessonOpt.isPresent()) {
            return lessonOpt.get();
        }
        throw new IdNotFoundException(String.format("Lesson with id %d not found.", id));
    }

    public Iterable<Lesson> allLessons() {
        return lessonRepo.findAll();
    }

    public void saveLesson(Lesson lesson) {
        lessonRepo.save(lesson);
    }

    public boolean deleteLesson(Long id) {
        if (lessonRepo.findById(id).isPresent()) {
            lessonRepo.deleteById(id);
            return true;
        }
        return false;
    }
}