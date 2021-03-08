package com.mario.movietickets.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mario.movietickets.entities.Role;
import com.mario.movietickets.repositories.RoleRepository;

@Service
public class RoleService {

	private final RoleRepository roleRepository;
	
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public List<Role> findAll(){
		return roleRepository.findAll();
	}
	
	public Role findById(Long id) {
		return roleRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("Role with id[" + id + "] doesn't exist"));
	}
}
