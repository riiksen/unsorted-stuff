/*
 * Decompiled with CFR 0_123.
 */
package com.krispdev.resilience.irc.src;

import java.util.Vector;

public class Queue {
    private Vector _queue = new Vector();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void add(Object o) {
        Vector vector = this._queue;
        synchronized (vector) {
            this._queue.addElement(o);
            this._queue.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addFront(Object o) {
        Vector vector = this._queue;
        synchronized (vector) {
            this._queue.insertElementAt(o, 0);
            this._queue.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object next() {
        Object o = null;
        Vector vector = this._queue;
        synchronized (vector) {
            block8 : {
                if (this._queue.size() != 0) break block8;
                try {
                    this._queue.wait();
                }
                catch (InterruptedException e) {
                    return null;
                }
            }
            try {
                o = this._queue.firstElement();
                this._queue.removeElementAt(0);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                throw new InternalError("Race hazard in Queue object.");
            }
        }
        return o;
    }

    public boolean hasNext() {
        if (this.size() != 0) {
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clear() {
        Vector vector = this._queue;
        synchronized (vector) {
            this._queue.removeAllElements();
        }
    }

    public int size() {
        return this._queue.size();
    }
}

