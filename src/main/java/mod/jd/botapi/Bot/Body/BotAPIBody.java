package mod.jd.botapi.Bot.Body;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used to mark all the Bodies to be registered in the list of Bodies which is used by the Bot for automatic Body selection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BotAPIBody {}
