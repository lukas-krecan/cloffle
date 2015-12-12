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

import clojure.lang.Symbol;
import com.oracle.truffle.api.frame.VirtualFrame;

// FIXME: Is it really a node?
public class BindingNode extends ClojureNode {
    private final Symbol name;
    private final Class<?> type;

    @Child
    private ClojureNode init;

    public BindingNode(Symbol name, ClojureNode init, Class<?> type) {
        this.name = name;
        this.init = init;
        this.type = type;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        virtualFrame.setObject(virtualFrame.getFrameDescriptor().addFrameSlot(name), init.execute(virtualFrame));
        // strange
        return null;
    }
}
