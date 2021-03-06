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

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import net.javacrumbs.cloffle.Clojure;

public class ClojureRootNode extends RootNode {
    @Child
    private ClojureNode node;

    public ClojureRootNode(ClojureNode node,
                           FrameDescriptor frameDescriptor) {
        super(Clojure.class, null, frameDescriptor);
        this.node = node;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        return node.executeGeneric(virtualFrame);
    }

    public static ClojureRootNode create(ClojureNode node, FrameDescriptor frameDescriptor) {
        return new ClojureRootNode(node, frameDescriptor);
    }
}
