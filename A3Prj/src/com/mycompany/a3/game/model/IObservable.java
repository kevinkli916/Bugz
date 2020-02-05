package com.mycompany.a3.game.model;

public interface IObservable<T>
{
	/*
	 * Interface for objects to be observed.
	 * Allows for event listeners to trigger events.
	 */
    void addObserver(IObserver<T> observer);
    void notifyObservers();
}
