package com.nightguard.api.venue;

public class SetCapacityRequest {

  private Integer capacity;
  private Integer currentOccupancy;

  public Integer getCapacity() { return capacity; }
  public void setCapacity(Integer capacity) { this.capacity = capacity; }

  public Integer getCurrentOccupancy() { return currentOccupancy; }
  public void setCurrentOccupancy(Integer currentOccupancy) { this.currentOccupancy = currentOccupancy; }
}
