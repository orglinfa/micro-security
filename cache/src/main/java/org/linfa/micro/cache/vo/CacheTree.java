package org.linfa.micro.cache.vo;

import org.linfa.micro.cache.entity.CacheBean;

import java.util.ArrayList;
import java.util.List;

public class CacheTree extends CacheBean{
    private String id;
    private String parentId;
    private String text = null;
    private List<CacheTree> nodes = new ArrayList();

    public CacheTree(CacheBean cache)
    {
        setKey(cache.getKey());
        setDesc(cache.getDesc());
        setExpireTime(cache.getExpireTime());
    }

    public CacheTree() {}

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.text = id;
        this.id = id;
    }

    public String getParentId()
    {
        return this.parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public int hashCode()
    {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        result = 31 * result + (this.parentId == null ? 0 : this.parentId.hashCode());
        return result;
    }

    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CacheTree other = (CacheTree)obj;
        if (this.id == null)
        {
            if (other.id != null) {
                return false;
            }
        }
        else if (!this.id.equals(other.id)) {
            return false;
        }
        if (this.parentId == null)
        {
            if (other.parentId != null) {
                return false;
            }
        }
        else if (!this.parentId.equals(other.parentId)) {
            return false;
        }
        return true;
    }

    public String getText()
    {
        return this.text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public List<CacheTree> getNodes()
    {
        return this.nodes;
    }

    public void setNodes(List<CacheTree> nodes)
    {
        this.nodes = nodes;
    }
}
