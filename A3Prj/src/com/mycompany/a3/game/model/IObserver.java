package com.mycompany.a3.game.model;

public interface IObserver<T>
{
	/*
	 * Interface for observer.
	 * Allows for event listeners to trigger events.
	 */
    void update(T obj);
}
