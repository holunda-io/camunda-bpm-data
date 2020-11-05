package io.holunda.camunda.bpm.data.mockito

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.junit.Assert
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.exceptions.base.MockitoAssertionError
import java.time.Instant
import java.util.Date

class VariableMockVerifierTest {

  private var execution = Mockito.mock(DelegateExecution::class.java)

  private val verifier = VariableMockVerifier()
  private val builder = VariableMockBuilder()

  @Nested
  inner class GetChecks {
    private val variable = CamundaBpmData.stringVariable("stringVariable")

    @Nested
    inner class Local {

      @Test
      fun `should verify expected read variable`() {
        // given
        builder.setLocal(execution, variable, "to successfully read you have to set it first")
        variable.from(execution).local

        // when
        verifier.verifyVariableGetLocal(execution, variable)
      }

      @Test
      fun `should detect expected but not read variable`() {
        // given
        // no variable was read

        // when
        try {
          verifier.verifyVariableGetLocal(execution, variable)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should verify not expected and not read variable`() {
        // given
        // no variable gets read

        // when
        verifier.verifyVariableNotGetLocal(execution, variable)

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but read variable`() {
        // given
        builder.setLocal(execution, variable, "to successfully read you have to set it first")
        variable.from(execution).local

        // when
        try {
          verifier.verifyVariableNotGetLocal(execution, variable)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }

    @Nested
    inner class Global {

      @Test
      fun `should verify expected read variable`() {
        // given
        builder.set(execution, variable, "to successfully read you have to set it first")
        variable.from(execution).get()

        // when
        verifier.verifyVariableGet(execution, variable)
      }

      @Test
      fun `should detect expected but not read variable`() {
        // given
        // no variable was read

        // when
        try {
          verifier.verifyVariableGet(execution, variable)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should verify not expected and not read variable`() {
        // given
        // no variable gets read

        // when
        verifier.verifyVariableNotGet(execution, variable)

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but read variable`() {
        // given
        builder.set(execution, variable, "to successfully read you have to set it first")
        variable.from(execution).get()

        // when
        try {
          verifier.verifyVariableNotGet(execution, variable)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }
  }

  @Nested
  inner class RemoveChecks {
    private val variable = CamundaBpmData.stringVariable("stringVariable")

    @Nested
    inner class Local {

      @Test
      fun `should verify expected removed variable`() {
        // given
        variable.on(execution).removeLocal()

        // when
        verifier.verifyVariableRemovedLocal(execution, variable)
      }

      @Test
      fun `should detect expected but not removed variable`() {
        // given
        // no variable gets removed

        // when
        try {
          verifier.verifyVariableRemovedLocal(execution, variable)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should verify not expected and not removed variable`() {
        // given
        // no variable gets removed

        // when
        verifier.verifyVariableNotRemovedLocal(execution, variable)

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but removed variable`() {
        // given
        variable.on(execution).removeLocal()

        // when
        try {
          verifier.verifyVariableNotRemovedLocal(execution, variable)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }

    @Nested
    inner class Global {

      @Test
      fun `should verify expected removed variable`() {
        // given
        variable.on(execution).remove()

        // when
        verifier.verifyVariableRemoved(execution, variable)
      }

      @Test
      fun `should detect expected but not removed variable`() {
        // given
        // no variable gets removed

        // when
        try {
          verifier.verifyVariableRemoved(execution, variable)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should verify not expected and not removed variable`() {
        // given
        // no variable gets removed

        // when
        verifier.verifyVariableNotRemoved(execution, variable)

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but removed variable`() {
        // given
        variable.on(execution).remove()

        // when
        try {
          verifier.verifyVariableNotRemoved(execution, variable)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }
  }

  @Nested
  inner class BoolChecks {
    private val booleanVariable = CamundaBpmData.booleanVariable("booleanVariable")

    @Nested
    inner class Local {
      @Test
      fun `should verify expected and set values`() {
        // given
        booleanVariable.on(execution).setLocal(true)

        // when
        verifier.verifyVariableSetLocal(execution, booleanVariable, true)

        // then
        // no Exception
      }

      @Test
      fun `should detect expected but not set values`() {
        // given
        // no variable is set

        // when
        try {
          verifier.verifyVariableSetLocal(execution, booleanVariable, true)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should verify not expected and not set value`() {
        // given
        // no variable is set

        // when
        verifier.verifyVariableNotSetLocal(execution, booleanVariable, true)

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but set values`() {
        // given
        booleanVariable.on(execution).setLocal(true)

        // when
        try {
          verifier.verifyVariableNotSetLocal(execution, booleanVariable, true)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }

    @Nested
    inner class Global {
      @Test
      fun `should verify expected and set values`() {
        // given
        booleanVariable.on(execution).set(true)

        // when
        verifier.verifyVariableSet(execution, booleanVariable, true)

        // then
        // no Exception
      }

      @Test
      fun `should verify not expected and not set value`() {
        // given
        // no variable is set

        // when
        try {
          verifier.verifyVariableSet(execution, booleanVariable, true)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should detect expected but not set values`() {
        // given
        // no variable is set

        // when
        verifier.verifyVariableNotSet(execution, booleanVariable, true)

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but set values`() {
        // given
        booleanVariable.on(execution).set(true)

        // when
        try {
          verifier.verifyVariableNotSet(execution, booleanVariable, true)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }
  }

  @Nested
  inner class StringChecks {
    private val stringVariable = CamundaBpmData.stringVariable("stringVariable")

    @Nested
    inner class Local {
      @Test
      fun `should verify expected and set values`() {
        // given
        stringVariable.on(execution).setLocal("string")

        // when
        verifier.verifyVariableSetLocal(execution, stringVariable, "string")

        // then
        // no Exception
      }

      @Test
      fun `should verify not expected and not set value`() {
        // given
        // no variable is set

        // when
        try {
          verifier.verifyVariableSetLocal(execution, stringVariable, "string")
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should detect expected but not set values`() {
        // given
        // no variable is set

        // when
        verifier.verifyVariableNotSetLocal(execution, stringVariable, "string")

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but set values`() {
        // given
        stringVariable.on(execution).setLocal("string")

        // when
        try {
          verifier.verifyVariableNotSetLocal(execution, stringVariable, "string")
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }

    @Nested
    inner class Global {
      @Test
      fun `should verify expected and set values`() {
        // given
        stringVariable.on(execution).set("string")

        // when
        verifier.verifyVariableSet(execution, stringVariable, "string")

        // then
        // no Exception
      }

      @Test
      fun `should verify not expected and not set value`() {
        // given
        // no variable is set

        // when
        try {
          verifier.verifyVariableSet(execution, stringVariable, "string")
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should detect expected but not set values`() {
        // given
        // no variable is set

        // when
        verifier.verifyVariableNotSet(execution, stringVariable, "string")

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but set values`() {
        // given
        stringVariable.on(execution).set("string")

        // when
        try {
          verifier.verifyVariableNotSet(execution, stringVariable, "string")
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }
  }

  @Nested
  inner class LongChecks {
    private val longVariable = CamundaBpmData.longVariable("longVariable")

    @Nested
    internal inner class Local {
      @Test
      fun `should verify expected and set values`() {
        // given
        longVariable.on(execution).setLocal(1L)

        // when
        verifier.verifyVariableSetLocal(execution, longVariable, 1L)

        // then
        // no Exception
      }

      @Test
      fun `should verify not expected and not set value`() {
        // given
        // no variable is set

        // when
        try {
          verifier.verifyVariableSetLocal(execution, longVariable, 1L)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should detect expected but not set values`() {
        // given
        // no variable is set

        // when
        verifier.verifyVariableNotSetLocal(execution, longVariable, 1L)

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but set values`() {
        // given
        longVariable.on(execution).setLocal(1L)

        // when
        try {
          verifier.verifyVariableNotSetLocal(execution, longVariable, 1L)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }

    @Nested
    internal inner class Global {
      @Test
      fun `should verify expected and set values`() {
        // given
        longVariable.on(execution).set(1L)

        // when
        verifier.verifyVariableSet(execution, longVariable, 1L)

        // then
        // no Exception
      }

      @Test
      fun `should verify not expected and not set value`() {
        // given
        // no variable is set

        // when
        try {
          verifier.verifyVariableSet(execution, longVariable, 1L)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should detect expected but not set values`() {
        // given
        // no variable is set

        // when
        verifier.verifyVariableNotSet(execution, longVariable, 1L)

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but set values`() {
        // given
        longVariable.on(execution).set(1L)

        // when
        try {
          verifier.verifyVariableNotSet(execution, longVariable, 1L)
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }
  }

  @Nested
  inner class DateChecks {
    private val dateVariable = CamundaBpmData.dateVariable("dateVariable")

    @Nested
    internal inner class Local {
      @Test
      fun `should verify expected and set values`() {
        // given
        dateVariable.on(execution).setLocal(Date.from(Instant.parse("2020-07-24T12:00:00Z")))

        // when
        verifier.verifyVariableSetLocal(execution, dateVariable, Date.from(Instant.parse("2020-07-24T12:00:00Z")))

        // then
        // no Exception
      }

      @Test
      fun `should verify not expected and not set value`() {
        // given
        // no variable is set

        // when
        try {
          verifier.verifyVariableSetLocal(execution, dateVariable, Date.from(Instant.parse("2020-07-24T12:00:00Z")))
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should detect expected but not set values`() {
        // given
        // no variable is set

        // when
        verifier.verifyVariableNotSetLocal(execution, dateVariable, Date.from(Instant.parse("2020-07-24T12:00:00Z")))

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but set values`() {
        // given
        dateVariable.on(execution).setLocal(Date.from(Instant.parse("2020-07-24T12:00:00Z")))

        // when
        try {
          verifier.verifyVariableNotSetLocal(execution, dateVariable, Date.from(Instant.parse("2020-07-24T12:00:00Z")))
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }

    @Nested
    internal inner class Global {
      @Test
      fun `should verify expected and set values`() {
        // given
        dateVariable.on(execution).set(Date.from(Instant.parse("2020-07-24T12:00:00Z")))

        // when
        verifier.verifyVariableSet(execution, dateVariable, Date.from(Instant.parse("2020-07-24T12:00:00Z")))

        // then
        // no Exception
      }

      @Test
      fun `should verify not expected and not set value`() {
        // given
        // no variable is set

        // when
        try {
          verifier.verifyVariableSet(execution, dateVariable, Date.from(Instant.parse("2020-07-24T12:00:00Z")))
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should detect expected but not set values`() {
        // given
        // no variable is set

        // when
        verifier.verifyVariableNotSet(execution, dateVariable, Date.from(Instant.parse("2020-07-24T12:00:00Z")))

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but set values`() {
        // given
        dateVariable.on(execution).set(Date.from(Instant.parse("2020-07-24T12:00:00Z")))

        // when
        try {
          verifier.verifyVariableNotSet(execution, dateVariable, Date.from(Instant.parse("2020-07-24T12:00:00Z")))
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }
  }

  data class TestClass(
    private val property: String = "test"
  )

  @Nested
  inner class CustomChecks {

    private val customVariable: VariableFactory<TestClass> = CamundaBpmData.customVariable("customVariable", TestClass::class.java)

    @Nested
    internal inner class Local {
      @Test
      fun `should verify expected and set values`() {
        // given
        customVariable.on(execution).setLocal(TestClass())

        // when
        verifier.verifyVariableSetLocal(execution, customVariable, TestClass())

        // then
        // no Exception
      }

      @Test
      fun `should verify not expected and not set value`() {
        // given
        // no variable is set

        // when
        try {
          verifier.verifyVariableSetLocal(execution, customVariable, TestClass())
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should detect expected but not set values`() {
        // given
        // no variable is set

        // when
        verifier.verifyVariableNotSetLocal(execution, customVariable, TestClass())

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but set values`() {
        // given
        customVariable.on(execution).setLocal(TestClass())

        // when
        try {
          verifier.verifyVariableNotSetLocal(execution, customVariable, TestClass())
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }

    @Nested
    internal inner class Global {
      @Test
      fun `should verify expected and set values`() {
        // given
        customVariable.on(execution).set(TestClass())

        // when
        verifier.verifyVariableSet(execution, customVariable, TestClass())

        // then
        // no Exception
      }

      @Test
      fun `should verify not expected and not set value`() {
        // given
        // no variable is set

        // when
        try {
          verifier.verifyVariableSet(execution, customVariable, TestClass())
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }

      @Test
      fun `should detect expected but not set values`() {
        // given
        // no variable is set

        // when
        verifier.verifyVariableNotSet(execution, customVariable, TestClass())

        // then
        // no Exception
      }

      @Test
      fun `should detect not expected but set values`() {
        // given
        customVariable.on(execution).set(TestClass())

        // when
        try {
          verifier.verifyVariableNotSet(execution, customVariable, TestClass())
          Assert.fail("Verification should fail")
        } catch (ignore: MockitoAssertionError) {
          // then
          // Assertion hits
        }
      }
    }
  }
}
