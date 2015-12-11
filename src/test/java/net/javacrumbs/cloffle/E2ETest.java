package net.javacrumbs.cloffle;

import clojure.lang.Keyword;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class E2ETest {
    private final AstBuilder astBuilder = new AstBuilder();
    private final Interpreter interpreter = new Interpreter();

    static {
        mikera.cljutils.Clojure.require("clojure.tools.analyzer.jvm");
    }

    @Test
    public void shouldAddThreeNumbers() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(+ 1 2 3))";
        System.out.println("Evaluating Clojure code: " + s);
        Object result = mikera.cljutils.Clojure.eval(s);
        ClojureNode node = astBuilder.build((Map<Keyword, Object>) result);
        assertThat(interpreter.interpret(node)).isEqualTo(6L);
    }


}