package com.xyphx.getwarranty.service;

import com.xyphx.getwarranty.model.Service;
import com.xyphx.getwarranty.model.User;
import com.xyphx.getwarranty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> addService(String userId, Service service) {
        return userRepository.findById(userId).map(user -> {
            service.setCreatedAt(LocalDateTime.now());
            service.setUpdatedAt(LocalDateTime.now());
            user.getServices().add(service);
            return userRepository.save(user);
        });
    }

    public Optional<User> updateService(String userId, String serviceId, Service updatedService) {
        return userRepository.findById(userId).map(user -> {
            List<Service> services = user.getServices();
            for (int i = 0; i < services.size(); i++) {
                if (services.get(i).getId().equals(serviceId)) {
                    updatedService.setId(serviceId);
                    updatedService.setCreatedAt(services.get(i).getCreatedAt());
                    updatedService.setUpdatedAt(LocalDateTime.now());
                    services.set(i, updatedService);
                    return userRepository.save(user);
                }
            }
            return null;
        });
    }

    public Optional<User> deleteService(String userId, String serviceId) {
        return userRepository.findById(userId).map(user -> {
            boolean removed = user.getServices().removeIf(service -> service.getId().equals(serviceId));
            if (removed) {
                return userRepository.save(user);
            }
            return null;
        });
    }
}
