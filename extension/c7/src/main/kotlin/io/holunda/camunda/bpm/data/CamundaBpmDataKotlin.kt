package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.factory.*
import java.util.*

/**
 * Provides reified methods for variable factory construction.
 */
object CamundaBpmDataKotlin {

  /**
   * Creates a string variable factory.
   *
   * @param variableName name of the variable.
   *
   * @return variable factory for string.
   */
  fun stringVariable(variableName: String): VariableFactory<String?> = BasicVariableFactory.forType<String?>(variableName)

  /**
   * Creates a date variable factory.
   *
   * @param variableName name of the variable.
   *
   * @return variable factory for date.
   */
  fun dateVariable(variableName: String): VariableFactory<Date?> = BasicVariableFactory.forType(variableName)

  /**
   * Creates an integer variable factory.
   *
   * @param variableName name of the variable.
   *
   * @return variable factory for integer.
   */
  fun intVariable(variableName: String): VariableFactory<Int?> = BasicVariableFactory.forType(variableName)

  /**
   * Creates a long variable factory.
   *
   * @param variableName name of the variable.
   *
   * @return variable factory for long.
   */
  fun longVariable(variableName: String): VariableFactory<Long?> = BasicVariableFactory.forType(variableName)

  /**
   * Creates a short variable factory.
   *
   * @param variableName name of the variable.
   *
   * @return variable factory for short.
   */
  fun shortVariable(variableName: String): VariableFactory<Short?> = BasicVariableFactory.forType(variableName)

  /**
   * Creates a double variable factory.
   *
   * @param variableName name of the variable.
   *
   * @return variable factory for double.
   */
  fun doubleVariable(variableName: String): VariableFactory<Double?> = BasicVariableFactory.forType(variableName)

  /**
   * Creates a boolean variable factory.
   *
   * @param variableName name of the variable.
   *
   * @return variable factory for boolean.
   */
  fun booleanVariable(variableName: String): VariableFactory<Boolean?> = BasicVariableFactory.forType(variableName)

  /**
   * Creates an uuid variable factory.
   *
   * @param variableName name of the variable.
   * @return variable factory for uuid.
   */
  fun uuidVariable(variableName: String): VariableFactory<UUID?> = BasicVariableFactory.forType(variableName)

  /**
   * Reified version of the basic variable factory.
   * @param name The name of the variable.
   * @param T The type of the variable.
   * @return instance of [VariableFactory]
   */
  inline fun <reified T : Any?> customVariable(name: String): VariableFactory<T> = BasicVariableFactory.forType(name)

  /**
   * Reified version of list variable factory.
   * @param name The name of the variable.
   * @param T The type of the variable.
   * @return instance of [VariableFactory]
   */
  inline fun <reified T : Any?> listVariable(name: String): VariableFactory<List<T>> = ListVariableFactory.forType(name)

  /**
   * Reified version of list variable factory.
   * @param name The name of the variable.
   * @param T The type of the variable.
   * @return instance of [VariableFactory]
   */
  inline fun <reified T : Any> listVariableNotNull(name: String): VariableFactory<List<T>> = ListVariableFactory.forType(name)

  /**
   * Reified version of set variable factory.
   * @param name The name of the variable.
   * @param T The type of the variable.
   * @param wrap a boolean flag controlling if the serializer should wrap a list into a wrapper object. Set this flag to true, if you use complex types as T.
   * @return instance of [VariableFactory]
   */
  inline fun <reified T : Any?> setVariable(name: String): VariableFactory<Set<T>> = SetVariableFactory.forType(name)

  /**
   * Reified version of set variable factory.
   * @param name The name of the variable.
   * @param T The type of the variable.
   * @param wrap a boolean flag controlling if the serializer should wrap a list into a wrapper object. Set this flag to true, if you use complex types as T.
   * @return instance of [VariableFactory]
   */
  inline fun <reified T : Any> setVariableNotNull(name: String): VariableFactory<Set<T>> = SetVariableFactory.forType(name)

  /**
   * Reified version of map variable factory.
   * @param name The name of the variable.
   * @param K The type of the variable key.
   * @param V The type of the variable value.
   * @return instance of [VariableFactory]
   */
  inline fun <reified K : Any, reified V : Any?> mapVariable(name: String): VariableFactory<Map<K, V>> = MapVariableFactory.forType(name)

  /**
   * Reified version of map variable factory.
   * @param name The name of the variable.
   * @param K The type of the variable key.
   * @param V The type of the variable value.
   * @return instance of [VariableFactory]
   */
  inline fun <reified K : Any, reified V : Any> mapVariableNotNullable(name: String): VariableFactory<Map<K, V>> = MapVariableFactory.forType(name)

}
