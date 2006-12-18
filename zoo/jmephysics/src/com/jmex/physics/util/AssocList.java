/*Copyright*/
package com.jmex.physics.util;

import java.util.AbstractList;
import java.util.List;

/**
 * A utility class that allow to use a list as association role implementation.
 * @author Irrisor
 */
public class AssocList<O> extends AbstractList<O> {

    /**
     * Callback interface for {@link AssocList}.
     */
    public interface ModificationHandler<O> {

        /**
         * @param element element that was added to the list
         */
        void added( O element );

        /**
         * @param element element that was removed from the list
         */
        void removed( O element );

        /**
         * @param element what could be added
         * @return true if element can be added, false to ignore add
         */
        boolean canAdd( O element );
    }

    private final List<O> delegate;
    private final ModificationHandler<O> handler;

    public AssocList( List<O> delegate, ModificationHandler<O> handler ) {
        if ( delegate == null ) {
            throw new NullPointerException();
        }
        if ( handler == null ) {
            throw new NullPointerException();
        }
        this.delegate = delegate;
        this.handler = handler;
    }

    @Override
    public O get( int index ) {
        return delegate.get( index );
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public void add( int index, O element ) {
        if ( element == null ) {
            throw new NullPointerException();
        }
        if ( handler.canAdd( element ) ) {
            delegate.add( index, element );
            handler.added( element );
        }
    }

    @Override
    public O remove( int index ) {
        O oldValue = delegate.remove( index );
        if ( oldValue != null )
        {
            handler.removed( oldValue );
        }
        return oldValue;
    }

    @Override
    public O set( int index, O element ) {
        if ( element == null ) {
            throw new NullPointerException();
        }
        O oldValue = delegate.set( index, element );
        if ( oldValue != element ) {
            handler.removed( oldValue );
            handler.added( element );
        }
        return oldValue;
    }
}

/*
 * $log$
 */

