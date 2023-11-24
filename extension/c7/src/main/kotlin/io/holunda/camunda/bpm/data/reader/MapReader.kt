package io.holunda.camunda.bpm.data.reader

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import io.holunda.camunda.bpm.data.factory.*
import java.math.BigDecimal
import java.time.temporal.Temporal
import java.util.*

class MapReader(
  private val objectMapper: ObjectMapper,
  private val json: Map<String, Any?>,
  private val delegateLocalToGlobal: Boolean = true
) : VariableReader {

  override fun <T : Any?> getOptional(variableFactory: VariableFactory<T>): Optional<T> {
    return Optional.ofNullable(
      if (json.containsKey(variableFactory.name)) {
        val variableNode = json[variableFactory.name]
        if (variableFactory.isComposite()) {
          // FIXME -> think of arrays, lists, sets
          objectMapper.convertValue(variableNode as Map<*, *>, variableFactory.constructType(objectMapper = objectMapper)) as T
        } else {
          objectMapper.convertValue(variableNode, variableFactory.constructType(objectMapper = objectMapper)) as T
        }
      } else {
        null
      }
    ) as Optional<T>
  }

  override fun <T : Any?> get(variableFactory: VariableFactory<T>): T {
    return getOptional(variableFactory).get()
  }

  override fun <T : Any?> getLocal(variableFactory: VariableFactory<T>): T {
    require(delegateLocalToGlobal) { "Access to local variables is not supported." }
    return getOptional(variableFactory).get()
  }

  override fun <T : Any?> getLocalOptional(variableFactory: VariableFactory<T>): Optional<T> {
    require(delegateLocalToGlobal) { "Access to local variables is not supported." }
    return getOptional(variableFactory)
  }

  private fun VariableFactory<*>.constructType(objectMapper: ObjectMapper): JavaType {
    return when (this) {
      is BasicVariableFactory -> objectMapper.typeFactory.constructSimpleType(this.variableClass, arrayOf())
      is ListVariableFactory<*> -> objectMapper.typeFactory.constructCollectionType(List::class.java, this.memberClass)
      is SetVariableFactory<*> -> objectMapper.typeFactory.constructCollectionType(Set::class.java, this.memberClass)
      is MapVariableFactory<*, *> -> objectMapper.typeFactory.constructMapType(Map::class.java, this.keyClass, this.valueClass)
      else -> throw IllegalArgumentException("Unsupported type of VariableFactory: $this")
    }
  }

  /**
   * Is composite?
   */
  private fun <T : Any?> VariableFactory<T>.isComposite() =
    this is BasicVariableFactory
      && !this.variableClass.isPrimitive // not a primitive (int, long, float, bool)
      && !this.variableClass.isAssignableFrom(String::class.java)
      && !this.variableClass.isAssignableFrom(Int::class.java)
      && !this.variableClass.isAssignableFrom(Number::class.java)
      && !this.variableClass.isAssignableFrom(Double::class.java)
      && !this.variableClass.isAssignableFrom(Float::class.java)
      && !this.variableClass.isAssignableFrom(Boolean::class.java)
      && !this.variableClass.isAssignableFrom(BigDecimal::class.java)
      && !this.variableClass.isAssignableFrom(Date::class.java)
      && !this.variableClass.isAssignableFrom(Temporal::class.java)

}
