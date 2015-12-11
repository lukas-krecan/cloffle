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

import clojure.lang.Keyword;
import clojure.lang.Symbol;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class AstBuilder {

    public static final Keyword OP = Keyword.find("op");
    public static final Keyword STATIC_CALL = Keyword.find("static-call");
    public static final Keyword CONST = Keyword.find("const");
    public static final Keyword CLASS = Keyword.find("class");
    public static final Keyword METHOD = Keyword.find("method");
    public static final Keyword TAG = Keyword.find("tag");
    public static final Keyword VAL = Keyword.find("val");

    public ClojureNode build(Map<Keyword, Object> tree) {
        Keyword op = (Keyword) tree.get(OP);
        if (STATIC_CALL.equals(op)) {
            Class<?> clazz = (Class<?>) tree.get(CLASS);
            Symbol methodName = (Symbol) tree.get(METHOD);
            Class<?> tag = (Class<?>) tree.get(TAG);
            List<Map<Keyword, Object>> args = (List<Map<Keyword, Object>>) tree.get(Keyword.find("args"));
            try {
                Method method = clazz.getMethod(methodName.getName(), tag, tag);
                return new ClojureStaticCall(method, build(args.get(0)), build(args.get(1)));
            } catch (NoSuchMethodException e) {
                throw new AstBuildException(e);
            }
        }
        if (CONST.equals(op)) {
            Class<?> tag = (Class<?>) tree.get(TAG);
            if (long.class.equals(tag)) {
                return new ClojureLongNode((Long) tree.get(VAL));
            }
        }
        throw new AstBuildException("Unsupported operation " + tree);

    }

    private class AstBuildException extends RuntimeException {
        public AstBuildException(Exception e) {
            super(e);
        }

        public AstBuildException(String message) {
            super(message);
        }
    }
}
