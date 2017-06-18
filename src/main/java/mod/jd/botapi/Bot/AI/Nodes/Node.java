/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package mod.jd.botapi.Bot.AI.Nodes;

/**
 * A Node is part of an algorithm which defines its execution and flow.
 */
public interface Node {

    enum NodeType{BLANK,ACTION}

    /**
     * Returns the type of the node.
     * @see NodeType
     * @return NodeType of the node.
     * */
    static NodeType getType()
    {
        return NodeType.BLANK;
    }

    /**
     * Returns the meta data object of the Node.
     */
    NodeMetaData getMetaData();
}

