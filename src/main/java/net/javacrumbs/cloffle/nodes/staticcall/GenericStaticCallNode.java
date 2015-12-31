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
package net.javacrumbs.cloffle.nodes.staticcall;

import com.oracle.truffle.api.frame.VirtualFrame;
import net.javacrumbs.cloffle.nodes.ClojureNode;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GenericStaticCallNode extends AbstractStaticCallNode {
    @Children
    private final ClojureNode[] args;


    public GenericStaticCallNode(Class<?> clazz, String methodName, ClojureNode[] args) {
        super(clazz, methodName);
        this.args = args;
    }

    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        Object[] argValues = new Object[args.length];
        Class<?>[] argTypes = new Class<?>[args.length];
        for (int i=0; i<args.length; i++) {
            argValues[i] = args[i].executeGeneric(virtualFrame);
            argTypes[i] = Object.class;
        }
        try {
            // FIXME optimize
            Method method = getClazz().getMethod(getMethodName(), argTypes);
            return method.invoke(null, argValues);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            // FIXME
            throw new IllegalStateException(e);
        }
    }
}
