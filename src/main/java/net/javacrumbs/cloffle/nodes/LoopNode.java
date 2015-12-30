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

public class LoopNode extends ClojureNode {

    @Children
    private final BindingNode[] bindings;

    @Child
    private ClojureNode body;

    public LoopNode(BindingNode[] bindings, ClojureNode body) {
        this.bindings = bindings;
        this.body = body;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        // should create new virtual frame
        for (BindingNode binding : bindings) {
            binding.execute(virtualFrame);
        }
        while (true) {
            Object result = body.execute(virtualFrame);
            if (!(result instanceof RecurNode)) {
                return result;
            } else {
                RecurNode recurNode = (RecurNode)result;
                ClojureNode[] exprs = recurNode.getExprs();
                for (int i = 0; i < bindings.length; i++) {
                    bindings[i].rebind(exprs[i], virtualFrame);
                }
            }
        }
    }
}
