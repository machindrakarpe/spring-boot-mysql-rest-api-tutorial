package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Candidate;
import com.example.easynotes.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Satish on 10/08/2020
 */
@RestController
@RequestMapping("/api")
public class CandidateController {

    @Autowired
    CandidateRepository candidateRepository;

    @GetMapping("/candidate")
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @PostMapping("/candidate")
    public Candidate createCandidate(@Valid @RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @GetMapping("/candidate/{id}")
    public Candidate getCandidateById(@PathVariable(value = "id") Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate", "id", candidateId));
    }

    @PutMapping("/candidate/{id}")
    public Candidate updateCandidate(@PathVariable(value = "id") Long candidateId,
                                @Valid @RequestBody Candidate candidateDetails) {

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate", "id", candidateId));

        candidate.setName(candidateDetails.getName());
        candidate.setEmail(candidateDetails.getEmail());
        candidate.setMobile(candidateDetails.getMobile());
        candidate.setAddress(candidateDetails.getAddress());

        Candidate updatedCandidate = candidateRepository.save(candidate);
        return updatedCandidate;
    }

    @DeleteMapping("/candidate/{id}")
    public ResponseEntity<?> deleteCandidate(@PathVariable(value = "id") Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate", "id", candidateId));

        candidateRepository.delete(candidate);

        return ResponseEntity.ok().build();
    }
}
