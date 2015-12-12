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
import net.javacrumbs.cloffle.nodes.ClojureNode;

import java.util.Map;

/**
 *  Analyzes one node in tree.
 */
abstract class AbstractNodeBuilder {
    private static final Keyword OP = Keyword.find("op");
    protected static final Keyword TAG = Keyword.find("tag");
    protected static final Keyword TYPE = Keyword.find("type");
    protected static final Keyword NIL = Keyword.find("nil");
    protected static final Keyword VAL = Keyword.find("val");


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

    public abstract ClojureNode buildNode(Map<Keyword, Object> node);
}
