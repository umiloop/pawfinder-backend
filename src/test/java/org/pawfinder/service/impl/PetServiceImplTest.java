package org.pawfinder.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.pawfinder.dao.*;
import org.pawfinder.dto.MissingPetReportDTO;
import org.pawfinder.dto.StrayAnimalReportDTO;
import org.pawfinder.entity.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceImplTest {

    @InjectMocks
    private PetServiceImpl petService;

    @Mock
    private RehomePetRepository rehomePetRepository;
    @Mock
    private ShelterPetRepository shelterPetRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private PetSheltersRepository petShelterRepository;
    @Mock
    private StrayAnimalReportRepository reportRepository;
    @Mock
    private MissingPetReportRepository reportMissingPetRepository;
    @Mock
    private PetAdoptionRequestRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetApprovedPets() {
        Pet pet = new Pet();
        pet.setPetName("Buddy");

        when(petRepository.findByReviewStatus("Approved")).thenReturn(Collections.singletonList(pet));

        List<Pet> result = petService.getApprovedPets();

        assertEquals(1, result.size());
        assertEquals("Buddy", result.get(0).getPetName());
    }

    @Test
    void testSaveMissingPetReport() {
        MissingPetReportDTO dto = new MissingPetReportDTO();
        dto.setPetName("Charlie");
        dto.setPetType("Dog");
        dto.setBreed("Labrador");
        dto.setAge(5);
        dto.setAgeUnit("Years");
        dto.setGender("Male");
        dto.setDescription("Lost near park");
        dto.setLocation_address("Main Street");
        dto.setLocation_city("Test City");
        dto.setLocation_coordinates("6.8344,79.8765");
        dto.setLocation_details("Near the temple");
        dto.setUserId(1001L);
        dto.setUsername("TestUser");
        dto.setOwnerName("Test Owner");
        dto.setPhoneNumber("0770000000");
        dto.setEmail("test@example.com");
        dto.setContactPreference("Phone");
        dto.setOfferReward(true);
        dto.setRewardAmount(5000.0);
        dto.setPhotoURLs(Arrays.asList("test_img1.jpg", "test_img2.jpg"));

        petService.saveMissingPetReport(dto);

        verify(reportMissingPetRepository, times(1)).save(any(MissingPetReport.class));
    }

    @Test
    void testSaveStrayAnimalReport() {
        StrayAnimalReportDTO dto = new StrayAnimalReportDTO();
        dto.setAnimalType("Cat");
        dto.setInjured(true);
        dto.setStray(true);
        dto.setMalnourished(false);
        dto.setDescription("Injured cat found");
        dto.setAnimalStatus("Injured");
        dto.setActionRequired("Medical Attention");
        dto.setLocationText("Test Park");
        dto.setCity("Test City");
        dto.setPostalCode("00000");
        dto.setLocationDetails("Under the bench");
        dto.setLatitude(6.9271);
        dto.setLongitude(79.8612);
        dto.setContactName("Test User");
        dto.setContactPhone("0770000000");
        dto.setContactEmail("test@example.com");
        dto.setUserId(1002L);
        dto.setUsername("TestUser");
        dto.setPhotoUrls(Collections.singletonList("test_cat.jpg"));

        petService.saveReport(dto);

        verify(reportRepository, times(1)).save(any(StrayAnimalReport.class));
    }

    @Test
    void testSaveAdoptionRequest() {
        PetAdoptionRequest request = new PetAdoptionRequest();
        request.setId(1003L);
        request.setUserId(1004L);

        when(repository.save(request)).thenReturn(request);

        PetAdoptionRequest saved = petService.saveAdoptionRequest(request);

        assertEquals(1003L, saved.getId());
        assertEquals(1004L, saved.getUserId());
    }
}
