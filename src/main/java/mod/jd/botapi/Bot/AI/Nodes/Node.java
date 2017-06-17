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

