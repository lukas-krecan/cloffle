package net.javacrumbs.cloffle;

import clojure.lang.Numbers;
import net.javacrumbs.cloffle.nodes.ClojureLongNode;
import net.javacrumbs.cloffle.nodes.ClojureStaticCallNode;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class InterpreterTest {
    private final Interpreter interpreter = new Interpreter();

    @Test
    public void shouldAddTwoNumbers() throws NoSuchMethodException {
        Method method = Numbers.class.getMethod("add", long.class, long.class);
        assertThat(interpreter.interpret(new ClojureStaticCallNode(method, new ClojureLongNode(1), new ClojureLongNode(2)))).isEqualTo(3L);
    }

    @Test
    public void shouldAddThreeNumbers() throws NoSuchMethodException {
        Method method = Numbers.class.getMethod("add", long.class, long.class);
        ClojureStaticCallNode addFirstTwo = new ClojureStaticCallNode(method, new ClojureLongNode(1), new ClojureLongNode(2));
        assertThat(interpreter.interpret(new ClojureStaticCallNode(method, addFirstTwo, new ClojureLongNode(3)))).isEqualTo(6L);
    }

}