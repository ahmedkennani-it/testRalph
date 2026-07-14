package com.sahti.backend.controller;

import com.sahti.backend.dto.CaregiverRequest;
import com.sahti.backend.dto.CaregiverResponse;
import com.sahti.backend.security.AuthenticatedUser;
import com.sahti.backend.service.CaregiverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/family-members/{familyMemberId}/caregivers")
public class CaregiverController {

    private final CaregiverService caregiverService;

    public CaregiverController(CaregiverService caregiverService) {
        this.caregiverService = caregiverService;
    }

    @GetMapping
    public List<CaregiverResponse> list(@AuthenticationPrincipal AuthenticatedUser principal,
                                         @PathVariable Long familyMemberId) {
        return caregiverService.list(principal.id(), familyMemberId).stream()
                .map(CaregiverResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<CaregiverResponse> invite(@AuthenticationPrincipal AuthenticatedUser principal,
                                                     @PathVariable Long familyMemberId,
                                                     @Valid @RequestBody CaregiverRequest request) {
        var created = caregiverService.invite(principal.id(), familyMemberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(CaregiverResponse.from(created));
    }
}
