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

import clojure.lang.Var;
import com.oracle.truffle.api.frame.VirtualFrame;

public class VarNode extends GetValueNode {
    public VarNode(Var var) {
        super(var);
    }

    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        // var returns closure node, we want to get the value
        ClojureNode node = (ClojureNode) super.executeGeneric(virtualFrame);
        return node.executeGeneric(virtualFrame);
    }
}
