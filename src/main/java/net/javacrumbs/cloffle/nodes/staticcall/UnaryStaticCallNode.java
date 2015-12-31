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

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeChildren;
import com.oracle.truffle.api.dsl.Specialization;
import net.javacrumbs.cloffle.nodes.ClojureNode;

import java.lang.invoke.MethodHandle;

/**
 * We want to optimize call with two args. Do not know how to simply optimize the generic case.
 */
@NodeChildren({@NodeChild(value = "arg", type = ClojureNode.class)})
public abstract class UnaryStaticCallNode extends AbstractStaticCallNode {
    public UnaryStaticCallNode(Class<?> clazz, String methodName) {
        super(clazz, methodName);
    }

    @Specialization(rewriteOn = NoSuchMethodException.class)
    protected long execute(long arg) throws NoSuchMethodException {
        MethodHandle methodHandle = getMethodHandle(long.class);
        try {
            return (long) methodHandle.invokeExact(arg);
        } catch (Throwable e) {
            // FIXME
            throw new IllegalStateException(e);
        }
    }

    @Specialization(rewriteOn = NoSuchMethodException.class)
    protected double execute(double arg) throws NoSuchMethodException {
        MethodHandle methodHandle = getMethodHandle(double.class);
        try {
            return (double) methodHandle.invokeExact(arg);
        } catch (Throwable e) {
            // FIXME
            throw new IllegalStateException(e);
        }
    }

    @Specialization
    protected Object execute(Object arg){
        MethodHandle methodHandle;
        try {
            methodHandle = getMethodHandle(Object.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
        try {
            return (Object) methodHandle.invokeExact(arg);
        } catch (Throwable e) {
            // FIXME
            throw new IllegalStateException(e);
        }
    }

    private MethodHandle getMethodHandle(Class<?> type) throws NoSuchMethodException {
        return getMethodHandle(type, type);
    }

}
