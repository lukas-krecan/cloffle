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
import net.javacrumbs.cloffle.nodes.ClojureNode;
import net.javacrumbs.cloffle.nodes.staticcall.BinaryStaticCallNodeGen;
import net.javacrumbs.cloffle.nodes.staticcall.GenericStaticCallNode;
import net.javacrumbs.cloffle.nodes.staticcall.UnaryStaticCallNodeGen;

import java.util.List;
import java.util.Map;

public class StaticCallNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword STATIC_CALL = keyword("static-call");
    private static final Keyword CLASS = keyword("class");
    private static final Keyword METHOD = keyword("method");

    protected StaticCallNodeBuilder(AstBuilder astBuilder) {
        super(STATIC_CALL, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        Class<?> clazz = (Class<?>) tree.get(CLASS);
        Symbol methodName = (Symbol) tree.get(METHOD);
        List<Map<Keyword, Object>> args = (List<Map<Keyword, Object>>) tree.get(keyword("args"));
        ClojureNode[] argValues = args.stream().map(this::build).toArray(ClojureNode[]::new);
        if (args.size() == 1) {
            return UnaryStaticCallNodeGen.create(clazz, methodName.getName(), argValues[0]);
        } else if (args.size() == 2) {
            return BinaryStaticCallNodeGen.create(clazz, methodName.getName(), argValues[0], argValues[1]);
        } else {
            return new GenericStaticCallNode(clazz, methodName.getName(), argValues);
        }
    }
}
