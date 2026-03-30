package org.pawfinder.service;

import org.pawfinder.entity.MissingPetReport;
import org.pawfinder.entity.PetAdoptionRequest;
import org.pawfinder.entity.StrayAnimalReport;
import java.util.List;

public interface AdminReviewService {
    List<StrayAnimalReport> getPendingStrayAnimalReports();
    List<StrayAnimalReport> getApprovedStrayAnimalReports();
    List<MissingPetReport> getPendingMissingPetReports();
    List<MissingPetReport> getApprovedMissingPetReports();
    void approveStrayAnimalReport(Long id);
    void approveMissingPetReport(Long id);
    void rejectStrayAnimalReport(Long id);
    void rejectMissingPetReport(Long id);
    List<PetAdoptionRequest> getAllAdoptionRequests();

} 