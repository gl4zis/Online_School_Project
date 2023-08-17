package ru.spring.school.online.service;

import org.springframework.stereotype.Service;
import ru.spring.school.online.exception.IdNotFoundException;
import ru.spring.school.online.model.security.Group;
import ru.spring.school.online.repository.GroupRepository;

import java.util.Optional;

@Service
public class GroupService {
    final GroupRepository groupRepo;


    public GroupService(GroupRepository groupRepository) {
        this.groupRepo = groupRepository;
    }

    public Group findGroup(Long id) {
        Optional<Group> groupOpt = groupRepo.findById(id);
        if (groupOpt.isPresent()) {
            return groupOpt.get();
        }
        throw new IdNotFoundException(String.format("Group with id %d not found.", id));
    }

    public Iterable<Group> allGroups() {
        return groupRepo.findAll();
    }

    public void saveGroup(Group group) {
        groupRepo.save(group);
    }

    public boolean deleteGroup(Long id) {
        if (groupRepo.findById(id).isPresent()) {
            groupRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
