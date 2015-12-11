package net.javacrumbs.cloffle;

import clojure.lang.Keyword;
import clojure.lang.Numbers;
import clojure.lang.Symbol;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestAdd {

    @Test
    public void shouldAddTwoNumbers() {
        Map<Keyword, Object> tree = new HashMap<>();
        tree.put(kw("op"), kw("static-call"));
        tree.put(kw("class"), Numbers.class);
        tree.put(kw("method"), Symbol.intern("add"));


    }

    private Keyword kw(String nsname) {
        return Keyword.intern(nsname);
    }

}