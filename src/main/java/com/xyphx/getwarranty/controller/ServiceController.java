package com.xyphx.getwarranty.controller;

import com.xyphx.getwarranty.model.Service;
import com.xyphx.getwarranty.service.ServiceService;
import com.xyphx.getwarranty.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addService(@PathVariable String userId, @RequestBody Service service) {
        return serviceService.addService(userId, service)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{userId}/{serviceId}")
    public ResponseEntity<?> updateService(
            @PathVariable String userId,
            @PathVariable String serviceId,
            @RequestBody Service updatedService) {
        User result = serviceService.updateService(userId, serviceId, updatedService).orElse(null);
        if (result != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.status(404).body("Service not found");
    }

    @DeleteMapping("/delete/{userId}/{serviceId}")
    public ResponseEntity<?> deleteService(@PathVariable String userId, @PathVariable String serviceId) {
        User result = serviceService.deleteService(userId, serviceId).orElse(null);
        if (result != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.status(404).body("Service not found");
    }
}
