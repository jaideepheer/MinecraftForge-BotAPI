package mod.jd.botapi.Bot.AI.Nodes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark all the Node classes which can be used in an {@link mod.jd.botapi.Bot.AI.Algorithms.Algorithm}.
 * @see Node
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BotAPIAlgorithmNode {
    // The modid of the Mod which introduces this Node.
    String modid();

    // The name of the function that returns a NodeMetaData Object. This function must be a publicly accessible and static function of the class.
    String NodeMetaDataObjectName();
}
