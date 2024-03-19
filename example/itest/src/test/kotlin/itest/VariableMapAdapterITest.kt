package io.holunda.camunda.bpm.data.itest

import io.holunda.camunda.bpm.data.CamundaBpmData.intVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.listVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.mapVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.setVariable
import io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable
import io.holunda.camunda.bpm.data.adapter.VariableNotFoundException
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException
import io.holunda.camunda.bpm.data.factory.VariableFactory
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.BOOLEAN
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX_LIST
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX_MAP
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.COMPLEX_SET
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DATE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.DOUBLE
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.INT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LIST_MAP_STRING_OBJECT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LIST_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.LONG
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.MAP_STRING_LONG
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SET_STRING
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.SHORT
import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase.Companion.Values.STRING
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class VariableMapAdapterITest : CamundaBpmDataITestBase() {

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
    MAP_STRING_LONG_VAR.on(variables).set(MAP_STRING_LONG.value)
    LIST_MAP_STRING_OBJECT_VAR.on(variables).set(LIST_MAP_STRING_OBJECT.value)
    COMPLEX_SET_VAR.on(variables).set(COMPLEX_SET.value)
    COMPLEX_LIST_VAR.on(variables).set(COMPLEX_LIST.value)
    COMPLEX_MAP_VAR.on(variables).set(COMPLEX_MAP.value)


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
    MAP_STRING_LONG_VAR.on(variables).remove()
    LIST_MAP_STRING_OBJECT_VAR.on(variables).remove()

    given()
      .process_with_delegate_is_deployed(delegateExpression = "\${${DelegateConfiguration::readOptionalFromVariableScope.name}}")

    whenever()
      .process_is_started_with_variables(variables = variables)

    then()
      .variables_had_not_value(delegateConfiguration.optionalVars,
        STRING_VAR,
        LIST_STRING_VAR,
        SET_STRING_VAR,
        MAP_STRING_LONG_VAR,
        LIST_MAP_STRING_OBJECT_VAR
      )
      .and()
      .variables_had_value(delegateConfiguration.optionalVars,
        setOf(LONG_VAR to Optional.of(LONG.value))
      )
  }

  @Test
  fun `should throw correct VNF exception`() {
    val nonExistent: VariableFactory<String> = stringVariable("non-existent")
    assertThrows(VariableNotFoundException::class.java) {
      nonExistent.from(createVariableMapUntyped()).get()
    }
  }

  @Test
  fun `should throw correct UO exception on basic setLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      STRING_VAR.on(createVariableMapUntyped()).setLocal(STRING.value)
    }
  }

  @Test
  fun `should throw correct UO exception on list setLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      LIST_STRING_VAR.on(createVariableMapUntyped()).setLocal(LIST_STRING.value)
    }
  }

  @Test
  fun `should throw correct UO exception on list of maps setLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      LIST_MAP_STRING_OBJECT_VAR.on(createVariableMapUntyped()).setLocal(LIST_MAP_STRING_OBJECT.value)
    }
  }

  @Test
  fun `should throw correct UO exception on set setLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      SET_STRING_VAR.on(createVariableMapUntyped()).setLocal(SET_STRING.value)
    }
  }

  @Test
  fun `should throw correct UO exception on map setLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      MAP_STRING_LONG_VAR.on(createVariableMapUntyped()).setLocal(MAP_STRING_LONG.value)
    }
  }

  @Test
  fun `should throw correct UO exception on basic removeLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      STRING_VAR.on(createVariableMapUntyped()).removeLocal()
    }
  }

  @Test
  fun `should throw correct UO exception on list removeLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      LIST_STRING_VAR.on(createVariableMapUntyped()).removeLocal()
    }
  }

  @Test
  fun `should throw correct UO exception on list of maps removeLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      LIST_MAP_STRING_OBJECT_VAR.on(createVariableMapUntyped()).removeLocal()
    }
  }

  @Test
  fun `should throw correct UO exception on set removeLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      SET_STRING_VAR.on(createVariableMapUntyped()).removeLocal()
    }
  }

  @Test
  fun `should throw correct UO exception on map removeLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      MAP_STRING_LONG_VAR.on(createVariableMapUntyped()).removeLocal()
    }
  }

  @Test
  fun `should throw correct UO exception on basic getLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      STRING_VAR.from(createVariableMapUntyped()).getLocal()
    }
  }

  @Test
  fun `should throw correct UO exception on list getLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      LIST_STRING_VAR.from(createVariableMapUntyped()).getLocal()
    }
  }

  @Test
  fun `should throw correct UO exception on list of maps getLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      LIST_MAP_STRING_OBJECT_VAR.from(createVariableMapUntyped()).getLocal()
    }
  }

  @Test
  fun `should throw correct UO exception on set getLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      SET_STRING_VAR.from(createVariableMapUntyped()).getLocal()
    }
  }

  @Test
  fun `should throw correct UO exception on map getLocal`() {
    assertThrows(UnsupportedOperationException::class.java) {
      MAP_STRING_LONG_VAR.from(createVariableMapUntyped()).getLocal()
    }
  }

  @Test
  fun `should throw correct UO exception on basic getLocalOptional`() {
    assertThrows(UnsupportedOperationException::class.java) {
      STRING_VAR.from(createVariableMapUntyped()).getLocalOptional()
    }
  }

  @Test
  fun `should throw correct UO exception on list getLocalOptional`() {
    assertThrows(UnsupportedOperationException::class.java) {
      LIST_STRING_VAR.from(createVariableMapUntyped()).getLocalOptional()
    }
  }

  @Test
  fun `should throw correct UO exception on list of maps getLocalOptional`() {
    assertThrows(UnsupportedOperationException::class.java) {
      LIST_MAP_STRING_OBJECT_VAR.from(createVariableMapUntyped()).getLocalOptional()
    }
  }

  @Test
  fun `should throw correct UO exception on set getLocalOptional`() {
    assertThrows(UnsupportedOperationException::class.java) {
      SET_STRING_VAR.from(createVariableMapUntyped()).getLocalOptional()
    }
  }

  @Test
  fun `should throw correct UO exception on map getLocalOptional`() {
    assertThrows(UnsupportedOperationException::class.java) {
      MAP_STRING_LONG_VAR.from(createVariableMapUntyped()).getLocalOptional()
    }
  }

  @Test
  fun `should throw correct WVT exception on basic`() {


    val variables = createVariableMapUntyped()
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    val wrongBasicType: VariableFactory<Integer> = intVariable(STRING_VAR.name)

    // wrong type
    assertThrows(WrongVariableTypeException::class.java) {
      wrongBasicType.from(variables).get()
    }
  }

  @Test
  fun `should throw correct WVT exception on basic optional`() {


    val variables = createVariableMapUntyped()
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    val wrongBasicType: VariableFactory<Integer> = intVariable(STRING_VAR.name)

    assertThrows(WrongVariableTypeException::class.java) {
      // wrong type
      wrongBasicType.from(variables).getOptional()
    }
  }

  @Test
  fun `should throw correct WVT exception on list `() {


    val variables = createVariableMapUntyped()
    val wrongListType: VariableFactory<List<Date>> = listVariable(STRING_VAR.name, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // not a list
      wrongListType.from(variables).get()
    }
  }

  @Test
  fun `should throw correct WVT exception on list optional`() {


    val variables = createVariableMapUntyped()
    val wrongListType: VariableFactory<List<Date>> = listVariable(STRING_VAR.name, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // not a list
      wrongListType.from(variables).getOptional()
    }
  }

  @Test
  fun `should throw correct WVT exception on list target`() {


    val variables = createVariableMapUntyped()
    val wrongListTargetType: VariableFactory<List<Date>> = listVariable(LIST_STRING_VAR.name, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // wrong type of the list
      wrongListTargetType.from(variables).get()
    }
  }

  @Test
  fun `should throw correct WVT exception on list target optional`() {


    val variables = createVariableMapUntyped()
    val wrongListTargetType: VariableFactory<List<Date>> = listVariable(LIST_STRING_VAR.name, Date::class.java)
    assertThrows(WrongVariableTypeException::class.java) {
      wrongListTargetType.from(variables).getOptional()
    }
  }

  @Test
  fun `should throw correct WVT exception on set `() {

    val variables = createVariableMapUntyped()
    val wrongSetType: VariableFactory<Set<Date>> = setVariable(STRING_VAR.name, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // not a set
      wrongSetType.from(variables).get()
    }
  }

  @Test
  fun `should throw correct WVT exception on set optional`() {

    val variables = createVariableMapUntyped()
    val wrongSetType: VariableFactory<Set<Date>> = setVariable(STRING_VAR.name, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // not a set
      wrongSetType.from(variables).getOptional()
    }
  }

  @Test
  fun `should throw correct WVT exception on set target`() {

    val variables = createVariableMapUntyped()
    val wrongSetTargetType: VariableFactory<Set<Date>> = setVariable(SET_STRING_VAR.name, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // wrong type of the set
      wrongSetTargetType.from(variables).get()
    }
  }

  @Test
  fun `should throw correct WVT exception on set target optional`() {

    val variables = createVariableMapUntyped()
    val wrongSetTargetType: VariableFactory<Set<Date>> = setVariable(SET_STRING_VAR.name, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // wrong type of the set
      wrongSetTargetType.from(variables).getOptional()
    }
  }

  @Test
  fun `should throw correct WVT exception on map`() {


    val variables = createVariableMapUntyped()
    val wrongMapType: VariableFactory<Map<Date, Date>> = mapVariable(STRING_VAR.name, Date::class.java, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // not a map
      wrongMapType.from(variables).get()
    }
  }

  @Test
  fun `should throw correct WVT exception on map optional`() {


    val variables = createVariableMapUntyped()
    val wrongMapType: VariableFactory<Map<Date, Date>> = mapVariable(STRING_VAR.name, Date::class.java, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // not a map
      wrongMapType.from(variables).getOptional()
    }
  }

  @Test
  fun `should throw correct WVT exception on key map`() {


    val variables = createVariableMapUntyped()
    val wrongMapKeyType: VariableFactory<Map<Date, String>> = mapVariable(MAP_STRING_LONG_VAR.name, Date::class.java, String::class.java)
    assertThrows(WrongVariableTypeException::class.java) {
      // wrong key type
      wrongMapKeyType.from(variables).get()
    }
  }

  @Test
  fun `should throw correct WVT exception on map key optional`() {

    val variables = createVariableMapUntyped()
    val wrongMapKeyType: VariableFactory<Map<Date, String>> = mapVariable(MAP_STRING_LONG_VAR.name, Date::class.java, String::class.java)
    assertThrows(WrongVariableTypeException::class.java) {
      // wrong key type
      wrongMapKeyType.from(variables).getOptional()
    }
  }

  @Test
  fun `should throw correct WVT exception on value map`() {

    val variables = createVariableMapUntyped()
    val wrongMapValueType: VariableFactory<Map<String, Date>> = mapVariable(MAP_STRING_LONG_VAR.name, String::class.java, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // wrong value type
      wrongMapValueType.from(variables).get()
    }
  }

  @Test
  fun `should throw correct WVT exception on map value optional`() {

    val variables = createVariableMapUntyped()
    val wrongMapValueType: VariableFactory<Map<String, Date>> = mapVariable(MAP_STRING_LONG_VAR.name, String::class.java, Date::class.java)

    assertThrows(WrongVariableTypeException::class.java) {
      // wrong value type
      wrongMapValueType.from(variables).getOptional()
    }
  }
}

