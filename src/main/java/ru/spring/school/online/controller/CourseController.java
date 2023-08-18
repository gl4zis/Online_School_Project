package ru.spring.school.online.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.spring.school.online.exception.IdNotFoundException;
import ru.spring.school.online.model.security.*;
import ru.spring.school.online.service.CourseService;
import ru.spring.school.online.service.GroupService;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final GroupService groupService;

    public CourseController(CourseService courseService, GroupService groupService) {
        this.courseService = courseService;
        this.groupService = groupService;
    }

    @GetMapping("/{courseId}/promo")
    public String getCoursePromo(@PathVariable Long courseId,
                                 Model model,
                                 @AuthenticationPrincipal User user) {

        if (isUserHasGroupById(user, courseId, model, true)) {
            Group group = (Group) model.getAttribute("group");
            assert group != null;
            return String.format("redirect:/course/%d/curriculum", group.getId());
        }
        if (isUserBoughtCourse(user, courseId, model)) {
            return String.format("redirect:/course/%d/promise", courseId);
        }
        try {
            Course course = courseService.findCourse(courseId);
            model.addAttribute("course", course);
            return "course_promo";
        } catch (IdNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{groupId}/curriculum")
    public String getCourseCurriculum(@PathVariable Long groupId,
                                      Model model,
                                      @AuthenticationPrincipal User user) {

        if (isUserHasGroupById(user, groupId, model, false)) {
            return "course_curriculum";
        }
        try {
            Group group = groupService.findGroup(groupId);
            Long courseId = group.getCourse().getId();
            if (isUserBoughtCourse(user, courseId, model)) {
                return String.format("redirect:/course/%d/promise", courseId);
            }
            if (user.getRole() == User.Role.ADMIN) {
                model.addAttribute("group", group);
                return "course_curriculum";
            }
            return String.format("redirect:/course/%d/promo", courseId);
        } catch (IdNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{courseId}/promise")
    public String getCoursePromise(@PathVariable Long courseId,
                                   Model model,
                                   @AuthenticationPrincipal User user){
        if (isUserHasGroupById(user, courseId, model, true)) {
            Group group = (Group) model.getAttribute("group");
            assert group != null;
            return String.format("redirect:/course/%d/curriculum", group.getId());
        }
        if (isUserBoughtCourse(user, courseId, model)) {
            return "course_promise";
        }
        if (user.getRole() == User.Role.ADMIN) {
            model.addAttribute("course", courseService.findCourse(courseId));
            return "course_promise";
        }
        return String.format("redirect:/course/%d/promo", courseId);
    }


    private boolean isUserBoughtCourse(User user, Long courseId, Model model) {
        if (user instanceof Student student) {
            if (student.getCourses() != null){
                if (student.getCourses().stream()
                        .anyMatch(course -> course.getId().equals(courseId))) {
                    model.addAttribute("course", courseService.findCourse(courseId));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isUserHasGroupById(User user,
                                     Long id,
                                     Model model,
                                     boolean isCourseId) {
        if (user instanceof Student student) {
            if (student.getStudentGroups() != null){
                return hasGroup(student.getStudentGroups().stream().map(StudentGroup::getGroup),
                        id,
                        model,
                        isCourseId);
            }
        }
        if (user instanceof Teacher teacher) {
            if (teacher.getGroups() != null) {
                return hasGroup(teacher.getGroups().stream(),
                        id,
                        model,
                        isCourseId);
            }
        }
        return false;
    }

    private boolean hasGroup(Stream<Group> groupStream,
                                     Long id,
                                     Model model,
                                     boolean isCourseId) {
        Predicate<Group> predicate = isCourseId ?
                (group -> group.getCourse().getId().equals(id)) :
                (group -> group.getId().equals(id));

        Optional<Group> groupOpt = groupStream
                .filter(predicate)
                .findFirst();

        if (groupOpt.isPresent()) {
            model.addAttribute("group", groupOpt.get());
            return true;
        }
        return false;
    }
}
