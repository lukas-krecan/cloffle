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

import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import net.javacrumbs.cloffle.ClojureTypes;
import net.javacrumbs.cloffle.ClojureTypesGen;

@NodeInfo(language = "Clojure in Truffle")
@TypeSystemReference(ClojureTypes.class)
public abstract class ClojureNode extends Node {
    public abstract Object executeGeneric(VirtualFrame virtualFrame);

    public boolean executeBoolean(VirtualFrame virtualFrame) throws UnexpectedResultException {
        return ClojureTypesGen.expectBoolean(this.executeGeneric(virtualFrame));
    }

    public long executeLong(VirtualFrame virtualFrame) throws UnexpectedResultException {
        return ClojureTypesGen.expectLong(this.executeGeneric(virtualFrame));
    }

    public double executeDouble(VirtualFrame virtualFrame) throws UnexpectedResultException {
        return ClojureTypesGen.expectDouble(this.executeGeneric(virtualFrame));
    }
}
