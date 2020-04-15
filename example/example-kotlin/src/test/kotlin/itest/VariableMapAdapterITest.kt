package io.holunda.camunda.bpm.data.itest

import io.holunda.camunda.bpm.data.CamundaBpmData.*
import io.holunda.camunda.bpm.data.adapter.VariableNotFoundException
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException
import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.BOOLEAN
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DATE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DOUBLE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.INT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LIST_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LONG
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.MAP_STRING_DATE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SET_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SHORT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.STRING
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class VariableMapAdapterITest : CamundaBpmDataITestBase() {

  @Suppress("RedundantVisibilityModifier")
  @get: Rule
  public val thrown: ExpectedException = ExpectedException.none()

  @Autowired
  lateinit var delegateConfiguration: DelegateConfiguration

  @Test
  fun `should write to map`() {

    val variables = createVariables()

    STRING_VAR.on(variables).set(STRING.value)
    DATE_VAR.on(variables).set(DATE.value)
    SHORT_VAR.on(variables).set(SHORT.value)
    INT_VAR.on(variables).set(INT.value)
    LONG_VAR.on(variables).set(LONG.value)
    DOUBLE_VAR.on(variables).set(DOUBLE.value)
    BOOLEAN_VAR.on(variables).set(BOOLEAN.value)
    COMPLEX_VAR.on(variables).set(COMPLEX.value)
    LIST_STRING_VAR.on(variables).set(LIST_STRING.value)
    SET_STRING_VAR.on(variables).set(SET_STRING.value)
    MAP_STRING_DATE_VAR.on(variables).set(MAP_STRING_DATE.value)

    given()
      .process_with_delegate_is_deployed(delegateExpression = "\${${delegateConfiguration::readFromVariableScope.name}}")
    whenever()
      .process_is_started_with_variables(variables = variables)
    then()
      .variables_had_value(delegateConfiguration.vars, createKeyValuePairs())
  }

  @Test
  fun `should read from map`() {

    given()
      .process_with_delegate_is_deployed(delegateExpression = "\${${DelegateConfiguration::readFromVariableMap.name}}")
    whenever()
      .process_is_started_with_variables(variables = createVariableMapUntyped())
    then()
      .variables_had_value(delegateConfiguration.vars, createKeyValuePairs())
  }

  @Test
  fun `should remove from map`() {

    val variables = createVariableMapUntyped()

    STRING_VAR.on(variables).remove()
    LIST_STRING_VAR.on(variables).remove()
    SET_STRING_VAR.on(variables).remove()
    MAP_STRING_DATE_VAR.on(variables).remove()

    given()
      .process_with_delegate_is_deployed(delegateExpression = "\${${DelegateConfiguration::readOptionalFromVariableScope.name}}")

    whenever()
      .process_is_started_with_variables(variables = variables)

    then()
      .variables_had_not_value(delegateConfiguration.optionalVars,
        STRING_VAR,
        LIST_STRING_VAR,
        SET_STRING_VAR,
        MAP_STRING_DATE_VAR
      )
      .and()
      .variables_had_value(delegateConfiguration.optionalVars,
        setOf(LONG_VAR to Optional.of(LONG.value))
      )
  }

  @Test
  fun `should throw correct VNF exception`() {
    thrown.expect(VariableNotFoundException::class.java)
    val nonExistent: VariableFactory<String> = stringVariable("non-existent")
    nonExistent.from(createVariableMapUntyped()).get()
  }

  @Test
  fun `should throw correct UO exception on basic setLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    STRING_VAR.on(createVariableMapUntyped()).setLocal(STRING.value)
  }

  @Test
  fun `should throw correct UO exception on list setLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    LIST_STRING_VAR.on(createVariableMapUntyped()).setLocal(LIST_STRING.value)
  }

  @Test
  fun `should throw correct UO exception on set setLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    SET_STRING_VAR.on(createVariableMapUntyped()).setLocal(SET_STRING.value)
  }

  @Test
  fun `should throw correct UO exception on map setLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    MAP_STRING_DATE_VAR.on(createVariableMapUntyped()).setLocal(MAP_STRING_DATE.value)
  }

  @Test
  fun `should throw correct UO exception on basic removeLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    STRING_VAR.on(createVariableMapUntyped()).removeLocal()
  }

  @Test
  fun `should throw correct UO exception on list removeLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    LIST_STRING_VAR.on(createVariableMapUntyped()).removeLocal()
  }

  @Test
  fun `should throw correct UO exception on set removeLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    SET_STRING_VAR.on(createVariableMapUntyped()).removeLocal()
  }

  @Test
  fun `should throw correct UO exception on map removeLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    MAP_STRING_DATE_VAR.on(createVariableMapUntyped()).removeLocal()
  }
  @Test
  fun `should throw correct UO exception on basic getLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    STRING_VAR.from(createVariableMapUntyped()).local
  }

  @Test
  fun `should throw correct UO exception on list getLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    LIST_STRING_VAR.from(createVariableMapUntyped()).local
  }

  @Test
  fun `should throw correct UO exception on set getLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    SET_STRING_VAR.from(createVariableMapUntyped()).local
  }

  @Test
  fun `should throw correct UO exception on map getLocal`() {
    thrown.expect(UnsupportedOperationException::class.java)
    MAP_STRING_DATE_VAR.from(createVariableMapUntyped()).local
  }

  @Test
  fun `should throw correct UO exception on basic getLocalOptional`() {
    thrown.expect(UnsupportedOperationException::class.java)
    STRING_VAR.from(createVariableMapUntyped()).localOptional
  }

  @Test
  fun `should throw correct UO exception on list getLocalOptional`() {
    thrown.expect(UnsupportedOperationException::class.java)
    LIST_STRING_VAR.from(createVariableMapUntyped()).localOptional
  }

  @Test
  fun `should throw correct UO exception on set getLocalOptional`() {
    thrown.expect(UnsupportedOperationException::class.java)
    SET_STRING_VAR.from(createVariableMapUntyped()).localOptional
  }

  @Test
  fun `should throw correct UO exception on map getLocalOptional`() {
    thrown.expect(UnsupportedOperationException::class.java)
    MAP_STRING_DATE_VAR.from(createVariableMapUntyped()).localOptional
  }

  @Test
  fun `should throw correct WVT exception on basic`() {

    thrown.expect(WrongVariableTypeException::class.java)

    val variables = createVariableMapUntyped()
    val wrongBasicType: VariableFactory<Int> = intVariable(STRING_VAR.name)

    // wrong type
    wrongBasicType.from(variables).get()
  }

  @Test
  fun `should throw correct WVT exception on basic optional`() {

    thrown.expect(WrongVariableTypeException::class.java)

    val variables = createVariableMapUntyped()
    val wrongBasicType: VariableFactory<Int> = intVariable(STRING_VAR.name)

    // wrong type
    wrongBasicType.from(variables).optional
  }

  @Test
  fun `should throw correct WVT exception on list `() {

    thrown.expect(WrongVariableTypeException::class.java)

    val variables = createVariableMapUntyped()
    val wrongListType: VariableFactory<List<Date>> = listVariable(STRING_VAR.name, Date::class.java)

    // not a list
    wrongListType.from(variables).get()
  }

  @Test
  fun `should throw correct WVT exception on list optional`() {

    thrown.expect(WrongVariableTypeException::class.java)

    val variables = createVariableMapUntyped()
    val wrongListType: VariableFactory<List<Date>> = listVariable(STRING_VAR.name, Date::class.java)

    // not a list
    wrongListType.from(variables).optional
  }

  @Test
  fun `should throw correct WVT exception on list target`() {

    thrown.expect(WrongVariableTypeException::class.java)

    val variables = createVariableMapUntyped()
    val wrongListTargetType: VariableFactory<List<Date>> = listVariable(LIST_STRING_VAR.name, Date::class.java)

    // wrong type of the list
    wrongListTargetType.from(variables).get()
  }

  @Test
  fun `should throw correct WVT exception on list target optional`() {

    thrown.expect(WrongVariableTypeException::class.java)

    val variables = createVariableMapUntyped()
    val wrongListTargetType: VariableFactory<List<Date>> = listVariable(LIST_STRING_VAR.name, Date::class.java)
    wrongListTargetType.from(variables).optional
  }

  @Test
  fun `should throw correct WVT exception on set `() {

    thrown.expect(WrongVariableTypeException::class.java)
    val variables = createVariableMapUntyped()
    val wrongSetType: VariableFactory<Set<Date>> = setVariable(STRING_VAR.name, Date::class.java)

    // not a set
    wrongSetType.from(variables).get()
  }

  @Test
  fun `should throw correct WVT exception on set optional`() {

    thrown.expect(WrongVariableTypeException::class.java)
    val variables = createVariableMapUntyped()
    val wrongSetType: VariableFactory<Set<Date>> = setVariable(STRING_VAR.name, Date::class.java)

    // not a set
    wrongSetType.from(variables).optional
  }

  @Test
  fun `should throw correct WVT exception on set target`() {

    thrown.expect(WrongVariableTypeException::class.java)
    val variables = createVariableMapUntyped()
    val wrongSetTargetType: VariableFactory<Set<Date>> = setVariable(SET_STRING_VAR.name, Date::class.java)

    // wrong type of the set
    wrongSetTargetType.from(variables).get()
  }

  @Test
  fun `should throw correct WVT exception on set target optional`() {

    thrown.expect(WrongVariableTypeException::class.java)
    val variables = createVariableMapUntyped()
    val wrongSetTargetType: VariableFactory<Set<Date>> = setVariable(SET_STRING_VAR.name, Date::class.java)

    // wrong type of the set
    wrongSetTargetType.from(variables).optional
  }

  @Test
  fun `should throw correct WVT exception on map`() {

    thrown.expect(WrongVariableTypeException::class.java)

    val variables = createVariableMapUntyped()
    val wrongMapType: VariableFactory<Map<Date, Date>> = mapVariable(STRING_VAR.name, Date::class.java, Date::class.java)

    // not a map
    wrongMapType.from(variables).get()
  }

  @Test
  fun `should throw correct WVT exception on map optional`() {

    thrown.expect(WrongVariableTypeException::class.java)

    val variables = createVariableMapUntyped()
    val wrongMapType: VariableFactory<Map<Date, Date>> = mapVariable(STRING_VAR.name, Date::class.java, Date::class.java)

    // not a map
    wrongMapType.from(variables).optional
  }

  @Test
  fun `should throw correct WVT exception on key map`() {

    thrown.expect(WrongVariableTypeException::class.java)

    val variables = createVariableMapUntyped()
    val wrongMapKeyType: VariableFactory<Map<Date, Date>> = mapVariable(MAP_STRING_DATE_VAR.name, Date::class.java, Date::class.java)
    // wrong key type
    wrongMapKeyType.from(variables).get()
  }

  @Test
  fun `should throw correct WVT exception on map key optional`() {

    thrown.expect(WrongVariableTypeException::class.java)
    val variables = createVariableMapUntyped()
    val wrongMapKeyType: VariableFactory<Map<Date, Date>> = mapVariable(MAP_STRING_DATE_VAR.name, Date::class.java, Date::class.java)
    // wrong key type
    wrongMapKeyType.from(variables).optional
  }

  @Test
  fun `should throw correct WVT exception on value map`() {

    thrown.expect(WrongVariableTypeException::class.java)
    val variables = createVariableMapUntyped()
    val wrongMapValueType: VariableFactory<Map<String, String>> = mapVariable(MAP_STRING_DATE_VAR.name, String::class.java, String::class.java)

    // wrong value type
    wrongMapValueType.from(variables).get()
  }

  @Test
  fun `should throw correct WVT exception on map value optional`() {

    thrown.expect(WrongVariableTypeException::class.java)
    val variables = createVariableMapUntyped()
    val wrongMapValueType: VariableFactory<Map<String, String>> = mapVariable(MAP_STRING_DATE_VAR.name, String::class.java, String::class.java)

    // wrong value type
    wrongMapValueType.from(variables).optional
  }
}

