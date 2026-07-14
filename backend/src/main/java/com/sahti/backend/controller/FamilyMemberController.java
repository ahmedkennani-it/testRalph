package com.sahti.backend.controller;

import com.sahti.backend.dto.FamilyMemberRequest;
import com.sahti.backend.dto.FamilyMemberResponse;
import com.sahti.backend.security.AuthenticatedUser;
import com.sahti.backend.service.FamilyMemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/family-members")
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

    public FamilyMemberController(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    @GetMapping
    public List<FamilyMemberResponse> list(@AuthenticationPrincipal AuthenticatedUser principal) {
        return familyMemberService.list(principal.id()).stream()
                .map(FamilyMemberResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<FamilyMemberResponse> create(@AuthenticationPrincipal AuthenticatedUser principal,
                                                        @Valid @RequestBody FamilyMemberRequest request) {
        var created = familyMemberService.create(principal.id(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(FamilyMemberResponse.from(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamilyMemberResponse> update(@AuthenticationPrincipal AuthenticatedUser principal,
                                                        @PathVariable Long id,
                                                        @Valid @RequestBody FamilyMemberRequest request) {
        var updated = familyMemberService.update(principal.id(), id, request);
        return ResponseEntity.ok(FamilyMemberResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal AuthenticatedUser principal, @PathVariable Long id) {
        familyMemberService.delete(principal.id(), id);
        return ResponseEntity.noContent().build();
    }
}
