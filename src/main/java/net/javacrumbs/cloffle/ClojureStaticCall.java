/**
 * Copyright 2009-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.cloffle;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClojureStaticCall extends ClojureNode {
    private final Method targetMethod;
    private final ClojureNode[] args;

    public ClojureStaticCall(Method targetMethod, ClojureNode[] args) {
        this.targetMethod = targetMethod;
        this.args = args;
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame virtualFrame) {
        //FIXME: get rid of reflection
        //FIXME: loop unroll
        CompilerAsserts.compilationConstant(args.length);
        Object[] argValues = new Object[args.length]; // has to be here, variable get may be different every time
        for (int i = 0; i< args.length; i++) {
            argValues[i] = args[i].execute(virtualFrame);
        }

        try {
            return targetMethod.invoke(null, argValues);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // FIXME
            throw new IllegalStateException(e);
        }
    }
}
