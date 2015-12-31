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
package net.javacrumbs.cloffle;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import net.javacrumbs.cloffle.nodes.ClojureNilNode;
import net.javacrumbs.cloffle.nodes.ClojureNode;
import net.javacrumbs.cloffle.nodes.ClojureRootNode;

public class Interpreter {

    public Object interpret(ClojureNode node) {
        FrameDescriptor frameDescriptor = new FrameDescriptor();
        RootCallTarget callTarget = Truffle.getRuntime().createCallTarget(ClojureRootNode.create(node, frameDescriptor));
        Object result = callTarget.call();
        return result != ClojureNilNode.Nil.VALUE ? result : null;
    }
}
