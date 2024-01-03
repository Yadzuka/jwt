package ru.yadzuka.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yadzuka.entities.Role;
import ru.yadzuka.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getRolesByName(Role.Names name) {
        return List.of(roleRepository.findByName(name.name()).get());
    }
}
