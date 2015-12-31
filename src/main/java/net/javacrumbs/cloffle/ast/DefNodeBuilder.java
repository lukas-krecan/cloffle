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
import clojure.lang.Var;
import net.javacrumbs.cloffle.nodes.ClojureNode;
import net.javacrumbs.cloffle.nodes.DefNode;

import java.util.Map;

public class DefNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword DEF = keyword("def");
    public static final Keyword VAR = keyword("var");
    private static final Keyword INIT = keyword("init");

    protected DefNodeBuilder(AstBuilder astBuilder) {
        super(DEF, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        Var var = (Var) tree.get(VAR);
        ClojureNode init = build(tree.get(INIT));
        return new DefNode(var, init);
    }
}
