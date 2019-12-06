package io.holunda.camunda.bpm.data.example;

import io.holunda.camunda.bpm.data.factory.VariableFactory;

import java.util.Date;

import static io.holunda.camunda.bpm.data.CamundaBpmData.*;

public class ApprovalProcessConstants {

  public static final VariableFactory<String> APPROVAL_ID = stringVariable("approval_id");
  public static final VariableFactory<Date> APPROVAL_DATE = dateVariable("approval_date");
  public static final VariableFactory<Integer> APPROVAL_QUANTITY = intVariable("approval_quantity");
  public static final VariableFactory<Long> APPROVAL_QUANTITY_LONG = longVariable("approval_quantity_long");
  public static final VariableFactory<Short> APPROVAL_QUANTITY_SHORT = shortVariable("approval_quantity_short");
  public static final VariableFactory<Double> APPROVAL_AMOUNT = doubleVariable("approval_amount");
  public static final VariableFactory<Boolean> APPROVAL_CRITICAL = booleanVariable("approval_critical");
}
