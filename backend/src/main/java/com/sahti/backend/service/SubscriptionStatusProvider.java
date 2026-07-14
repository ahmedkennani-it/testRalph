package com.sahti.backend.service;

/**
 * Abstraction over the Pro subscription status of a user, so that Pro-gated business rules
 * (e.g. the free-tier family member limit) can be implemented and tested before the real
 * Subscription model exists (see Phase 9). {@link com.sahti.backend.service.SubscriptionService}
 * will implement this interface once it is introduced.
 */
public interface SubscriptionStatusProvider {

    boolean isProActive(Long userId);
}
