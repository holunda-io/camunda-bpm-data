package io.holunda.camunda.bpm.data.example.rest;

/**
 * DTO to carry the approve task response.
 */
public class ApproveTaskCompleteDto {
  /**
   * Response value.
   */
  private Boolean approved;

  /**
   * Empty constructor.
   */
  public ApproveTaskCompleteDto() {
  }

  /**
   * Constructs DTO with response.
   *
   * @param approved response value.
   */
  public ApproveTaskCompleteDto(Boolean approved) {
    this.approved = approved;
  }

  /**
   * Retrieves response value.
   *
   * @return response value.
   */
  public Boolean getApproved() {
    return approved;
  }

  /**
   * Sets response value.
   *
   * @param approved response value to set.
   */
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
