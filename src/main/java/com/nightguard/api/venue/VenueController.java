package com.nightguard.api.venue;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nightguard.api.notification.NotificationService;
import com.nightguard.api.notification.NotificationSubscription;
import com.nightguard.api.notification.SubscribeRequest;

@RestController
@RequestMapping("/venues")
public class VenueController {

  private final VenueService venueService;
  private final VenueCapacityService venueCapacityService;
  private final VenueHeadcountService venueHeadcountService;
  private final NotificationService notificationService;

  public VenueController(VenueService venueService, VenueCapacityService venueCapacityService,
      VenueHeadcountService venueHeadcountService, NotificationService notificationService) {
    this.venueService = venueService;
    this.venueCapacityService = venueCapacityService;
    this.venueHeadcountService = venueHeadcountService;
    this.notificationService = notificationService;
  }

  @PostMapping
  public ResponseEntity<Venue> createVenue(@RequestBody CreateVenueRequest request) {
    return ResponseEntity.ok(venueService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<Venue>> getVenues(Authentication authentication) {
    return ResponseEntity.ok(venueService.getAllForUser(authentication.getName()));
  }

  @GetMapping("/nearby")
  public ResponseEntity<List<NearbyVenueResponse>> getNearbyVenues(
      @RequestParam String city,
      @RequestParam String state,
      @RequestParam(required = false) String zip) {
    List<NearbyVenueResponse> venues = venueService.getNearbyVenues(city, state, zip).stream()
        .map(NearbyVenueResponse::from)
        .toList();
    return ResponseEntity.ok(venues);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Venue> getVenue(@PathVariable UUID id) {
    return ResponseEntity.ok(venueService.getById(id));
  }

  @PostMapping("/{id}/members")
  public ResponseEntity<List<VenueMemberResponse>> addMembers(
      @PathVariable UUID id,
      @RequestBody List<AddVenueMemberRequest> requests,
      Authentication authentication) {
    List<VenueMemberResponse> members = venueService.addMembers(id, requests, authentication.getName()).stream()
        .map(VenueMemberResponse::from)
        .toList();
    return ResponseEntity.ok(members);
  }

  @GetMapping("/{id}/members")
  public ResponseEntity<List<VenueMemberResponse>> getMembers(@PathVariable UUID id) {
    List<VenueMemberResponse> members = venueService.getMembers(id).stream()
        .map(VenueMemberResponse::from)
        .toList();
    return ResponseEntity.ok(members);
  }

  @DeleteMapping("/{id}/members/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeMember(
      @PathVariable UUID id,
      @PathVariable String userId,
      Authentication authentication) {
    venueService.removeMember(id, userId, authentication.getName());
  }

  @PostMapping("/join/{code}")
  public ResponseEntity<VenueMemberResponse> joinVenue(
      @PathVariable String code,
      Authentication authentication) {
    VenueMember member = venueService.joinByInviteCode(code, authentication.getName());
    return ResponseEntity.ok(VenueMemberResponse.from(member));
  }

  @PatchMapping("/{id}/members/{userId}")
  public ResponseEntity<VenueMemberResponse> updateMemberRole(
      @PathVariable UUID id,
      @PathVariable String userId,
      @RequestBody UpdateMemberRoleRequest request,
      Authentication authentication) {
    VenueMember updated = venueService.updateMemberRole(id, userId, request, authentication.getName());
    return ResponseEntity.ok(VenueMemberResponse.from(updated));
  }

  @GetMapping("/{id}/capacity")
  public ResponseEntity<VenueCapacityResponse> getCapacity(
      @PathVariable UUID id,
      Authentication authentication) {
    VenueCapacity capacity = venueCapacityService.getCapacity(id, authentication.getName());
    return ResponseEntity.ok(VenueCapacityResponse.from(capacity));
  }

  @PutMapping("/{id}/capacity")
  public ResponseEntity<VenueCapacityResponse> setCapacity(
      @PathVariable UUID id,
      @RequestBody SetCapacityRequest request,
      Authentication authentication) {
    VenueCapacity capacity = venueCapacityService.setCapacity(id, request, authentication.getName());
    return ResponseEntity.ok(VenueCapacityResponse.from(capacity));
  }

  @GetMapping("/{id}/headcount")
  public ResponseEntity<List<VenueHeadcountResponse>> getHeadcounts(
      @PathVariable UUID id,
      Authentication authentication) {
    return ResponseEntity.ok(venueHeadcountService.getHeadcounts(id, authentication.getName()));
  }

  @PostMapping("/{id}/headcount")
  public ResponseEntity<VenueHeadcountResponse> addHeadcount(
      @PathVariable UUID id,
      @RequestBody AddHeadcountRequest request,
      Authentication authentication) {
    return ResponseEntity.ok(venueHeadcountService.addHeadcount(id, request, authentication.getName()));
  }

  @GetMapping("/{id}/subscriptions")
  public ResponseEntity<List<NotificationSubscription>> getSubscriptions(@PathVariable UUID id) {
    return ResponseEntity.ok(notificationService.getSubscriptions(id));
  }

  @PostMapping("/{id}/subscriptions")
  public ResponseEntity<List<NotificationSubscription>> subscribe(
      @PathVariable UUID id,
      @RequestBody SubscribeRequest request) {
    return ResponseEntity.ok(notificationService.subscribe(id, request.getVenueIds()));
  }

  @DeleteMapping("/{id}/subscriptions/{venueId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unsubscribe(@PathVariable UUID id, @PathVariable UUID venueId) {
    notificationService.unsubscribe(id, venueId);
  }
}
