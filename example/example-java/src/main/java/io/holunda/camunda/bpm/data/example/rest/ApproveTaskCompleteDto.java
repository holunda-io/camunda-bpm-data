package io.holunda.camunda.bpm.data.example.rest;

public class ApproveTaskCompleteDto {
  private Boolean approved;

  public ApproveTaskCompleteDto() {

  }

  public ApproveTaskCompleteDto(Boolean approved) {this.approved = approved;}

  public Boolean getApproved() {
    return approved;
  }

  public void setApproved(Boolean approved) {
    this.approved = approved;
  }

  @Override
  public String toString() {
    return "ApproveTaskCompleteDto{" +
      "approved=" + approved +
      '}';
  }
}
