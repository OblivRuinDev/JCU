// JCU: Java Classfile Util, which provides low-level primitives for
//  interacting with class bytecodes and unsafe but fast APIs
// Copyright (c) 2025 OblivRuinDev.
// All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package dev.oblivruin.jcu.util;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * This is a sequential domain container.
 * The domains within this container do not have any range conflicts.
 * <p>
 * Note: For code that depends on exception throwing,
 * this container may not be applicable to such types of code.
 * <br>
 * For details about Domain specification, please see {@link Domain}
 * @apiNote
 * Warning: This class can only ensure that
 * the correct API actions are executed correctly.
 * <br>
 * If values not belonging to this container are passed into the API,
 * unpredictable results (such as damaging the data structure) will occur.
 * <br>
 * Developers should be responsible for their actions.
 *
 * @param <T> {@inheritDoc}
 *
 * @author OblivRuinDev
 */
public class SequentialDomains<T extends SequentialDomains.Domain<T>> extends AbstractSet<T> {
    T first = null;
    T last = null;
    int size = 0;

    public final T replace(T oldV, T newV) {
        T perv = oldV.previous;
        T next = oldV.next;
        if (perv != null && perv.end > newV.start) {
            return perv;// conflict domain
        }
        if (next != null && next.start < newV.end) {
            return next;// conflict domain
        }
        if (perv == null) {
            first = newV;
        } else {
            perv.next = newV;
            newV.previous = perv;
        }
        if (next == null) {
            last = newV;
        } else {
            next.previous = newV;
            newV.next = next;
        }
        oldV.clear();
        return null;
    }

    public final void remove(T t) {
        T prev = t.previous;
        T next = t.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.previous = prev;
        }
        t.clear();
        size--;
    }

    public final T tryAdd(T domain) {
        if (first == null) {
            first = domain;
            last = domain;
            size = 1;
            return null;
        } else {
            return tryInsert(first, domain);
        }
    }

    /**
     * Try to find the appropriate insertion position starting from the given value.
     * @param target insert pos
     * @param newV which may be inserted
     * @return null if inserted successfully, or the domain which conflicts with newV
     */
    public final T tryInsert(T target, T newV) {
        int nEnd = newV.end;
        int nStart = newV.start;
        if (nEnd <= target.start) {// Domain desc: newV ...?... target
            do {// now find nearest domain
                target = target.previous;
                if (target == null) {// find first
                    first = ((newV.next = first).previous = newV);
                    size++;
                    return null;
                }
            } while (nEnd <= target.start);
            // now has found
            if (target.end > nStart) {
                return target;// conflict
            }
            target.next = ((newV.next = ((newV.previous = target).next)).previous = newV);
        } else if (target.end <= nStart) {// Domain desc: target ...?... newV
            do {
                target = target.next;
                if (target == null) {// find last
                    last = ((newV.previous = last).next = newV);
                    size++;
                    return null;
                }
            } while (target.end <= nStart);
            // now has found
            if (target.start < nEnd) {
                return target;// conflict
            }
            target.previous = ((newV.previous = (newV.next = target).previous).next = newV);
        } else {
            return target;
        }
        size++;
        return null;
    }

    public final T getFirst() {
        return first;
    }

    public final T getLast() {
        return last;
    }

    @Override
    public final Iterator<T> iterator() {
        return new Iterator<T>() {
            T node = first;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                T ret = node;
                if (ret == null) {
                    throw new NoSuchElementException();
                }
                node = node.next;
                return ret;
            }

            @Override
            public void remove() {
                if (node == null) {
                    T local = last;
                    (last = local.previous).next = null;
                    local.clear();
                } else if (node == first) {
                    throw new IllegalStateException();
                } else {
                    T current = node.previous;// always not null because node isn't first
                    T perv = current.previous;

                }
                size--;
            }
        };
    }

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     * @param t element whose presence in this collection is to be ensured
     * @return if add the given value successfully
     * @deprecated use {@link #tryAdd(T)} or {@link #tryInsert(T, T)} instead
     */
    @Deprecated
    @Override
    public final boolean add(T t) {
        return tryAdd(t) == null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SequentialDomains)) {
            return false;
        }
        SequentialDomains<?> another = (SequentialDomains<?>) o;
        int size = this.size;
        if (another.size != size) {
            return false;
        }
        if (size == 0) {
            return true;
        }
        T thiz = first;
        Domain<?> that = another.first;
        for (int i = 0; i < size; i++) {
            if (!thiz.equalRange(that)) {
                return false;
            }
            thiz = thiz.next;
            that = that.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (first != null) {
            int hash = (31 * size + first.hashCode()) * 31;
            if (last != null) {
                hash = (hash + last.hashCode()) * 31;
            }
            return hash;
        } else {
            return 0;
        }
    }

    @Override
    public final void forEach(Consumer<? super T> action) {
        T node = first;
        while (node != null) {
            action.accept(node);
            node = node.next;
        }
    }

    /**
     * This will always true:
     * 0 <= {@link #start} <= {@link #end}
     *
     * @param <T> the type of elements in this
     * @author OblivRuinDev
     */
    public static class Domain<T extends Domain<T>> extends dev.oblivruin.jcu.util.Domain implements Iterable<T> {
        T next = null;
        T previous = null;

        /**
         * Construct a new domain.
         *
         * @param start the start of the domain (inclusive)
         * @param end the end of the domain (exclusive)
         * @throws IllegalArgumentException if arguments is illegal
         */
        protected Domain(int start, int end) {
            super(start, end);
        }

        public final T getNext() {
            return next;
        }

        public final T getPrevious() {
            return previous;
        }

        protected final void clear() {
            next = null;
            previous = null;
        }

        /**
         * @implSpec Override should delegate to this method or {@link #equalRange(Domain)} first.<br>
         *           Then, if the return value is true, execute their custom logic.
         */
        @Override
        public boolean equals(Object o) {
            return o instanceof Domain && equalRange((Domain<?>) o);
        }

        public final boolean equalRange(Domain<?> domain) {
            return domain != null &&
                    start == domain.start && end == domain.end;
        }

        public final boolean isConflict(Domain<?> domain) {
            return !(domain.start >= end || domain.end <= start);
        }

        @Override
        public int hashCode() {
            return start * 32767 + end;
        }

        @Override
        public final Iterator<T> iterator() {
            return new Iterator<T>() {
                T node = next;
                @Override
                public boolean hasNext() {
                    return node != null;
                }

                @Override
                public T next() {
                    T ret = node;
                    node = node.next;
                    return ret;
                }
            };
        }
    }
}