package org.linfa.micro.cache.rest;

import org.linfa.micro.cache.service.ICacheManager;
import org.linfa.micro.cache.utils.TreeUtils;
import org.linfa.micro.cache.vo.CacheTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping({"cache"})
public class CacheRest {
    @Autowired
    private ICacheManager cacheManager;

    @RequestMapping({"/list"})
    @ResponseBody
    public List<CacheTree> listAll()
    {
        return TreeUtils.buildTree(this.cacheManager.getAll());
    }

    @RequestMapping(path={"/pre/{pre:.*}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public List<CacheTree> listPre(@PathVariable("pre") String pre)
    {
        return TreeUtils.buildTree(this.cacheManager.getByPre(pre));
    }

    @RequestMapping(path={"/{key:.*}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public String get(@PathVariable("key") String key)
    {
        return this.cacheManager.get(key);
    }

    @RequestMapping(path={"/remove"}, method={org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseBody
    public void removeAll()
    {
        this.cacheManager.removeAll();
    }

    @RequestMapping(path={"/pre/{pre:.*}"}, method={org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseBody
    public void removePre(@PathVariable("pre") String pre)
    {
        this.cacheManager.removeByPre(pre);
    }

    @RequestMapping(path={"/{key:.*}"}, method={org.springframework.web.bind.annotation.RequestMethod.DELETE})
    @ResponseBody
    public void removeKey(@PathVariable("key") String key)
    {
        this.cacheManager.remove(key);
    }

    @RequestMapping(path={"/{key:.*}"}, method={org.springframework.web.bind.annotation.RequestMethod.PUT})
    @ResponseBody
    public void updateTime(@PathVariable("key") String key, int hour)
    {
        this.cacheManager.update(key, hour);
    }

    @RequestMapping({""})
    public String index()
    {
        return "/static/cache/index.html";
    }
}
