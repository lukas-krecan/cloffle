package net.javacrumbs.cloffle;

import clojure.lang.Keyword;
import net.javacrumbs.cloffle.ast.AstBuilder;
import net.javacrumbs.cloffle.nodes.ClojureNode;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CloffleE2ETest extends AbstractE2ETest {
    private final AstBuilder astBuilder = new AstBuilder();
    private final Interpreter interpreter = new Interpreter();

    static {
        mikera.cljutils.Clojure.require("clojure.tools.analyzer.jvm");
    }

    @Override
    protected Object run(String expression) {
        String s = "(clojure.tools.analyzer.jvm/analyze '" + expression + ")";
        ClojureNode node = astBuilder.build(eval(s));
        return interpreter.interpret(node);
    }

    @Test
    @Ignore
    public void shouldLoadClojureCore() throws IOException {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("clojure/core.clj")) {
            runAndAssert(convertStreamToString(is), null);
        }
    }


    @SuppressWarnings("unchecked")
    private Map<Keyword, Object> eval(String s) {
        return (Map<Keyword, Object>) mikera.cljutils.Clojure.eval(s);
    }


    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}