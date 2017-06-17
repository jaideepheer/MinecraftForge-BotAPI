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
