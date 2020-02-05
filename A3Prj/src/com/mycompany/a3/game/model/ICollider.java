package com.mycompany.a3.game.model;

import com.codename1.charts.models.Point;

public interface ICollider
{
	/*
	 * Interface to detect and handle collision
	 */
    default boolean collideWith(GameObject gameObject) {return false; }

    default void handleCollision(GameObject gameObject) {}

    Point getCenterPoint();

    int getCollisionRadius();
}
