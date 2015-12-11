package net.javacrumbs.cloffle;

import clojure.lang.Numbers;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class InterpreterTest {
    private final Interpreter interpreter = new Interpreter();
    @Test
    public void shouldExecuteTheCode() throws NoSuchMethodException {
        Method method = Numbers.class.getMethod("add", long.class, long.class);
        ClojureNode[] args = new ClojureNode[]{new ClojureNumberNode(1), new ClojureNumberNode(2)};
        assertThat(interpreter.interpret(new ClojureStaticCall(method, args))).isEqualTo(3L);
    }

}