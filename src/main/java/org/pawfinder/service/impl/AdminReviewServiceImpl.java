package org.pawfinder.service.impl;

import org.pawfinder.dao.MissingPetReportRepository;
import org.pawfinder.dao.PetAdoptionRequestRepository;
import org.pawfinder.dao.StrayAnimalReportRepository;
import org.pawfinder.entity.MissingPetReport;
import org.pawfinder.entity.PetAdoptionRequest;
import org.pawfinder.entity.StrayAnimalReport;
import org.pawfinder.service.AdminReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminReviewServiceImpl implements AdminReviewService {

    @Autowired
    private StrayAnimalReportRepository strayAnimalReportRepository;

    @Autowired
    private MissingPetReportRepository missingPetReportRepository;

    @Autowired
    private PetAdoptionRequestRepository petAdoptionRequestRepository;


    @Override
    public List<StrayAnimalReport> getPendingStrayAnimalReports() {
        return strayAnimalReportRepository.findByReviewStatus("Pending");
    }

    @Override
    public List<StrayAnimalReport> getApprovedStrayAnimalReports() {
        return strayAnimalReportRepository.findByReviewStatus("Approved");
    }

    @Override
    public List<MissingPetReport> getPendingMissingPetReports() {
        return missingPetReportRepository.findByReviewStatus("Pending");
    }

    @Override
    public List<MissingPetReport> getApprovedMissingPetReports() {
        return missingPetReportRepository.findByReviewStatus("Approved");
    }

    @Override
    @Transactional
    public void approveStrayAnimalReport(Long id) {
        StrayAnimalReport report = strayAnimalReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stray animal report not found"));
        report.setReviewStatus("Approved");
        strayAnimalReportRepository.save(report);
    }

    @Override
    @Transactional
    public void approveMissingPetReport(Long id) {
        MissingPetReport report = missingPetReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missing pet report not found"));
        report.setReviewStatus("Approved");
        missingPetReportRepository.save(report);
    }

    @Override
    @Transactional
    public void rejectStrayAnimalReport(Long id) {
        if (!strayAnimalReportRepository.existsById(id)) {
            throw new RuntimeException("Stray animal report not found");
        }
        strayAnimalReportRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void rejectMissingPetReport(Long id) {
        if (!missingPetReportRepository.existsById(id)) {
            throw new RuntimeException("Missing pet report not found");
        }
        missingPetReportRepository.deleteById(id);
    }

    @Override
    public List<PetAdoptionRequest> getAllAdoptionRequests() {
        return petAdoptionRequestRepository.findAll();
    }
} 