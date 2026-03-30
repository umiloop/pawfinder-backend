package org.pawfinder.controller;

import org.pawfinder.dto.EventDTO;
import org.pawfinder.entity.*;
import org.pawfinder.service.AdminReviewService;
import org.pawfinder.service.impl.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*") // Allow frontend access
public class AdminController {

    @Autowired
    private EventService eventService;

    @Autowired
    private AdminReviewService adminReviewService;

    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
        try {
            Event createdEvent = eventService.createEvent(eventDTO);
            return ResponseEntity.ok(createdEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error creating event: " + e.getMessage());
        }
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        try {
            Event event = eventService.getEventById(id);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error fetching event: " + e.getMessage());
        }
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDTO);
            return ResponseEntity.ok(updatedEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error updating event: " + e.getMessage());
        }
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok().body("Event deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error deleting event: " + e.getMessage());
        }
    }

    // New endpoints for admin review functionality
    @GetMapping("/reports/stray-animals/pending")
    public ResponseEntity<List<StrayAnimalReport>> getPendingStrayAnimalReports() {
        List<StrayAnimalReport> reports = adminReviewService.getPendingStrayAnimalReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/reports/stray-animals/approved")
    public ResponseEntity<List<StrayAnimalReport>> getApprovedStrayAnimalReports() {
        List<StrayAnimalReport> reports = adminReviewService.getApprovedStrayAnimalReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/reports/missing-pets/pending")
    public ResponseEntity<List<MissingPetReport>> getPendingMissingPetReports() {
        List<MissingPetReport> reports = adminReviewService.getPendingMissingPetReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/reports/missing-pets/approved")
    public ResponseEntity<List<MissingPetReport>> getApprovedMissingPetReports() {
        List<MissingPetReport> reports = adminReviewService.getApprovedMissingPetReports();
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/reports/stray-animals/{id}/approve")
    public ResponseEntity<?> approveStrayAnimalReport(@PathVariable Long id) {
        try {
            adminReviewService.approveStrayAnimalReport(id);
            return ResponseEntity.ok().body("Stray animal report approved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error approving report: " + e.getMessage());
        }
    }

    @PostMapping("/reports/missing-pets/{id}/approve")
    public ResponseEntity<?> approveMissingPetReport(@PathVariable Long id) {
        try {
            adminReviewService.approveMissingPetReport(id);
            return ResponseEntity.ok().body("Missing pet report approved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error approving report: " + e.getMessage());
        }
    }

    @DeleteMapping("/reports/stray-animals/{id}")
    public ResponseEntity<?> rejectStrayAnimalReport(@PathVariable Long id) {
        try {
            adminReviewService.rejectStrayAnimalReport(id);
            return ResponseEntity.ok().body("Stray animal report rejected and deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error rejecting report: " + e.getMessage());
        }
    }

    @DeleteMapping("/reports/missing-pets/{id}")
    public ResponseEntity<?> rejectMissingPetReport(@PathVariable Long id) {
        try {
            adminReviewService.rejectMissingPetReport(id);
            return ResponseEntity.ok().body("Missing pet report rejected and deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error rejecting report: " + e.getMessage());
        }
    }

    @GetMapping("/adoptionrequests")
    public ResponseEntity<List<PetAdoptionRequest>> getAllAdoptionRequests() {
        List<PetAdoptionRequest> requests = adminReviewService.getAllAdoptionRequests();
        return ResponseEntity.ok(requests);
    }

}