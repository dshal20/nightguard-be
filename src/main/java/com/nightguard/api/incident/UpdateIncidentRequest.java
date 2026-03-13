package com.nightguard.api.incident;

import java.util.List;

public class UpdateIncidentRequest {

  private IncidentType type;
  private IncidentSeverity severity;
  private String description;
  private List<String> keywords;
  private IncidentStatus status;

  public IncidentType getType() { return type; }
  public void setType(IncidentType type) { this.type = type; }

  public IncidentSeverity getSeverity() { return severity; }
  public void setSeverity(IncidentSeverity severity) { this.severity = severity; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public List<String> getKeywords() { return keywords; }
  public void setKeywords(List<String> keywords) { this.keywords = keywords; }

  public IncidentStatus getStatus() { return status; }
  public void setStatus(IncidentStatus status) { this.status = status; }
}
