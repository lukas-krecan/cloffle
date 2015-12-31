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
import net.javacrumbs.cloffle.nodes.StaticFieldNode;

import java.lang.reflect.Field;
import java.util.Map;

public class StaticFieldNodeBuilder extends AbstractNodeBuilder {
    private static final Keyword STATIC_FIELD = keyword("static-field");
    private static final Keyword CLASS = keyword("class");
    private static final Keyword FIELD = keyword("field");

    protected StaticFieldNodeBuilder(AstBuilder astBuilder) {
        super(STATIC_FIELD, astBuilder);
    }

    @Override
    public ClojureNode buildNode(Map<Keyword, Object> tree) {
        Class<?> clazz = (Class<?>) tree.get(CLASS);
        Symbol fieldName = (Symbol) tree.get(FIELD);
        try {
            Field field = clazz.getField(fieldName.getName());
            return new StaticFieldNode(field);
        } catch (NoSuchFieldException e) {
            throw new AstBuildException(e);
        }

    }
}
