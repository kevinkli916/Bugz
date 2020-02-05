package com.mycompany.a3.game.model.component;

import com.mycompany.a3.game.model.IObserver;

import java.util.ArrayList;
import java.util.List;
/*
 * For observer data structure implementation
 */
public class Observable<T>
{
    private List<IObserver<T>> observers = new ArrayList<>();

    public void addObserver(IObserver<T> observer)
    {
        observers.add(observer);
    }

    public void notifyObservers(T obj)
    {
        for (IObserver<T> observer : observers)
        {
            observer.update(obj);
        }
    }
}
