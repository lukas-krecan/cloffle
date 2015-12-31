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
package net.javacrumbs.cloffle.ast;

import clojure.lang.Keyword;
import clojure.lang.Symbol;
import com.oracle.truffle.api.frame.FrameSlot;
import net.javacrumbs.cloffle.nodes.ClojureNode;

import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

/**
 * Analyzes one node in tree.
 */
abstract class AbstractNodeBuilder {

    private static final Keyword OP = keyword("op");
    protected static final Keyword TAG = keyword("tag");
    protected static final Keyword TYPE = keyword("type");
    protected static final Keyword NIL = keyword("nil");
    protected static final Keyword VAL = keyword("val");


    private final Keyword supportedOperation;
    private final AstBuilder astBuilder;


    protected AbstractNodeBuilder(Keyword supportedOperation, AstBuilder astBuilder) {
        this.supportedOperation = supportedOperation;
        this.astBuilder = astBuilder;
    }

    public boolean supports(Map<Keyword, Object> node) {
        return supportedOperation.equals(node.get(OP));
    }

    public ClojureNode build(Object node) {
        return astBuilder.build(node);
    }

    public ClojureNode buildOptional(Object node) {
        if (node != null) {
            return astBuilder.build(node);
        } else {
            return null;
        }
    }

    public abstract ClojureNode buildNode(Map<Keyword, Object> node);

    protected <T> T[] convertToNodes(Object value, IntFunction<T[]> supplier) {
        List<Object> args = (List<Object>) value;
        return args.stream().map(this::build).toArray(supplier);
    }

    protected static Keyword keyword(String nsname) {
        return Keyword.intern(nsname);
    }

    protected FrameSlot getFrameSlot(Symbol name) {
        return astBuilder.getFrameDescriptor().findOrAddFrameSlot(name);
    }
}
