package mod.jd.botapi.Bot;

import mod.jd.botapi.Bot.Body.Body;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This is the main Bot class.
 * It manages the Body and the AI, including Script execution and stuff.
 * It creates and manages all the threads for its Body and AI.
 * @see mod.jd.botapi.Bot.Body.Body
 */
public class Bot {
    private static LinkedHashSet<Class<? extends Body>> BodyList = new LinkedHashSet<Class<? extends Body>>();
    private Body botBody;

    /**
     * Registers a Body to its list of available bodies to assign a valid body to an object passed to bind function.
     * @param b : the body class to register to the list of available bodies
     */
    public static void registerBody(Class<? extends Body> b)
    {
        BodyList.add(b);
    }

    /**
     * Binds with the body passed.
     * @param b : the body to bind with.
     */
    public void bind(Body b)
    {
        botBody = b;
    }
    /**
     * Binds the object passed to a compatible body in the list of registered bodies and binds the bot to that body.
     * @param object : the object to bind a body with.
     */
    public <T> void bind(T object)
    {
        botBody = null;
        for(Class<? extends Body> c:BodyList)
        {
            try {
                Body body = null;
                try {
                    body = c.getDeclaredConstructor().newInstance();
                } catch (InvocationTargetException | NoSuchMethodException e) {
                    try {
                        body = c.getDeclaredConstructor(object.getClass()).newInstance();
                    } catch (InvocationTargetException | NoSuchMethodException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
                Set<Class<?>> list = body.getCompatibleClassList();
                for(Class<?> s:list)
                {
                    if(s.isAssignableFrom(object.getClass()))
                    {
                        botBody = body;
                        botBody.bindEntity(object);
                    }
                }
            } catch (IllegalAccessException | InstantiationException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        if(botBody==null)
        {
            ;//Oops no compatible body found ...!
        }
    }
}
