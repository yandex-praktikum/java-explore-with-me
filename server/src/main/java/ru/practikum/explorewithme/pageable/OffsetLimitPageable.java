package ru.practikum.explorewithme.pageable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class OffsetLimitPageable implements Pageable {
    private final int offset;
    private final int limit;
    private final Sort sort;

    private static final int DEFAULT_PAGE_SIZE = 20;

    protected OffsetLimitPageable(int offset, int limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public static Pageable of(Integer from, Integer size) {
        if (from == null && size == null) {
            from = 0;
            size = DEFAULT_PAGE_SIZE;
        }
        return new OffsetLimitPageable(saveUnboxing(from), saveUnboxing(size), Sort.unsorted());
    }

    public static Pageable of(Integer from, Integer size, Sort sort) {
        if (from == null && size == null) {
            from = 0;
            size = DEFAULT_PAGE_SIZE;
        }
        return new OffsetLimitPageable(saveUnboxing(from), saveUnboxing(size), sort);
    }

    public static int saveUnboxing(Integer value) {
        return Optional.ofNullable(value).orElse(0);
    }

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetLimitPageable(offset + limit, limit, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return new OffsetLimitPageable(offset, limit, sort);
    }

    @Override
    public Pageable first() {
        return new OffsetLimitPageable(offset, limit, sort);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new OffsetLimitPageable(offset + limit * pageNumber, limit, sort);
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
