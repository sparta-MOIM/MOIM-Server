package com.sparta.moim.organization.presentation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations/{organizationTrackingId}/applications")
@RequiredArgsConstructor
public class OrganizationApplicationController {

    @PostMapping
    
}
