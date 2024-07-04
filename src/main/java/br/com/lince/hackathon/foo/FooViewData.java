package br.com.lince.hackathon.foo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class FooViewData {
    public FooViewData(List<Foo> foos, LocalDateTime dateTime, int page, int pageSize, int count) {
        this.foos = foos;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = null;
        this.foo = null;
    }

    public FooViewData(HashMap<String, String> errors, Foo foo, List<Foo> foos, LocalDateTime dateTime, int page, int pageSize, int count) {
        this.foos = foos;
        this.dateTime = dateTime;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.errors = errors;
        this.foo = foo;
    }

    private final Foo foo;
    private final List<Foo> foos;
    private final LocalDateTime dateTime;
    private final int page;
    private final int pageSize;
    private final int count;
    private final HashMap<String, String> errors;

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public Foo getFoo() {
        return foo;
    }

    public List<Foo> getFoos() {
        return foos;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getIndex() {
        return page;
    }

    public int getPage() {
        return page + 1;
    }

    public int getCount() {
        return count;
    }

    public int getTotalPages() {
        return Double.valueOf(Math.ceil(((Integer.valueOf(count).doubleValue()) / pageSize))).intValue();
    }

    public boolean getHasPrevious() {
        return page > 0;
    }

    public int getPrevious() {
        return page - 1;
    }

    public int getNext() {
        return getPage() < getTotalPages() ? page + 1 : 0;
    }
}
