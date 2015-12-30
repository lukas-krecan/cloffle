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
package net.javacrumbs.cloffle.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ClojureStaticCallNode extends ClojureNode {
    private final Method targetMethod;
    @Children
    private final ClojureNode[] args;

    public ClojureStaticCallNode(Method targetMethod, ClojureNode... args) {
        this.targetMethod = targetMethod;
        this.args = args;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        Object[] argValues = Arrays.stream(args).map(a -> a.execute(virtualFrame)).toArray(Object[]::new);
        try {
            return targetMethod.invoke(null, argValues);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // FIXME
            throw new IllegalStateException(e);
        }
    }
}
