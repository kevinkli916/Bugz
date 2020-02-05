package com.mycompany.a3.game.model.component;

import com.mycompany.a3.game.model.ICollider;
import com.mycompany.a3.util.Util;

import java.util.ArrayList;

public class Collider
{
    private ArrayList<ICollider> alreadyColliding = new ArrayList<>();
    private ICollider gameObject;

    public Collider(ICollider gameObject)
    {
        this.gameObject = gameObject;
    }

    public boolean collidesWith(ICollider otherObject)
    {
        if (gameObject == otherObject) return false;
        float distanceSquared = Util.distanceSquared(gameObject.getCenterPoint(), otherObject.getCenterPoint());
        float sumOfRadii = gameObject.getCollisionRadius() + otherObject.getCollisionRadius();
        float sumOfRadiiSquared = sumOfRadii * sumOfRadii;

        // Test to see of colliding
        if (distanceSquared <= sumOfRadiiSquared)
        {
            if (alreadyColliding.contains(otherObject))
            {
                return false;
            }
            else
            {
                alreadyColliding.add(otherObject);
                return true;
            }
        }
        else
        {
            alreadyColliding.remove(otherObject);
            return false;
        }
    }
}
