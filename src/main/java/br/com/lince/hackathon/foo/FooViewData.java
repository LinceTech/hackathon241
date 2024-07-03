package br.com.lince.hackathon.foo;

import java.time.LocalDateTime;
import java.util.List;

public class FooViewData {
    public FooViewData(List<Foo> foos, LocalDateTime dateTime) {
        this.foos = foos;
        this.dateTime = dateTime;
    }

    private final List<Foo> foos;
    private final LocalDateTime dateTime;

    public List<Foo> getFoos() {
        return foos;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
