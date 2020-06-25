package io.holunda.camunda.bpm.data.acl;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.acl.transform.IdentityVariableMapTransformer;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.camunda.bpm.data.guard.VariablesGuard;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.VariableMap;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;
import static io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.exists;

public class CamundaBpmDataACLFactoryTest {

    private RuntimeService runtimeService;
    private static VariableFactory<String> FOO = stringVariable("foo");
    private static AntiCorruptionLayer MY_ACL = CamundaBpmDataACL.guardTransformingReplace("__transient", true,
                                                                                           new VariablesGuard(exists(FOO)),
                                                                                           IdentityVariableMapTransformer.INSTANCE
    );

    public void testCallFromJava(String value) {
        VariableMap variableMap = MY_ACL.checkAndWrap(CamundaBpmData.builder().set(FOO, value).build());
        runtimeService.correlateMessage("message", variableMap);
    }
}
