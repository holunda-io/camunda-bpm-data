package io.holunda.camunda.bpm.data

import io.holunda.camunda.bpm.data.factory.*

/**
 * Provides reified methods for variable factory construction.
 */
object CamundaBpmDataKotlin {

  /**
   * Reified version of the basic variable factory.
   * @param name The name of the variable.
   * @param T The type of the variable.
   * @return instance of [VariableFactory]
   */
  inline fun <reified T : Any> customVariable(name: String): VariableFactory<T> = BasicVariableFactory(name, T::class.java)

  /**
   * Reified version of list variable factory.
   * @param name The name of the variable.
   * @param T The type of the variable.
   * @return instance of [VariableFactory]
   */
  inline fun <reified T : Any> listVariable(name: String): VariableFactory<List<T>> = ListVariableFactory(name, T::class.java)

  /**
   * Reified version of set variable factory.
   * @param name The name of the variable.
   * @param T The type of the variable.
   * @return instance of [VariableFactory]
   */
  inline fun <reified T : Any> setVariable(name: String): VariableFactory<Set<T>> = SetVariableFactory(name, T::class.java)

  /**
   * Reified version of map variable factory.
   * @param name The name of the variable.
   * @param T The type of the variable.
   * @return instance of [VariableFactory]
   */
  inline fun <reified K : Any, reified V : Any> mapVariable(name: String): VariableFactory<Map<K, V>> = MapVariableFactory(name, K::class.java, V::class.java)
}
