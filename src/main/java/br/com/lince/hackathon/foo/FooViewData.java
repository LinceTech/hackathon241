package br.com.lince.hackathon.foo;

import java.time.LocalDateTime;
import java.util.List;

public class FooViewData {
    public FooViewData(List<Foo> foos, LocalDateTime dateTime) {
        this.foos = foos;
        this.dateTime = dateTime;
    }

    public final List<Foo> foos;
    public final LocalDateTime dateTime;
}
