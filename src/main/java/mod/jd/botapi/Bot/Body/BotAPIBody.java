/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package mod.jd.botapi.Bot.Body;

import java.lang.annotation.*;

/**
 * This annotation is used to mark all the Bodies to be registered in the list of Bodies which is used by the Bot for automatic Body selection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BotAPIBody {}
