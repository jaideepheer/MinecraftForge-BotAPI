/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package mod.jd.botapi.Bot.AI.Nodes;

public class NodeMetaData
{
    private String name;
    private String description;

    public NodeMetaData(String name,String description)
    {
        this.name = name;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
}
