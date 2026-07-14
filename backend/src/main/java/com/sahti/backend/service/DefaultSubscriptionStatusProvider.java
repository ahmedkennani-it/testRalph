package com.sahti.backend.service;

import org.springframework.stereotype.Service;

/**
 * Placeholder used until Phase 9 introduces the real Subscription entity and SubscriptionService.
 * No user has a subscription yet, so everyone is treated as free-tier.
 */
@Service
public class DefaultSubscriptionStatusProvider implements SubscriptionStatusProvider {

    @Override
    public boolean isProActive(Long userId) {
        return false;
    }
}
