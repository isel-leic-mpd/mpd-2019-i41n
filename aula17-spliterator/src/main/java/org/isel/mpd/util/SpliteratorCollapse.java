package org.isel.mpd.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;

public class SpliteratorCollapse<T> extends AbstractSpliterator<T> {
    private final Iterator<T> iter;

    public SpliteratorCollapse(Iterable<T> src) {
        super(Long.MAX_VALUE, Spliterator.ORDERED);
        iter = src.iterator();
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        // 1. Obter elemento da fonte
        // 2. Avaliar
        // 3. Devolver o elemento se nao for repetido: action.accept(item);
        // 4. Retornar false se a fonte não tiver mais elementos.
        return false;
    }
}
