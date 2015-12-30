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
import net.javacrumbs.cloffle.nodes.ClojureStaticCallNode;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StaticCodeNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword STATIC_CALL = keyword("static-call");
    private static final Keyword CLASS = keyword("class");
    private static final Keyword METHOD = keyword("method");

    protected StaticCodeNodeBuilder(AstBuilder astBuilder) {
        super(STATIC_CALL, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        Class<?> clazz = (Class<?>) tree.get(CLASS);
        Symbol methodName = (Symbol) tree.get(METHOD);
        List<Map<Keyword, Object>> args = (List<Map<Keyword, Object>>) tree.get(keyword("args"));
        ClojureNode[] argValues = args.stream().map(this::build).toArray(ClojureNode[]::new);
        Class<?>[] argTypes = args.stream().map(a -> Object.class).toArray(Class[]::new);
        try {
            Method method = clazz.getMethod(methodName.getName(), argTypes);
            return new ClojureStaticCallNode(method, argValues);
        } catch (NoSuchMethodException e) {
            throw new AstBuildException(e);
        }

    }
}
