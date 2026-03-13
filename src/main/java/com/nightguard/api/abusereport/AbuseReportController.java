package com.nightguard.api.abusereport;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/abuse-reports")
public class AbuseReportController {

  private final AbuseReportRepository abuseReportRepository;

  public AbuseReportController(AbuseReportRepository abuseReportRepository) {
    this.abuseReportRepository = abuseReportRepository;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void submit(@RequestBody SubmitAbuseReportRequest request) {
    AbuseReport report = new AbuseReport();
    report.setFirstName(request.getFirstName());
    report.setLastName(request.getLastName());
    report.setEmail(request.getEmail());
    report.setPhoneNumber(request.getPhoneNumber());
    report.setMessage(request.getMessage());
    abuseReportRepository.save(report);
  }
}
