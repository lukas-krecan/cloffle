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
package net.javacrumbs.cloffle.nodes.invoke;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import net.javacrumbs.cloffle.nodes.ClojureNode;

public class InvokeNode extends ClojureNode {
    private final DirectCallNode callNode;

    @Children
    private final ClojureNode[] args;

    public InvokeNode(RootCallTarget callTarget, ClojureNode[] args) {
        callNode = Truffle.getRuntime().createDirectCallNode(callTarget);
        this.args = args;
    }

    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        Object[] resolvedArgs = new Object[args.length];
        // move to another node for optimization see the tutorial
        for (int i = 0; i < args.length; i++) {
            resolvedArgs[i] = args[i].executeGeneric(virtualFrame);
        }
        return callNode.call(virtualFrame, resolvedArgs);
    }
}
