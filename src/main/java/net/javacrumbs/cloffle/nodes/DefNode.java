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
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

// FIXME: Is it really a node?
public class DefNode extends ClojureNode {
    private final FrameSlot frameSlot;

    @Child
    private ClojureNode init;

    public DefNode(FrameSlot frameSlot, ClojureNode init) {
        this.frameSlot = frameSlot;
        this.init = init;
    }

    @Override
    public Object executeGeneric(VirtualFrame virtualFrame) {
        virtualFrame.setObject(frameSlot, init);
        // strange
        return null;
    }
}
