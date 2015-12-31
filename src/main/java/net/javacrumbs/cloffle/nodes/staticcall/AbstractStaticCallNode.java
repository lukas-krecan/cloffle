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

import net.javacrumbs.cloffle.nodes.ClojureNode;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public abstract class AbstractStaticCallNode extends ClojureNode {
    private final Class<?> clazz;
    private final String methodName;


    public AbstractStaticCallNode(Class<?> clazz, String methodName) {
        this.clazz = clazz;
        this.methodName = methodName;
    }

    protected MethodHandle getMethodHandle(Class<?> returnType, Class<?>... argTypes) throws NoSuchMethodException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            return lookup.findStatic(clazz, methodName, MethodType.methodType(returnType, argTypes));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    protected Class<?> getClazz() {
        return clazz;
    }

    protected String getMethodName() {
        return methodName;
    }
}
