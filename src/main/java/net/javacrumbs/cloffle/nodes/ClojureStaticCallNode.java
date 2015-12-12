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

public class ClojureStaticCallNode extends ClojureNode {
    private final Method targetMethod;
    @Child
    private ClojureNode arg1;
    @Child
    private ClojureNode arg2;

    public ClojureStaticCallNode(Method targetMethod, ClojureNode arg1, ClojureNode arg2) {
        this.targetMethod = targetMethod;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        Object arg1Value = arg1.execute(virtualFrame);
        Object arg2Value = arg2.execute(virtualFrame);
        try {
            return targetMethod.invoke(null, arg1Value, arg2Value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // FIXME
            throw new IllegalStateException(e);
        }
    }
}
