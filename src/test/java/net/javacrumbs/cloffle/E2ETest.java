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
        String s = "(clojure.tools.analyzer.jvm/analyze '(+ 1 2 3.0))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(6d);
    }

    @Test
    public void shouldInterpretIf() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(if true 2.0 3.0))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(2.0);
    }

    @Test
    public void shouldInterpretNil() {
        String s = "(clojure.tools.analyzer.jvm/analyze '(if nil 2.0 3.0))";
        ClojureNode node = astBuilder.build(eval(s));
        assertThat(interpreter.interpret(node)).isEqualTo(3.0);
    }

    @SuppressWarnings("unchecked")
    private Map<Keyword, Object> eval(String s) {
        return (Map<Keyword, Object>) mikera.cljutils.Clojure.eval(s);
    }
}